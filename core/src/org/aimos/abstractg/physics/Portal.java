package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by Angyay0 on 03/11/2015.
 */
public class Portal extends Item {
    public boolean side = true;//false = izq
    private ParticleEffect particle_A,particle_B;

    public Portal(Play p,Vector2 pos,Vector2 partyPos,boolean side) {
        super(p);
        setSprite(new Sprite(new Texture(Gdx.files.internal("data/portal.png"))));
        if( !side )
            getSprite().flip(true,false);

        this.side = side;
        float a = MathUtils.atan2(-partyPos.y, -partyPos.x) * MathUtils.radiansToDegrees;


        particle_A = new ParticleEffect();
        particle_A.load(Gdx.files.internal("data/portal.p"), Gdx.files.internal("data"));
        float scale = particle_A.getEmitters().first().getScale().getHighMax();
        particle_A.getEmitters().first().setPosition(partyPos.x, partyPos.y);
        particle_A.getEmitters().first().getScale().setHigh(scale * 1f);/*
        particle_A.getEmitters().first().getAngle().setHigh( (a+90f) , (a+90f) );*/
        //particle_A.getEmitters().first().getAngle().setLow(a);

        particle_B = new ParticleEffect();
        particle_B.load(Gdx.files.internal("data/particles.p"), Gdx.files.internal("data"));
        scale = particle_B.getEmitters().first().getScale().getHighMax();
        particle_B.getEmitters().first().setPosition(partyPos.x, partyPos.y);
        particle_B.getEmitters().first().getScale().setHigh( scale * 0.70f);/*
        particle_B.getEmitters().first().getAngle().setHigh( (a +90f) , (a+90f) );*/
        //particle_B.getEmitters().first().getAngle().setLow( a );
/**/

        particle_A.start();
        particle_B.start();

        initBody(pos);
    }

    @Override
    protected void createBody(Vector2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x / Constants.PTM, pos.y / Constants.PTM);
        //bodyDef.fixedRotation = true;
        createBody(bodyDef);
/*
        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth() / 2) / Constants.PTM);*/
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2.2f) / Constants.PTM, (getHeight() / 2.2f) / Constants.PTM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = (short) (Constants.BIT.ITEM.BIT() | Constants.BIT.PORTAL.BIT());
        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.CHARACTER.BIT() ); //cambiar por player
        fdef.restitution = 0.4f;

        // create character collision box fixture
        getBody().createFixture(fdef).setUserData(Constants.DATA.PORTAL);
        shape.dispose();

        getBody().setUserData(this);
    }

    @Override
    public void render(SpriteBatch sb){
        super.render(sb);

        sb.begin();
        particle_A.draw(sb,Gdx.graphics.getDeltaTime());
        particle_B.draw(sb,Gdx.graphics.getDeltaTime());
        sb.end();

        if (particle_A.isComplete())
            particle_A.reset();

        if (particle_B.isComplete())
            particle_B.reset();

    }

}
