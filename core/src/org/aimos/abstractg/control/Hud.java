package org.aimos.abstractg.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.gamestate.Play;

import java.util.List;

/**
 * Created by EinarGretch,Angyay0 on 09/09/2015.
 */
public class Hud{

    //Default Buttons Name With Atlas file
    //Salto
    private static final String JUMP = "jump";
    //Ataque Melee
    private static final String MELEE = "punch";
    //Ataque disparo
    private static final String SHOOT = "shoot";
    //Ataque granada
    private static final String THROW = "granade";
    //Pad BG
    private static final String BGPAD = "bgpad";
    //Pad Circle
    private static final String PAD = "circle";
    //Default Buttons Name With Atlas file
    //MARGEN (Tile)
    private static final float CTRL_MARGIN = 50f;
    //Square Size for circle pad
    private static final float CTRL_SIZE = 160f;

    //Lista con los botones del control
    private List<Button> buttons;
    //coordenadas para eventos del control
    private int posX1, posX2, posY1, posY2;

    //Tamaño de las imagenes
    private float SQUARE = 50f;


    public Hud(final Play play) {
        super();
        TextureAtlas atlas = Launcher.res.getAtlas("control");
        AtlasRegion jumpRegion = atlas.findRegion(JUMP);
        AtlasRegion bgpadRegion = atlas.findRegion(BGPAD);
        AtlasRegion padRegion = atlas.findRegion(PAD);


        Button jumpButton = new Button(new TextureRegionDrawable(jumpRegion));
        jumpButton.setSize(90f, 90f);
        jumpButton.setPosition(Launcher.WIDTH - jumpRegion.getRegionWidth(),
                CTRL_MARGIN);
        jumpButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                play.getPlayer().jump();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

            }
        });

        final Image bgPad = new Image(new TextureRegionDrawable(bgpadRegion));
        bgPad.setSize(CTRL_SIZE, CTRL_SIZE);
        bgPad.setPosition(0, 0);
        bgPad.setVisible(false);

        final Image pad = new Image(new TextureRegionDrawable(padRegion));
        pad.setSize(CTRL_SIZE/2, CTRL_SIZE/2);
        pad.setPosition(0, 0);
        pad.setVisible(false);

        Actor movementCross = new Actor();
        movementCross.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (5 / 4));
        movementCross.setPosition(0, 0);
        movementCross.addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                posX1 = (int) x;
                posY1 = (int) y;
                bgPad.setPosition(posX1 - (bgPad.getWidth() / 2), posY1 - (bgPad.getHeight() / 2));
                pad.setPosition(posX1 - (pad.getWidth() / 2), posY1 - (pad.getHeight() / 2));
                bgPad.setVisible(true);
                pad.setVisible(true);
                play.getPlayer().setWalking(true);
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                posX2 = (int) x - posX1;
                posY2 = (int) y - posY1;

                if (posX2 > 40) {//derecha
                    play.getPlayer().move(true);
                } else if (posX2 < -40) {//izquierda
                    play.getPlayer().move(false);
                }
                //System.out.println(posX2);
                if (posY2 < -20) {//agacharse
                    if (play.getPlayer().isCrouching()) {
                        //interactuar suelo
                    } else {
                        play.getPlayer().setCrouching(true);
                    }
                } else if (posY2 > 20) {//mirar arriba
                    //interactuar escaleras
                }


                pad.setPosition(x - (pad.getWidth() / 2), y - (pad.getHeight() / 2));
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                float velX = -play.getPlayer().getBody().getLinearVelocity().x * .96f;
                float velY = -play.getPlayer().getBody().getLinearVelocity().y * .96f;
                float forceX = (float) (play.getPlayer().getBody().getMass() * velX / (1 / 60.0)); // f = mv/t
                float forceY = (float) (play.getPlayer().getBody().getMass() * velY / (1 / 60.0)); // f = mv/t

                if (play.getPlayer().getBody().getPosition().x <= 1.15) {
                    forceX = 0;
                }

                play.getPlayer().getBody().applyForce(new Vector2(forceX, forceY), play.getPlayer().getBody().getWorldCenter(), true);
                bgPad.setVisible(false);
                pad.setVisible(false);
                bgPad.setPosition(0, 0);
                pad.setPosition(0, 0);
                play.getPlayer().setWalking(false);
                if (play.getPlayer().isCrouching() && !play.getPlayer().isForceCrouched()) {
                    play.getPlayer().setCrouching(false);
                    System.out.println("levantarse");
                }

            }
        });

        Button pause = new Button(new TextureRegionDrawable( new TextureRegion( Launcher.res.getTexture("pause"))));//pause
        pause.setSize(SQUARE, SQUARE);
        pause.setPosition(375f, 410f);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play.getManager().setTempState();
            }
        });
        Image cir = new Image(Launcher.res.getTexture("circle"));
        cir.setSize(SQUARE, SQUARE);
        cir.setPosition(10f, 410f);
        Image bar = new Image(Launcher.res.getTexture("bar"));
        bar.setSize(250, SQUARE);
        bar.setPosition(35f, 410f);


        play.addActor(bgPad);
        play.addActor(pad);
        play.addActor(jumpButton);
        play.addActor(movementCross);
        play.addActor(pause);
        play.addActor(bar);
        play.addActor(cir);
    }

}
