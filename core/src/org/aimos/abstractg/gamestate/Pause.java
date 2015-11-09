package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameConfiguration;

/**
 * Created by EinarGretch,DiegoBanderas on 30/09/2015.
 * @version 2.5
 */
public class Pause extends GameState {

    //Variable Game State
    GameState actual;
    //Variable Botones
    private  Button btnPause;
    //Variable Tamaño imagenes
    private float imgCuad = 10f;
    //Variable visualizar menu
    private boolean opt = false;
    //Variable Skin
    private Skin skin;
    //Variable Windows
    Window window;

    /**
     * Menu Pausa dentro del juego
     * @param gsm
     * @param act
     */
    public Pause(GameStateManager gsm, GameState act) {
        super(gsm);
        actual = act;
        createWindowPause();
    }

    /**
     * Menu Opciones, dentro del menu principal
     * @param gsm
     * @param act
     * @param opt
     */
    public Pause(GameStateManager gsm, GameState act,boolean opt) {
        super(gsm);
        actual = act;
        this.opt = opt;
        createWindowPause();
    }

    /**
     * Create ventana pausa o opciones
     */
    private void createWindowPause() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.add("fx_on",  Launcher.res.getTexture("fxOnB"));
        skin.add("fx_off", Launcher.res.getTexture("fxOffB"));
        skin.add("so_on",  Launcher.res.getTexture("soundOnB"));
        skin.add("so_off", Launcher.res.getTexture("soundOffB"));
        skin.add("vi_on",  Launcher.res.getTexture("violenceOnB"));
        skin.add("vi_off", Launcher.res.getTexture("violenceOffB"));



        //boton ocultar ventana
        Button btnhide = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("hideW"))));//Close
        btnhide.setSize(imgCuad, imgCuad);
        btnhide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back();
            }
        });

        TextureRegion reload =new TextureRegion( Launcher.res.getTexture("reload"));
        Button reloadG = new Button(new TextureRegionDrawable( reload ));
        reloadG.setWidth(80f);
        reloadG.setHeight(80f);
        reloadG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(Constants.STATE.SOLO_PLAY);
            }
        });


        //Btones para Opciones o Regresar a menu principal, depende de la pantalla
        if(opt){
            btnPause = new Button(new TextureRegionDrawable(new TextureRegion( Launcher.res.getTexture("creditos")))); //options
            btnPause.setWidth(200f);
            btnPause.setHeight(80f);
            btnPause.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.debug("Evento", "Creditos");
                }
            });
        }else {
            btnPause = new Button(new TextureRegionDrawable(new TextureRegion( Launcher.res.getTexture("home")))); //Home
            btnPause.setSize(imgCuad, imgCuad);
            btnPause.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gsm.doublePopState();
                    //gsm.setState(Constants.STATE.MENU);
                }
            });
        }

        final ImageButton fxText = new ImageButton(skin.getDrawable("fx_on"),skin.getDrawable("fx_off"),skin.getDrawable("fx_off"));
        final ImageButton msText = new ImageButton(skin.getDrawable("so_on"),skin.getDrawable("so_off"),skin.getDrawable("so_off"));
        final ImageButton viText = new ImageButton(skin.getDrawable("vi_on"),skin.getDrawable("vi_off"),skin.getDrawable("vi_off"));


        //boton Violencia y check
        final CheckBox Violencia = new CheckBox("Violencia",skin);
        Violencia.setDisabled(true);
        if(GameConfiguration.getInstance().getFx()) {
            viText.setChecked(false);
            Violencia.setChecked(true);
        }else {
            viText.setChecked(true);
            Violencia.setChecked(false);
        }
        viText.setSize(imgCuad, imgCuad);
        viText.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "violencia");
                if (Violencia.isChecked()) {
                    Violencia.setChecked(false);
                    viText.setChecked(true);
                } else {
                    Violencia.setChecked(true);
                    viText.setChecked(false);
                }
            }
        });

        //boton y check Musica
        final CheckBox Musica = new CheckBox("Musica", skin);
        Musica.setDisabled(true);
        if(AudioManager.getInstance().isPlaying()){
            msText.setChecked(false);
            Musica.setChecked(true);}
        else{
            Musica.setChecked(false);
            msText.setChecked(true);}

        msText.setSize(imgCuad, imgCuad);
        msText.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Musica.isChecked()) {
                    if (AudioManager.getInstance().isPlaying()) {
                        Musica.setChecked(false);
                        AudioManager.getInstance().stopAudio();
                        GameConfiguration.getInstance().saveMusic(false);
                        msText.setChecked(true);
                    }
                } else {
                    if (!AudioManager.getInstance().isPlaying()) {
                        AudioManager.getInstance().play();
                        Musica.setChecked(true);
                        GameConfiguration.getInstance().saveMusic(true);
                        msText.setChecked(false);
                    }
                }
            }
        });

        // boton y check Efecto
        final CheckBox Efecto = new CheckBox("Efectos", skin);
        Efecto.setDisabled(true);
        if(GameConfiguration.getInstance().getFx()) {
            Efecto.setChecked(true);
            fxText.setChecked(false);
        }else{
            Efecto.setChecked(false);
            fxText.setChecked(true);
        }

        fxText.setSize(imgCuad, imgCuad);
        fxText.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Efecto");
                if (Efecto.isChecked()) {
                    Efecto.setChecked(false);
                    fxText.setChecked(true);
                }
                else{
                    Efecto.setChecked(true);
                    fxText.setChecked(false);
                }
            }
        });

        SplitPane botonesAxu = new SplitPane(btnhide,btnPause, false, skin, "default-horizontal");
        //Ventana
        window = new Window(" ", skin);
        //window.setDebug(true);
        window.setMovable(false);
        window.setResizable(true);
        window.setSize(500, 500);
        window.setResizeBorder(4);
        window.setPosition(250, 150);
        window.defaults().spaceBottom(10);
        window.row().fill().expandX().expandY();
        window.add(viText).padLeft(5);
        window.add(msText).padLeft(5);
        window.add(fxText).padLeft(5).padRight(5);
        //window.add(btnPause[3]).padLeft(5).padRight(5);
        window.row();
        window.add(Violencia);
        window.add(Musica);
        window.add(Efecto);
        window.row();
        if(opt){
            window.add(botonesAxu).colspan(4);
        }else {
            window.add(btnhide).padLeft(5);
            window.add(reloadG).padLeft(5);
            window.add(btnPause).padLeft(5).padRight(5);
        }
        window.pack();
        addActor(window);
    }

    /**
     * Limpia graficos en pantalla
     */
    @Override
    protected final void disposeState() {
        Gdx.input.setInputProcessor(actual);
    }

    /**
     * Retrocede Pantalla
     */
    @Override
    public void back() {
        gsm.popState();
    }

    /**
     * actualiza graficos en pantalla
     * @param dt
     */
    @Override
    public void update(float dt) {}

    /**
     * ilumina graficos
     */
    @Override
    public void render() {
        actual.render();
        draw();
        act();
    }
}