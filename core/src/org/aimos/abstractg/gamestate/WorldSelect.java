package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by DiegoArmando on 28/09/2015.
 */
public class WorldSelect extends GameState {

    private Button[] btnWorld = new Button[4];
    private float delta = 0;
    private Texture background;
    private Texture back;
    private float dCircular = 220f;

    // Faltan cargar texturas en launcher

    protected WorldSelect(GameStateManager gsm) {
        super(gsm);
        background = Launcher.res.getTexture("fondo");
        back = Launcher.res.getTexture("back");

        initButtons();
        for (Button btn : btnWorld) addActor(btn);


    }

    private void initButtons() {
        btnWorld[0] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("cityIcon")))); //Cuidad
        btnWorld[1] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("forestIcon")))); //Bosque
        btnWorld[2] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("spaceIcon")))); //Espacio
        btnWorld[3] = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("back")))); //back

        //Mundo Marte
        btnWorld[0].setSize(dCircular, dCircular);
        btnWorld[0].setPosition(30f, 130f);
        btnWorld[0].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounceOut))));
        btnWorld[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("tierra");

            }
        });
        //Mundo TIERRA
        btnWorld[1].setSize(dCircular, dCircular);
        btnWorld[1].setPosition(290f, 130f);
        btnWorld[1].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));

        btnWorld[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("marte");
            }
        });

        //Mundo Espacio
        btnWorld[2].setSize(dCircular, dCircular);
        btnWorld[2].setPosition(550f, 130f);
        btnWorld[2].addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounceIn))));
        btnWorld[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("espacio");
            }
        });

        //boton regresar vista
        btnWorld[3].setSize(140f, 95f);
        btnWorld[3].setPosition(10f, 410f);
        btnWorld[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back();
            }
        });
    }
    public void setLevel(String name){
        LevelSelect.setWorld(name);
        gsm.setState(Constants.STATE.LEVEL_SELECT);
    }

    @Override
    public void disposeState() {
    }

    @Override
    public void back() {
        gsm.setState(Constants.STATE.MENU);
    }

    @Override
    public void update(float dt) {
        delta = dt;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(hudCam.combined);

        sb.begin();
        sb.draw(background, 120f, 0f, 600f, 480f);
        font.draw(sb, "Selecciona El Capitulo", 200, 490f);
        sb.end();


        super.act(delta);
        super.draw();


    }


}
