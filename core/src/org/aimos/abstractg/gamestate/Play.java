package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Timer;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.control.Hud;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.MapLoader;
import org.aimos.abstractg.physics.Coin;

import java.awt.event.KeyEvent;


/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Play extends GameState{

    private World world;
    private GameContactListener contact;

    private Player player;
    private Array<Coin> coins;
    private MapLoader loader;

    private Hud hud;

    private static String map = "";

    protected Play(GameStateManager gsm) {
        super(gsm);

        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));

        coins = new Array<Coin>();
        game.setFlagT();

        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        contact = new GameContactListener();
        world.setContactListener(contact);

        //create player
        player = new Player("player","Hero", world, new Vector2(120, 120));
        loader = new MapLoader(world, player);

        Coin.generateCoins(world, new Vector2(160, 140), 116);

        //Create Hud
        hud = new Hud(this);

        //Play music
        AudioManager.getInstance().play(0.5f, true);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);



    }

    @Override
    public void update(float dt) {
        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        player.update(dt);
    }

    @Override
    public void render() {


        // clear the screen
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        loader.render();

        /*
        if(Gdx.input.isKeyPressed(Keys.HOME) || Gdx.input.isButtonPressed(Keys.HOME) || Gdx.input.isTouched(Keys.HOME)){
            game.setFlag();
            game.pause();
            Gdx.app.debug("EVentooo!!!","pause");
        }*/

        // draw player
        sb.setProjectionMatrix(loader.getFloorCamera().combined);
        for (Coin coin : coins) {
            coin.render(sb);
        }
        player.render(sb);
        draw();
    }

    @Override
    public void disposeState() {
        if(AudioManager.getInstance().isPlaying()){
            AudioManager.getInstance().stopAudio();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void addCoins(Array<Coin> c){
        coins.addAll(c);
    }

    public static void levelSelect(String m){
        map = m;
    }

    public static String getMap(){
        return map;
    }
/*
    @Override
    public boolean keyDown(int keycode) {

        return super.keyDown(keycode);
    }
*/

}
