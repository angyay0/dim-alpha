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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
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

    //Variable del mundo
    World world;
    //Variable del mapa
    TiledMap tileMap;
    //variable para renderizar el mapa
    OrthogonalTiledMapRenderer tmRenderer;
    //Variables para obtener las dimensiones del mundo
    int tileMapWidth, tileMapHeight, tileSize;
    //Variables para obtener las capas del mapa
    TiledMapTileLayer[] layers = new TiledMapTileLayer[5];
    //Variables para obtener las diferentes camaras del mundo
    BoundedCamera floor, fx, xFx, b2dCam;
    //Variable para renderizar las fisicas del mundo
    private Box2DDebugRenderer b2dRenderer;
    //Variable para debuggear el mundo
    private boolean debug = true;
    //Variable para obtener al jugador
    private Player player;
    //Variable para obtener los tiles que se usaran en el portal
    StaticTiledMapTile[][] portalTiles = new StaticTiledMapTile[10][4];
    //Variable para obtener las celdas en donde se pintara el portal
    TiledMapTileLayer.Cell[] parts = new TiledMapTileLayer.Cell[10];
    //indice para cambiar el cuadro del portal
    int index = 0;
    //Variable para obtener el tiempo en el que se cambiara el cuadro del portal
    float elapseTime = 0;
    public MapLoader(World world, Player player){
        super();
        this.world = world;
        this.player = player;
        b2dRenderer = new Box2DDebugRenderer();
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

    /**
     * Funcion para obtener el alto del mapa
     * */
    public int getHeight(){
        return tileMapHeight;
    }
    /**
     * Función que sirve para renderizar el mundo
     * */
    public void render() {

        tmRenderer.getBatch().begin();

        if (fx.position.x >= ((tileMapWidth * tileSize) + (Launcher.WIDTH / 2))) {
            fx.position.x = -(tileMapWidth * tileSize);
        }
        if (xFx.position.x >= ((tileMapWidth * tileSize) + (Launcher.WIDTH / 2))) {
            xFx.position.x = -(tileMapWidth * tileSize);
        }
        //Efecto de movimiento de las nubes
        fx.translate(1, 0, 0);
        xFx.translate(.25f, 0, 0);
        //Camara del mapa siguiendo al jugador
        floor.setPosition(player.getX() * Constants.PTM + Launcher.WIDTH / 4, player.getY() * Constants.PTM + Launcher.HEIGHT / 4);
        //actualización de camaras
        fx.update();
        xFx.update();
        floor.update();
        //Dibujado de los layers
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

        //Efecto del portal
        elapseTime += Gdx.graphics.getDeltaTime();
        if(elapseTime > 0.1f) {
            for (int i = 0; i < parts.length; i++){
                parts[i].setTile(portalTiles[i][index]);
            }
            index++;
            elapseTime = 0.0f;
        }
        if(index == (portalTiles[0].length)){
            index = 0;
        }

        //Debuggeado de las fisicas
        if (debug) {
            b2dCam.setPosition(player.getX() + Launcher.WIDTH / 4 / Constants.PTM, player.getY() + Launcher.HEIGHT / 4 / Constants.PTM);
            b2dCam.update();
            b2dRenderer.render(world, b2dCam.combined);
        }
    }

    /**
     * Función que retorna la camara del piso
     * @return floor
     *
     * */
    public BoundedCamera getFloorCamera() {
        return floor;
    }
    /**
     *
     * Funcion para obtener el mapa,layers y objetos a pintar
     *
     * */

    private void createMap() {

        // load tile map and map renderer
        try {
           tileMap = new TmxMapLoader().load(Play.getMap() + "/" + Play.getMap() + ".tmx");
            System.out.println(tileSize);
        } catch (Exception e) {
            Gdx.app.error("ERROR ", "Cannot find file: " + Play.getMap() + ".tmx");
            Gdx.app.exit();
        }

        //-----------------Obtener las dimensiones del mapa----------------------------

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



        //----------------Obtener los tiles del portal---------------------

        TiledMapTileSet tileset =tileMap.getTileSets().getTileSet("portal");
        for(int i = 0; i < portalTiles.length; i++) {
            index = 0;
            for (TiledMapTile tile : tileset) {
                Object property = tile.getProperties().get("part" + (i + 1));
                if (property != null) {
                    portalTiles[i][index] = new StaticTiledMapTile(tile.getTextureRegion());
                    index++;
                }
            }
        }
        index = 0;
        //---------Obtener los tiles del portal dentro de la capa del suelo----------
        for(int i = 0; i < parts.length; i++){
            for(int x = 0; x < layers[3].getWidth();x++){
                for(int y = 0; y < layers[3].getHeight();y++){
                    TiledMapTileLayer.Cell cell = layers[3].getCell(x, y);
                    Object property = null;
                    if (cell!= null) {
                            if (cell.getTile().getProperties().get("part" + (i+1)) != null) {
                                property = cell.getTile().getProperties().get("part" +(i +1));
                            }
                            if(property != null){
                                parts[i] = cell;
                            }
                        }
                    }
                }
        }

        //Mandar a pintar las fisicas del mundo con objetos o sin objetos
        MapObjects objects = null;
        if(tileMap.getLayers().get("objetos") != null) {
            objects = tileMap.getLayers().get("objetos").getObjects();
        }
        if(objects != null){
            createBlocks(objects);
        }else {
            createBlocks(layers[3]);
        }
    }

    /**
     *
     * Función que sirve para crear las fisicas del mundo sin objetos
     * @param layer sirve para obtener las celdas del layer en donde se dibujaran las fisicas
     *
     */
    private void createBlocks(TiledMapTileLayer layer) {

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
        //Algorimo para obtener las dimensiones de las fisicas a pintar
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
        //Pintar las fisicas en el mundo
        for (int i = 0; i < coords.size; i++) {
            bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((coords.get(i).x * tileSize / Constants.PTM) + (((size.get(i).x * tileSize) / 2) / Constants.PTM),
                    (coords.get(i).y * tileSize / Constants.PTM) + (((size.get(i).y * tileSize) / 2) / Constants.PTM));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((((size.get(i).x * tileSize) / 2) / Constants.PTM), (((size.get(i).y * tileSize) / 2) / Constants.PTM));
            fd = new FixtureDef();
            fd.friction = 1;
            fd.shape = shape;
            fd.filter.categoryBits = Constants.BIT.FLOOR.BIT();
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
    /**
     *
     * Funcion que sirve para crear las fisicas del mundo con objetos
     * @param objects parametro para obtener los objetos con los que se pintara el mundo
    * */
    private void createBlocks(MapObjects objects){
        //Crea las paredes del mundo
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
        //Algoritmo para crear las fisicas del mundo
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
