package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.GameConfiguration;

/**
 * Created by EinarGretch,DiegoBanderas on 30/09/2015.
 * @version 2.5
 */
public class Pause extends GameState {

    GameState actual;

    private  Button [] btnPause = new Button[5];
    private float imgCuad = 90f;
    private boolean opt = false;
    private Skin skin;

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

    private void createWindowPause() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        final Label labels;

        //boton ocultar ventana
        Button btnhide = new Button( new TextureRegionDrawable( new TextureRegion( new Texture("menu/hideWin.png"))));//Close
        btnhide.setSize(imgCuad, imgCuad);
        btnhide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.disposeTemp();
            }
        });

        //Btones para Opciones o Regresar a menu principal, depende de la pantalla
        if(opt){
            btnPause[0] = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("menu/creditos.png")))); //options
            btnPause[0].setWidth(200f);
            btnPause[0].setHeight(80f);
            btnPause[0].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.debug("Evento", "Optiones");
                }
            });
            labels = new Label("Continuar  Opciones",skin);
        }else {
            btnPause[0] = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("menu/home2.png")))); //Home
            btnPause[0].setSize(imgCuad, imgCuad);
            btnPause[0].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gsm.backToMenu();
                }
            });
            labels = new Label("Continuar Salir",skin);
        }

        //boton Violencia y check
        final CheckBox Violencia = new CheckBox("Violencia",skin);
        Violencia.setDisabled(true);
        if(GameConfiguration.getInstance().getFx())
            Violencia.setChecked(true);
        else
            Violencia.setChecked(false);
        btnPause[1] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/violenceOff.png"))));//Violence
        btnPause[1].setSize(imgCuad, imgCuad);
        btnPause[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "violencia");
                if (Violencia.isChecked()) {
                    Violencia.setChecked(false);
                } else {
                    Violencia.setChecked(true);
                }
            }
        });

        //boton y check Musica
        final CheckBox Musica = new CheckBox("Musica", skin);
        Musica.setDisabled(true);
        if(AudioManager.getInstance().isPlaying())
            Musica.setChecked(true);
        else
            Musica.setChecked(false);
        btnPause[2] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/soundOff.png"))));//Musica
        btnPause[2].setSize(imgCuad, imgCuad);
        btnPause[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Musica.isChecked()) {
                    if (AudioManager.getInstance().isPlaying()) {
                        Musica.setChecked(false);
                        AudioManager.getInstance().stopAudio();
                        GameConfiguration.getInstance().saveMusic(false);
                    }
                } else {
                    if (!AudioManager.getInstance().isPlaying()) {
                        AudioManager.getInstance().play();
                        Musica.setChecked(true);
                        GameConfiguration.getInstance().saveMusic(true);
                    }
                }
            }
        });

        // boton y check Efecto
        final CheckBox Efecto = new CheckBox("Efectos", skin);
        Efecto.setDisabled(true);
        if(GameConfiguration.getInstance().getFx())
            Efecto.setChecked(true);
        else
            Efecto.setChecked(false);
        btnPause[3] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/fxOff.png"))));//Fx
        btnPause[3].setSize(imgCuad, imgCuad);
        btnPause[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Efecto");
                if (Efecto.isChecked()) Efecto.setChecked(false);
                else Efecto.setChecked(true);
            }
        });


        SplitPane botonesAxu = new SplitPane(btnhide,btnPause[0], false, skin, "default-horizontal");
        //Ventana
        window = new Window(" ", skin);
        window.setDebug(true);
        window.setMovable(false);
        window.setResizable(false);
        window.setPosition(270, 100);
        window.defaults().spaceBottom(10);
        window.row().fill().expandX();
        window.add(btnPause[1]).padLeft(5);
        window.add(btnPause[2]).padLeft(5);
        window.add(btnPause[3]).padLeft(5).padRight(5);
        window.row();
        window.add(Violencia);
        window.add(Musica);
        window.add(Efecto);
        window.row();
        window.add(botonesAxu).colspan(4);
        window.pack();
        addActor(window);

    }

    @Override
    protected final void disposeState() {
        Gdx.input.setInputProcessor(actual);
    }

    @Override
    public void update(float dt) {}

    @Override
    public void render() {
        actual.render();
        draw();
    }
}
