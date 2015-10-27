package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.core.Launcher;

/**
 * Created by DiegoArmando on 19/10/2015.
 */
public class LevelSelect extends GameState {

    private Button [] btn;
    private static String wworld;
    private Texture background;
    private final float diametro = 220f,lh = 175f, lm = 130f, lb = 10,ld = 220;
    float db2, dm = 800/3 -220, db = db2 =800/2 -220;
    float delta =0 ;
    private Array<String> levels;
    private Array<String> icons;
    private Array<String> tmx;


    protected LevelSelect(GameStateManager gsm){
        super(gsm);
        game.setFlag(false);
        background = Launcher.res.getTexture("fondo");
        JsonIO.ReadJSON(wworld);
        levels = JsonIO.setLevelName();
        tmx = JsonIO.setTmxName();

        btn = new Button[levels.size];
        initButtons();
    }

    private void initButtons() {

        for (int i=0; i < btn.length;i++){
            final int fi = i;
           // btn[i] = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture(JsonIO.iconName.get(i)))));
            btn[i] = new Button( new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("bgcpad"))));
            btn[i].setSize(diametro, diametro);
            if(btn.length ==3){
                btn[i].setPosition(dm, lm);
                dm += (diametro +30);
            }else if(btn.length == 4){
                if(i < 2){
                    btn[i].setPosition(db, ld);
                    db += (diametro +30);
                }else{
                    btn[i].setPosition(db2, lb);
                    db2 += (diametro +30);
                }
            }else if(btn.length >= 5){
                if(i <3){
                    btn[i].setPosition(dm, lh);
                    dm += (diametro +30);
                }else{
                    btn[i].setPosition(db,lb);
                    db +=(diametro+20);
                }
            }

            btn[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //setLevel("tutorial");
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
                gsm.setState(GameStateManager.WORLD_SELECT);
            }
        });
        addActor(btn);

    }

    public void setLevel(String mapLevel){
        Play.levelSelect(mapLevel);
        gsm.setState(GameStateManager.SOLO_PLAY);
    }
    public static void setWworld(String ww) {
        wworld = ww;
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
        font.draw(sb, "Click any Level", 260, 490);
        sb.end();
        super.act(delta);
        super.draw();
    }


    @Override
    protected void disposeState() {

    }

    @Override
    public void back() {
        gsm.setState(GameStateManager.WORLD_SELECT);
    }
}
