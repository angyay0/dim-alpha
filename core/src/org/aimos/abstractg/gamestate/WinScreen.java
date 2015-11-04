package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by DiegoArmando on 04/11/2015.
 */
public class WinScreen extends GameState {

    GameState actual;

    private Label nivel,comp,score,total;
    private Skin skinLabel;
    private static String map="";
    private static String scor="";
    private static String tot="";
    private static Array<String> maps;
    private ShapeRenderer shapeRenderer;
    private float duration =5f;
    private float timer = 0;
    private float alpha =0;


    protected WinScreen(GameStateManager gsm, GameState act) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        actual = act;
        initSkin();
        initLabel();
        initButton();
    }

    private void initButton() {
        TextureRegion reload = new TextureRegion(Launcher.res.getTexture("reload"));
        Button reloadG = new Button(new TextureRegionDrawable(reload));
        reloadG.setWidth(80f);
        reloadG.setHeight(80f);
        reloadG.setPosition(215f, 20f);
        reloadG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(Constants.STATE.SOLO_PLAY);
            }
        });
        TextureRegion home = new TextureRegion(Launcher.res.getTexture("home2"));
        Button homeb = new Button(new TextureRegionDrawable(home));
        homeb.setWidth(80f);
        homeb.setHeight(80f);
        homeb.setPosition(310f, 20f);
        homeb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back();
            }
        });

        TextureRegion shareI = new TextureRegion(Launcher.res.getTexture("share"));
        Button shareB = new Button(new TextureRegionDrawable(shareI));
        shareB.setWidth(80f);
        shareB.setHeight(80f);
        shareB.setPosition(420f, 20f);
        shareB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pushState(Constants.STATE.MENU);
            }
        });

        TextureRegion nextI = new TextureRegion(Launcher.res.getTexture("next"));
        Button nextB = new Button(new TextureRegionDrawable(nextI));
        nextB.setWidth(80f);
        nextB.setHeight(80f);
        nextB.setPosition(520f, 20f);
        nextB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //gsm.pushState(Constants.STATE.MENU);
                nextLevel(map);
            }
        });

        addActor(reloadG);
        addActor(homeb);
        addActor(shareB);
        addActor(nextB);
    }

    private void nextLevel(String mapita) {
        if(maps.get(0) == mapita) {
            Play.levelSelect(maps.get(1));
            gsm.pushState(Constants.STATE.SOLO_PLAY);
        }else {
            for(int i=1;i<maps.size;i++){
                if(maps.get(i) == mapita && i < maps.size-1){
                    Play.levelSelect(maps.get(i+1));
                    gsm.pushState(Constants.STATE.SOLO_PLAY);
                }else{
                    gsm.pushState(Constants.STATE.WORLD_SELECT);
                }
            }
        }
        /*for(int i=1;i<maps.size;i++){
            if(maps.get(0) == mapita){
                Play.levelSelect(maps.get(1));
                gsm.pushState(Constants.STATE.SOLO_PLAY);
            }else if(maps.get(i) == mapita && i < maps.size-1){
                Play.levelSelect(maps.get(i+1));
                gsm.pushState(Constants.STATE.SOLO_PLAY);
            }else{
                gsm.pushState(Constants.STATE.WORLD_SELECT);
            }
        }*/

    }

    private void initLabel(){
        nivel = new Label("Nivel "+map, skinLabel, "default");
        comp  = new Label("COMPLETO!!", skinLabel, "default");
        score = new Label("Score: "+scor+"/500", skinLabel, "default");
        total = new Label("Total: "+tot, skinLabel, "default");


        nivel.setPosition(270, 450);
        nivel.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));
        nivel.setAlignment(Align.left);

        comp.setPosition(270, 350);
        comp.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));
        comp.setAlignment(Align.left);

        score.setPosition(220, 250);
        score.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));
        score.setAlignment(Align.center);

        total.setPosition(220, 150);
        total.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, 1.5f, Interpolation.bounce))));
        total.setAlignment(Align.center);

        addActor(nivel);
        addActor(comp);
        addActor(score);
        addActor(total);
    }

    private void initSkin() {
        skinLabel = new Skin();
        skinLabel.addRegions(Launcher.res.getAtlas("uiskin"));
        skinLabel.add("default-font", font);
        skinLabel.load(Gdx.files.internal("data/uiskin2.json"));
    }

    public static void settter(String total, String score, String mapa, Array<String> tmxName) {
        tot = total;
        scor = score;
        map = mapa;
        maps=tmxName;
    }

    @Override
    public void update(float dt) {
        timer += dt;
        alpha = 1 - (timer - duration/2) * (timer - duration/2) / (duration/2);
    }


    @Override
    public void render() {
        actual.render();
        /*Gdx.gl.glClearColor(200, 0, 0, 0.3);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        */

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.setColor(0, 0, 0, alpha);
        shapeRenderer.rect(200, 10, 420, 460);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        draw();
        act();
    }

    @Override
    protected void disposeState() {

    }

    @Override
    public void back() {
        gsm.pushState(Constants.STATE.MENU);
    }
}
