package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

    //Variable Game State Manager
    protected GameStateManager gsm;
    //Variable Launcher
    protected Launcher game;
    //Variable Sprite Batch
    protected SpriteBatch sb;
    //Variable Bounded Camera
    protected BoundedCamera cam;
    //Variable Orthographic Camera
    protected OrthographicCamera hudCam;
    //Variable para la creacion de letras
    protected BitmapFont font;
    //Variable State
    public Constants.STATE id;

    /**
     *
     * @param gsm
     */
    protected GameState(GameStateManager gsm) {
        super( new StretchViewport(Launcher.WIDTH, Launcher.HEIGHT, gsm.game().getHUDCamera()) );
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHUDCamera();
        font = game.getFont();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK) {
                    back();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * actualizacion de graficos
     * @param dt
     */
    public abstract void update(float dt);

    /**
     * ilumina graficos en pantalla
     */
    public abstract void render();

    /**
     * limpiaa graficos
     */
    @Override
    public void dispose(){
        super.dispose();
        disposeState();
    }


    /**
     *
     * @return GameStateManager
     */
    public GameStateManager getManager(){
        return gsm;
    }


    /**
     * limpiar buffer de pantalla
     */
    protected abstract void disposeState();

    /**
     *
     */
    public abstract void back();

    /**
     *
     * @return int
     */
    public Constants.STATE getID(){    return id;  }

    /**
     *
     * @param id
     * @return GameState
     */
    public GameState setID(Constants.STATE id){ this.id = id; return this;}

}
