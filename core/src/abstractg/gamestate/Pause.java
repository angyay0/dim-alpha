package abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by EinarGretch,DiegoBanderas on 30/09/2015.
 */
public class Pause extends GameState {

    GameState actual;
    private  Button [] btnPause = new Button[5];
    private float delta = 0;
    private float imgCuad = 90f;
    TextButton btnResume;
    Window window;
    Skin skin;

    public Pause(GameStateManager gsm, GameState act) {
        super(gsm);
        actual = act;
        createWindowPause();
    }

    private void createWindowPause() {

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        final Label homelabel = new Label("Salir",skin);
        btnResume = new TextButton("X",skin);
        btnResume.addListener(new ClickListener(){@Override public void clicked(InputEvent evt, float x, float y) {
            Gdx.app.debug("Evento","Cerrar Menu");
            gsm.disposeTemp();
         }});

        btnPause[0]  = new Button( new TextureRegionDrawable( new TextureRegion( new Texture( "menu/home.png") ))); //Home
        btnPause[0].setSize(imgCuad, imgCuad);
        btnPause[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.backToMenu();
            }
        });

        final CheckBox Violencia = new CheckBox(" Violencia",skin);
        Violencia.setChecked(true);
        Violencia.setDisabled(true);
        btnPause[1] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/violenceOff.png"))));//Violence
        btnPause[1].setSize(imgCuad, imgCuad);
        btnPause[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "violencia");
                if (Violencia.isChecked()) {
                        Violencia.setChecked(false);
                } else {
                        Violencia.setChecked(true);
                }
            }
        });

        final CheckBox Musica = new CheckBox(" Musica", skin);
        Musica.setChecked(true);
        Musica.setDisabled(true);
        btnPause[2] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/soundOff.png"))));//Musica
        btnPause[2].setSize(imgCuad, imgCuad);
        btnPause[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Musica");
                if (Musica.isChecked()) {
                    if(Play.getMusic().isPlaying()){
                        Musica.setChecked(false);
                        Play.getMusic().stop();
                    }
                }else{
                    if(!Play.getMusic().isPlaying()){
                        Play.getMusic().play();
                        Musica.setChecked(true);
                    }
                }
            }
        });

        final CheckBox Efecto = new CheckBox(" Efectos", skin);
        Efecto.setChecked(true);
        Efecto.setDisabled(true);
        btnPause[3] = new Button(new TextureRegionDrawable( new TextureRegion( new Texture( "menu/fxOff.png"))));//Fx
        btnPause[3].setSize(imgCuad, imgCuad);
        btnPause[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("Evento", "Efecto");
                if (Efecto.isChecked()) Efecto.setChecked(false);
                else Efecto.setChecked(true);
            }
        });



        window = new Window(" ", skin);
        window.setMovable(false);
        //window.setVisible(false);
        window.setResizable(false);
        window.padTop(20);
        //window.getTitleTable().add(btnResume).height(window.getPadTop());
        window.setPosition(270, 130);
        window.defaults().spaceTop(20);
        window.defaults().spaceBottom(10);
        window.defaults().spaceLeft(5);
        window.defaults().spaceRight(5);
        window.row().fill().expandX();
        window.add(btnPause[1]);
        window.add(btnPause[2]);
        window.add(btnPause[3]);
        window.row();
        window.add(Violencia);
        window.add(Musica);
        window.add(Efecto);
        window.row();
        window.add(btnPause[0]).colspan(2);
        window.row();
        window.add(homelabel).colspan(2).spaceBottom(0);
        window.pack();
        addActor(window);
    }

    @Override
    public void disposeState() {
        Gdx.input.setInputProcessor(actual);
    }

    @Override
    public void update(float dt) {
        delta = dt;
    }

    @Override
    public void render() {
        actual.render();
        draw();
    }


}
