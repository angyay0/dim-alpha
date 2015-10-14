package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import org.aimos.abstractg.core.Launcher;

import org.aimos.abstractg.handlers.BoundedCamera;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public abstract class GameState extends Stage {

    protected GameStateManager gsm;
    protected Launcher game;

    protected SpriteBatch sb;
    protected BoundedCamera cam;
    protected OrthographicCamera hudCam;

    protected BitmapFont font;

    protected GameState(GameStateManager gsm) {
        super( new FillViewport(Launcher.WIDTH, Launcher.HEIGHT, gsm.game().getCamera()) );
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHUDCamera();
        font = game.getFont();
        Gdx.input.setInputProcessor(this);
        /*
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setCatchMenuKey(true);
        */
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
