package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.character.Character;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ThrowWeapon extends Weapon {

    private Array<Ammo> thrown;

    private static final int EXP_TIME = 30;

    private static final int RECOVERY = 22;

    private static final int SLEEP = 100;

    /**
     * Default Constructor for Weapon
     *
     * @param bd        bonus damage
     * @param m         multiplier
     * @param v         value
     * @param p
     * @param spriteSrc
     */
    public ThrowWeapon(long bd, float m, long v, long a, Play p, String spriteSrc) {
        super(bd, m, v, a, p, spriteSrc);
        thrown = new Array<Ammo>();
        final Weapon tw = this;
        ammoPool = new Pool<Ammo>((int)ammo, (int)maxAmmo) {
            @Override
            protected Ammo newObject() {
                return new Ammo(getPlay(), tw) {
                    @Override
                    protected void extraInit() {
                        setSprite(tw.getSprite());
                        targets = new Array<Character>();
                    }

                    @Override
                    protected void extraDispose() {
                        ((ThrowWeapon)getWeapon()).getThrown().removeValue(this, true);
                    }

                    @Override
                    public void run() {
                        for (int i = 0; i < EXP_TIME; i++) {
                            if(i == RECOVERY) getWeapon().setVisibility(true);
                            try {
                                Thread.sleep(SLEEP);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Explosión");
                        for (Character character : getTargets()) {
                            tw.getOwner().damage(character);
                        }
                        getTargets().clear();
                        getPlay().remove(this);
                    }

                    @Override
                    protected void createBody(Vector2 pos) {
                        BodyDef bdef = new BodyDef();
                        bdef.type = BodyDef.BodyType.DynamicBody;
                        bdef.position.set(pos.cpy());

                        createBody(bdef);
                        FixtureDef fdef = new FixtureDef();

                        CircleShape shape = new CircleShape();
                        shape.setRadius(10f / Constants.PTM);
                        fdef.shape = shape;
                        fdef.friction = 1;
                        fdef.density = 1;
                        fdef.filter.categoryBits = Constants.BIT.GRANADE.BIT();
                        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                                Constants.BIT.BULLET.BIT());
                        fdef.restitution = 0.5f;
                        getBody().createFixture(fdef).setUserData(Constants.DATA.GRANADE);

                        shape.setRadius(100f / Constants.PTM);
                        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                                Constants.BIT.CHARACTER.BIT() | Constants.BIT.BULLET.BIT());
                        fdef.isSensor = true;
                        getBody().createFixture(fdef).setUserData(Constants.DATA.EXPLOSION);

                        shape.dispose();

                        MassData mass = getBody().getMassData();
                        mass.mass = 0.5f;
                        getBody().setMassData(mass);

                        getBody().setUserData(this);
                    }
                };
            }
        };
    }

    @Override
    protected void attackMotion() {
        //Eliminar joint
        /*if(hasJoint()){
            RevoluteJoint revj = (RevoluteJoint) joint;
            world.destroyJoint(revj);
            joint = null;

            body.applyForce(new Vector2((body.getMass() * (getBody().getLinearVelocity().x + 3.5f) / (1 / 60.0f)),
                    (body.getMass() * (getBody().getLinearVelocity().y + 3.0f) / (1 / 60.0f))), body.getWorldCenter(), true);
            //Concurrencia explosion / regenerar granada
        }*/

        if(isVisible() && ammo > 0) {
            Ammo granade = ammoPool.obtain();
            thrown.add(granade);
            granade.initBody(getBody().getPosition());
            Gdx.app.debug("Amount free ammoPool", "" + ammoPool.peak + "-" + ammoPool.max);
            setVisibility(false);
            new Thread(granade).start();
            //Gdx.app.postRunnable(granade);
            granade.getBody().applyForce(new Vector2((granade.getBody().getMass() * (granade.getBody().getLinearVelocity().x + 3.5f) / (1 / 60.0f)),
                    (granade.getBody().getMass() * (granade.getBody().getLinearVelocity().y + 3.0f) / (1 / 60.0f))), granade.getBody().getWorldCenter(), true);
            ammo--;
        }
    }

    @Override
    protected final void createBody(Vector2 pos) {

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos.cpy());
        createBody(bdef);

        MassData m = getBody().getMassData();
        m.mass = -1;
        getBody().setMassData(m);
        getBody().setGravityScale(0);

        //Crear Joint
        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = getBody();
        rjd.localAnchorA .set(0.36f,0.15f);
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

    @Override
    public void draw(SpriteBatch sb) {
        if(hasAmmo()) super.draw(sb);
        for (Ammo granade : thrown) {
            granade.draw(sb);
        }
    }

    public Array<Ammo> getThrown() {
        return thrown;
    }

    /*public class Granade extends Item implements Pool.Poolable {

        private ThrowWeapon weapon;

        public Agranade(World w, ThrowWeapon tw) {
            super(w);
            weapon = tw;
            sprite = this.weapon.getSprite();
        }

        @Override
        protected void createBody(Vector2 pos) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.position.set(pos.cpy());

            body = world.createBody(bdef);
            FixtureDef fdef = new FixtureDef();

            CircleShape shape = new CircleShape();
            shape.setRadius(10f / Constants.PTM);
            fdef.shape = shape;
            fdef.friction = 1;
            fdef.density = 1;
            fdef.filter.categoryBits = Constants.BIT.GRANADE.BIT();
            fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                    Constants.BIT.BULLET.BIT());
            fdef.restitution = 0.5f;
            body.createFixture(fdef).setUserData(Constants.DATA.GRANADE);

            shape.setRadius(100f / Constants.PTM);
            fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() | Constants.BIT.GRANADE.BIT() |
                    Constants.BIT.CHARACTER.BIT() | Constants.BIT.BULLET.BIT());
            fdef.isSensor = true;
            body.createFixture(fdef).setUserData(Constants.DATA.EXPLOSION);

            shape.dispose();

            MassData mass = body.getMassData();
            mass.mass = 0.5f;
            body.setMassData(mass);

            body.setUserData(this);
        }

        public Weapon getWeapon(){
            return weapon;
        }

        @Override
        public void reset() {
            super.dispose();
            weapon.getThrown().removeValue(this, true);
        }

        @Override
        public void dispose() {
            weapon.getPool().free(this);
        }
    }*/
}
