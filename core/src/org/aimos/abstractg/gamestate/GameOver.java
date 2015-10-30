package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

/**
 * Created by Herialvaro on 15/10/2015.
 */
public class GameOver extends GameState{

GameStateManager gsm;
    BitmapFont go;
    long p;
    protected GameOver(GameStateManager gsm,long p) {
        super(gsm);
        this.gsm = gsm;
        this.p = p;
        initButtons();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        font.draw(sb, "Game Over", 280, 400);
        font.draw(sb,"Score: " + p,310,300);
        font.draw(sb,"Share ",310,80);
        sb.end();
        super.draw();
        super.act();


    }

    @Override
    protected void disposeState() {
    }

    @Override
    public void back() {

    }


    public void  initButtons(){
        TextureRegion reload =new TextureRegion( Launcher.res.getTexture("reload"));
        Button reloadG = new Button(new TextureRegionDrawable( reload ));
            reloadG.setWidth(80f);
            reloadG.setHeight(80f);
            reloadG.setPosition(310f, 150f);
            reloadG.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gsm.reloadGame();
                }
            });
        TextureRegion home =new TextureRegion( Launcher.res.getTexture("home2"));
        Button homeb = new Button(new TextureRegionDrawable( home ));
        homeb.setWidth(80f);
        homeb.setHeight(80f);
        homeb.setPosition(410f, 150f);
        homeb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pushState(Constants.STATE.MENU);
            }
        });

        TextureRegion shareI =new TextureRegion( Launcher.res.getTexture("share"));
        Button shareB = new Button(new TextureRegionDrawable( shareI ));
        shareB.setWidth(80f);
        shareB.setHeight(80f);
        shareB.setPosition(450f, 25f);
        shareB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.pushState(Constants.STATE.MENU);
            }
        });
            addActor(reloadG);
            addActor(homeb);
            addActor(shareB);

        }
}
