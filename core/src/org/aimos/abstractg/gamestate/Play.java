package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.control.Hud;
import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.core.SavePoint;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.MapLoader;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.MeleeWeapon;
import org.aimos.abstractg.physics.PickUp;
import org.aimos.abstractg.physics.Weapon;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

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
    Json json = new Json();

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
      //  player.setWeapon(mw);

        addCoins(Coin.generateCoins(world, new Vector2(256, 512), 116));

        //Create Hud
        hud = new Hud(player, gsm);
        addActor(hud);
        JsonIO.readPlay(map,worldLvel);
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
          //  mw.render(sb);
            if ((player.getY() + ((player.getHeight() / player.BODY_SCALE) / Constants.PTM)) < 0) {

                if (!gameOver) {
                    gameOver = true;
                    long money = 0;
                    long enem = 0;
                    Array<Weapon> weap = null;
                    JsonIO.savePlay(player, map, worldLvel);
                    if(JsonIO.readProfile()){
                        money = JsonIO.coins;
                        enem = JsonIO.enemy;
                        weap = JsonIO.weapons;
                    }
                    JsonIO.saveProfile(player.getWeapons(),money+player.getMoney(),enem+player.getEnemiesKilled());
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

    public void save(){

        // json.setOutputType(JsonWriter.OutputType.json);
        //json.setTypeName("settingDatos");
        //json.writeObjectStart();
       // json.writeValue("items",list);
        //json.writeObjectEnd();

        //FileHandle file = new FileHandle("./data/set.txt");
        //FileHandle fil = Gdx.files.getFileHandle("./data/set.text",Files.FileType.External);
        //File file = Gdx.files.getFileHandle("./Android/data/setting.json", Files.FileType.Internal).file(); //new FileHandle("ssetting.json").file();
        //FileHandle file = Gdx.files.getFileHandle("data/test.json",Files.FileType.Internal);
        //FileHandle fileMp=Gdx.files.getFileHandle("Mi_Musica/Maps.mp3",Files.FileType.External);
        //if (file.exists()) {
       //}
        //if (fileMp.exists()) {
            //Gdx.app.debug("EVENTO","CARD");
        //}

            /*
            Gdx.app.debug("EVentooo",String.valueOf(player.getMoney()));
            BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            output.write(json.toJson(guardado));
            output.close();
            */
        //json.setElementType(SavePoint.class, "weapons", WeaponsArray.class);
        //FileHandle file = new FileHandle("data/setting.json");//Gdx.files.internal("setting.json").file();
        //file.writeString(json.toJson(guardado), false);
        //file.copyTo(Gdx.files.internal("data/setting.json"));
        //FileHandle file = Gdx.files.local("setting.txt");
        //file.writeString(json.toJson(guardado),false);


    }

}
