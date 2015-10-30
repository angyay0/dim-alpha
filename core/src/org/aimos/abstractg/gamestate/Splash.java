package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;


/**
 * Created by EinarGretch,DiegoBanderas, angyay0 on 18/09/2015.
 */
public class Splash extends GameState{

    private Sprite[] imgs;
    private float x,y;
    private float w,h;
    private float pw,ph,px,py;
    private float aPx, aPy;
    private float elapsed = 0f;
    private float velRot = 0.3f;
    private float delta = 0;


    protected Splash(GameStateManager gsm) {
        super(gsm);
        imgs = new Sprite[]{
        new Sprite(Launcher.res.getTexture("logo_base")), //1-1=0
        new Sprite(Launcher.res.getTexture("logo_hilo")), //2-1=1
        new Sprite(Launcher.res.getTexture("pluma")), //3-1=2
        new Sprite(Launcher.res.getTexture("pluma")), //4-1=3
        new Sprite(Launcher.res.getTexture("pluma")), //5-1=4
        new Sprite(Launcher.res.getTexture("aimos")), //6-1=5
        new Sprite(Launcher.res.getTexture("studio")) //7-1=6
    };


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

        imgs[0].setPosition(x, y);
        imgs[0].setSize(w, h);
        imgs[0].setOriginCenter();

        imgs[1].setPosition(x, y);
        imgs[1].setSize(w, h);
        imgs[1].setOriginCenter();

        imgs[2].setSize(pw, ph);
        imgs[2].setPosition(px, py);
        imgs[2].setOriginCenter();
        imgs[2].setOrigin(imgs[2].getOriginX(), imgs[2].getOriginY() + (ph / 2));

        imgs[3].setSize(pw, ph);
        imgs[3].setPosition(-10 + px + w / 2, py - (h / 2) + 10);
        imgs[3].setOriginCenter();
        imgs[3].setOrigin(imgs[3].getOriginX() + 3, imgs[3].getOriginY() + (ph / 2));

        imgs[4].setSize(pw, ph);
        imgs[4].setPosition(px + 180, py);
        imgs[4].setOriginCenter();
        imgs[4].setOrigin(imgs[4].getOriginX() - 3, imgs[4].getOriginY() + (ph / 2));

        imgs[5].setSize(250f, 80f);
        imgs[5].setPosition(0, 80);

        imgs[6].setSize(250f, 80f);
        imgs[6].setPosition(546, 80);

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
                sb.draw(imgs[2], aPx, aPy, pw, ph);
                sb.draw(imgs[4], aPx + 180, aPy, pw, ph);
                sb.draw(imgs[3], -10 + px + w / 2, aPy - (h / 2) + 10, pw, ph);
                if (aPy <=  py +5) {
                    aPy +=2;
                }else{
                    aPy = py;
                }
            }
            imgs[0].rotate(2);
            imgs[1].rotate(2);

        }

        imgs[0].draw(sb);
        imgs[1].draw(sb);
        if (elapsed > 3) {
            imgs[2].rotate(velRot);
            imgs[3].rotate(velRot);
            imgs[4].rotate(velRot);

            if (imgs[2].getRotation() > 25)
                velRot *= -1;
            else if (imgs[2].getRotation() < -24.9)
                velRot *= -1;

            imgs[2].draw(sb);
            imgs[3].draw(sb);
            imgs[4].draw(sb);
            imgs[5].draw(sb);
            imgs[6].draw(sb);
        }
        sb.end();

        if( elapsed > 7 ){
            gsm.setState(Constants.STATE.MENU);
        }
    }

    @Override
    public void disposeState() {
        Launcher.res.removeTexture("logo_base");
        Launcher.res.removeTexture("logo_hilo");
        Launcher.res.removeTexture("pluma");
        Launcher.res.removeTexture("pluma");
        Launcher.res.removeTexture("pluma");
        Launcher.res.removeTexture("aimos");
        Launcher.res.removeTexture("studio");
    }

    @Override
    public void back() {
    }
}
