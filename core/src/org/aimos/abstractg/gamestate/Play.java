package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import org.aimos.abstractg.handlers.GameConfiguration;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.MapLoader;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.Item;
import org.aimos.abstractg.physics.MeleeWeapon;
import org.aimos.abstractg.physics.PhysicalBody;
import org.aimos.abstractg.physics.Portal;
import org.aimos.abstractg.physics.ShootWeapon;
import org.aimos.abstractg.physics.ThrowWeapon;
import org.aimos.abstractg.physics.Weapon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public abstract class Play extends GameState {

    private World world;
    private GameContactListener contact;

    private Player player;
   // private Enemy ene;
    private Array<Coin> coins;
    private Array<PhysicalBody> removed;
    private MapLoader loader;
    private Hud hud;
    private Portal portal;
    private boolean win;
    MeleeWeapon mw;
    ShootWeapon sw;
    ThrowWeapon tw;
    Skin skin;
    Thread t;
    Label labelInfo/*,labelInfo*/;

    private static String map = "tutorial";
    private static int worldLvel = 1;


    protected Play(GameStateManager gsm) {
        super(gsm);
        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));
        AudioManager.getInstance().play(0.5f, true);
       // Enemy.running = true;

        coins = new Array<Coin>();

        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        removed = new Array<PhysicalBody>();
        contact = new GameContactListener(removed);
        world.setContactListener(contact);
        win = false;
        //create the portal

        //create player
        player = new Player("player","Hero", this, new Vector2(128,128));
       /* ene = new Boss("player","Enemy", this, new Vector2(200, 128));/*{
            @Override
            public void run(){
                while( running ){
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
       /* ene.setConfigurations("boss.lua",1000,0);
        ene.setIndicators( new Indicators() );*/
        
        loader = new MapLoader(world, player);
      //  mw = new MeleeWeapon(10, 10, 10, 7, this, "sword");
        //sw = new ShootWeapon(10,10,10,7,this,"gun");
        // tw = new ThrowWeapon(10,10,10,7,this,"steel");
      //  player.setWeapon(mw);
        //player.setWeapon(sw);
        //  player.setWeapon(tw);

        skin = new Skin();
        skin.addRegions(Launcher.res.getAtlas("uiskin"));
        skin.add("default-font", font);
        skin.load(Gdx.files.internal("data/uiskin2.json"));
        Vector2[] pos = new Vector2[5];
        pos[0] = new Vector2(256,512);
        pos[1] = new Vector2(704,1455);
        pos[2] = new Vector2(445,1585);
        pos[3] = new Vector2(223,2034);
        pos[4] = new Vector2(768,2650);

        Vector2 porPos = new Vector2(25,/*2560*/128);
        Vector2 parPos = new Vector2(25,/*2530*/128);
       // addCoins(Coin.generateCoins(this, new Vector2(256, 512), 1000));
        addCoins(Coin.generateCoins(this, pos, pos.length));

        //Create Hud
        hud = new Hud(player, gsm);
        addActor(hud);
        initLabel();

        portal = new Portal(this,porPos,parPos,false);
        portal.setVisibility(false);
     /*   t = new Thread(ene);
        t.start();*/
    }

    @Override
    public void update(float dt) {
        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        if(!AudioManager.getInstance().isPlaying() && GameConfiguration.getInstance().getMusic()) AudioManager.getInstance().play();
        player.update(dt);
        if( !portal.isVisible() ){
            if( player.hasMinimumCoins(4) )
                portal.setVisibility(true);
        }
        if(win){
            WinScreen.settter(JsonIO.readProfileTScore(), String.valueOf(player.getMoney()), map, JsonIO.tmxName);
            disposeState();
            getManager().pushState(Constants.STATE.WINNER);

            //Gdx.app.exit();
        }
      /*  ene.update(dt);*/
        /*if(ene.isDead()) {
            System.out.println("Enemy muerto");
        }*/
        //ene.update(dt);
        removeBodies();

        float y1 = (player.getY() +((player.getHeight() / player.BODY_SCALE) / Constants.PTM));

        if (y1 < 0 && loader.getFloorCamera().position.y == (Launcher.HEIGHT/2)) {

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
            */

            gsm.pushState(Constants.STATE.GAME_OVER);
            disposeState();
        }
        updLabel(String.valueOf(player.getMoney()));
      /*  updArm(String.valueOf(player.getMoney()));   */
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(103/255f, 219/255f, 248/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        loader.render();
        sb.setProjectionMatrix(loader.getFloorCamera().combined);
        // Dibuja las monedas en el mapa
        for (Coin coin : coins) {
            coin.draw(sb);
        }
        //Dibuja el jugador y el portal
        player.draw(sb);
        //portal.render(sb);
       // System.out.println(player.getBody().getLinearVelocity().x + " " + player.getBody().getLinearVelocity().y);
        /*ene.draw(sb);*/
        draw();
    }

    @Override
    public void disposeState() {
        if (AudioManager.getInstance().isPlaying()) {
            AudioManager.getInstance().stopAudio();
        }

    }

    @Override
    public void back() {
        gsm.pushState(Constants.STATE.PAUSE);
    }

    public void removeBodies() {
        for (PhysicalBody body : removed) {
            if (body instanceof Coin) coins.removeValue((Coin) body, false);
            body.dispose();
        }
        removed.clear();
    }

    public Player getPlayer() {
        return player;
    }

    public void addCoins(Array<Coin> c) {
        coins.addAll(c);
    }

    public static void levelSelect(String m) {
        map = m;
    }

    public static String getMap() {
        return map;
    }

    public void initLabel() {
        labelInfo = new Label("0", skin, "default");
        labelInfo.setPosition(690f, 450f);
        labelInfo.setAlignment(Align.right);

      /*  labelInf = new Label("0", skin, "default");
        labelInf.setPosition(690f, 400f);
        labelInf.setAlignment(Align.right);*/

        addActor(labelInfo);
    /*    addActor(labelInf);*/
    }
/*
    public void updArm(String val) {
        labelInf.setText(val);
    }*/

    public void updLabel(String val) {
        labelInfo.setText(val);
    }

    public World getWorld() {
        return world;
    }

    public void remove(PhysicalBody body) {
        removed.add(body);
    }

    public Constants.STATE getGameState() {
        return gsm.getState().getID();
    }

    public void setWin(boolean bool) {
        win = bool;
    }
}
