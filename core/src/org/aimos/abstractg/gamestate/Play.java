package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Enemy;
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
import org.aimos.abstractg.physics.PhysicalBody;
import org.aimos.abstractg.physics.ShootWeapon;
import org.aimos.abstractg.physics.ThrowWeapon;
import org.aimos.abstractg.physics.Weapon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public abstract class Play extends GameState{

    private World world;
    private GameContactListener contact;

    private Player player;
    private Enemy ene;
    private Array<Coin> coins;
    private Array<PhysicalBody> removed;
    private MapLoader loader;
    private Hud hud;
    MeleeWeapon mw;
    ShootWeapon sw;
    ThrowWeapon tw;
    Skin skin;
    Thread t;
    Label labelInfo,labelInf;

    private static String map = "tutorial";
    private static int worldLvel = 1;


    protected Play(GameStateManager gsm) {
        super(gsm);
        game.setFlag(true);
        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));
        AudioManager.getInstance().play(0.5f, true);
        Enemy.running = true;

        coins = new Array<Coin>();

        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        removed = new Array<PhysicalBody>();
        contact = new GameContactListener(removed);
        world.setContactListener(contact);

        //create player
        player = new Player("player","Hero", this, new Vector2(128,128));
        /*ene = new Enemy("player","Enemy", this, new Vector2(200, 128)){
            @Override
            public void run(){
                while( running(getPlay().getGameState() == Constants.STATE.SOLO_PLAY || getPlay().getGameState() == Constants.STATE.MULTI_PLAY )){
                    try{


                    act();

                    try{
                        Thread.sleep(50);
                    }catch(Exception e){
                        Gdx.app.error("Fatal Thread Failure", e.getMessage());
                        Gdx.app.exit();
                    }
                }catch(Exception e){
                        e.printStackTrace();
                        Gdx.app.exit();
                    }
                }
            }
        };*/

        loader = new MapLoader(world, player);
       // mw = new MeleeWeapon(10,10,10,7,this,"sword");
        //sw = new ShootWeapon(10,10,10,7,this,"gun");
       // tw = new ThrowWeapon(10,10,10,7,this,"steel");
        //player.setWeapon(mw);
        //player.setWeapon(sw);
      //  player.setWeapon(tw);

        skin = new Skin();
        skin.addRegions(Launcher.res.getAtlas("uiskin"));
        skin.add("default-font", font);
        skin.load(Gdx.files.internal("data/uiskin2.json"));

        addCoins(Coin.generateCoins(this, new Vector2(256, 512), 100));

        //Create Hud
        hud = new Hud(player, gsm);
        addActor(hud);
        initLabel();

        //t = new Thread(ene);
        //t.start();
    }

    @Override
    public void update(float dt) {
        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        player.update(dt);
        /*if(ene.isDead()) {
            System.out.println("Enemy muerto");
        }*/
        //ene.update(dt);
        removeBodies();

        float y1 = (player.getY() +((player.getHeight() / player.BODY_SCALE) / Constants.PTM));
        if(y1 < 0) {
            System.out.println("Entro  " + y1);
        }
        float y3 = ((player.getHeight() / player.BODY_SCALE) / Constants.PTM);
        if (y1 < 0 && loader.getFloorCamera().position.y == (Launcher.HEIGHT/2)) {
           System.out.println("Entro a morir " + y1);
          //  System.out.println("Entro a morir " + y1);
            System.out.println("Entro a morir " + y3);

            /*long money = 0;
            long enem = 0;
            Array<Weapon> weap = null;
            JsonIO.savePlay(player, map, worldLvel);
            if(JsonIO.readProfile()){
                money = JsonIO.coins;
                enem = JsonIO.enemy;
                weap = JsonIO.weapons;
            }
            JsonIO.saveProfile(player.getWeapons(),money+player.getMoney(),enem+player.getEnemiesKilled());
            */gsm.gameOver(player.getMoney());
            disposeState();
        }
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
            //System.out.println(player.getBody().getLinearVelocityFromLocalPoint(new Vector2(0,0)));
            player.draw(sb);
            //System.out.println(player.getBody().getLinearVelocity().y);
            //ene.draw(sb);
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

    public void removeBodies(){
        for (PhysicalBody body : removed) {
            if(body instanceof Coin) coins.removeValue((Coin)body, false);
            body.dispose();
        }
        removed.clear();
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

    public World getWorld() {
        return world;
    }

    public void remove(PhysicalBody body){
        removed.add(body);
    }

    public Constants.STATE getGameState(){ return gsm.getState().getID(); }

}
