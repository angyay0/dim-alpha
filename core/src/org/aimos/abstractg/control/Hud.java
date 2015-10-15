package org.aimos.abstractg.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.gamestate.GameStateManager;

import java.util.List;

/**
 * Created by EinarGretch,Angyay0 on 09/09/2015.
 */
public class Hud extends Table {

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
    //Tamaño de las imagenes
    private float SQUARE = 50f;
    //Margen para los botones
    private static final float CTRL_MARGIN = 50f;
    //Square Size for circle pad
    private static final float CTRL_SIZE = 160f;

    //Lista con los botones del control
    private List<Button> buttons;
    //Coordenadas para eventos del control
    private int posX1, posX2, posY1, posY2;
    //Instancia de player
    private Player player;
    //Instancia del GameStateManager
    private GameStateManager manager;

    public Hud(Player player, GameStateManager gsm) {
        this.player = player;
        manager = gsm;
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
        init();
    }

    private void init() {
        final Player p = player;
        final GameStateManager gsm = manager;
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

                p.jump();
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
        pad.setSize(CTRL_SIZE / 2, CTRL_SIZE / 2);
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
                //p.setWalking(true);
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                posX2 = (int) x - posX1;
                posY2 = (int) y - posY1;

                double radius = Math.sqrt(Math.pow(posX2, 2) + Math.pow(posY2, 2));

                double angle = Math.toDegrees(Math.atan2(posY2, posX2));

                angle = (angle < 0) ? 360 + angle : angle;

                int cuad = (int) angle / 45;

                switch (cuad) {
                    case 0:
                    case 7://Derecha
                        p.move(true);
                        break;
                    case 1://Arriba
                    case 2:
                        if (p.isCrouching() && !p.isForceCrouched()) {
                            p.setCrouching(false);
                        }
                        break;
                    case 3://Izquierda
                    case 4:
                        p.move(false);
                        break;
                    case 5://Abajo
                    case 6:
                        p.setCrouching(true);
                        break;
                }

                /*if (posX2 > 40) {//derecha
                    p.move(true);
                } else if (posX2 < -40) {//izquierda
                    p.move(false);
                }
                //System.out.println(posX2);
                if (posY2 < -20) {//agacharse
                    if (!p.isCrouching()) {
                        p.setCrouching(true);
                    }
                } else if (posY2 > 20) {//mirar arriba
                    //interactuar escaleras
                }*/

                //System.out.println("x " + posX2 + ", Angle " + angle + ", Radius " + radius + ", Cos " + (Math.cos(Math.toRadians(angle)))+", X " + (angle * Math.cos(Math.toRadians(angle))));
                //System.out.println("y " + posY2 + ", Angle " + angle + ", Radius " + radius + ", Sen " + (Math.sin(Math.toRadians(angle)))+", Y " + (angle * Math.sin(Math.toRadians(angle))));
                if ((CTRL_SIZE / 2) >= radius) {
                    pad.setPosition(x - (pad.getWidth() / 2), y - (pad.getHeight() / 2));
                } else {
                    pad.setPosition((float) ((CTRL_SIZE / 2) * Math.cos(Math.toRadians(angle))) - (pad.getWidth() / 2) + posX1,
                            (float) ((CTRL_SIZE / 2) * Math.sin(Math.toRadians(angle))) - (pad.getHeight() / 2) + posY1);
                }

            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                bgPad.setVisible(false);
                pad.setVisible(false);
                bgPad.setPosition(0, 0);
                pad.setPosition(0, 0);
                p.setWalking(false);
            }
        });

        Button pause = new Button(new TextureRegionDrawable(new TextureRegion(Launcher.res.getTexture("pause"))));//pause
        pause.setSize(SQUARE, SQUARE);
        pause.setPosition(375f, 410f);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.setTempState();
            }
        });

        Image cir = new Image(Launcher.res.getTexture("circle"));
        cir.setSize(SQUARE, SQUARE);
        cir.setPosition(10f, 410f);
        Image bar = new Image(Launcher.res.getTexture("bar"));
        bar.setSize(250, SQUARE);
        bar.setPosition(35f, 410f);

        addActor(bgPad);
        addActor(pad);
        addActor(jumpButton);
        addActor(movementCross);
        addActor(pause);
        addActor(bar);
        addActor(cir);
    }

}
