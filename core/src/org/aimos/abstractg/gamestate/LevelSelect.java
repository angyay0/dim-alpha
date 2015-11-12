package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameConfiguration;

/**
 * Created by DiegoArmando on 19/10/2015.
 */
public class LevelSelect extends GameState {
    //Variable para los botones del menu
    private Button [] btn;
    //variable para el nombre del mundo
    private static String world;
    //variable para la imagen de fondo
    private Texture background;
    //Variables para las posiciones en y de los botones
    private final float diametro = 175f,lh = 175f, lm = 130f, lb = 10,ld = 220;
    //Variables para las posiciones en x de los botones
    float db2, dm = 800/3 -220, db = db2 =800/2 -220;
    //Almacenas los niveles
    private Array<String> levels;
    //Almacena los iconos
    private Array<String> icons;
    //Almacena los mapas
    private Array<String> tmx;

    /**
     *
     * @param gsm
     */
    protected LevelSelect(GameStateManager gsm){
        super(gsm);
        background = Launcher.res.getTexture("fondo");
        JsonIO.ReadJSON(world);
        levels = JsonIO.setLevelName();
        tmx = JsonIO.setTmxName();
        icons=JsonIO.setIconName();
        btn = new Button[levels.size];
        initButtons();
    }

    /**
     * Inicializa los botones en pantalla
     */
    private void initButtons() {
        float  spc = 85 , spc2= 75;
        for (int i=0; i < btn.length;i++){
            final int fi = i;
            btn[i] = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture(icons.get(i)))));
           // btn[i] = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad"))));
            btn[i].setSize(diametro, diametro);
            if(btn.length ==3){
                btn[i].setPosition(dm, lm);
                dm += (diametro +spc);
            }else if(btn.length == 4){
                if(i < 2){
                    btn[i].setPosition(db, ld);
                    db += (diametro +spc);
                }else{
                    btn[i].setPosition(db2, lb);
                    db2 += (diametro +spc);
                }
            }else if(btn.length >= 5){
                if(i <3){
                    btn[i].setPosition(dm, lh);
                    dm += (diametro +spc);
                }else{
                    btn[i].setPosition(db,lb);
                    db +=(diametro+spc2);
                }
            }
            btn[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Launcher.playClick();
                    setLevel(tmx.get(fi));
                }
            });
            addActor(btn[i]);
        }
        Button btn  = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("back")))); //back
        btn.setSize(140f, 95f);
        btn.setPosition(10f, 410f);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Launcher.playClick();
                back();
            }
        });
        addActor(btn);
    }

    /**
     * Establece el nombre del mapa y envia al estado Play
     * @param mapLevel
     */
    public void setLevel(String mapLevel){
        Play.levelSelect(mapLevel);
        gsm.pushState(Constants.STATE.SOLO_PLAY);
    }

    /**
     * Establece el mundo
     * @param w
     */
    public static void setWorld(String w) {
        world = w;
    }

    /**
     *
     * @param dt
     */
    @Override
    public void update(float dt){
        if(!AudioManager.getInstance().isPlaying() && GameConfiguration.getInstance().getMusic()) AudioManager.getInstance().continuarAudio(Launcher.res.getMusic("menu_base"));
    }

    /**
     * Ilumina instrucciones
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background, 120f, 0f, 600f, 480f);
        font.draw(sb, "Click any Level", 260, 490);
        sb.end();
        super.act();
        super.draw();
    }

    /**
     * limpia estado
     */
    @Override
    protected void disposeState() {
        if(AudioManager.getInstance().isPlaying()){
            AudioManager.getInstance().stopAudio();
        }
    }

    /**
     * Retrocede pantalla
     */
    @Override
    public void back() {
        gsm.popState();
    }
}
