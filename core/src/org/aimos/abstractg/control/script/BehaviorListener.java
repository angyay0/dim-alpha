package org.aimos.abstractg.control.script;

/**
 * Created by Angyay0 on 06/10/2015.
 */
public interface BehaviorListener {

    public void act();

    public void loadScript();

    public void loadScript(String file);

    public void setSelfToScript();
}
