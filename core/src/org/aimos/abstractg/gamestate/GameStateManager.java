package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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


    public GameStateManager(Launcher game) {
        this.game = game;
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
                return new Splash(this);
            case MENU:
                return new MainMenu(this);
            case WORLD_SELECT:
                return new WorldSelectScreen(this);
            case SOLO_PLAY:
                return new Play(this);
            case LEVEL_SELECT:
                return new LevelSelectScreen(this);
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
        tmp = new Pause(this, gameStates.peek());
    }

    public void setTemOpt(){
        tmp = new Pause(this, gameStates.peek(), true);
    }

    public void disposeTemp(){
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

    public  void  reloadGame(){
        if(tmp != null) {
            disposeTemp();
            pushState(SOLO_PLAY);
        }
    }

    public boolean isPause() {
        return (tmp != null);
    }
}
