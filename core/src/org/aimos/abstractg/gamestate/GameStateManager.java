package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import java.util.Stack;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class GameStateManager {

    private Launcher game;

    private Stack<GameState> gameStates;

    private GameState tmp;

    private boolean paused = false;

    public GameStateManager(Launcher game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(Constants.STATE.SPLASH);
    }

    public void update(float dt) {
        getState().update(dt);
    }

    public void render() {
        getState().render();
    }

    public Launcher game() {
        return game;
    }

    public SpriteBatch getRender(){
        return game.getSpriteBatch();
    }

    private GameState getState(Constants.STATE state) {
        switch(state){
            case SPLASH:
                return new Splash(this).setID(state);
            case MENU:
                return new MainMenu(this).setID(state);
            case WORLD_SELECT:
                return new WorldSelect(this).setID(state);
            case SOLO_PLAY:
                return new SoloPlay(this).setID(state);
            case MULTI_PLAY:
                return new MultiPlay(this).setID(state);
            case LEVEL_SELECT:
                return new LevelSelect(this).setID(state);
            case PAUSE:
                if(getState() instanceof Play) return new Pause(this, (Play)getState()).setID(state);
                else return getState();
            case OPTIONS:
                if(getState() instanceof Play) return new Pause(this, (Play)getState(),true).setID(state);
                else return getState();
            case GAME_OVER:
                if(getState() instanceof Play) return new GameOver(this, (Play)getState()).setID(state);
                else return getState();
            default:
                return null;
        }
    }

    public void setState(Constants.STATE state) {
        popState();
        pushState(state);
    }

    public void pushState(Constants.STATE state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

    public void dispose(){
        for(GameState g : gameStates){
            g.dispose();
        }
    }

    public GameState getState(){
        if(tmp == null){
            return gameStates.peek();
        }else{
            return tmp;
        }
    }

    public void back(){
        getState().back();
    }

    public boolean isPause(){
        return paused;
    }

    public Constants.STATE getStateID() {
        return getState().getID();
    }

}
