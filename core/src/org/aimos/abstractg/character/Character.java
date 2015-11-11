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
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.control.script.BehaviorListener;
import org.aimos.abstractg.control.script.lua.LuaChunk;
import org.aimos.abstractg.control.script.lua.LuaLoader;
import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Animation;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.physics.Interactive;
import org.aimos.abstractg.physics.PhysicalBody;
import org.aimos.abstractg.physics.Weapon;

import java.util.LinkedList;

/**
 * Clase que representa el modelo abstracto
 * del personaje para los escenarios
 *
 * @author EinarGretch, Angyay0,Diego,Herialvaro
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public abstract class Character extends PhysicalBody implements BehaviorListener, Runnable {
    //Asgina nombre al personaje
    private String name;
    //frame del personaje
    private int animationIndex = 0;
    //almacena las animaciones del personaje
    private Array<Animation> animations;
    //verifica si esta agachado **corregir
    private boolean crouch = false;
    //Verifica si continua agachado
    private boolean keepCrouched = false;
    //verifica si esta caminando
    private boolean walking = false;
    //Atlas de los sprite del personaje
    private TextureAtlas atlas;
    //saltos posibles por el personaje
    private int jumps = 1;
    //maximos saltos
    private int maxJumps = 1;
    //direccion
    private boolean direction = true;
    //armas del personaje
    private Weapon weapon;
    //interactuar con partes en el juego
    private Interactive interactive;
    // MaxHp value of the character
    private long maxHp = 10;
    // Hp value of the character
    private long hp = 10;
    // Base Attack value of the character
    private long atk = 1;
    // Score the character has or gives when killed
    private long score = 0;
    // Current money the character has or gives when killed
    protected long money = 0;
    //Velocidad al caminar
    private float maxVelocity;
    //
    private volatile LinkedList<Fixture> fixFoot = new LinkedList<Fixture>();
    //Constants
    //Tamaño del personaje
    public static final float BODY_SCALE = 2.2f;
    //velocidad de visualizacion en animaciones
    protected static final float ANIMATION_DELTA = 1 / 5f;
    //Definicion de Variables para el ATLAS
    protected static final String STAND_SEQ = "breath"; // 0
    protected static final String WALK_SEQ = "walk"; // 1
    protected static final String JUMP_SEQ = "jump"; // 2
    protected static final String CROUCH_SEQ = "crouch"; // 3
    protected static final String CROUCH_MOVE_SEQ = "crouch"; // 4
    protected static final String FALL_SEQ = "jump"; // 5
    //si esta en el suelo
    protected boolean onGround = false;
    //verifica si el personaje esta vivo
    protected  boolean alive = true;
    //verifica salto
    protected boolean jumping;
    //verifica si tiene poder
    protected boolean invencible = false;
    //verifica
    protected boolean transition;
    //Variable
    protected LuaChunk iaChunk;
    //Variable
    protected Character killer;
    //VAriable
    private Indicators indicators;

    /**
     * @param spriteSrc
     * @param name
     * @param play
     * @param pos
     */
    protected Character(String spriteSrc, String name, Play play, Vector2 pos) {
        super(play);
        this.name = name;
        atlas = Launcher.res.getAtlas(spriteSrc);
        killer = null;
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

    /**
     * Obtiene la animacion
     * @return animacion
     */
    public Animation getAnimation() {
        return animations.get(animationIndex);
    }

    /**
     * ayuda al personaje a seguir agachado
     * @param keepCrouched
     */
    public void forceCrouch(boolean keepCrouched) {
        this.keepCrouched = keepCrouched;
    }

    /**
     * indica la direccion del personaje
     * @param dir
     */
    public void setDirection(boolean dir) {
        if (direction == dir) return;
        direction = dir;

        if ( hasWeapon() ) {
            weapon.updateBody();
        }
    }

    /**
     * obtiene la direccion del personaje
     * @return direction boolean
     */
    public boolean getDirection() {
        return direction;
    }

    /**
     * verifica la muerte el personaje
     * @return
     */
    public boolean isDead() {
        return (hp <= 0);
    }

    /**
     * Verifica si esta caminando
     * @param w
     */
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

    /**
     * @return walking
     */
    public boolean isWalking() {
        return walking;
    }

    /**
     * ayuda a saber cuantos saltos tiene permitido
     * @return boolean
     */
    public boolean canJump() {
        return (jumps > 0);
    }

    /**
     * verifica si esta agachado
     * @param crouch
     */
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

    /**
     * obtiene el ancho de la textura
     * @return int
     */
    @Override
    public int getWidth() {
        return getFrame().getRegionWidth();
    }

    /**
     * Obtiene lo alto de la textura
     * @return int
     */
    @Override
    public int getHeight() {
        return getFrame().getRegionHeight();
    }

    /**
     * estasblece animacion
     * @param i
     */
    public void setAnimation(int i) {
        if (animationIndex == i || i < 0 || i > animations.size) return;
        animationIndex = i;
        transition = true;
        updateBody();
        transition = false;
    }

    /**
     * actualiza graficos en pantalla
     * @param dt
     */
    public void update(float dt) {
        Vector2 pos = getPosition();
        pos.cpy();
        //Gdx.app.debug("Posicion:=>",pos.toString());

        if (isDead()) die();
        else animations.get(animationIndex).update(dt);
    }

    /**
     * ilumina graficos en pantalla
     * @param sb
     */
    @Override
    protected void render(SpriteBatch sb) {
        if (!isDead()) {
            float x = (getX() * Constants.PTM) - (getWidth() / 2);
            float y = (getY() * Constants.PTM) - (getHeight() / 2);
            if (hasWeapon()) weapon.draw(sb);
            sb.begin();
            sb.draw(getFrame(), getDirection() ? x : x + getWidth(), y, getDirection() ? getWidth() : -getWidth(), getHeight());
            sb.end();
        }
    }

    /**
     * verifica el salto
     * @return boolean
     */
    public boolean jump() {
        if (isCrouching() && !isForceCrouched()) {
            setCrouching(false);
        }
        float forceY = (getMass() * (5f / (1 / 60.0f))); // f = mv/t
        if (canJump()) {
            jumping = true;
            setAnimation(2);
            getBody().applyForce(new Vector2(0, forceY), getBody().getWorldCenter(), true);
            jumps--;
            return true;
        } else {
            return false;
        }
        //return  true;
    }

    /**
     * verifica si esta en el suelo
     * @return boolean
     */
    public boolean isOnGround() {
        if(fixFoot.isEmpty()) return false;
        for (Fixture f : fixFoot){
            Vector2 s = (Vector2) f.getBody().getUserData();
            if(((((f.getBody().getPosition().x -s.x) <= getX() -((getWidth() / 2.2) / Constants.PTM))
                    && (((f.getBody().getPosition().x +s.x) >= getX() -((getWidth() / 2.2) / Constants.PTM)))) ||
                    ((((f.getBody().getPosition().x + s.x) >= getX() +((getWidth() / 2.2) / Constants.PTM))) &&
                            ((f.getBody().getPosition().x - s.x) <= getX() +((getWidth() / 2.2) / Constants.PTM))) ||
                    (f.getBody().getPosition().x + s.x >=  getX() && f.getBody().getPosition().x - s.x <= getX()))
                    && (f.getBody().getPosition().y + s.y) <= getY() - ((getHeight() / 2.2) / Constants.PTM)){
                onGround();
                return true;

            }
        }
        return  false;

    }

    /**
     * establece las animaciones cuando se encuentre en el suelo
     */
    public void onGround() {
        //if (isOnGround()) return;
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

    /**
     * permite el movimiento del personaje
     * @param direction
     * @return boolean
     */
    public boolean move(boolean direction) {
        float desiredVelX;
        setDirection(direction);
        setWalking(true);
        if (direction) {
            if (isOnGround()) {
                if (isCrouching()) {
                    maxVelocity = 2;
                    desiredVelX = 0.5f;
                } else {
                    desiredVelX = 0.7f;
                    maxVelocity = 3;
                }
            } else {
                desiredVelX = 0.1f;//*
            }
        } else {
            if (isOnGround()) {
                if (isCrouching()) {
                    maxVelocity = 2;
                    desiredVelX = -0.5f;
                } else {
                    maxVelocity = 3;
                    desiredVelX = -0.7f;
                }
            } else {
                desiredVelX = -0.1f;
            }
        }
        float forceX = (float) (getBody().getMass() * desiredVelX / (1 / 60.0)); // f = mv/t
        if(Math.abs(getBody().getLinearVelocity().x) < maxVelocity) {
            getBody().applyForce(new Vector2(forceX, 0), getBody().getWorldCenter(), true);
        }
        return true;
    }

    /**
     * establece la animacion al caer
     */
    public void fall() {
        setAnimation(5);// cambiar a fall
        jumps--;
    }

    /**
     * @return crouch
     */
    public boolean isCrouching() {
        return crouch;
    }

    /**
     * crea el cuerpo del personaje
     * @param pos posicion (X,Y)
     */
    @Override
    public void createBody(Vector2 pos) {
        // create bodydef
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x / Constants.PTM, pos.y / Constants.PTM);

        // create body from bodydef
        createBody(bodyDef);

        // create box shape for character collision box
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / BODY_SCALE) / Constants.PTM, (getHeight() / BODY_SCALE) / Constants.PTM);
        //     ChainShape shape = new ChainShape();
        //    Vector2 points[] = new Vector2[11];
