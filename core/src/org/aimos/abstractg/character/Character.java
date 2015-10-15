//abstractg->character->Character
package org.aimos.abstractg.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.control.script.BehaviorListener;
import org.aimos.abstractg.control.script.lua.LuaChunk;
import org.aimos.abstractg.control.script.lua.LuaLoader;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Animation;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.physics.Interactive;
import org.aimos.abstractg.physics.PhysicalBody;
import org.aimos.abstractg.physics.Weapon;

/**
 * Clase que representa el modelo abstracto
 * del personaje para los escenarios
 *
 * @author Angyay0, EinarGretch
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public abstract class Character extends PhysicalBody implements BehaviorListener, Runnable {

    protected String name;
    protected int animationIndex = 0;
    protected Array<Animation> animations;
    protected boolean crouch = false;
    protected boolean keepCrouched = false;
    protected boolean walking = false;
    protected TextureAtlas atlas;
    protected int jumps = 1;
    protected int maxJumps = 1;
    protected boolean direction = true;
    protected Weapon weapon;
    protected Interactive interactive;
    // Hp value of the character
    protected long hp;
    // Base Attack value of the character
    protected long atk;
    // Score the character has or gives when killed
    protected long score;
    // Current money the character has or gives when killed
    protected long money;

    //Constants
    protected float ANIMATION_DELTA = 1 / 5f;
    public final float BODY_SCALE = 2.2f;

    //Definicion de Variables para el ATLAS
    protected static final String STAND_SEQ = "breath"; // 0
    protected static final String WALK_SEQ = "walk"; // 1
    protected static final String JUMP_SEQ = "jump"; // 2
    protected static final String CROUCH_SEQ = "crouch"; // 3
    protected static final String CROUCH_MOVE_SEQ = "crouch"; // 4
    protected static final String FALL_SEQ = "jump"; // 5

    protected boolean jumping;
    protected boolean invencible = false;
    protected long attack;
    protected boolean transition;
    protected LuaChunk iaChunk;

    /**
     * @param spriteSrc
     * @param name
     * @param world
     * @param pos
     */
    protected Character(String spriteSrc, String name, World world, Vector2 pos) {
        super(world);
        this.name = name;
        atlas = Launcher.res.getAtlas(spriteSrc);
        animations = new Array<Animation>();
        animations.add(new Animation(atlas.findRegions(STAND_SEQ), ANIMATION_DELTA));
        animations.add(new Animation(atlas.findRegions(WALK_SEQ), ANIMATION_DELTA));
        animations.add(new Animation(atlas.findRegions(JUMP_SEQ), ANIMATION_DELTA));
        animations.add(new Animation(atlas.findRegions(CROUCH_SEQ), ANIMATION_DELTA));
        animations.add(new Animation(atlas.findRegions(CROUCH_MOVE_SEQ), ANIMATION_DELTA));
        animations.add(new Animation(atlas.findRegions(FALL_SEQ), ANIMATION_DELTA));
        setExtraAnimations();
        initBody(pos);
    }

    public Animation getAnimation() {
        return animations.get(animationIndex);
    }

    public void forceCrouch(boolean keepCrouched) {
        this.keepCrouched = keepCrouched;
    }

    public void setDirection(boolean dir) {
        if (direction == dir) return;
        direction = dir;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setWalking(boolean w) {
        if (walking == w) return;
        walking = w;
        if (walking) {
            if (isCrouching()) {
                setAnimation(4);
            } else {
                setAnimation(1);
            }
        } else {

            float velX = -getBody().getLinearVelocity().x * .96f;
            float velY = -getBody().getLinearVelocity().y * .96f;
            float forceX = (float) (getBody().getMass() * velX / (1 / 60.0)); // f = mv/t
            float forceY = (float) (getBody().getMass() * velY / (1 / 60.0)); // f = mv/t
            getBody().applyForce(new Vector2(forceX, forceY), getBody().getWorldCenter(), true);

            if (isCrouching()) {
                setAnimation(3);
            } else {
                setAnimation(0);
            }
        }
    }

    public boolean isWalking() {
        return walking;
    }

    public boolean canJump() {
        return (jumps > 0);
    }

    public void setCrouching(boolean crouch) {
        if (this.crouch == crouch) return;
        this.crouch = crouch;
        if (isOnGround()) {
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

    @Override
    public int getWidth() {
        return getFrame().getRegionWidth();
    }

    @Override
    public int getHeight() {
        return getFrame().getRegionHeight();
    }

    public void setAnimation(int i) {
        if (animationIndex == i || i < 0 || i > animations.size) return;
        animationIndex = i;
        transition = true;
        updateBody();
        transition = false;
    }

    public void update(float dt) {
        animations.get(animationIndex).update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        float x = (getX() * Constants.PTM) - (getWidth() / 2);
        float y = (getY() * Constants.PTM) - (getHeight() / 2);
        sb.begin();
        sb.draw(getFrame(), getDirection() ? x : x + getWidth(), y, getDirection() ? getWidth() : -getWidth(), getHeight());
        sb.end();
    }

    public boolean jump() {
        if (isCrouching()) {
            return false;
        } else {
            float forceY = (getBody().getMass() * (5f / (1 / 60.0f))); // f = mv/t
            if (canJump()) {
                jumping = true;
                setAnimation(2);
                getBody().applyForce(new Vector2(0, forceY), getBody().getWorldCenter(), true);
                jumps--;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isOnGround() {
        return jumps == maxJumps;
    }

    public void onGround() {
        if (isOnGround()) return;
        jumps = maxJumps;
        jumping = false;
        if (isWalking()) {
            if (isCrouching()) {
                setAnimation(4);
            } else {
                setAnimation(1);
            }
        } else {
            if (isCrouching()) {
                setAnimation(3);
            } else {
                setAnimation(0);
            }
        }
    }

    public boolean move(boolean direction) {
        float desiredVelX;
        setDirection(direction);
        setWalking(true);
        if (direction) {
            if (isOnGround()) {
                if (isCrouching()) {
                    desiredVelX = 0.5f;
                } else {
                    desiredVelX = 0.7f;
                }
            } else {
                desiredVelX = 0.1f;//*
            }
        } else {
            if (isOnGround()) {
                if (isCrouching()) {
                    desiredVelX = -0.5f;
                } else {
                    desiredVelX = -0.7f;
                }
            } else {
                desiredVelX = -0.1f;
            }
        }
        float forceX = (float) (getBody().getMass() * desiredVelX / (1 / 60.0)); // f = mv/t
        getBody().applyForce(new Vector2(forceX, 0), getBody().getWorldCenter(), true);
        return true;
    }

    public void fall() {
        setAnimation(5);// cambiar a fall
        jumps--;
    }

    public boolean isCrouching() {
        return crouch;
    }

    @Override
    public void createBody(Vector2 pos) {
        // create bodydef
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x / Constants.PTM, pos.y / Constants.PTM);

        // create body from bodydef
        body = world.createBody(bodyDef);

        // create box shape for character collision box
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / BODY_SCALE) / Constants.PTM, (getHeight() / BODY_SCALE) / Constants.PTM);

        // create fixturedef for character collision box
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = Constants.BIT.CHARACTER.BIT();
        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() |
                Constants.BIT.GRANADE.BIT() | Constants.BIT.BULLET.BIT() | Constants.BIT.ITEM.BIT());
        fdef.restitution = 0f;

        // create character collision box fixture
        body.createFixture(fdef).setUserData(Constants.DATA.BODY);

        // create fixturedef for player foot
        shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE)) / Constants.PTM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT.CHARACTER.BIT();
        fdef.filter.maskBits = Constants.BIT.FLOOR.BIT();
        body.createFixture(fdef).setUserData(Constants.DATA.FOOT);

        //create fixturedef for player head
        shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (getHeight() / BODY_SCALE) / Constants.PTM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT.CHARACTER.BIT();
        fdef.filter.maskBits = (short) (Constants.BIT.WALL.BIT() | Constants.BIT.FLOOR.BIT() | Constants.BIT.ITEM.BIT());
        body.createFixture(fdef).setUserData(Constants.DATA.HEAD);

        //create fixturedef for player attack zone
        shape.setAsBox((getWidth() / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT.CHARACTER.BIT();
        fdef.filter.maskBits = Constants.BIT.CHARACTER.BIT();
        body.createFixture(fdef).setUserData(Constants.DATA.ATTACK);

        //dispose shape
        shape.dispose();
        //set character extra fixtures
        createBodyExtra(pos);
        //sets body's userData as this player
        body.setUserData(this);

        // sets the character body mass to 1 kg
        MassData md = body.getMassData();
        md.mass = 1;
        body.setMassData(md);

        body.setFixedRotation(true);
    }

    protected void updateBody() {
        Fixture fix = getBody().getFixtureList().get(0);
        PolygonShape shape = (PolygonShape) fix.getShape();
        switch (animationIndex) {
            case 0://stand
            case 1://walk
            case 2://jump
            case 5://fall
                shape.setAsBox((getWidth() / BODY_SCALE) / Constants.PTM, (getHeight() / BODY_SCALE) / Constants.PTM);
                fix = getBody().getFixtureList().get(1);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE / 4)) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE)) / Constants.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE / 4)) / Constants.PTM, new Vector2(0, ((getHeight() / BODY_SCALE)) / Constants.PTM), 0);
                break;
            case 3://crouch
            case 4://crouch walk
                shape.setAsBox((getWidth() / BODY_SCALE) * 1.25f / Constants.PTM, (getHeight() / BODY_SCALE) * 0.62f / Constants.PTM);
                fix = getBody().getFixtureList().get(1);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE) * 0.75f) / Constants.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth() - 1) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, ((getHeight() / BODY_SCALE) * 0.75f) / Constants.PTM), 0);
                break;
        }
        getBody().applyForce(new Vector2(0, 0), getBody().getWorldCenter(), true);
    }

    public boolean isForceCrouched() {
        return keepCrouched;
    }

    /**
     * Attack Logic
     **/
    public void attack() {
        if (weapon != null) {
            weapon.attack();
        } else {
            //Ataque con puño
        }
    }

    /**
     * En este metodo se define la interaccion
     * que el usuario tiene con los objectos y otros personajes
     * del escenario
     **/
    public void interact() {
        if (interactive == null) return;
        interactive.interact();
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.weapon.setOwner(this);
    }

    public Weapon removeWeapon() {
        Weapon w = weapon;
        weapon = null;
        w.dispose();
        return w;
    }

    public void setInteractive(Interactive i) {
        interactive = i;
    }

    public void removeInteractive() {
        interactive = null;
    }

    protected abstract void createBodyExtra(Vector2 pos);

    protected abstract void setExtraAnimations();

    public boolean isJumping() {
        return jumping;
    }

    public boolean damage(long d) {
        if (invencible) {
            return false;
        } else {
            hp -= d;
            return true;
        }
    }

    public AtlasRegion getFrame(){
        return getAnimation().getFrame();
    }

    public long getAttack() {
        return attack;
    }

    public boolean isInTransition() {
        return transition;
    }

    @Override
    public void act() {
        if (iaChunk.isCharacterSet()) {
            Gdx.app.debug("Should Execute Script", "Let see if it works!");
            iaChunk.exec();
        } else
            Gdx.app.error("Cannot Execute Script", "Character Is Not Set");
    }

    @Override
    public void loadScript() {
        iaChunk = LuaLoader.getInstance().loadScript();
    }

    @Override
    public void loadScript(String file) {
        iaChunk = LuaLoader.getInstance().loadIAScript("basic_enemy.lua");
    }

    @Override
    public abstract void setSelfToScript();

    @Override
    public abstract void run();
}
