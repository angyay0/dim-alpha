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
import com.badlogic.gdx.utils.Pool;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.gamestate.Play;
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
     * @param p
     * @param spriteSrc
     */
    public MeleeWeapon(long bd, float m, long v, long a, Play p, String spriteSrc) {
        super(bd, m, v, a, p, spriteSrc);
    }

    @Override
    protected void attackMotion() {
        RevoluteJoint revj = (RevoluteJoint) joint;
        //body.applyTorque(5f,true);
       // body.setTransform( new Vector2(body.getWorldCenter().x, body.getWorldCenter().y), -5f );
        getBody().setAngularVelocity(0f);
        //body.setTransform(new Vector2(body.getWorldCenter().x, body.getWorldCenter().y), 45f * MathUtils.radiansToDegrees);
        getBody().setTransform( new Vector2(getBody().getWorldCenter().x , getBody().getWorldCenter().y), 1f * MathUtils.degreesToRadians );
        //body.setAngularVelocity(0.5f);//0.5f,0.5f,body.getWorldCenter().x,body.getWorldCenter().y,true);
        Gdx.app.debug("Si","LO HAGO");
    }

    @Override
    public Pool<Ammo> getPool() {
        return ammoPool;
    }

    @Override
    protected final void createBody(Vector2 pos) {
        Vector2 npos = pos.cpy();
        npos.set(npos.x*2f,npos.y);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(npos);

        createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        //shape.setAsBox(sprite.getRegionWidth() / 2 / Constants.PTM, sprite.getRegionHeight() / 2 / Constants.PTM);// mw.getWidth()/4,mw.getHeight()/4
        shape.setAsBox(getSprite().getRegionWidth() / (2f * Constants.PTM), getSprite().getRegionHeight() / (2f * Constants.PTM) );
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;
        fdef.filter.categoryBits = Constants.BIT.SWORD.BIT();
        fdef.filter.maskBits = (short) ( Constants.BIT.CHARACTER.BIT() );
        fdef.restitution = 0.5f;
        getBody().createFixture(fdef).setUserData(Constants.DATA.ATTACK);

        shape.dispose();

        MassData mass = getBody().getMassData();
        mass.mass = 0.5f;
        getBody().setMassData(mass);

        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.bodyA = owner.getBody();
        rjd.bodyB = getBody();
        rjd.localAnchorA .set(0.30f,0.23f);
       // rjd.localAnchorB.set(0f,-1f);
        rjd.referenceAngle = 0.49f * MathUtils.PI;
        rjd.upperAngle = 0.50f * MathUtils.PI;
        rjd.lowerAngle = 0f * MathUtils.PI;// * MathUtils.PI;
        //rjd.motorSpeed = 1f;
        rjd.enableLimit = true;
        rjd.maxMotorTorque = -1f;
        rjd.motorSpeed = 0f;
        rjd.enableMotor = false;


        joint = getWorld().createJoint(rjd);
    }

}
