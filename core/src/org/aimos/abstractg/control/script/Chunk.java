package org.aimos.abstractg.control.script;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;

/**
 * Created by Alumno on 28/09/2015.
 */

public abstract class Chunk {

    private String script_type;
    private String script_language;
    public long parent;
    protected Character character;

    public Chunk(String type, String language,long parent){
        script_type = type;
        script_language = language;
        this.parent = parent;
    }
/*
    public void setCharacter(Character c){
        character = c;
    }

    public boolean isCharacterSet(){
        if( character != null )
            return true;

        return false;
    }*/

    public abstract void inflate();

    public abstract void exec();

    public abstract Object getBehavior();

    public String getType(){    return script_type; }

    public String getLanguage(){    return script_language; }

    public void dispose(){

    }
}
