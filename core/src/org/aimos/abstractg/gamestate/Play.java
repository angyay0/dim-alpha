package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.control.Hud;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.MapLoader;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.MeleeWeapon;
import org.aimos.abstractg.physics.PickUp;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Play extends GameState{

    private World world;
    private GameContactListener contact;

    private Player player;
    private Array<Coin> coins;
    private MapLoader loader;
    private boolean gameOver = false;
    private Hud hud;
    MeleeWeapon mw;

    private static String map = "";

    protected Play(GameStateManager gsm) {
        super(gsm);
        game.setFlag(true);

        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));

        coins = new Array<Coin>();

        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        contact = new GameContactListener();
        world.setContactListener(contact);

        //create player
        player = new Player("player","Hero", world, new Vector2(128,128));
        loader = new MapLoader(world, player);
        mw = new MeleeWeapon(10,10,10,world,"sword");
        player.setWeapon(mw);

        addCoins(Coin.generateCoins(world, new Vector2(256, 512), 116));

        //Create Hud
        hud = new Hud(player, gsm);
        addActor(hud);

        //Play music
        AudioManager.getInstance().play(0.5f, true);
    }

    @Override
    public void update(float dt) {
        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        player.update(dt);
        removeBodies(contact.getRemovedBodies());
    }

    @Override
    public void render() {


            // clear the screen
            Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            loader.render();

            // draw player
            sb.setProjectionMatrix(loader.getFloorCamera().combined);
            for (Coin coin : coins) {
                coin.render(sb);
            }
            player.render(sb);
            mw.render(sb);
            if ((player.getY() + ((player.getHeight() / player.BODY_SCALE) / Constants.PTM)) < 0) {

                if (!gameOver) {
                    gameOver = true;
                    getManager().setTempState();
                }
            }
            draw();

    }

    @Override
    public void disposeState() {
        if(AudioManager.getInstance().isPlaying()){
            AudioManager.getInstance().stopAudio();
        }

    }

    public void removeBodies(Array<PickUp> pickUp){
        for (PickUp p : pickUp) {
            if(p instanceof Coin) coins.removeValue((Coin)p, false);
            p.dispose();
        }
        pickUp.clear();
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
}
