//abstractg->character->Character
package org.aimos.abstractg.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.AimosVars;
import org.aimos.abstractg.handlers.Animation;

/**
 * Clase que representa el modelo abstracto
 * del personaje para los escenarios
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Angyay0,EinarGretch
 * @company AIMOS Studio
 *
 **/

 public abstract class Character{

    protected String name;
    protected Body body;
    protected int animationIndex = 0;
    protected Array<Animation> animations;
    protected World world;
    protected boolean crouch = false;
    protected boolean keepCrouched = false;
    protected boolean visible = true;
    protected boolean walking = false;
    protected TextureAtlas atlas;
    protected int jumps = 1;
    protected int maxJumps = 1;
    protected boolean direction = true;

    //Definicion de Variables para el ATLAS
    protected static final String STAND_SEQ = "breath"; // 0
    protected static final String WALK_SEQ = "walk"; // 1
    protected static final String JUMP_SEQ = "jump"; // 2
    private static final String CROUCH_SEQ = "crouch"; // 3
    private static final String CROUCH_MOVE_SEQ = "crouch"; // 4


    /**
     * Creates a new character
     *
     * @param spriteSrc Archivo Atlas
     * @param name
     */
     protected Character(String spriteSrc, String name, World world, float x, float y) {
         this.name = name;

         try{
            atlas = Launcher.res.getAtlas(spriteSrc);
         }catch(Exception e){
             Gdx.app.log("Error Cargando Atlas de Personaje", e.getMessage());
             Gdx.app.exit();
         }

         this.world = world;
         body = null;
         animations = new Array<Animation>();
         animations.add(new Animation(atlas.findRegions(STAND_SEQ),1/5f));
         animations.add(new Animation(atlas.findRegions(WALK_SEQ),1/5f));
         animations.add(new Animation(atlas.findRegions(JUMP_SEQ),1/5f));
         animations.add(new Animation(atlas.findRegions(CROUCH_SEQ),1/5f));
         animations.add(new Animation(atlas.findRegions(CROUCH_MOVE_SEQ),1/5f));

         setAnimations();
         createBody(x, y);

     }

    public Animation getAnimation(){
        return animations.get(animationIndex);
    }

    public Body getBody(){
        return body;
    }

    public boolean setSprite(int i){
        if(i >= 0 && i < animations.size){
            animationIndex = i;
            return true;
        }
        return false;
    }

    public  void forceCrouch(boolean keepCrouched){
        this.keepCrouched = keepCrouched;
    }


    public boolean setBody(Body body){
        if(body != null) {
            this.body = body;
            return true;
        }
        return false;
    }

    public void setDirection(boolean dir){
        if(direction == dir) return;
        direction = dir;
        if (direction) flip(false, false);
        else flip(true, false);
    }

    public boolean getDirection(){
        return direction;
    }

    public void setWalking(boolean w){
        if(walking == w) return;
        walking = w;
        if(walking){
            if(isCrouching()){
                setAnimation(4);
            }else{
                setAnimation(1);
            }
        }else{
            if(isCrouching()){
                setAnimation(3);
            }else{
                setAnimation(0);
            }
        }
    }

    public boolean isWalking(){
        return walking;
    }

    public boolean canJump(){
        return jumps >= maxJumps;
    };

    public void setCrouching(boolean crouch){
        if(this.crouch == crouch) return;
        this.crouch = crouch;
        if(isOnGround()) {
            if (isCrouching()) {
                if (isWalking()) {
                    setAnimation(3);
                } else {
                    setAnimation(4);
                }
            } else {
                if (isWalking()) {
                    setAnimation(1);
                } else {
                    setAnimation(0);
                }
            }
        }
    }

    public void dispose(){
        atlas.dispose();
        world.destroyBody(body);
    }

    public void setVisibility(boolean visible){
        this.visible = visible;
    }

    public boolean getVisibility(){
        return visible;
    }

    public boolean flipVisibility(){
        visible = !visible;
        return visible;
    }

    protected Animation makeAnimation(String name, int count){
        TextureAtlas.AtlasRegion[] regions = new AtlasRegion[count];
        for (int i = 0; i < count; i++) {
            regions[i] = (atlas.findRegion(name+"_"+count));
        }
        return new Animation(regions, 0.1f);
    }

    public int getWidth(){
        return animations.get(animationIndex).getFrame().getRegionWidth();
    }

    public int getHeight(){
        return animations.get(animationIndex).getFrame().getRegionHeight();
    }

    public void setPosition(float x, float y){
        body.getPosition().x = x;
        body.getPosition().y = y;
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getY(){
        return body.getPosition().y;
    }

    public void setAnimation(int i) {
        if(animationIndex == i) return;
        animationIndex = i;
        updateBody(i);
    }

    public void update(float dt) {
        animations.get(animationIndex).update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(animations.get(animationIndex).getFrame(), getX() * AimosVars.PTM - (getWidth() / 2), getY() * AimosVars.PTM - (getHeight() / 2));
        sb.end();
    }

    public boolean jump(){
        if(isCrouching()){
            return false;
        }else {
            float forceY = (getBody().getMass() * (5f / (1 / 60.0f))); // f = mv/t
            if (jumps > 0) {
                setAnimation(2);
                getBody().applyForce(new Vector2(0, forceY), getBody().getWorldCenter(), true);
                jumps--;
                return true;
            } else {
                return false;
            }
        }
    }

    public void flip(boolean x, boolean y){
        for (Animation a: animations) {
            a.flip(x,y);
        }
    }

    public boolean isOnGround(){
        return jumps == maxJumps;
    }

    public void onGround(){
        if(isOnGround()) return;
        jumps = maxJumps;
        if(isWalking()) {
            if(isCrouching()) {
                setAnimation(4);
            }else{
                setAnimation(1);
            }
        }else {
            if(isCrouching()) {
                setAnimation(3);
            }else{
                setAnimation(0);
            }
        }
    }

    public boolean move(boolean direction){
        float desiredVelX;
        setDirection(direction);
        if(direction){
            if(isOnGround()) {
                if(isCrouching()) {
                    desiredVelX = 0.5f;
                }else{
                    desiredVelX = 0.7f;
                }
            }else{
                desiredVelX = 0.1f;
            }
        }else{
            if(isOnGround()) {
                if(isCrouching()) {
                    desiredVelX = -0.5f;
                }else{
                    desiredVelX = -0.7f;
                }
            }else{
                desiredVelX = -0.1f;
            }
        }
        float forceX = (float) (getBody().getMass() * desiredVelX / (1 / 60.0)); // f = mv/t
        getBody().applyForce(new Vector2(forceX, 0), getBody().getWorldCenter(), true);
        return true;
    }

    public boolean isCrouching(){
        return crouch;
    }

    public void createBody(float x, float y) {
        // create bodydef
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / AimosVars.PTM, y / AimosVars.PTM);

        // create body from bodydef
        body = world.createBody(bodyDef);

        // create box shape for character collision box
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((to2DBoxSize(getWidth())) / AimosVars.PTM, (to2DBoxSize(getHeight())) / AimosVars.PTM);

        // create fixturedef for character collision box
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = AimosVars.BIT_CHARACTER;
        fdef.filter.maskBits = AimosVars.BIT_FLOOR | AimosVars.BIT_WALL | //AimosVars.BIT_GRANADE |
                AimosVars.BIT_BULLET;
        fdef.restitution = 0f;

        // create character collision box fixture
        body.createFixture(fdef).setUserData("body");
        shape.dispose();

        //set character extra fixtures
        createBodyExtra(x, y);
        //sets body's userData as this player
        body.setUserData(this);

        // sets the character body mass to 1 kg
        MassData md = body.getMassData();
        md.mass = 1;
        body.setMassData(md);

        body.setFixedRotation(true);
    }

    public void updateBody(int pose){
        Fixture fix = getBody().getFixtureList().get(0);
        PolygonShape shape = (PolygonShape) fix.getShape();

        switch (pose){
            case 0:
            case 1:
            case 2:
                shape.setAsBox(to2DBoxSize(getWidth()) / AimosVars.PTM, to2DBoxSize(getHeight()) / AimosVars.PTM);
                fix = getBody().getFixtureList().get(1);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox((to2DBoxSize(getWidth() / 2)) / AimosVars.PTM, (to2DBoxSize(getHeight() / 4)) / AimosVars.PTM, new Vector2(0, (-to2DBoxSize(getHeight())) / AimosVars.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox((to2DBoxSize(getWidth() / 2)) / AimosVars.PTM, (to2DBoxSize(getHeight() / 4)) / AimosVars.PTM, new Vector2(0, (to2DBoxSize(getHeight())) / AimosVars.PTM), 0);
                break;
            case 3:
            case 4:
                shape.setAsBox(to2DBoxSize(getWidth()) * 1.25f / AimosVars.PTM, to2DBoxSize(getHeight()) * 0.62f / AimosVars.PTM);
                fix = getBody().getFixtureList().get(1);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox((to2DBoxSize(getWidth() / 2)) / AimosVars.PTM, (to2DBoxSize(getHeight() / 4)) / AimosVars.PTM, new Vector2(0, (-to2DBoxSize(getHeight()) / 2) / AimosVars.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox((to2DBoxSize(getWidth() / 2)) / AimosVars.PTM, (to2DBoxSize(getHeight() / 4)) / AimosVars.PTM, new Vector2(0, (to2DBoxSize(getHeight()) / 2) / AimosVars.PTM), 0);
                break;
        }
        getBody().applyForce(new Vector2(0, 0), getBody().getWorldCenter(), true);
    }

    public void shootAtk(){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set(new Vector2(getX(),getY()));

        Body bullet = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(5f / AimosVars.PTM);
        fdef.shape = shape;
        fdef.friction = 0;
        fdef.density = 1;
        fdef.filter.categoryBits = AimosVars.BIT_BULLET;
        fdef.filter.maskBits = AimosVars.BIT_FLOOR | AimosVars.BIT_WALL | AimosVars.BIT_GRANADE |
                AimosVars.BIT_CHARACTER | AimosVars.BIT_BULLET;
        fdef.restitution = 0.1f;

        bullet.createFixture(fdef).setUserData("bullet");
        shape.dispose();

        MassData mass = bullet.getMassData();
        mass.mass = 0.2f;
        body.setMassData(mass);

        bullet.applyForce(new Vector2((bullet.getMass() * (getBody().getLinearVelocity().x + 30.0f) / (1 / 60.0f)), 0), bullet.getWorldCenter(), true);
    }

    public void throwAtk(){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(getPosition().cpy());

        Body granade = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(10f / AimosVars.PTM);
        fdef.shape = shape;
        fdef.friction = 1;
        fdef.density = 1;
        fdef.filter.categoryBits = AimosVars.BIT_GRANADE;
        fdef.filter.maskBits = AimosVars.BIT_FLOOR | AimosVars.BIT_WALL | AimosVars.BIT_CHARACTER |
                AimosVars.BIT_BULLET;
        fdef.restitution = 0.5f;
        granade.createFixture(fdef).setUserData("granade");

        shape.setRadius(100f / AimosVars.PTM);
        fdef.filter.maskBits = AimosVars.BIT_FLOOR | AimosVars.BIT_WALL | AimosVars.BIT_GRANADE |
                AimosVars.BIT_CHARACTER | AimosVars.BIT_BULLET;
        fdef.isSensor = true;
        granade.createFixture(fdef).setUserData("granade_exp");

        shape.dispose();

        MassData mass = granade.getMassData();
        mass.mass = 0.5f;
        body.setMassData(mass);

        granade.applyForce(new Vector2((granade.getMass() * (getBody().getLinearVelocity().x + 3.5f) / (1 / 60.0f)),
                (granade.getMass() * (getBody().getLinearVelocity().y + 3.0f) / (1 / 60.0f))), granade.getWorldCenter(), true);
    }

    public void meeleAtk(){

    }

    public  boolean isForceCrouched(){
        return keepCrouched;
    }

    public float to2DBoxSize(float f){
        return f / 2.1f;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }


    public abstract void createBodyExtra(float x, float y);

    public abstract void setAnimations();
}
