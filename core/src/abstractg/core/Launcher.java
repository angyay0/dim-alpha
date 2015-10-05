package abstractg.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import abstractg.gamestate.GameStateManager;
import abstractg.handlers.BoundedCamera;
import abstractg.handlers.Resources;


/**
 * Created by Einargretch,DiegoArmando on 13/09/2015.
 */
public class Launcher extends Game {

    public static final String TITLE = "Pixel Dimensions";
    public static final float WIDTH = 800f;
    public static final float HEIGHT = 512f;
    public static final float STEP = 1 / 60f;

    public static Resources res;

    public SpriteBatch batch;
    private BoundedCamera cam;
    private OrthographicCamera hudCam;

    public GameStateManager manager;

    private BitmapFont font;

    @Override
    public void create () {

        //Debug Logger
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Load Resources
        res = new Resources();
        res.loadAtlas("hero/player.atlas");
        res.loadAtlas("ui/control.atlas");
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

        res.loadTexture("ui/bgcpad.png");
        res.loadTexture("menu/back.png");
        res.loadTexture("menu/pause.png");
        //-------------------------
        res.loadMusic("music/arcade.mp3");
        res.loadMusic("music/field.mp3");
        res.loadMusic("music/city_l2.wav");

        //SpriteBatch
        batch = new SpriteBatch();

        //Cameras
        cam = new BoundedCamera();
        cam.setToOrtho(false, WIDTH, HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, WIDTH, HEIGHT);

        //Fonts
        initFonts();

        //GameStateManager
        manager = new GameStateManager(this);
    }

    @Override
    public void render() {
        super.render();
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
        manager.update(Gdx.graphics.getDeltaTime());
        manager.render();
    }

    @Override
    public void dispose(){
        batch.dispose();
        manager.dispose();
    }

    public void initFonts(){
        //font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/24_LED_Bright.ttf"));
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
}
