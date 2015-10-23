package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.control.Hud;
import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.MapLoader;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.Item;
import org.aimos.abstractg.physics.MeleeWeapon;
import org.aimos.abstractg.physics.ShootWeapon;
import org.aimos.abstractg.physics.ThrowWeapon;
import org.aimos.abstractg.physics.Weapon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
    Skin skin;
    Label labelInfo,labelInf;

    private static String map = "tutorial";
    private static int worldLvel = 1;


    protected Play(GameStateManager gsm) {
        super(gsm);
        game.setFlag(true);
        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));
        AudioManager.getInstance().play(0.5f, true);

        coins = new Array<Coin>();

        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        contact = new GameContactListener();
        world.setContactListener(contact);

        //create player
        player = new Player("player","Hero", world, new Vector2(128,128));
        loader = new MapLoader(world, player);
      //  mw = new MeleeWeapon(10,10,10,world,"sword");

         skin = new Skin();
         skin.addRegions(Launcher.res.getAtlas("uiskin"));
         skin.add("default-font", font);
         skin.load(Gdx.files.internal("data/uiskin2.json"));

        addCoins(Coin.generateCoins(world, new Vector2(256, 512), 100));

        //Create Hud
        hud = new Hud(player, gsm);
        addActor(hud);
        initLabel();
    }

    @Override
    public void update(float dt) {
        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        player.update(dt);
        removeBodies(contact.getRemovedBodies());
        updLabel(String.valueOf(player.getMoney()));
        updArm(String.valueOf(player.getMoney()));

    }

    @Override
    public void render() {
            loader.render();
            // draw player
            sb.setProjectionMatrix(loader.getFloorCamera().combined);
            for (Coin coin : coins) {
                coin.draw(sb);
            }
            player.draw(sb);
            if ((player.getY() + ((player.getHeight() / player.BODY_SCALE) / Constants.PTM)) < 0) {
                if (!gameOver) {
                    gameOver = true;
                    long money = 0;
                    long enem = 0;
                   // Array<Weapon> weap = null;
                    JsonIO.savePlay(player, map, worldLvel);
                    if(JsonIO.readProfile()){
                        money = JsonIO.coins;
                        enem = JsonIO.enemy;
                        //weap = JsonIO.weapons;
                    }
                    JsonIO.saveProfile(player.getWeapons(), money + player.getMoney(), enem + player.getEnemiesKilled());
                    gsm.gameOver(player);
                    disposeState();
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

    @Override
    public void back() {

    }

    public void removeBodies(Array<PickUp> pickUp){
        for (PickUp p : pickUp) {
            if(p instanceof Coin) coins.removeValue((Coin)p, false);
            p.dispose();
        }
        items.clear();
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

    public void initLabel(){
        labelInfo = new Label("0", skin, "default");
        labelInfo.setPosition(690f, 450f);
        labelInfo.setAlignment(Align.right);

        labelInf = new Label("0", skin, "default");
        labelInf.setPosition(690f, 400f);
        labelInf.setAlignment(Align.right);

        addActor(labelInf);
        addActor(labelInfo);
    }

    public void updArm(String val){
        labelInf.setText(val);
    }

    public void updLabel(String val){
        labelInfo.setText(val);
    }

}
