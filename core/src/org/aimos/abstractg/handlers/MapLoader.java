package org.aimos.abstractg.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.core.Launcher;

/**
 * Created by Herialvaro on 06/10/2015.
 */
        public class MapLoader {



    World world;
    String level;
    TiledMap tileMap;
    OrthogonalTiledMapRenderer tmRenderer;
    int tileMapWidth, tileMapHeight,tileSize;
    TiledMapTileLayer[] layers = new TiledMapTileLayer[5];
    BoundedCamera floor,fx,xFx,b2dCam;
    private Box2DDebugRenderer b2dRenderer;
    private boolean debug = true;
    SpriteBatch batch;
    Texture bg;
    private Player player;
    public MapLoader(String level, World world, Player player){
        this.world = world;
        this.level = level;
        this.player = player;
        b2dRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        bg = new Texture(level +"/bck.png");
        createWalls();
        //Camera to Fx
        fx = new BoundedCamera();
        fx.setToOrtho(false, Launcher.WIDTH, Launcher.HEIGHT);
        fx.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        xFx = new BoundedCamera();
        xFx.setToOrtho(false, Launcher.WIDTH, Launcher.HEIGHT);
        xFx.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        floor = new BoundedCamera();
        floor.setToOrtho(false, Launcher.WIDTH, Launcher.HEIGHT);
        floor.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);

        // set up box2d cam
        b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, Launcher.WIDTH / Constants.PTM, Launcher.HEIGHT / Constants.PTM);
        b2dCam.setBounds(0, (tileMapWidth * tileSize) / Constants.PTM, 0, (tileMapHeight * tileSize) / Constants.PTM);
    }

    public void render(){
        batch.begin();
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        tmRenderer.getBatch().begin();

        if(fx.position.x >= ((tileMapWidth *  tileSize)+(Launcher.WIDTH/2))){
            fx.position.x = -(tileMapWidth *  tileSize);
        }
        if(xFx.position.x >= ((tileMapWidth *  tileSize)+(Launcher.WIDTH/2))){
            xFx.position.x = -(tileMapWidth *  tileSize);
        }
        fx.translate(1, 0, 0);
        xFx.translate(.25f, 0, 0);
        //floor.position.x = ((player.getX()) * AimosVars.PTM + Launcher.WIDTH / 4) ;
        floor.setPosition(player.getX() * Constants.PTM + Launcher.WIDTH / 4, player.getY() *Constants.PTM + Launcher.HEIGHT / 4);
        fx.update();
        xFx.update();
        floor.update();
        if(layers[0] != null) {
            tmRenderer.setView(fx);
            tmRenderer.renderTileLayer(layers[0]);
        }
        if (layers[1] != null) {
            tmRenderer.setView(xFx);
            tmRenderer.renderTileLayer(layers[1]);
        }
        tmRenderer.setView(floor);
        if(layers[4] != null) {
            tmRenderer.renderTileLayer(layers[4]);
        }
        if (layers[2] != null) {
            tmRenderer.renderTileLayer(layers[2]);
        }
        if (layers[3] != null) {
            tmRenderer.renderTileLayer(layers[3]);
        }

        tmRenderer.getBatch().end();
        if(debug) {
            b2dCam.setPosition(player.getX() + Launcher.WIDTH / 4 / Constants.PTM, player.getY() + Launcher.HEIGHT / 4 / Constants.PTM);
            b2dCam.update();
            b2dRenderer.render(world, b2dCam.combined);
        }
    }

    public BoundedCamera getFloorCamera(){
        return  floor;
    }
    private void createWalls() {

        // load tile map and map renderer
        try {
            //tileMap = new TmxMapLoader().load("level" + level + ".tmx");
            //tileMap = new TmxMapLoader().load("tutorial/tutorial.tmx");
            tileMap = new TmxMapLoader().load(level+"/"+level+".tmx");
        }
        catch(Exception e) {
            Gdx.app.error("ERROR ", "Cannot find file: " + level + ".tmx");
            Gdx.app.exit();
        }
        tileMapWidth = Integer.parseInt(tileMap.getProperties().get("width").toString());
        tileMapHeight = Integer.parseInt(tileMap.getProperties().get("height").toString());
        tileSize = Integer.parseInt(tileMap.getProperties().get("tilewidth").toString());
        tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

        //Get All layers

        //Get Fx layer
        layers[0] = (TiledMapTileLayer) tileMap.getLayers().get("fx");
        //Get xFx layer
        layers[1] = (TiledMapTileLayer) tileMap.getLayers().get("xfx");
        //Get more layer
        layers[2] = (TiledMapTileLayer) tileMap.getLayers().get("more");
        //Get floor layer
        layers[3] = (TiledMapTileLayer) tileMap.getLayers().get("floor");
        //Building
        layers[4] = (TiledMapTileLayer) tileMap.getLayers().get("building");
        createBlocks(layers[3], Constants.BIT_FLOOR);
        /*

        // create walls

        //create left wall
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(150, 150);
        EdgeShape shape = new EdgeShape();
        FixtureDef fd = new FixtureDef();
        //shape.set(-Gdx.graphics.getWidth() / AimosVars.PTM, -Gdx.graphics.getHeight() / AimosVars.PTM, +Gdx.graphics.getWidth() / AimosVars.PTM, -Gdx.graphics.getHeight() / AimosVars.PTM);
        shape.set(0,0,50,50);
        fd.shape = shape;
        fd.filter.categoryBits = AimosVars.BIT_WALL;
        fd.filter.maskBits = AimosVars.BIT_CHARACTER;
        world.createBody(bdef).createFixture(fd);

        //create right wall
        bdef.position.set(Gdx.graphics.getWidth() / AimosVars.PTM, 0);
        fd.shape = shape;
        world.createBody(bdef).createFixture(fd);
        shape.dispose();*/
    }

    private void createBlocks(TiledMapTileLayer layer, short bits) {

        // tile size
        float ts = layer.getTileWidth();
        int width = 0;
        com.badlogic.gdx.utils.Array<Vector2> size = new com.badlogic.gdx.utils.Array<Vector2>();
        com.badlogic.gdx.utils.Array<Vector2> coords = new com.badlogic.gdx.utils.Array<Vector2>();

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
                                if ((coords.get(coords.size -1).x == coords.get(x).x &&
                                        size.get(size.size -1).x == size.get(x).x) && (coords.get(coords.size -1).y) ==
                                        (coords.get(x).y +size.get(x).y )){
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
            bdef.position.set((coords.get(i).x * ts / Constants.PTM) + (((size.get(i).x * ts) / 2) / Constants.PTM),
                    (coords.get(i).y * ts / Constants.PTM) + (((size.get(i).y * ts) / 2) / Constants.PTM));
            /*ChainShape cs = new ChainShape();
            Vector2[] v = new Vector2[5];
            v[0] = new Vector2(0, 0);
            v[1] = new Vector2(0, (size.get(i).y * ts) / AimosVars.PTM);
            v[2] = new Vector2((size.get(i).x * ts) / AimosVars.PTM, (size.get(i).y * ts) / AimosVars.PTM);
            v[3] = new Vector2((size.get(i).x * ts) / AimosVars.PTM, 0);
            v[4] = new Vector2(0 , 0);
            cs.createChain(v);*/
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((((size.get(i).x * ts)/2) / Constants.PTM), (((size.get(i).y * ts)/2)  / Constants.PTM));
            FixtureDef fd = new FixtureDef();
            fd.friction = 1;
            fd.shape = shape;
            fd.filter.categoryBits = bits;
            fd.filter.maskBits = Constants.BIT_CHARACTER;
            Body b = world.createBody(bdef);
            b.createFixture(fd).setUserData("cell");
            //b.setUserData(size.get(i));
            //cs.dispose();
            shape.dispose();
        }
        size.clear();
        coords.clear();

    }

}