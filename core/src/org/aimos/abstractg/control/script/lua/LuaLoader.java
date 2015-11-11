package org.aimos.abstractg.control.script.lua;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Resources;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
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

    public static Globals GLOBALS;

    private Config conf;

    private long id;

    private LuaLoader(){;}

    public static LuaLoader getInstance(){
        if( loader == null ){
            loader = new LuaLoader();
            GLOBALS = JsePlatform.standardGlobals();
            loader.setGlobals();
            loader.id = (long) (Math.random()*1000.0);
        }

        return loader;
    }

    private void setGlobals(){
        GLOBALS = JsePlatform.standardGlobals();
        GLOBALS.finder = this;

        Gdx.app.debug("SETTED","GLOBALS");
    }

    @Override
    public void setup() {
        conf = new Config(Gdx.files.internal(URL+"conf.lua").file());
    }

    @Override
    public LuaChunk loadIAScript(String file) {

        LuaChunk chunk = new LuaChunk("TestScript","LUA",getId());
        //;
        //GLOBALS.load
        GLOBALS.loadfile(file).call();
        try{
            chunk.setBehavior( GLOBALS.get("behavior")  );
        }catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
      //  chunk.inflate();
        return chunk;
    }

    @Override
    public LuaChunk loadBehaviorScript(String file) {
        return null;
    }

    @Override
    public LuaChunk loadScript() {
        //Try Script
        //basic.lua
        LuaChunk chunk = new LuaChunk("TestScript","LUA",getId());
       //;
        //GLOBALS.load
        GLOBALS.loadfile("basic.lua").call();
        try{
            chunk.setBehavior( GLOBALS.get("behavior")  );
        }catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
       // chunk.inflate();
        return chunk;
    }

    @Override
    public LuaValue loadFunction(String file, String function) {
        return null;
    }

    @Override
    public void destroy() {
        ;
    }

    @Override
    public void destroyChildren() {
        ;
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
            Gdx.app.error("Error Loading Script",e.getMessage());
            return null;
        }
    }
}
