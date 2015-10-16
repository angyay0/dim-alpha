package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
        RevoluteJoint revj = (RevoluteJoint) joint;
        //body.applyTorque(5f,true);
       // body.setTransform( new Vector2(body.getWorldCenter().x, body.getWorldCenter().y), -5f );
        //body.setAngularVelocity(0f);
        //body.setTransform(new Vector2(body.getWorldCenter().x, body.getWorldCenter().y), 45f * MathUtils.radiansToDegrees);
        //body.setTransform( new Vector2(body.getWorldCenter().x , body.getWorldCenter().y), 0.5f * MathUtils.degreesToRadians );
        //body.setAngularVelocity(0.5f);//0.5f,0.5f,body.getWorldCenter().x,body.getWorldCenter().y,true);

        //set the angel of the sword to 12:10 when the hit starts
        body.setTransform(new Vector2(getX(), getY() + (4 / Constants.PTM) ), -10 * MathUtils.degreesToRadians);
        body.applyTorque(1600f, true);
        Gdx.app.debug("Si","LO HAGO");
    }

    @Override
    protected final void createBody(Vector2 pos) {
        Vector2 npos = pos.cpy();
        npos.add( 1f / Constants.PTM ,0 );
        //npos.setAngle(90f);

        Vector2 apos = npos.cpy();
        apos.add(0 , 5 / Constants.PTM);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;

        bdef.position.set(npos);
        //bdef.position.set(pos.add(0, 30 / Constants.PTM));

        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        //shape.setAsBox(sprite.getRegionWidth() / 2 / Constants.PTM, sprite.getRegionHeight() / 2 / Constants.PTM);// mw.getWidth()/4,mw.getHeight()/4
        shape.setAsBox(sprite.getRegionWidth() / (4f * Constants.PTM), sprite.getRegionHeight() / (2f * Constants.PTM) );
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;
        fdef.filter.categoryBits = Constants.BIT.SWORD.BIT();
        fdef.filter.maskBits = (short) ( Constants.BIT.CHARACTER.BIT() );
        fdef.restitution = 0.5f;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(Constants.DATA.ATTACK);

        shape.dispose();

        MassData mass = body.getMassData();
        mass.mass = 0.5f;
        body.setMassData(mass);

        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = body;
        rjd.localAnchorA .set(owner.getBody().getLocalCenter());
        rjd.localAnchorB.set(body.getLocalPoint(apos));
        /*rjd.referenceAngle = 0.49f * MathUtils.PI;
        rjd.upperAngle = 0.50f * MathUtils.PI;
        rjd.lowerAngle = 0f;// * MathUtils.PI;
        //rjd.motorSpeed = 1f;
        rjd.enableLimit = true;
        rjd.maxMotorTorque = 0.5f;
        rjd.motorSpeed = 0f;
        rjd.enableMotor = false;*/

        //rjd.initialize(owner.getBody(),body, pos.add(0 , 0.5f / Constants.PTM));

        joint = world.createJoint(rjd);
    }

}
