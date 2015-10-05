package abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import abstractg.core.Launcher;


/**
 * Created by EinarGretch,DiegoBanderas, angyay0 on 18/09/2015.
 */
public class Splash extends GameState{

    private Array<Sprite> imgs;
    private float x,y;
    private float w,h;
    private float pw,ph,px,py;
    private float aPx, aPy;
    private float elapsed = 0f;
    private float velRot = 0.3f;
    private float delta = 0;


    protected Splash(GameStateManager gsm) {
        super(gsm);
        imgs = new Array<Sprite>();
        imgs.add( new Sprite( Launcher.res.getTexture("logo_base") ) );  //1-1=0
        imgs.add( new Sprite( Launcher.res.getTexture("logo_hilo") ) ); //2-1=1
        imgs.add( new Sprite( Launcher.res.getTexture("pluma") ) ); //3-1=2
        imgs.add( new Sprite( Launcher.res.getTexture("pluma") ) ); //4-1=3
        imgs.add( new Sprite( Launcher.res.getTexture("pluma") ) ); //5-1=4
        imgs.add( new Sprite( Launcher.res.getTexture("aimos") ) ); //6-1=5
        imgs.add( new Sprite( Launcher.res.getTexture("studio") ) ); //7-1=6


        x = 308;
        y = 265;
        w = 192;
        h = 190;
        pw = 45;
        ph = 220;

        py = 150;
        px = 290;
        aPx = 290;
        aPy = 0;

        imgs.get(0).setPosition(x, y);
        imgs.get(0).setSize(w, h);
        imgs.get(0).setOriginCenter();

        imgs.get(1).setPosition(x, y);
        imgs.get(1).setSize(w, h);
        imgs.get(1).setOriginCenter();

        imgs.get(2).setSize(pw, ph);
        imgs.get(2).setPosition(px, py);
        imgs.get(2).setOriginCenter();
        imgs.get(2).setOrigin(imgs.get(2).getOriginX(), imgs.get(2).getOriginY() + (ph / 2));

        imgs.get(3).setSize(pw, ph);
        imgs.get(3).setPosition(-10 + px + w / 2, py - (h / 2) + 10);
        imgs.get(3).setOriginCenter();
        imgs.get(3).setOrigin(imgs.get(3).getOriginX() + 3, imgs.get(3).getOriginY() + (ph / 2));

        imgs.get(4).setSize(pw, ph);
        imgs.get(4).setPosition(px + 180, py);
        imgs.get(4).setOriginCenter();
        imgs.get(4).setOrigin(imgs.get(4).getOriginX() - 3, imgs.get(4).getOriginY() + (ph / 2));

        imgs.get(5).setSize(250f, 80f);
        imgs.get(5).setPosition(0, 80);

        imgs.get(6).setSize(250f, 80f);
        imgs.get(6).setPosition(546, 80);

    }

    @Override
    public void update(float dt) {
        delta = dt;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsed += delta;
        update(delta);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        if( elapsed < 3 ){
            if (elapsed > 1 ) {
                sb.draw(imgs.get(2), aPx, aPy, pw, ph);
                sb.draw(imgs.get(4), aPx + 180, aPy, pw, ph);
                sb.draw(imgs.get(3), -10 + px + w / 2, aPy - (h / 2) + 10, pw, ph);
                if (aPy <=  py +5) {
                    aPy +=2;
                }else{
                    aPy = py;
                }
            }
            imgs.get(0).rotate(2);
            imgs.get(1).rotate(2);

        }

        imgs.get(0).draw(sb);
        imgs.get(1).draw(sb);
        if (elapsed > 3) {
            imgs.get(2).rotate(velRot);
            imgs.get(3).rotate(velRot);
            imgs.get(4).rotate(velRot);

            if (imgs.get(2).getRotation() > 25)
                velRot *= -1;
            else if (imgs.get(2).getRotation() < -24.9)
                velRot *= -1;

            imgs.get(2).draw(sb);
            imgs.get(3).draw(sb);
            imgs.get(4).draw(sb);
            imgs.get(5).draw(sb);
            imgs.get(6).draw(sb);
        }
        sb.end();

        if( elapsed > 7 ){
            gsm.setState(GameStateManager.MENU);
        }
    }

    @Override
    public void disposeState() {
        for(int i=0;i<imgs.size;i++)
            imgs.pop().getTexture().dispose();
    }
}
