package org.aimos.abstractg.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.aimos.abstractg.gamestate.GameStateManager;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.BoundedCamera;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.Resources;
import org.aimos.abstractg.handlers.SocialMedia;


/**
 * Created by Einargretch,DiegoArmando on 13/09/2015.
 */
public class Launcher extends Game {

    //Ancho de la Pantalla
    public static final float WIDTH = 800f;
    //Alto de la Pantalla
    public static final float HEIGHT = 512f;
    //Variable para los segundos
    public static final float STEP = 1 / 60f;
    //Variable de para almacenar los recursos del juego
    public static Resources res;
    //Variable Social Media
    public static SocialMedia socialMedia;
    //Variable SpriteBatch
    public SpriteBatch batch;
    //Variable camara 1
    private BoundedCamera cam;
    //Varibale Camara 2
    private OrthographicCamera hudCam;
    //Variable Game State Manager
    public GameStateManager manager;
    //Variable para las letras
    private BitmapFont font;
    //variable viewport
    private Viewport viewport;

    /**
     *Carga los recursos para el juego
     */
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
        res.loadAtlas("data/uiskin.atlas");
        //res.loadAtlas("player/player1.atlas");
        //Menu HOME
        res.loadTexture("menu/fondo.png");
        res.loadTexture("menu/mask.png");
        res.loadTexture("menu/play.png");
        res.loadTexture("menu/Opciones.png");
        res.loadTexture("menu/Salir.png");
        res.loadTexture("menu/reload.png");
        //SPLASH
        res.loadTexture("splash/logo_base.png");
        res.loadTexture("splash/logo_hilo.png");
        res.loadTexture("splash/pluma.png");
        res.loadTexture("splash/plumas.png");
        res.loadTexture("splash/aimos.png");
        res.loadTexture("splash/studio.png");
        //
        res.loadTexture("menu/bgcpad.png");
        res.loadTexture("menu/circle.png");
        res.loadTexture("menu/bar.png");
        /*menu pausa, Game Over, Winner*/
        res.loadTexture("menu/back.png");
        res.loadTexture("menu/pause.png");


        /*MENU*/
        res.loadTexture("menu/home.png");
        res.loadTexture("menu/prba.png");
        res.loadTexture("menu/hideW.png");
        res.loadTexture("menu/fxOnB.png");
        res.loadTexture("menu/fxOffB.png");
        res.loadTexture("menu/soundOnB.png");
        res.loadTexture("menu/soundOffB.png");
        res.loadTexture("menu/violenceOnB.png");
        res.loadTexture("menu/violenceOffB.png");
        res.loadTexture("menu/creditos.png");
        res.loadTexture("menu/share.png");
        res.loadTexture("menu/next.png");
        res.loadTexture("data/cityIcon.png");
        res.loadTexture("data/forestIcon.png");
        res.loadTexture("data/spaceIcon.png");
        res.loadTexture("data/num1.png");
        res.loadTexture("data/num2.png");
        res.loadTexture("data/num3.png");
        res.loadTexture("data/num4.png");
        res.loadTexture("data/num5.png");


        //Musica
        res.loadMusic("music/menu_base.mp3");
        res.loadMusic("music/cap1.mp3");
        //SpriteBatch
        batch = new SpriteBatch();
        //Cameras
        cam = new BoundedCamera();
        cam.setToOrtho(false,WIDTH,HEIGHT);
        hudCam = new OrthographicCamera();

        viewport = new StretchViewport(800,512,hudCam);
        viewport.apply();
        //Fonts
        initFonts();
        //GameStateManager
        manager = new GameStateManager(this);
    }

    /**
     * ilumina graficos
     */
    @Override
    public void render() {
        super.render();
        manager.update(Gdx.graphics.getDeltaTime());
        //Gdx.graphics.getGL20().glClearColor(0.7f, 0.7f, 1.0f, 1);
        //Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        manager.render();
    }

    /**
     * Limpia el buffer
     */
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
        manager.dispose();
    }

    /**
     * metodo donde se inicializa las letras.
     */
    public void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 48;
        params.shadowOffsetX = 3;
        params.shadowOffsetY = 3;
        params.magFilter = Texture.TextureFilter.Linear;
        params.minFilter = Texture.TextureFilter.Linear;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
        generator.dispose();
    }

    /**
     * @return el tipo de letras
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * @return batch
     */
    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    /**
     * @return bounded camera
     */
    public BoundedCamera getCamera() {
        return cam;
    }

    /**
     * @return orthographic camera
     */
    public OrthographicCamera getHUDCamera() {
        return hudCam;
    }

    /**
     * envia a estado pausa
     */
    @Override
    public void pause() {
        super.pause();
        if(manager.getState() instanceof Play){
            manager.pushState(Constants.STATE.PAUSE);
        }
    }

    /**
     * redimensiona las dimensiones de las pantallas de los dispositivos
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hudCam.position.set(hudCam.viewportWidth/2,hudCam.viewportHeight/2,0);
    }
}
