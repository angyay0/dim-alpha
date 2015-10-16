package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ThrowWeapon extends Weapon {


    /**
     * Default Constructor for Weapon
     *
     * @param bd        bonus damage
     * @param m         multiplier
     * @param v         value
     * @param w
     * @param spriteSrc
     */
    public ThrowWeapon(long bd, float m, long v, World w, String spriteSrc) {
        super(bd, m, v, w, spriteSrc);
    }

    @Override
    protected void attackMotion() {
        //Eliminar joint
        body.applyForce(new Vector2((body.getMass() * (getBody().getLinearVelocity().x + 3.5f) / (1 / 60.0f)),
                (body.getMass() * (getBody().getLinearVelocity().y + 3.0f) / (1 / 60.0f))), body.getWorldCenter(), true);
        //Concurrencia explosion / regenerar granada
    }

    @Override
    protected final void createBody(Vector2 pos) {
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

        body.setUserData(owner);

        //Crear Joint
    }
}
