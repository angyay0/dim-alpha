package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by DiegoArmando on 28/09/2015.
 */
public class WorldSelectScreen  extends GameState {

    private  Button [] btnWorld = new Button[6];
    private float delta = 0;
    private Texture background;
    private Texture back;
    private float dCircular = 220f;

    protected WorldSelectScreen(GameStateManager gsm) {
        super(gsm);
        game.setFlag(false);
        background = new Texture("menu/fondo.png");
        back = new Texture("menu/back.png");

        initButtons();
        for(Button btn: btnWorld) addActor(btn);
    }

    private void initButtons() {
        btnWorld[0]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Cuidad
        btnWorld[1]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Bosque
        btnWorld[2]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Espacio
        btnWorld[3]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //PCH1
        btnWorld[4]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Tierra
        btnWorld[5]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("back")))); //back

        btnWorld[0].setSize(dCircular, dCircular);
        btnWorld[0].setPosition(30f, 175f);
        btnWorld[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Ciudad");
            }
        });

        btnWorld[1].setSize(dCircular, dCircular);
        btnWorld[1].setPosition(290f, 175f);
        btnWorld[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Constants.botones =3;
                gsm.setState(GameStateManager.LEVEL_SELECT);
            }
        });

        btnWorld[2].setSize(dCircular, dCircular);
        btnWorld[2].setPosition(550f, 175f);
        btnWorld[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Espacio");
            }
        });

        btnWorld[3].setSize(dCircular, dCircular);
        btnWorld[3].setPosition(165f, 10f);
        btnWorld[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "PCH1");
            }
        });

        btnWorld[4].setSize(dCircular, dCircular);
        btnWorld[4].setPosition(425f, 10f);
        btnWorld[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Tierra");
            }
        });
        // sb.draw(back, 0f, 410f, 135f, 95f);
        btnWorld[5].setSize(140f, 95f);
        btnWorld[5].setPosition(10f, 380f);
        btnWorld[5].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 gsm.setState(GameStateManager.MENU);
            }
        });
        for (int i = 0; i < btnWorld.length; i++) {
            btnWorld[i].addAction(sequence(alpha(0.5f),delay(.5f),fadeIn(.5f)));
        }
    }

    @Override
    public void disposeState() {
        background.dispose();
        back.dispose();
    }

    @Override
    public void update(float dt) {
        delta = dt;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
            sb.draw(background, 120f, 0f, 600f, 480f);
            font.draw(sb, "Selecciona El Capitulo", 200, 450);
        sb.end();

        super.act(delta);
        super.draw();

    }



}
