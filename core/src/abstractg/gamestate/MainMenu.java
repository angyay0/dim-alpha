package abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import abstractg.core.Launcher;

/**
 * Created by EinarGretch,angyay0 on 17/09/2015.
 */

public class MainMenu extends GameState{

    private Music bgMusic;
    private float dx = 0f;
    private float dy = 0f;

    protected MainMenu(GameStateManager gsm) {
        super(gsm);

        TextureRegion bgRegion = new TextureRegion(Launcher.res.getTexture("fondo"));
        TextureRegion logoRegion = new TextureRegion(Launcher.res.getTexture("mask"));
        TextureRegion startRegion = new TextureRegion(Launcher.res.getTexture("play"));
        TextureRegion optRegion = new TextureRegion(Launcher.res.getTexture("Opciones"));
        TextureRegion exitRegion = new TextureRegion(Launcher.res.getTexture("Salir"));

        Image bg = new Image(new TextureRegionDrawable( bgRegion ));
        bg.setWidth(600f);
        bg.setHeight(480f);
        bg.setPosition(120f, 0f);

        Image logo = new Image(new TextureRegionDrawable( logoRegion ));
        logo.setWidth(200f);
        logo.setHeight(230f);
        logo.setPosition(305f,200f);

        final GameStateManager gsmFinal = gsm;
        Button startButton = new Button(new TextureRegionDrawable( startRegion ));
        startButton.setWidth(200f);
        startButton.setHeight(80f);
        startButton.setPosition(300f,100f);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if( bgMusic.isPlaying() ){
                    bgMusic.stop();
                }
                bgMusic.dispose();
                gsmFinal.setState(GameStateManager.LEVEL_SELECT);
            }
        });

        Button optButton = new Button(new TextureRegionDrawable( optRegion ));
        optButton.setWidth(90f);
        optButton.setHeight(90f);
        optButton.setPosition(5f, 15f);
        optButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Opciones");
            }
        });

        Button exitButton = new Button(new TextureRegionDrawable( exitRegion ));
        exitButton.setWidth(120f);
        exitButton.setHeight(120f);
        exitButton.setPosition(680f,15f);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        addActor(bg);
        addActor(logo);
        addActor(startButton);
        addActor(optButton);
        addActor(exitButton);

        bgMusic = Launcher.res.getMusic("field");
        bgMusic.setVolume(0.5f);
        bgMusic.setLooping(true);

        bgMusic.play();
    }

    @Override
    public void update(float dt) {}

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        draw();
    }

    @Override
    public void disposeState() {
        if(bgMusic.isPlaying()){
            bgMusic.stop();
        }
        bgMusic.dispose();
    }
}
