package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.BoundedCamera;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public abstract class GameState extends Stage{

    protected GameStateManager gsm;
    protected Launcher game;

    protected SpriteBatch sb;
    protected BoundedCamera cam;
    protected OrthographicCamera hudCam;

    protected BitmapFont font;

    private Viewport viewport;

    public Constants.STATE id;

    protected GameState(GameStateManager gsm) {

        //super(new FillViewport(800,512, gsm.game().getHUDCamera()));
        super( new StretchViewport(Launcher.WIDTH, Launcher.HEIGHT, gsm.game().getHUDCamera()) );
        //this.stage = new Stage(new FillViewport(Launcher.WIDTH, 512, gsm.game().getCamera()));

        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHUDCamera();
        font = game.getFont();
        /*
        viewport = new StretchViewport(800,512,hudCam);
        viewport.apply();
        setViewport(viewport);
        */

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }

    public abstract void update(float dt);

    public abstract void render();

    @Override
    public void dispose(){
        super.dispose();
        disposeState();
    }

    public GameStateManager getManager(){
        return gsm;
    }

    protected abstract void disposeState();

    public abstract void back();

    public Constants.STATE getID(){    return id;  }

    public GameState setID(Constants.STATE id){ this.id = id; return this;}

}
