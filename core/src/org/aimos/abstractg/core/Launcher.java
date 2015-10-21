package org.aimos.abstractg.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.aimos.abstractg.gamestate.GameStateManager;
import org.aimos.abstractg.handlers.BoundedCamera;
import org.aimos.abstractg.handlers.Resources;


/**
 * Created by Einargretch,DiegoArmando on 13/09/2015.
 */
public class Launcher extends Game {

    public static final String TITLE = "Dimensions";
    public static final float WIDTH = 800f;
    public static final float HEIGHT = 512f;
    public static final float STEP = 1 / 60f;

    public static Resources res;

    public SpriteBatch batch;
    private BoundedCamera cam;
    private OrthographicCamera hudCam;

    public GameStateManager manager;
    private BitmapFont font;
    public static boolean flag;
    private Viewport viewport;

    @Override
    public void create () {

        //Debug Logger
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Load Resources
        res = new org.aimos.abstractg.handlers.Resources();
        res.loadAtlas("hero/player.atlas");
        res.loadAtlas("control/control.atlas");
        res.loadAtlas("coins/coins.atlas");
        res.loadAtlas("armas/armas.atlas");
        //------------------------
        res.loadTexture("menu/fondo.png");
        res.loadTexture("menu/mask.png");
        res.loadTexture("menu/play.png");
        res.loadTexture("menu/Opciones.png");
        res.loadTexture("menu/Salir.png");

        res.loadTexture("splash/logo_base.png");
        res.loadTexture("splash/logo_hilo.png");
        res.loadTexture("splash/pluma.png");
        res.loadTexture("splash/plumas.png");
        res.loadTexture("splash/aimos.png");
        res.loadTexture("splash/studio.png");

        res.loadTexture("menu/bgcpad.png");
        res.loadTexture("menu/back.png");
        res.loadTexture("menu/pause.png");

        res.loadTexture("menu/circle.png");
        res.loadTexture("menu/bar.png");
        //-------------------------
        res.loadMusic("music/arcade.mp3");
        res.loadMusic("music/field.mp3");
        res.loadMusic("music/city_l2.wav");

        //SpriteBatch
        batch = new SpriteBatch();

        //Cameras
        cam = new BoundedCamera();
        cam.setToOrtho(false,WIDTH,HEIGHT);
        hudCam = new OrthographicCamera();

        viewport = new StretchViewport(800,512,hudCam);
        viewport.apply();
        //hudCam.setToOrtho(false, WIDTH, HEIGHT);

        //Fonts
        initFonts();

        //GameStateManager
        manager = new GameStateManager(this);
    }

    @Override
    public void render() {
        super.render();
        manager.update(Gdx.graphics.getDeltaTime());
        // clear the screen
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        manager.render();
    }

    @Override
    public void dispose(){
        batch.dispose();
        manager.dispose();
    }

    public void initFonts(){
        //font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 48;
        params.shadowOffsetX = 3;
        params.shadowOffsetY = 3;
        params.color = Color.SKY;
        font = generator.generateFont(params);
        generator.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }
    public BoundedCamera getCamera() {
        return cam;
    }
    public OrthographicCamera getHUDCamera() {
        return hudCam;
    }


    @Override
    public void pause() {
        super.pause();
        //gsm.back();
        if(flag){
            if(!manager.isPause()) {
                manager.setTempState();
            }
        }
    }
    public void setFlag(boolean flagP){
        flag = flagP;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        hudCam.position.set(hudCam.viewportWidth/2,hudCam.viewportHeight/2,0);
    }

    public void initSkinLabel(){
        Skin skin = new Skin();
        skin.addRegions(Launcher.res.getAtlas("uiskin"));
        skin.add("default-font", font);
        skin.load(Gdx.files.internal("data/uiskin2.json"));
    }

}
