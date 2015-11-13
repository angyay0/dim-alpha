package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameConfiguration;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by DiegoArmando on 28/09/2015.
 */
public class WorldSelect extends GameState {
    //Variable para los botones en pantalla
    private Button[] btnWorld = new Button[4];
    //Variable para el fondo
    private Texture background;
    //Variable para la textura del boton regresar
    private Texture back;
    //dimension del circulo
    private float dCircular = 220f;
    private Skin skinLabel;
    private boolean flag = true;

    /**
     * Mentodo constructor donde se realizan las inicializaciones
     * @param gsm
     */
    protected WorldSelect(GameStateManager gsm) {
        super(gsm);
        background = Launcher.res.getTexture("fondo");
        back = Launcher.res.getTexture("back");
        initButtons();
        for (Button btn : btnWorld) addActor(btn);
        skinLabel = new Skin();
        skinLabel.addRegions(Launcher.res.getAtlas("uiskin"));
        skinLabel.add("default-font", font);
        skinLabel.load(Gdx.files.internal("data/uiskin2.json"));

    }

    /**
     * crea los botones a visualizar en pantalla
     */
    private void initButtons() {
        btnWorld[0] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("cityIcon")))); //Cuidad
        btnWorld[1] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("forestIconS")))); //Bosque
        btnWorld[2] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("spaceIconS")))); //Espacio
        btnWorld[3] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("back")))); //back

        //Mundo Marte
        btnWorld[0].setSize(dCircular, dCircular);
        btnWorld[0].setPosition(30f, 130f);
        btnWorld[0].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounceOut))));
        btnWorld[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                setLevel("tierra");

            }
        });
        //Mundo TIERRA
        btnWorld[1].setSize(dCircular, dCircular);
        btnWorld[1].setPosition(290f, 130f);
        btnWorld[1].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));

        btnWorld[1].addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                //Launcher.playClick();
                //setLevel("marte");
                if(flag)
                    csoon();
            }
        });

        //Mundo Espacio
        btnWorld[2].setSize(dCircular, dCircular);
        btnWorld[2].setPosition(550f, 130f);
        btnWorld[2].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounceIn))));
        btnWorld[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // Launcher.playClick();
               //setLevel("espacio");
               if(flag)
                   csoon();

            }
        });

        //boton regresar vista
        btnWorld[3].setSize(140f, 95f);
        btnWorld[3].setPosition(10f, 410f);
        btnWorld[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                back();
            }
        });
    }

    private void csoon() {
        flag = false;
        Label soon = new Label("Proximamente!!!", skinLabel, "default");
        soon.setPosition((getWidth() / 2) - 250, getHeight() + 32);
        soon.addAction(sequence(alpha(0),
                scaleTo(.1f, .1f),
                parallel(fadeIn(1f,
                                Interpolation.pow2),
                        scaleTo(0.9f, 1f, 1.5f,
                                Interpolation.pow5),
                        moveTo(getWidth() / 2 - 150,
                                getHeight() / 2 - 32, 2f,
                                Interpolation.swing)),
                fadeOut(0.95f)));
            addActor(soon);
       flag = true;
    }

    /**
     * Establece la conexion con level select
     * @param name
     */
    public void setLevel(String name){
        LevelSelect.setWorld(name);
        gsm.pushState(Constants.STATE.LEVEL_SELECT);
    }

    /**
     * Limpia pantalla
     */
    @Override
    public void disposeState() {
    }

    /**
     * Retrocede pantalla
     */
    @Override
    public void back() {
        //gsm.setState(Constants.STATE.MENU);
        gsm.popState();
    }

    /**
     * Actualiza graficos
     * @param dt
     */
    @Override
    public void update(float dt) {
        if(!AudioManager.getInstance().isPlaying() && GameConfiguration.getInstance().getMusic()) AudioManager.getInstance().play();
    }

    /**
     * Ilumina graficos
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(hudCam.combined);

        sb.begin();
        sb.draw(background, 120f, 0f, 600f, 480f);
        font.draw(sb, "Selecciona El Capitulo", 200, 490f);
        sb.end();

        super.act();
        super.draw();
    }
}
