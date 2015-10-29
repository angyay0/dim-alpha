package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.core.Launcher;

import java.util.Stack;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class GameStateManager {

    private Launcher game;

    private Stack<GameState> gameStates;

    private GameState tmp;

    public static final int SPLASH = 0;
    public static final int MENU = 1;
    public static final int WORLD_SELECT = 2;
    public static final int SOLO_PLAY = 3;
    public static final int LEVEL_SELECT =4;
    public static final int MULTI_PLAY = 5;
    public static final int PAUSE = 6;
    public static final int GAME_OVER = 7;

    private boolean inPause = false;

    public GameStateManager(Launcher game) {
        this.game = game;7
        gameStates = new Stack<GameState>();
        pushState(SPLASH);
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

    private GameState getState(int state) {
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

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
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
        isPaused = true;
        tmp = new Pause(this, gameStates.peek());
    }

    public void setTemOpt(){
        tmp = new Pause(this, gameStates.peek(), true).setID(PAUSE_MODE);
    }

    public void disposeTemp(){
        isPaused = false;
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
            pushState(MENU);
        }
    }

    public void back(){
        getState().back();
    }

    public boolean isPause(){
        return isPaused;
    }

    public void reloadGame() {
       if(tmp != null){
           disposeTemp();
       }
        pushState(SOLO_PLAY);
    }

    public void gameOver(Player p){
        gameStates.push(new GameOver(this, p).setID(GAME_OVER));
    }

    public int getStateID() {
        return getState().getID();
    }

}
