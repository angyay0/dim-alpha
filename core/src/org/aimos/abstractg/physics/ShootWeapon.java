package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ShootWeapon extends Weapon {

    //private Pool<Bullet> bullets;

    private int ammo = 7;

    /**
     * Default Constructor for Weapon
     *
     * @param bd        bonus damage
     * @param m         multiplier
     * @param v         value
     * @param w
     * @param spriteSrc
     */
    public ShootWeapon(long bd, float m, long v, World w, String spriteSrc) {
        super(bd, m, v, w, spriteSrc);

        /*bullets = new Pool<Bullet>(ammo) {
            @Override
            protected Bullet newObject() {
                Bullet b = new Bullet(world,bullets);
                b.createBody(getBody().getPosition());
                return b;
            }
        };*/
    }

    @Override
    protected void attackMotion() {
        if (ammo > 0) {
            BodyDef bdef = new BodyDef();
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
            ammo--;
        }
        /*Bullet bullet = bullets.obtain();
        Gdx.app.debug("Amount free bullets",""+bullets.max+"-"+bullets.peak);
        bullet.getBody().applyForce(new Vector2((bullet.getBody().getMass() * (getBody().getLinearVelocity().x + 30.0f) / (1 / 60.0f)), 0), bullet.getBody().getWorldCenter(), true);
        */

    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos.cpy());
        body = world.createBody(bdef);

        //Crear Joint
        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = body;
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


        joint = world.createJoint(rjd);
    }

    /*public class Bullet extends Item implements Pool.Poolable {
        private Pool pool;

        public Bullet(World w,Pool pool) {
            super(w);
            this.pool = pool;
        }

        @Override
        protected void createBody(Vector2 pos) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.bullet = true;
            bdef.position.set(pos.cpy());

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
            body = bullet;
        }

        @Override
        public void reset() {

        }
    }*/
}
