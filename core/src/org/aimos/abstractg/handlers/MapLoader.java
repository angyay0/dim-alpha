package org.aimos.abstractg.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.gamestate.Play;

import java.util.Iterator;

/**
 * Created by Herialvaro on 06/10/2015.
 */
public class MapLoader {


    World world;
    TiledMap tileMap;
    OrthogonalTiledMapRenderer tmRenderer;
    int tileMapWidth, tileMapHeight, tileSize;
    TiledMapTileLayer[] layers = new TiledMapTileLayer[5];
    BoundedCamera floor, fx, xFx, b2dCam;
    private Box2DDebugRenderer b2dRenderer;
    private boolean debug = true;
    SpriteBatch batch;
    static Texture  bg = new Texture("data/bck.png");
    private Player player;

    private ShapeRenderer sr;

    public MapLoader(World world, Player player){
        super();
        this.world = world;
        this.player = player;
        b2dRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        createMap();
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

    public void render() {
        if(bg != null) {
            batch.begin();
            batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }

        tmRenderer.getBatch().begin();

        if (fx.position.x >= ((tileMapWidth * tileSize) + (Launcher.WIDTH / 2))) {
            fx.position.x = -(tileMapWidth * tileSize);
        }
        if (xFx.position.x >= ((tileMapWidth * tileSize) + (Launcher.WIDTH / 2))) {
            xFx.position.x = -(tileMapWidth * tileSize);
        }
        fx.translate(1, 0, 0);
        xFx.translate(.25f, 0, 0);
        //floor.position.x = ((player.getX()) * AimosVars.PTM + Launcher.WIDTH / 4) ;
        floor.setPosition(player.getX() * Constants.PTM + Launcher.WIDTH / 4, player.getY() * Constants.PTM + Launcher.HEIGHT / 4);
        fx.update();
        xFx.update();
        floor.update();
        if (layers[0] != null) {
            tmRenderer.setView(fx);
            tmRenderer.renderTileLayer(layers[0]);
        }
        if (layers[1] != null) {
            tmRenderer.setView(xFx);
            tmRenderer.renderTileLayer(layers[1]);
        }
        tmRenderer.setView(floor);
        if (layers[4] != null) {
            tmRenderer.renderTileLayer(layers[4]);
        }
        if (layers[2] != null) {
            tmRenderer.renderTileLayer(layers[2]);
        }
        if (layers[3] != null) {
            tmRenderer.renderTileLayer(layers[3]);
        }

        tmRenderer.getBatch().end();
        if (debug) {
            b2dCam.setPosition(player.getX() + Launcher.WIDTH / 4 / Constants.PTM, player.getY() + Launcher.HEIGHT / 4 / Constants.PTM);
            b2dCam.update();
            b2dRenderer.render(world, b2dCam.combined);
        }
    }

    public BoundedCamera getFloorCamera() {
        return floor;
    }

    private void createMap() {

        // load tile map and map renderer
        try {
           tileMap = new TmxMapLoader().load(Play.getMap() + "/" + Play.getMap() + ".tmx");
            System.out.println(tileSize);
        } catch (Exception e) {
            Gdx.app.error("ERROR ", "Cannot find file: " + Play.getMap() + ".tmx");
            Gdx.app.exit();
        }
        tileMapWidth = Integer.parseInt(tileMap.getProperties().get("width").toString());
        tileMapHeight = Integer.parseInt(tileMap.getProperties().get("height").toString());
        tileSize = Integer.parseInt(tileMap.getProperties().get("tilewidth").toString());
        tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

        //------------------Get All layers-------------

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

       // TiledMapTileLayer objects = (TiledMapTileLayer) tileMap.getLayers().get()
        MapObjects objects = null;
        if(tileMap.getLayers().get("objetos") != null) {
            objects = tileMap.getLayers().get("objetos").getObjects();
        }


        if(objects != null){


            createBlocks(objects);
        }else {

            createBlocks(layers[3], Constants.BIT.FLOOR.BIT());
        }


    }

    private void createBlocks(TiledMapTileLayer layer, short bits) {

        // tile size
        float ts = layer.getTileWidth();
        int width = 0;
        Array<Vector2> size = new Array<Vector2>();
        Array<Vector2> coords = new Array<Vector2>();

        //Create World Walls
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(0, 0);
        ChainShape cs = new ChainShape();
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(0, 0);
        v[1] = new Vector2(0, (tileMapHeight * ts) / Constants.PTM);
        v[2] = new Vector2((tileMapWidth * ts) / Constants.PTM, (tileMapHeight * ts) / Constants.PTM);
        v[3] = new Vector2((tileMapWidth * ts) / Constants.PTM, 0);
        cs.createChain(v);

        FixtureDef fd = new FixtureDef();
        fd.friction = 0;
        fd.shape = cs;
        fd.filter.categoryBits = Constants.BIT.WALL.BIT();
        fd.filter.maskBits = Constants.BIT.CHARACTER.BIT();
        Body b = world.createBody(bdef);
        b.createFixture(fd).setUserData(Constants.DATA.CELL);
        cs.dispose();

        // go through all cells in layer
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                // check that there is a cell
                if (cell == null) {
                    if (width > 0) {
                        size.add(new Vector2());
                        size.get(size.size - 1).x = width;
                        coords.get(coords.size - 1).y = row;
                        size.get(size.size - 1).y = 1;
                        if (coords.size > 1) {
                            boolean check = false;
                            for (int x = 0; x < coords.size - 1; x++) {
                                if ((coords.get(coords.size - 1).x == coords.get(x).x &&
                                        size.get(size.size - 1).x == size.get(x).x) && (coords.get(coords.size - 1).y) ==
                                        (coords.get(x).y + size.get(x).y)) {
                                    size.get(x).y++;
                                    check = true;
                                }
                            }
                            if (check) {
                                coords.pop();
                                size.pop();
                            }
                        }
                        width = 0;

                    }
                    continue;
                }
                if (cell.getTile() == null) continue;
                if (width == 0) {
                    coords.add(new Vector2());
                    coords.get(coords.size - 1).x = col;

                }
                width++;

            }
            if (width > 0){
                size.add(new Vector2());
                size.get(size.size - 1).x = width;
                coords.get(coords.size - 1).y = row;
                size.get(size.size - 1).y = 1;
                if (coords.size > 1) {
                    boolean check = false;
                    for (int x = 0; x < coords.size - 1; x++) {
                        if (coords.get(coords.size - 1).x == coords.get(x).x && size.get(size.size - 1).x == size.get(x).x) {
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

        for (int i = 0; i < coords.size; i++) {
            bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((coords.get(i).x * ts / Constants.PTM) + (((size.get(i).x * ts) / 2) / Constants.PTM),
                    (coords.get(i).y * ts / Constants.PTM) + (((size.get(i).y * ts) / 2) / Constants.PTM));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((((size.get(i).x * ts) / 2) / Constants.PTM), (((size.get(i).y * ts) / 2) / Constants.PTM));
            fd = new FixtureDef();
            fd.friction = 1;
            fd.shape = shape;
            fd.filter.categoryBits = bits;
            fd.filter.maskBits = (short) (Constants.BIT.CHARACTER.BIT() | Constants.BIT.FLOOR.BULLET.BIT() |
                                Constants.BIT.GRANADE.BIT() | Constants.BIT.ITEM.BIT());
            b = world.createBody(bdef);
            b.createFixture(fd).setUserData(Constants.DATA.CELL);
            b.setUserData(new Vector2((((size.get(i).x * ts) / 2) / Constants.PTM), (((size.get(i).y * ts) / 2) / Constants.PTM)));
            shape.dispose();
        }
        size.clear();
        coords.clear();

    }

    private void createBlocks(MapObjects objects){

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(0, 0);
        ChainShape cs = new ChainShape();
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(0, 0);
        v[1] = new Vector2(0, (tileMapHeight * tileSize) / Constants.PTM);
        v[2] = new Vector2((tileMapWidth * tileSize) / Constants.PTM, (tileMapHeight * tileSize) / Constants.PTM);
        v[3] = new Vector2((tileMapWidth * tileSize) / Constants.PTM, 0);
        cs.createChain(v);
        FixtureDef fd = new FixtureDef();
        fd.friction = 0;
        fd.shape = cs;
        fd.filter.categoryBits = Constants.BIT.WALL.BIT();
        fd.filter.maskBits = Constants.BIT.CHARACTER.BIT();
        Body b = world.createBody(bdef);
        b.createFixture(fd).setUserData(Constants.DATA.CELL);
        cs.dispose();
        for (MapObject obj : objects){
            if (obj.getProperties().containsKey("clase") && obj.getProperties().get("clase").equals("1")){
                Vector2 c = new Vector2(Float.parseFloat(obj.getProperties().get("x").toString())/ Constants.PTM,
                        Float.parseFloat(obj.getProperties().get("y").toString())/ Constants.PTM);
                Vector2 s = new Vector2((Float.parseFloat(obj.getProperties().get("width").toString())/ Constants.PTM) / 2,
                        (Float.parseFloat(obj.getProperties().get("height").toString())/ Constants.PTM) / 2);
                bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(c.x + s.x,c.y + s.y);
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(s.x,s.y);
                fd = new FixtureDef();
                fd.friction = 1;
                fd.shape = shape;
                fd.filter.categoryBits = Constants.BIT.FLOOR.BIT();
                fd.filter.maskBits = (short) (Constants.BIT.CHARACTER.BIT() | Constants.BIT.FLOOR.BULLET.BIT() |
                        Constants.BIT.GRANADE.BIT() | Constants.BIT.ITEM.BIT());
                b = world.createBody(bdef);
                b.createFixture(fd).setUserData(Constants.DATA.CELL);
                b.setUserData(s);
                shape.dispose();
            }

        }

    }

}
