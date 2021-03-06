package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameConfiguration;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;



/**
 * Created by EinarGretch,angyay0,DiegoArmando on 17/09/2015.
 * @version 3.5
 */

public class MainMenu extends GameState{

    /**
     * Metodo constructor
     * @param gsm
     */
    protected MainMenu(final GameStateManager gsm) {
        super(gsm);
        initButtons();
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("menu_base"));
        AudioManager.getInstance().play(0.5f, true);
    }

    /**
     * actualiza graficos en pantalla
     * @param dt
     */
    @Override
    public void update(float dt) {

    }

    /**
     * ilumina graficos en pantalla
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            Gdx.app.exit();
        }
        sb.begin();
            super.draw();
            super.act();
        sb.end();
    }

    /**
     * limpia graficos en pantalla
     */
    @Override
    protected final void disposeState() {
        if(AudioManager.getInstance().isPlaying()){
            AudioManager.getInstance().stopAudio();
        }
    }

    /**
     * retrocede pantalla
     */
    @Override
    public void back() {
        Gdx.app.exit();
    }

    /**
     * Inicializa botones
     */
    public void initButtons(){
        TextureRegion bgRegion = new TextureRegion(Launcher.res.getTexture("fondo"));
        TextureRegion logoRegion = new TextureRegion(Launcher.res.getTexture("mask"));
        TextureRegion startRegion = new TextureRegion(Launcher.res.getTexture("play"));
        TextureRegion optRegion = new TextureRegion(Launcher.res.getTexture("Opciones"));
        TextureRegion exitRegion = new TextureRegion(Launcher.res.getTexture("Salir"));

        Image bg = new Image( new TextureRegionDrawable( bgRegion));
        bg.setWidth(600f);
        bg.setHeight(480f);
        bg.setPosition(120f,0f);


        Image logo = new Image(new TextureRegionDrawable( logoRegion ));
        logo.setWidth(200f);
        logo.setHeight(230f);
        logo.setPosition(305f, 200f);

        final GameStateManager gsmFinal = gsm;
        Button startButton = new Button(new TextureRegionDrawable( startRegion ));
        startButton.setWidth(200f);
        startButton.setHeight(80f);
        startButton.setPosition(300f, 100f);
        startButton.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.swing))));
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                gsmFinal.pushState(Constants.STATE.WORLD_SELECT);
            }
        });

        Button optButton = new Button(new TextureRegionDrawable( optRegion ));
        optButton.setWidth(90f);
        optButton.setHeight(90f);
        optButton.setPosition(5f, 15f);
        optButton.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(+50, 0, 1.5f, Interpolation.pow5Out))));
        optButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                gsmFinal.pushState(Constants.STATE.OPTIONS);
            }
        });

        Button exitButton = new Button(new TextureRegionDrawable( exitRegion ));
        exitButton.setWidth(120f);
        exitButton.setHeight(120f);
        exitButton.setPosition(680f, 15f);
        exitButton.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(-50, 0, 1.5f, Interpolation.pow5Out))));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                back();
            }
        });

        addActor(bg);
        addActor(logo);
        addActor(startButton);
        addActor(optButton);
        addActor(exitButton);
    }

}
