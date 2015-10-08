package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.control.Hud;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.GameContactListener;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.handlers.AudioManager;
import org.aimos.abstractg.handlers.BoundedCamera;
import org.aimos.abstractg.handlers.MapLoader;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Play extends GameState{

    private World world;
    private GameContactListener contact;

    private Player player;
    private MapLoader map;

    private Hud hud;

    private Array<Vector2> coords = new Array<Vector2>();
    private Array<Vector2> size = new Array<Vector2>();

    protected Play(GameStateManager gsm) {
        super(gsm);

        //Load music
        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));


        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        contact = new GameContactListener();
        world.setContactListener(contact);

        //create player
        player = new Player("player","Hero", world, new Vector2(120, 120));
        map = new MapLoader("cityla",world,player);

        //Create Hud
        hud = new Hud(this);

        //Play music
        AudioManager.getInstance().play(0.5f, true);
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
        map.render();

        // draw player
        sb.setProjectionMatrix(map.getFloorCamera().combined);
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
}
