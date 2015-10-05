package abstractg.physics;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class Floor {

    private Body body;
    private float width;
    private float height;

    public Floor(Body body, float w, float h){
        this.body = body;
        width = w;
        height = h;
    }
}
