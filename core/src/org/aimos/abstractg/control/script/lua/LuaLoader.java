package org.aimos.abstractg.control.script.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.Gdx;

import java.io.InputStream;

import org.aimos.abstractg.control.script.ScriptLoader;
import org.aimos.abstractg.control.script.Config;
import org.aimos.abstractg.control.script.Chunk;

/**
 * Created by Angyay0 on 28/09/2015.
 */

public class LuaLoader implements ScriptLoader,ResourceFinder {

    private static LuaLoader loader = null;

    private static final String URL = "scripts/";

    private Globals GLOBALS;

    private Config conf;

    private long id;

    private LuaLoader(){;}

    public static LuaLoader getInstance(){
        if( loader == null ){
            loader = new LuaLoader();
            loader.setGlobals();
            loader.id = (long) (Math.random()*1000.0);
        }

        return loader;
    }

    private void setGlobals(){
        GLOBALS = JsePlatform.standardGlobals();
        GLOBALS.finder = this;
    }

    @Override
    public void setup() {
        conf = new Config(Gdx.files.internal(URL+"conf.lua").file());
    }

    @Override
    public void setup(Config conf) {
        this.conf = conf;
    }

    @Override
    public Chunk loadIAScript(String file) {
        return null;
    }

    @Override
    public Chunk loadBehaviorScript(String file) {
        return null;
    }

    @Override
    public Chunk loadScript() {
        //Try Script
        //basic.lua
        LuaChunk chunk = new LuaChunk("TestScript","LUA",getId());
       // chunk.setFile( Gdx.files.internal(URL+"script.lua") );
        chunk.setBehavior( GLOBALS.get("behavior") );
        chunk.inflate();
        return chunk;
    }

    @Override
    public void destroy() {
        ;
    }

    @Override
    public void destroyChildren() {

    }

    @Override
    public long getId(){
        if( loader == null )
            return -1;

        return loader.id;
    }

    @Override
    public InputStream findResource(String filename) {
        try{
            return Gdx.files.internal(URL+filename).read();
        }catch(Exception e){
            return null;
        }
    }
}
