package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Play extends GameState{

    private World world;
    private Box2DDebugRenderer b2dRenderer;
    private GameContactListener contact;
    private BoundedCamera b2dCam;

    private Player player;

    private TiledMap tileMap;
    private int tileMapWidth;
    private int tileMapHeight;
    private int tileSize;
    private BoundedCamera fx,floor,xFx;
    private TiledMapTileLayer fxLayer,floorLayer,moreLayer,xFxLayer, buildingLayer;
    private OrthogonalTiledMapRenderer tmRenderer;

    private Hud hud;

    private boolean debug = true;

    private boolean flag = false;

    public static int level;

    private Array<Vector2> coords = new Array<Vector2>();
    private Array<Vector2> size = new Array<Vector2>();

    protected Play(GameStateManager gsm) {
        super(gsm);

        //Music for your <3 baby *-*

        AudioManager.getInstance().initializeAudio(Launcher.res.getMusic("city_l2"));


        //set up the world
        world = new World(new Vector2(0, -9.81f), true);
        contact = new GameContactListener();
        world.setContactListener(contact);
        b2dRenderer = new Box2DDebugRenderer();

        //create player
        player = new Player("player","Hero", world, 120, 120);

        // create walls
        createWalls();

        //Camera to Fx
        fx = new BoundedCamera();
        fx.setToOrtho(false, Launcher.WIDTH, Launcher.HEIGHT);
        fx.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        xFx = new BoundedCamera();
        xFx.setToOrtho(false, Launcher.WIDTH,Launcher.HEIGHT);
        xFx.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        floor = new BoundedCamera();
        floor.setToOrtho(false, Launcher.WIDTH, Launcher.HEIGHT);
        floor.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);

        // set up box2d cam
        b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, Launcher.WIDTH / Constants.PTM, Launcher.HEIGHT / Constants.PTM);
        b2dCam.setBounds(0, (tileMapWidth * tileSize) / Constants.PTM, 0, (tileMapHeight * tileSize) / Constants.PTM);

        //initialize HUD
        hud = new Hud(this);

        //Play bg music
        AudioManager.getInstance().play(0.5f, true);
    }

    @Override
    public void update(float dt) {
        //check input

        //update box2d world
        world.step(Launcher.STEP, 6, 2); // 6 - 8, 2 - 3
        player.update(dt);
    }

    @Override
    public void render() {

        tmRenderer.setView(cam);

        // draw tilemap
        tmRenderer.render();
        fxLayer.setVisible(false);
        floorLayer.setVisible(false);
        moreLayer.setVisible(false);
        xFxLayer.setVisible(false);
        if(flag) buildingLayer.setVisible(false);

        //Draw the effect Fx
        tmRenderer.getBatch().begin();

        if(fx.position.x >= ((tileMapWidth *  tileSize)+(Launcher.WIDTH/2))){
            fx.position.x = -(tileMapWidth *  tileSize);
        }
        if(xFx.position.x >= ((tileMapWidth *  tileSize)+(Launcher.WIDTH/2))){
            xFx.position.x = -(tileMapWidth *  tileSize);
        }
        fx.translate(1, 0, 0);
        xFx.translate(.25f, 0, 0);
        //floor.position.x = ((player.getX()) * Constants.PTM + Launcher.WIDTH / 4) ;
        floor.setPosition(player.getX() * Constants.PTM + Launcher.WIDTH / 4, player.getY() * Constants.PTM + Launcher.HEIGHT / 4);
        fx.update();
        xFx.update();
        floor.update();
        tmRenderer.setView(fx);
        tmRenderer.renderTileLayer(fxLayer);
        tmRenderer.setView(xFx);
        tmRenderer.renderTileLayer(xFxLayer);
        tmRenderer.setView(floor);
        if(flag)tmRenderer.renderTileLayer(buildingLayer);
        tmRenderer.renderTileLayer(floorLayer);
        tmRenderer.renderTileLayer(moreLayer);
        tmRenderer.getBatch().end();

        // draw player
        sb.setProjectionMatrix(floor.combined);
        player.render(sb);
        draw();
        if(debug) {
            b2dCam.setPosition(player.getX() + Launcher.WIDTH / 4 / Constants.PTM, player.getY() + Launcher.HEIGHT / 4 / Constants.PTM);
            b2dCam.update();
            b2dRenderer.render(world, b2dCam.combined);
        }
    }

    @Override
    public void disposeState() {
        if(AudioManager.getInstance().isPlaying()){
            AudioManager.getInstance().stopAudio();
        }
    }

    /**
     * Sets up the tile map collidable tiles.
     * Reads in tile map layers and sets up box2d bodies.
     */
    private void createWalls() {

        // load tile map and map renderer
        try {
            //tileMap = new TmxMapLoader().load("level" + level + ".tmx");
            //tileMap = new TmxMapLoader().load("tutorial/tutorial.tmx");
            tileMap = new TmxMapLoader().load("cityla/cityla.tmx");
        }
        catch(Exception e) {
            Gdx.app.error("ERROR: ", "Cannot find file: " + level + ".tmx");
            Gdx.app.exit();
        }
        tileMapWidth = Integer.parseInt(tileMap.getProperties().get("width").toString());
        tileMapHeight = Integer.parseInt(tileMap.getProperties().get("height").toString());
        tileSize = Integer.parseInt(tileMap.getProperties().get("tilewidth").toString());
        tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

        //Get Fx layer
        fxLayer = (TiledMapTileLayer) tileMap.getLayers().get("fx");
        //Get more layer
        moreLayer = (TiledMapTileLayer) tileMap.getLayers().get("more");
        //Get xFx layer
        xFxLayer = (TiledMapTileLayer) tileMap.getLayers().get("xfx");
        //Get floor layer
        floorLayer = (TiledMapTileLayer) tileMap.getLayers().get("floor");
        //Get building layer

        buildingLayer = (TiledMapTileLayer) tileMap.getLayers().get("building");
        if(buildingLayer != null){
            flag = true;
        }
        createBlocks(floorLayer, Constants.BIT_FLOOR);
        /*

        // create walls

        //create left wall
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(150, 150);
        EdgeShape shape = new EdgeShape();
        FixtureDef fd = new FixtureDef();
        //shape.set(-Gdx.graphics.getWidth() / Constants.PTM, -Gdx.graphics.getHeight() / Constants.PTM, +Gdx.graphics.getWidth() / Constants.PTM, -Gdx.graphics.getHeight() / Constants.PTM);
        shape.set(0,0,50,50);
        fd.shape = shape;
        fd.filter.categoryBits = Constants.BIT_WALL;
        fd.filter.maskBits = Constants.BIT_CHARACTER;
        world.createBody(bdef).createFixture(fd);

        //create right wall
        bdef.position.set(Gdx.graphics.getWidth() / Constants.PTM, 0);
        fd.shape = shape;
        world.createBody(bdef).createFixture(fd);
        shape.dispose();*/
    }

    private void createBlocks(TiledMapTileLayer layer, short bits) {

        // tile size
        float ts = layer.getTileWidth();
        int width = 0;

        // go through all cells in layer
        for(int row = 0; row < layer.getHeight(); row++) {
            for(int col = 0; col < layer.getWidth(); col++) {
                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                // check that there is a cell
                if(cell == null){
                    if (width > 0) {
                        size.add(new Vector2());
                        size.get(size.size -1).x = width;
                        coords.get(coords.size -1).y = row;
                        size.get(size.size -1).y = 1;
                        if (coords.size > 1 ){
                            boolean check = false;
                            for (int x =0; x < coords.size-1;x++){
                                if ( (coords.get( coords.size -1).x == coords.get(x).x) &&
                                        (size.get(size.size -1).x == size.get(x).x) && (coords.get(coords.size - 1).y ==
                                        (coords.get(x).y + size.get(x).y)) ){
                                    size.get(x).y++;
                                    check = true;
                                }
                            }
                            if (check){
                                coords.pop();
                                size.pop();
                            }
                        }
                        width = 0;

                    }
                    continue;
                }
                if(cell.getTile() == null)continue;
                if(width == 0){
                    coords.add(new Vector2());
                    coords.get(coords.size-1).x = col;

                }
                width++;

            }
            if (width > 0){
                size.add(new Vector2());
                size.get(size.size -1).x = width;
                coords.get(coords.size -1).y = row;
                size.get(size.size -1).y = 1;
                if (coords.size > 1 ){
                    boolean check = false;
                    for (int x =0; x < coords.size-1;x++){
                        if (coords.get(coords.size -1).x == coords.get(x).x && size.get(size.size -1).x == size.get(x).x){
                            size.get(x).y++;
                            check = true;
                        }
                    }
                    if (check){
                        coords.pop();
                        size.pop();
                    }
                }
                width = 0;
            }
        }

        for (int i = 0; i < coords.size ; i++) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((coords.get(i).x * ts / Constants.PTM) + (((size.get(i).x * ts) / 2)/ Constants.PTM),
                    (coords.get(i).y * ts / Constants.PTM) + (((size.get(i).y * ts) / 2)/ Constants.PTM));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(((size.get(i).x * ts) / 2)/ Constants.PTM, ((size.get(i).y * ts) / 2)/ Constants.PTM);
            FixtureDef fd = new FixtureDef();
            fd.friction = 1;
            fd.shape = shape;
            fd.filter.categoryBits = bits;
            fd.filter.maskBits = Constants.BIT_CHARACTER;
            Body b = world.createBody(bdef);
            b.createFixture(fd).setUserData("cell");
            b.setUserData(size.get(i));
            shape.dispose();
        }

    }

    public void attack(){
        //depende del weapon
    }


    public Player getPlayer() {
        return player;
    }

}
