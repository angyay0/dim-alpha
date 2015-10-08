package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ShootWeapon extends Weapon {



    /**
     * Default Constructor for Weapon
     *
     * @param bd bonus damage
     * @param m  multiplier
     * @param v  value
     */
    public ShootWeapon(long bd, float m, long v, World w) {
        super(bd, m, v, w);
    }

    @Override
    protected void attackMotion() {
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

        bullet.applyForce(new Vector2((bullet.getMass() * (getBody().getLinearVelocity().x + 30.0f) / (1 / 60.0f)), 0), bullet.getWorldCenter(), true);
    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos.cpy());
        body = world.createBody(bdef);

        //Crear Joint
    }
}
