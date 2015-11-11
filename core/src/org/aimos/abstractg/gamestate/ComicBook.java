package org.aimos.abstractg.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by angyay0 on 06/11/2015.
 */

public class ComicBook extends GameState {

    private boolean showComic = false;
    private Texture comic;

    protected ComicBook(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        if( showComic ){

        }
    }

    @Override
    protected void disposeState() {

    }

    @Override
    public void back() {

    }

    public void setComicToShow(int numComic,boolean canExit){
      /*  try {
            comic = ComicLoader.getInstance().readComicByNum(numComic);
        }catch (Exception e){
            Gdx.app.debug("Mensaje de Error :>",e.getMessage());
        }*/
        if( canExit ){
            initButtons();
        }
    }

    public void showMe() {
        showComic = (comic!=null);
    }

    public void initButtons(){

    }

}
