package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Pool;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ShootWeapon extends Weapon {

    private int COOLDOWN = 2200;

    /**
     * Default Constructor for Weapon
     *
     * @param bd        bonus damage
     * @param m         multiplier
     * @param v         value
     * @param p
     * @param spriteSrc
     */
    public ShootWeapon(long bd, float m, long v, long a, Play p, String spriteSrc) {
        super(bd, m, v, a, p, spriteSrc);
        /*ammoPool = new Pool<Bullet>((int)ammo, (int)ammo) {
            @Override
            protected Bullet newObject() {
                Bullet b = new Bullet(world, this);
                return b;
            }
        };*/
        final Weapon tw = this;
        ammoPool = new Pool<Ammo>((int)ammo, (int)maxAmmo) {
            @Override
            protected Ammo newObject() {
                return new Ammo(getPlay(), tw) {
                    @Override
                    protected void extraInit() {}

                    @Override
                    protected void extraDispose() {}

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(COOLDOWN);
                            getWeapon().setVisibility(true);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void createBody(Vector2 pos) {
                        BodyDef bdef = new BodyDef();
                        bdef.type = BodyDef.BodyType.DynamicBody;
                        bdef.bullet = true;
                        bdef.position.set(pos.cpy());

                        createBody(bdef);
                        FixtureDef fdef = new FixtureDef();

                        CircleShape shape = new CircleShape();
                        shape.setRadius(5f / Constants.PTM);
                        fdef.shape = shape;
                        fdef.friction = 0;
                        fdef.density = 1;
                        fdef.filter.categoryBits = Constants.BIT.BULLET.BIT();
                        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                                Constants.BIT.CHARACTER.BIT() | Constants.BIT.BULLET.BIT());
                        fdef.restitution = 0.1f;

                        getBody().createFixture(fdef).setUserData(Constants.DATA.BULLET);
                        shape.dispose();

                        MassData mass = getBody().getMassData();
                        mass.mass = 0.2f;
                        getBody().setMassData(mass);

                        getBody().setUserData(this);
                    }
                };
            }
        };
    }

    @Override
    protected void attackMotion() {
        if (isVisible() && ammo > 0) {
            /*BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.bullet = true;
            bdef.position.set(getPosition().cpy());

            Body bullet = world.createBody(bdef);
            FixtureDef fdef = new FixtureDef();

            CircleShape shape = new CircleShape();
            shape.setRadius(5f / Constants.PTM);
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.density = 1;
            fdef.filter.categoryBits = Constants.BIT.BULLET.BIT();
            fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                    Constants.BIT.CHARACTER.BIT() | Constants.BIT.BULLET.BIT());
            fdef.restitution = 0.1f;

            bullet.createFixture(fdef).setUserData(Constants.DATA.BULLET);
            shape.dispose();

            MassData mass = bullet.getMassData();
            mass.mass = 0.2f;
            bullet.setMassData(mass);

            bullet.setUserData(owner);

            bullet.applyForce(new Vector2((bullet.getMass() * (getBody().getLinearVelocity().x + 30.0f) / (1 / 60.0f)), 0), bullet.getWorldCenter(), true);
            */

            //Bullet bullet = ammoPool.obtain();
            Ammo bullet = ammoPool.obtain();
            bullet.initBody(getBody().getPosition());
            Gdx.app.debug("Amount free ammoPool", "" + ammoPool.peak + "-" + ammoPool.max);
            setVisibility(false);
            new Thread(bullet).start();
            bullet.getBody().applyForce(new Vector2((bullet.getBody().getMass() * (getBody().getLinearVelocity().x + 30.0f) / (1 / 60.0f)), 0), bullet.getBody().getWorldCenter(), true);

            ammo--;
        }
    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos.cpy());
        createBody(bdef);

        //Crear Joint
        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = getBody();
        rjd.localAnchorA.set(0.36f, 0.15f);
        // rjd.localAnchorB.set(0f,-1f);
        rjd.referenceAngle = 0.49f * MathUtils.PI;
        rjd.upperAngle = 0.50f * MathUtils.PI;
        rjd.lowerAngle = 0f * MathUtils.PI;// * MathUtils.PI;
        //rjd.motorSpeed = 1f;
        rjd.enableLimit = true;
        rjd.maxMotorTorque = 0f;
        rjd.motorSpeed = 0f;
        rjd.enableMotor = false;


        joint = getWorld().createJoint(rjd);
    }

    /*public class Bullet extends Item implements Pool.Poolable {

        private ShootWeapon weapon;

        public Bullet(World w, ShootWeapon sw) {
            super(w);
            weapon = sw;
        }

        @Override
        protected void createBody(Vector2 pos) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.bullet = true;
            bdef.position.set(pos.cpy());

            body = world.createBody(bdef);
            FixtureDef fdef = new FixtureDef();

            CircleShape shape = new CircleShape();
            shape.setRadius(5f / Constants.PTM);
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.density = 1;
            fdef.filter.categoryBits = Constants.BIT.BULLET.BIT();
            fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                    Constants.BIT.CHARACTER.BIT() | Constants.BIT.BULLET.BIT());
            fdef.restitution = 0.1f;

            body.createFixture(fdef).setUserData(Constants.DATA.BULLET);
            shape.dispose();

            MassData mass = body.getMassData();
            mass.mass = 0.2f;
            body.setMassData(mass);

            body.setUserData(this);
        }

        public Weapon getWeapon(){
            return weapon;
        }

        @Override
        public void reset() {
            super.dispose();
        }

        @Override
        public void dispose() {
            weapon.getPool().free(this);
        }
    }*/
}
