package org.aimos.abstractg.control.script.lua;

import com.badlogic.gdx.Gdx;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;
import org.luaj.vm2.LuaValue;


import org.aimos.abstractg.control.script.Chunk;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

/**
 * Created by Angyay0 on 28/09/2015.
 */

public class LuaChunk extends Chunk {

    private LuaValue behavior;

    private Character character;
    private int charType = 0; //Abstract Character

    public LuaChunk(String type, String lang, long parent){
        super(type,lang,parent);
    }

    public void setBehavior(LuaValue lv){
        behavior = lv;
    }

    public void setCharacter(Character c){
        character = c;

        if( c instanceof Enemy )
            charType = 1;
        else if( c instanceof NPC )
            charType = 2;
        else if( c instanceof Player )
            charType = 3;

    }

   // @Override
    public boolean isCharacterSet(){
        if( character != null )
            return true;

        return false;
    }

    @Override
    public void inflate() {
        if( behavior.isnil() ){
        //   behavior = LuaLoader.GLOBALS.loadfile("basic.lua").get(0);
        }
    }

    @Override
    public void exec() {
        if( behavior != null ){
            if( !behavior.isnil() ){
                try{
                    behavior.call( CoerceJavaToLua.coerce(character) );
                }catch(Exception  e){
                    Gdx.app.error("Error",e.getMessage());
                }
            }else
                Gdx.app.debug("LUA MEHTOD","NIL");
        }

    }

    @Override
    public void disposeExt() {

    }

    // @Override
    public Object getBehavior() {
        return behavior;
    }
}
