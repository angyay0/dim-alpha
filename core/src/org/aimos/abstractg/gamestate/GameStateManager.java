package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

import java.util.Stack;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class GameStateManager {
    //
    private Launcher game;
    //
    private Stack<GameState> gameStates;
    //
    private Stack<GameState> disposed;

    /**
     *
     * @param game
     */
    public GameStateManager(Launcher game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        disposed = new Stack<GameState>();
        pushState(Constants.STATE.SPLASH);
    }

    /**
     *
     * @param dt
     */
    public void update(float dt) {
        getState().update(dt);
        while(!disposed.empty()){
            GameState g = disposed.pop();
            g.dispose();
        }
        if(!Gdx.input.getInputProcessor().equals(getState())) Gdx.input.setInputProcessor(getState());
    }

    /**
     *
     */
    public void render() {
        getState().render();
    }

    /**
     *
     * @return
     */
    public Launcher game() {
        return game;
    }

    /**
     *
     * @return
     */
    public SpriteBatch getRender(){
        return game.getSpriteBatch();
    }

    /**
     *
     * @param state
     * @return
     */
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
                if(getState() instanceof MainMenu) return new Pause(this, (MainMenu)getState(),true).setID(state);
                else return getState();
            case GAME_OVER:
                if(getState() instanceof Play) return new GameOver(this, (Play)getState()).setID(state);
                else return getState();
            case WINNER:
                if(getState() instanceof Play) return new WinScreen(this,(Play)getState()).setID(state);
                else return getState();
            default:
                return null;
        }
    }

    /**
     *
     * @param state
     */
    public void setState(Constants.STATE state) {
        popState();
        pushState(state);
    }

    /**
     *
     * @param state
     */
    public void pushState(Constants.STATE state) {
        gameStates.push(getState(state));
    }

    /**
     *
     */
    public void popState() {
        GameState g = gameStates.pop();
        disposed.push(g);
    }

    /**
     *
     */
    public void doublePopState() {
        GameState g1 = gameStates.pop();
        GameState g2 = gameStates.pop();
        disposed.push(g2);
        disposed.push(g1);
    }

    /**
     *
     * @param state
     */
    public void popAndSetState(Constants.STATE state) {
        GameState g = gameStates.pop();
        setState(state);
        disposed.push(g);
    }

    /**
     *
     */
    public void dispose(){
        for(GameState g : gameStates){
            g.dispose();
        }
    }

    /**
     *
     * @return
     */
    public GameState getState(){
        return gameStates.peek();
    }

    /**
     *
     */
    public void back(){
        getState().back();
    }

    /**
     *
     * @return
     */
    public Constants.STATE getStateID() {
        return getState().getID();
    }

}
