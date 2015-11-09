package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    //Variable Game Stae
    GameState actual;
    //Variables para labels
    private Label nivel,comp,score,total;
    //Variable para Skin
    private Skin skinLabel;
    //Variable para guardar nomnre de mapa
    private static String map="";
    //VAriabla para almacenar puntuacion de mapa
    private static String scor="";
    //Variable para visualizar total de puntuacion
    private static String tot="";
    //Variable para almacenar los nombre de los mapas
    private static Array<String> maps;
    //Variable para el fondo de pantalla
    private ShapeRenderer shapeRenderer;


    /**
     * Metodo constructor donde se realizan las inicializaciones
     * @param gsm
     * @param act
     */
    protected WinScreen(GameStateManager gsm, GameState act) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        actual = act;
        initSkin();
        initLabel();
        initButton();
    }

    /**
     * crea los botones a visualizar en pantalla
     */
    private void initButton() {
        TextureRegion reload = new TextureRegion(Launcher.res.getTexture("reload"));
        Button reloadG = new Button(new TextureRegionDrawable(reload));
        reloadG.setWidth(80f);
        reloadG.setHeight(80f);
        reloadG.setPosition(215f, 20f);
        reloadG.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.popAndSetState(Constants.STATE.SOLO_PLAY);
            }
        });
        TextureRegion home = new TextureRegion(Launcher.res.getTexture("home"));
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
                //gsm.pushState(Constants.STATE.MENU);
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

    /**
     * Aayuda cambiar de nivel o regresar a menu
     * @param mapita
     */
    private void nextLevel(String mapita) {
        for(int i=0;i<maps.size-1;i++){
            if(maps.get(i).equals(mapita)){
                Play.levelSelect(maps.get(i+1));
                gsm.popAndSetState(Constants.STATE.SOLO_PLAY);
                return;
            }
        }
        gsm.doublePopState();
    }

    /**
     * crea labels con los datos previamente establecidos
     */
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

    /**
     * crea el skin para los label
     */
    private void initSkin() {
        skinLabel = new Skin();
        skinLabel.addRegions(Launcher.res.getAtlas("uiskin"));
        skinLabel.add("default-font", font);
        skinLabel.load(Gdx.files.internal("data/uiskin2.json"));
    }

    /**
     * Establece los datos de partida a visualizar en pantalla
     * @param total
     * @param score
     * @param mapa
     * @param tmxName
     */
    public static void settter(String total, String score, String mapa, Array<String> tmxName) {
        tot = total;
        scor = score;
        map = mapa;
        maps=tmxName;
    }

    /**
     * actualiza graficos en pantalla
     * @param dt
     */
    @Override
    public void update(float dt) {}


    /**
     * Ilumina graficos en pantalla
     */
    @Override
    public void render() {
        actual.render();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0,0,0, 0.5f));
        shapeRenderer.rect(0f,0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        draw();
        act();
    }

    /**
     * Elimina graficos
     */
    @Override
    protected void disposeState() {

    }

    /**
     * reteocede pantalla
     */
    @Override
    public void back() {
        gsm.doublePopState();
    }
}
