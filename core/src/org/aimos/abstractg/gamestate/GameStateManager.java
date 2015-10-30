package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.aimos.abstractg.character.Player;
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
                return new WorldSelectScreen(this).setID(state);
            case SOLO_PLAY:
                return new SoloPlay(this).setID(state);
            case MULTI_PLAY:
                return new MultiPlay(this).setID(state);
            case LEVEL_SELECT:
                return new LevelSelect(this).setID(state);
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

    public void setTempState(){
        paused = true;
        tmp = new Pause(this, gameStates.peek());
    }

    public void setTemOpt(){
        tmp = new Pause(this, gameStates.peek(), true).setID(Constants.STATE.PAUSE);
    }

    public void disposeTemp(){
        paused = false;
        tmp.dispose();
        tmp = null;
    }

    public GameState getState(){
        if(tmp == null){
            return gameStates.peek();
        }else{
            return tmp;
        }
    }


    public void backToMenu(){
        if(tmp != null) {
            disposeTemp();
            pushState(Constants.STATE.MENU);
        }
    }

    public void back(){
        getState().back();
    }

    public boolean isPause(){
        return paused;
    }

    public void reloadGame() {
       if(tmp != null){
           disposeTemp();
       }
        pushState(Constants.STATE.SOLO_PLAY);
    }

    public void gameOver(long p){
        gameStates.push(new GameOver(this, p).setID(Constants.STATE.GAME_OVER));
    }

    public Constants.STATE getStateID() {
        return getState().getID();
    }

}
