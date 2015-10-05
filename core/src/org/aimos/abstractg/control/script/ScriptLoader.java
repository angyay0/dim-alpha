package org.aimos.abstractg.control.script;

/**
 * Created by Angyay0 on 28/09/2015.
 */

public interface ScriptLoader {

    public void setup();

    public void setup(Config config);

    public Chunk loadIAScript(final String file);

    public Chunk loadBehaviorScript(final String file);

    public Chunk loadScript();

    public void destroy();

    public void destroyChildren();

    public long getId();

}
