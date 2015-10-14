package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by DiegoArmando on 07/10/2015.
 */
public class LevelSelectScreen extends GameState{

    Button [] buttonsLevel;

    private float delta = 0;
    private Texture background;
    private Texture back;
    private float dCircular = 220f;


    protected LevelSelectScreen(GameStateManager gsm) {
        super(gsm);
        game.setFlag(false);
        background = new Texture("menu/fondo.png");
        back = new Texture("menu/back.png");

        initButtons();

        for(Button btn: buttonsLevel) addActor(btn);

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
            font.draw(sb, "Click any Level", 200, 400);
            font.draw(sb, "Tutorial", 60f, 110f);
            font.draw(sb, "Ciudad 1", 320, 110f);
            font.draw(sb, "Ciudad 2", 520, 110f);
        sb.end();
        super.act(delta);
        super.draw();
    }

    @Override
    protected void disposeState() {
        background.dispose();
        back.dispose();
    }


    public void initButtons(){
        buttonsLevel = new Button[3];
        buttonsLevel[0]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Level
        buttonsLevel[1]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Level
        buttonsLevel[2]  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad")))); //Level
        Button btn  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("back")))); //back

        buttonsLevel[0].setSize(dCircular, dCircular);
        buttonsLevel[0].setPosition(50f, 100f);
        buttonsLevel[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("tutorial");

            }
        });

        buttonsLevel[1].setSize(dCircular, dCircular);
        buttonsLevel[1].setPosition(300f, 100f);
        buttonsLevel[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("cityla");
            }
        });


        buttonsLevel[2].setSize(dCircular, dCircular);
        buttonsLevel[2].setPosition(550f, 100f);
        buttonsLevel[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevel("citylb");
            }
        });

        btn.setSize(140f, 95f);
        btn.setPosition(10f, 380f);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(GameStateManager.MENU);
            }
        });
        addActor(btn);
    }


    public void setLevel(String mapLevel){
        Play.levelSelect(mapLevel);
        gsm.setState(GameStateManager.SOLO_PLAY);
    }
}
