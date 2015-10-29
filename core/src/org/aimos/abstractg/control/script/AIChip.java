package org.aimos.abstractg.control.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Enemy;
import org.aimos.abstractg.character.NPC;
import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.control.script.lua.LuaLoader;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * Created by Angyay0 on 28/10/2015.
 */

public class AIChip extends Chunk {

    private Array<LuaValue> functions;

    private Character character;
    private int charType = -1; //Abstract Character

    public AIChip(String type, String language, long parent, int slots) {
        super(type, language, parent);
        functions = new Array<LuaValue>(slots);
    }

    public AIChip(int slots){
        super("DEFAULT","LUA",0);
        functions = new Array<LuaValue>(slots);
    }

    public AIChip(){
        super("DEFAULT","LUA",0);
        functions = new Array<LuaValue>(2);
    }

    public void setCharacter(Character c){
        character = c;

        if( c instanceof Enemy)
            charType = 1;
        else if( c instanceof NPC)
            charType = 2;
        else if( c instanceof Player)
            charType = 3;
        else
            charType = 0;

    }

    public boolean isCharacterSet(){
        if( character != null )
            return true;

        return false;
    }

    @Override
    public void inflate(){;}

    public void initialize(String script){
        functions.add( LuaLoader.getInstance().loadFunction(script,"base") );
    }

    public boolean expandAI(String script,String function){

        if( functions.toArray().length < functions.size ) {
            functions.add( LuaLoader.getInstance().loadFunction(script,function) );
            return true;
        }

        return false;
    }

    @Override
    public void exec() {

        if( functions != null ){
            for (LuaValue function : functions){
                if(!function.isnil()){
                    try{
                        function.call( CoerceJavaToLua.coerce(character) );
                    }catch (Exception e){
                        Gdx.app.error("Error",e.getMessage());
                    }
                }else
                    Gdx.app.debug("LUA MEHTOD","NIL");
            }
        }else{
            Gdx.app.debug("NO FUNCTIONS","NULL");
        }

    }

    @Override
    public void disposeExt() {
        functions = null;
        character = null;
        charType = -1;
    }


}
