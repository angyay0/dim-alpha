package org.aimos.abstractg.control.script.lua;

import org.luaj.vm2.LuaValue;


import org.aimos.abstractg.control.script.Chunk;

/**
 * Created by Angyay0 on 28/09/2015.
 */

public class LuaChunk extends Chunk {

    private LuaValue behavior;

    public LuaChunk(String type, String lang, long parent){
        super(type,lang,parent);
    }

    public void setBehavior(LuaValue lv){
        behavior = lv;
    }

    @Override
    public void inflate() {

    }

    @Override
    public void exec() {

    }

    @Override
    public Object getBehavior() {
        return behavior;
    }
}
