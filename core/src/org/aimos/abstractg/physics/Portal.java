package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;

/**
 * Created by Angyay0 on 03/11/2015.
 */
public class Portal extends Item {
    public boolean side = true;//false = izq
    private ParticleEffect particle_A,particle_B;
    //Variable para obtener los tiles que se usaran en el portal
    StaticTiledMapTile[][] portalTiles = new StaticTiledMapTile[10][4];
    //Variable para obtener las celdas en donde se pintara el portal
    TiledMapTileLayer.Cell[] parts = new TiledMapTileLayer.Cell[10];
    //indice para cambiar el cuadro del portal
    int index = 0;
    //Variable para obtener el tiempo en el que se cambiara el cuadro del portal
    float elapseTime = 0;
    public Portal(Play p,TiledMap tiledMap) {
        super(p);

        //----------------Obtener los tiles del portal---------------------

        TiledMapTileSet tileset =tiledMap.getTileSets().getTileSet("portal");
        for(int i = 0; i < portalTiles.length; i++) {
            index = 0;
            for (TiledMapTile tile : tileset) {
                Object property = tile.getProperties().get("part" + (i + 1));
                if (property != null) {
                    System.out.print(property);
                    portalTiles[i][index] = new StaticTiledMapTile(tile.getTextureRegion());
                    index++;
                }
            }
        }
        index = 0;
        //---------Obtener los tiles del portal dentro de la capa del suelo----------
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get("floor");
        for(int i = 0; i < parts.length; i++){
            for(int x = 0; x < layer.getWidth();x++){
                for(int y = 0; y < layer.getHeight();y++){
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
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

        MapObjects objects = tiledMap.getLayers().get("objetos").getObjects();
        createBody(objects);



        /*setSprite(new Sprite(new Texture(Gdx.files.internal("data/portal.png"))));
        if( !side )
            getSprite().flip(true,false);*/



    }


    protected void createBody(MapObjects objects) {
        for (MapObject obj : objects){
            if (obj.getProperties().containsKey("clase") && obj.getProperties().get("clase").equals("p")) {
                Vector2 c = new Vector2(Float.parseFloat(obj.getProperties().get("x").toString())/ Constants.PTM,
                        Float.parseFloat(obj.getProperties().get("y").toString())/ Constants.PTM);
                Vector2 s = new Vector2((Float.parseFloat(obj.getProperties().get("width").toString())/ Constants.PTM) / 2,
                        (Float.parseFloat(obj.getProperties().get("height").toString())/ Constants.PTM) / 2);
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(c.x + s.x,c.y + s.y);
                //bodyDef.fixedRotation = true;
                createBody(bodyDef);
/*
        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth() / 2) / Constants.PTM);*/
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(s.x,s.y);

                FixtureDef fdef = new FixtureDef();
                fdef.shape = shape;
                fdef.density = 1;
                fdef.friction = 1f;
                fdef.filter.categoryBits = (short) (Constants.BIT.ITEM.BIT() | Constants.BIT.PORTAL.BIT());
                fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.CHARACTER.BIT() ); //cambiar por player
                fdef.restitution = 0.4f;

                // create character collision box fixture
                getBody().createFixture(fdef).setUserData(Constants.DATA.PORTAL);
                shape.dispose();

                getBody().setUserData(this);
                this.side = side;
                //float a = MathUtils.atan2(-partyPos.y, -partyPos.x) * MathUtils.radiansToDegrees;


                particle_A = new ParticleEffect();
                particle_A.load(Gdx.files.internal("data/portal.p"), Gdx.files.internal("data"));
                float scale = particle_A.getEmitters().first().getScale().getHighMax();
                particle_A.getEmitters().first().setPosition(c.x * Constants.PTM, c.y * Constants.PTM);
                particle_A.getEmitters().first().getScale().setHigh(scale * 1f);/*
        particle_A.getEmitters().first().getAngle().setHigh( (a+90f) , (a+90f) );*/
                //particle_A.getEmitters().first().getAngle().setLow(a);

                particle_B = new ParticleEffect();
                particle_B.load(Gdx.files.internal("data/particles.p"), Gdx.files.internal("data"));
                scale = particle_B.getEmitters().first().getScale().getHighMax();
                particle_B.getEmitters().first().setPosition(c.x * Constants.PTM, c.y * Constants.PTM);
                particle_B.getEmitters().first().getScale().setHigh( scale * 0.70f);/*
        particle_B.getEmitters().first().getAngle().setHigh( (a +90f) , (a+90f) );*/
                //particle_B.getEmitters().first().getAngle().setLow( a );
/**/

                particle_A.start();
                particle_B.start();
                break;
            }

        }
    }

    @Override
    public void render(SpriteBatch sb){
        //super.render(sb);
        //Efecto del portal
        elapseTime += Gdx.graphics.getDeltaTime();
        if(elapseTime > 0.1f) {
            for (int i = 0; i < parts.length; i++){
                if(parts[i] != null) {
                    parts[i].setTile(portalTiles[i][index]);
                }
            }
            index++;
            elapseTime = 0.0f;
        }
        if(index == (portalTiles[0].length)){
            index = 0;
        }
        sb.begin();
        particle_A.draw(sb,Gdx.graphics.getDeltaTime());
        particle_B.draw(sb,Gdx.graphics.getDeltaTime());
        sb.end();

        if (particle_A.isComplete())
            particle_A.reset();

        if (particle_B.isComplete())
            particle_B.reset();

    }

    @Override
    protected void createBody(Vector2 pos) {

    }

}
