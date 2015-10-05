package org.aimos.abstractg.control.script;

/**
 * Created by Alumno on 28/09/2015.
 */

public abstract class Chunk {

    private String script_type;
    private String script_language;
    public long parent;

    public Chunk(String type, String language,long parent){
        script_type = type;
        script_language = language;
        this.parent = parent;
    }

    public abstract void inflate();

    public abstract void exec();

    public abstract Object getBehavior();

    public String getType(){    return script_type; }

    public String getLanguage(){    return script_language; }

    public void dispose(){

    }
}
