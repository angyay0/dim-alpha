package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by Alumno on 03/11/2015.
 */
public class Portal extends Item {

    public Portal(Play p,Vector2 pos) {
        super(p);
        setSprite(new Sprite(new Texture(Gdx.files.internal("data/portal.png"))));
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

}
