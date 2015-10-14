package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.aimos.abstractg.core.Launcher;

import org.aimos.abstractg.handlers.BoundedCamera;

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

    protected GameState(GameStateManager gsm) {

        //super(new FillViewport(800,512, gsm.game().getHUDCamera()));
        super( new StretchViewport(Launcher.WIDTH, Launcher.HEIGHT, gsm.game().getHUDCamera()) );
        //viewport = new FitViewport(800, 512, gsm.game().getCamera());
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

    }

    public abstract void update(float dt);
    public abstract void render();

    @Override
    public void dispose(){
        disposeState();
        super.dispose();
    }

    public GameStateManager getManager(){
        return gsm;
    }

    protected abstract void disposeState();


}