/*
        points[0] = new Vector2(0 / Constants.PTM, 0 / Constants.PTM);
        points[1] = new Vector2(15 / Constants.PTM, 20 / Constants.PTM);
        points[2] = new Vector2(15 / Constants.PTM, 60 / Constants.PTM);
        points[3] = new Vector2(0 / Constants.PTM, 95 / Constants.PTM);
        points[4] = new Vector2(50 / Constants.PTM, 95 / Constants.PTM);
        points[5] = new Vector2(50 / Constants.PTM, 60 / Constants.PTM);
        points[6] = new Vector2(62 / Constants.PTM, 40 / Constants.PTM);
        points[7] = new Vector2(62 / Constants.PTM, 30 / Constants.PTM);
        points[8] = new Vector2(50 / Constants.PTM, 30 / Constants.PTM);
        points[9] = new Vector2(50 / Constants.PTM, 0 / Constants.PTM);
        points[10] = new Vector2(0 / Constants.PTM, 0 / Constants.PTM);
/*
points[0] = new Vector2(0 / Constants.PTM, 0 / Constants.PTM);
        points[1] = new Vector2(0 / Constants.PTM, 95 / Constants.PTM);
        points[2] = new Vector2(62 / Constants.PTM, 95 / Constants.PTM);
        points[3] = new Vector2(62 / Constants.PTM, 0 / Constants.PTM);
 */
        //      shape.createChain(points);
        // shape.createLoop(points);

        // create fixturedef for character collision box
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = (short) (Constants.BIT.CHARACTER.BIT() |Constants.BIT.PLAYER.BIT());
        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.WALL.BIT() |
                Constants.BIT.GRANADE.BIT() | Constants.BIT.BULLET.BIT() | Constants.BIT.ITEM.BIT());
        fdef.restitution = 0f;

        // create character collision box fixture
        getBody().createFixture(fdef).setUserData(Constants.DATA.BODY);

        // create fixturedef for player foot
        shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE)) / Constants.PTM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = (short) (Constants.BIT.CHARACTER.BIT() |Constants.BIT.PLAYER.BIT());
        fdef.filter.maskBits = Constants.BIT.FLOOR.BIT();
        getBody().createFixture(fdef).setUserData(Constants.DATA.FOOT);

        //create fixturedef for player head
        shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (getHeight() / BODY_SCALE) / Constants.PTM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = (short) (Constants.BIT.CHARACTER.BIT() |Constants.BIT.PLAYER.BIT());
        fdef.filter.maskBits = (short) (Constants.BIT.WALL.BIT() | Constants.BIT.FLOOR.BIT() | Constants.BIT.ITEM.BIT());
        getBody().createFixture(fdef).setUserData(Constants.DATA.HEAD);

        //create fixturedef for player attack zone
        shape.setAsBox((getWidth() / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = (short) (Constants.BIT.CHARACTER.BIT() |Constants.BIT.PLAYER.BIT());
        fdef.filter.maskBits = Constants.BIT.CHARACTER.BIT();
        getBody().createFixture(fdef).setUserData(Constants.DATA.ATTACK);

        //dispose shape
        shape.dispose();
        //set character extra fixtures
        createBodyExtra(pos);
        //sets getBody()'s userData as this player
        getBody().setUserData(this);

        // sets the character body mass to 1 kg
        MassData md = getBody().getMassData();
        md.mass = 1;
        getBody().setMassData(md);
        getBody().setFixedRotation(true);
    }

    /**
     * actualiza el cuerpo del personaje
     */
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
                shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE / 4)) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE)) / Constants.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE / 4)) / Constants.PTM, new Vector2(0, ((getHeight() / BODY_SCALE)) / Constants.PTM), 0);
                break;
            case 3://crouch
            case 4://crouch walk
                shape.setAsBox((getWidth() / BODY_SCALE) * 1.25f / Constants.PTM, (getHeight() / BODY_SCALE) * 0.62f / Constants.PTM);
                fix = getBody().getFixtureList().get(1);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, (-(getHeight() / BODY_SCALE) * 0.75f) / Constants.PTM), 0);
                fix = getBody().getFixtureList().get(2);
                shape = (PolygonShape) fix.getShape();
                shape.setAsBox(((getWidth()) / BODY_SCALE) / Constants.PTM, ((getHeight() / BODY_SCALE) / 4) / Constants.PTM, new Vector2(0, ((getHeight() / BODY_SCALE) * 0.75f) / Constants.PTM), 0);
                break;
        }
        getBody().applyForce(new Vector2(0, 0), getBody().getWorldCenter(), true);
    }

    /**
     * @return keepCrouched
     */
    public boolean isForceCrouched() {
        return keepCrouched;
    }

    /**
     * Attack Logic
     **/
    public void attack() {
        if (hasWeapon()) {
            weapon.attack();
        } else {
            //Ataque con puño
        }
    }

    /**
     * permite saber el daño ocasinado al personaje
     * @param c
     * @return
     */
    public long damage(Character c) {
        long dam;
        if (hasWeapon()) {
            dam = weapon.damage(c);

        } else {
            c.damage(getAttack());
            dam = getAttack();
        }
        if (c.isDead()) c.killer = this;
        return dam;
    }

    /**
     * agrega score al personaje
     * @param l
     */
    public void addMoney(long l) {
        money += l;
    }

    /**
     * obtiene el score del personaje
     * @return money
     */
    public long getMoney() {
        return money;
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

    /**
     * establece arma actual
     * @param weapon
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.weapon.setOwner(this);
    }

    /**
     * remueve armas de lista
     * @return
     */
    public Weapon removeWeapon() {
        Weapon w = weapon;
        weapon = null;
        w.dispose();
        return w;
    }

    /**
     * @return weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * establece interactive
     * @param i
     */
    public void setInteractive(Interactive i) {
        interactive = i;
    }

    /**
     * remueve interactive
     */
    public void removeInteractive() {
        interactive = null;
    }

    /**
     * crea cuerpo extra
     * @param pos
     */
    protected abstract void createBodyExtra(Vector2 pos);

    /**
     * establece animacion extra
     */
    protected abstract void setExtraAnimations();

    /**
     *
     * @return jumping
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * @param d
     * @return boolean
     */
    public boolean damage(long d) {
        if (invencible) {
            return false;
        } else {
            hp -= d;
            return true;
        }
    }

    /**
     * da a conocer si cuenta con arma
     * @return
     */
    public boolean hasWeapon() {
        return (weapon != null);
    }

    /**
     * ayuda a conocer si el cuerpo cuenta con masa
     * @return boolean
     */
    public float getMass() {
        return (hasWeapon()) ? getBody().getMass() + weapon.getBody().getMass() : getBody().getMass();
    }

    /**
     * obtiene el frame del personaje
     * @return atlas region
     */
    public AtlasRegion getFrame() {
        return getAnimation().getFrame();
    }

    /**
     *  obtiene ataque
     * @return atk
     */
    public long getAttack() {
        return atk;
    }

    /**
     * obtiene la vida
     * @return hp
     */
    public long getHP() {
        return hp;
    }

    /**
     * obtiene el score
     * @return score
     */
    public long getScore() {
        return score;
    }

    /**
     *
     * @return
     */
    public long getMaxHP() {
        return maxHp;
    }

    /**
     * @return boolean
     */
    public boolean isInTransition() {
        return transition;
    }

    /**
     * establece ataque y vida
     * @param hp
     * @param at
     */
    public void setStats(long hp, long at){
        atk = at;
        this.hp = hp;
    }

    /**
     * Establece maximo de vida
     * @param mhp
     */
    public void setMaxHp(long mhp){ maxHp = mhp;    }

    /**
     * establece score
     * @param score
     */
    public void setScore(long score){   this.score = score; }

    /**
     * añade score
     * @param ad
     */
    public void addScore(long ad){  score += ad;    }

    /**
     * @return indicators
     */
    public Indicators getIndicators(){ return indicators;   }

    /**
     * establece indicadore
     * @param indi
     */
    public void setIndicators(Indicators indi){ indicators = indi;  }

    /**
     * verifica si esta muerto
     */
    public abstract void die();

    /**
     * establece script
     */
    @Override
    public void setSelfToScript() {
        iaChunk.setCharacter(this);
    }

    /**
     * realiza las acciones cargadas en el script
     */
    @Override
    public void act() {
        if (iaChunk.isCharacterSet()) {
            //Gdx.app.debug("Should Execute Script", "Let see if it works!");
            iaChunk.exec();
        } else
            Gdx.app.error("Cannot Execute Script", "Character Is Not Set");
    }

    /**
     * Agrega fixture collide
     * @param f
     */
    public void addFixtureCollide(Fixture f){
        fixFoot.add(f);
    }

    /**
     * remove fixture collide
     * @param f
     */
    public void removeFixtureCollide(Fixture f){
        fixFoot.remove(f);
    }

    /**
     * carga script
     */
    @Override
    public void loadScript() {
        iaChunk = LuaLoader.getInstance().loadScript();
    }

    /**
     * carga script de archivo definido
     * @param file
     */
    @Override
    public void loadScript(String file) {
        iaChunk = LuaLoader.getInstance().loadIAScript(file);
    }

    /**
     * hilo
     */
    @Override
    public abstract void run();

}
