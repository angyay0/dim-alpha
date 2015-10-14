package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class MeleeWeapon extends Weapon {


    /**
     * Default Constructor for Weapon
     *
     * @param bd        bonus damage
     * @param m         multiplier
     * @param v         value
     * @param w
     * @param spriteSrc
     */
    public MeleeWeapon(long bd, float m, long v, World w, String spriteSrc) {
        super(bd, m, v, w, spriteSrc);
    }

    @Override
    protected void attackMotion() {

    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos.cpy());

        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getRegionWidth() / 2 / Constants.PTM, sprite.getRegionHeight() / 2 / Constants.PTM);
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;
        fdef.filter.categoryBits = Constants.BIT.SWORD.BIT();
        fdef.filter.maskBits = (short) ( Constants.BIT.CHARACTER.BIT() );
        fdef.restitution = 0.5f;
        body.createFixture(fdef).setUserData(Constants.DATA.ATTACK);

        shape.dispose();

        MassData mass = body.getMassData();
        mass.mass = 0.5f;
        body.setMassData(mass);

        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = body;
        rjd.enableLimit = true;
        rjd.enableMotor = true;
        rjd.maxMotorTorque = 0.5f;
        rjd.lowerAngle = 0f;
        rjd.motorSpeed = 1f;
        rjd.upperAngle = 60f;

        joint = world.createJoint(rjd);
    }

}
