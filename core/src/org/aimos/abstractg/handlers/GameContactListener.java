package org.aimos.abstractg.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.physics.Ammo;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;
import org.aimos.abstractg.physics.Interactive;
import org.aimos.abstractg.physics.Item;
import org.aimos.abstractg.physics.PhysicalBody;
import org.aimos.abstractg.physics.Portal;

/**
 * Created by EinarGretch on 25/09/2015.
 */
public class GameContactListener implements ContactListener {

    private Array<PhysicalBody> remove;

    public GameContactListener(Array<PhysicalBody> r) {
        super();
        remove = r;
    }



    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Jump
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.FOOT)) {
            Character c = (Character) fa.getBody().getUserData();
            Vector2 s = (Vector2) fb.getBody().getUserData();
            //System.out.println((fb.getBody().getPosition().x + s.x));
            c.addFixtureCollide(fb);
            //System.out.println((c.getX() -((c.getWidth() / 2.2) / Constants.PTM)));
            /*if(((((fb.getBody().getPosition().x -s.x) <= c.getX() -((c.getWidth() / 2.2) / Constants.PTM))
                    && (((fb.getBody().getPosition().x +s.x) >= c.getX() -((c.getWidth() / 2.2) / Constants.PTM)))) ||
                    ((((fb.getBody().getPosition().x + s.x) >= c.getX() +((c.getWidth() / 2.2) / Constants.PTM))) &&
                    ((fb.getBody().getPosition().x - s.x) <= c.getX() +((c.getWidth() / 2.2) / Constants.PTM))) ||
                    (fb.getBody().getPosition().x + s.x >=  c.getX() && fb.getBody().getPosition().x - s.x <= c.getX()))
                    && (fb.getBody().getPosition().y + s.y) <= c.getY() - ((c.getHeight() / 2.2) / Constants.PTM))
                 {
                     System.out.println("Entro");
                    c.onGround();

                }*/


        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.FOOT)) {
           /* Character c = (Character) fb.getBody().getUserData();
            if (c != null) {
                c.onGround();
            }*/
            Character c = (Character) fb.getBody().getUserData();
            Vector2 s = (Vector2) fa.getBody().getUserData();
            //System.out.println((fb.getBody().getPosition().x + s.x));
            c.addFixtureCollide(fa);
        }
        //Side Collisions
        if(fa.getUserData() !=null && fa.getUserData().equals(Constants.DATA.BODY)){
            Character c = (Character)fa.getBody().getUserData();
            if(fb.getUserData().equals(Constants.DATA.CELL)){
                if(!c.isOnGround()){
                    fb.setFriction(0);
                }
            }
        }
        if(fb.getUserData() !=null && fb.getUserData().equals(Constants.DATA.BODY)){
            Character c = (Character)fb.getBody().getUserData();
            if(fa.getUserData().equals(Constants.DATA.CELL)){
                if(!c.isOnGround()){
                    fa.setFriction(0);
                }
            }
        }
        //Crouch
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.CELL)) {
                if (c.isCrouching()) c.forceCrouch(true);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.CELL)) {
                if (c.isCrouching()) c.forceCrouch(true);
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character c = (Character) fb.getBody().getUserData();
                c.setInteractive(i);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character c = (Character) fa.getBody().getUserData();
                c.setInteractive(i);
            }
        }
        //Portal
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.PORTAL)) {
            Portal i = (Portal) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character c = (Character) fb.getBody().getUserData();
                if( c instanceof Player ){
                    Player pl = (Player) c;
                    pl.setWinMapLevel();
                }
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.PORTAL)) {
            Portal i = (Portal) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Portal j = (Portal) fb.getBody().getUserData();
                j.getPlay().getPlayer().setWinMapLevel();
            }
        }
        //Pick-up
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.PICKUP)) {
            Item pi = (Item) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY) &&
                fb.getBody().getUserData() instanceof Player) {
                Player p = (Player) fb.getBody().getUserData();
                if(pi instanceof Coin) {
                    p.addMoney((Coin) pi);
                }else if(pi instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) pi);
                }
                remove.add(pi);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.PICKUP)) {
            Item pi = (Item) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY) &&
                    fa.getBody().getUserData() instanceof Player) {
                Player p = (Player) fa.getBody().getUserData();
                if(pi instanceof Coin) {
                    p.addMoney((Coin) pi);
                }else if(pi instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) pi);
                }
                remove.add(pi);
            }
        }
        //Melee Weapon
        if(fa.getUserData() !=  null && fa.getUserData().equals(Constants.DATA.ATTACK)){
            Character attacker = (Character) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fb.getBody().getUserData();
                attacker.damage(defender);
            }
        }
        if(fb.getUserData() !=  null && fb.getUserData().equals(Constants.DATA.ATTACK)){
            Character attacker = (Character) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fa.getBody().getUserData();
                attacker.damage(defender);
            }
        }
        //Shoot Weapon
        if(fa.getUserData() !=  null && fa.getUserData().equals(Constants.DATA.BULLET)){
            Ammo a = (Ammo) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character attacker = a.getWeapon().getOwner();
                Character defender = (Character) fb.getBody().getUserData();
                attacker.damage(defender);
            }
            remove.add(a);
        }
        if(fb.getUserData() !=  null && fb.getUserData().equals(Constants.DATA.BULLET)){
            Ammo a = (Ammo) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character attacker = a.getWeapon().getOwner();
                Character defender = (Character) fa.getBody().getUserData();
                attacker.damage(defender);
            }
            remove.add(a);
        }
        //Throw Weapon
        if(fa.getUserData() !=  null && fa.getUserData().equals(Constants.DATA.EXPLOSION)){
            Ammo a = (Ammo) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fb.getBody().getUserData();
                a.getTargets().add(defender);
            }
        }
        if(fb.getUserData() !=  null && fb.getUserData().equals(Constants.DATA.EXPLOSION)){
            Ammo a = (Ammo) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fa.getBody().getUserData();
                a.getTargets().add(defender);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Fall
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.FOOT)) {
            Character c = (Character) fa.getBody().getUserData();
            //Vector2 s = (Vector2) fb.getBody().getUserData();
            c.removeFixtureCollide(fb);
            /*if(((((fb.getBody().getPosition().x -s.x) <= c.getX() -((c.getWidth() / 2.2) / Constants.PTM))
                    && (((fb.getBody().getPosition().x +s.x) >= c.getX() -((c.getWidth() / 2.2) / Constants.PTM)))) ||
                    ((((fb.getBody().getPosition().x + s.x) >= c.getX() +((c.getWidth() / 2.2) / Constants.PTM))) &&
                            ((fb.getBody().getPosition().x - s.x) <= c.getX() +((c.getWidth() / 2.2) / Constants.PTM))) ||
                    (fb.getBody().getPosition().x + s.x >=  c.getX() && fb.getBody().getPosition().x - s.x <= c.getX()))
                    && (fb.getBody().getPosition().y + s.y) <= c.getY() - ((c.getHeight() / 2.2) / Constants.PTM)){
                System.out.println("Entro para salir");
                System.out.println((fb.getBody().getPosition().x -s.x));
                System.out.println((fb.getBody().getPosition().x +s.x));
                System.out.println((c.getX() -((c.getWidth() / 2.2) / Constants.PTM)));
                System.out.println((c.getX() +((c.getWidth() / 2.2) / Constants.PTM)));

                c.fall();
            }*/
            //System.out.println((c.getY() -(((c.getHeight() / 2)) / Constants.PTM)));
            /*if((c.getY() -(((c.getHeight() / 2)) / Constants.PTM))> fb.getBody().getPosition().y &&
                    !(c.isJumping() || c.isInTransition())){
                c.fall(); //needs fixing
            }*/
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.FOOT)) {
            Character c = (Character) fb.getBody().getUserData();
            if(!(c.isJumping() || c.isInTransition())){
                c.fall(); //needs fixing
            }
        }
        //Crouch
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fa.getBody().getUserData();
            if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.CELL)) {
                if (c.isCrouching() && c.isForceCrouched()){
                    c.forceCrouch(false);
                }
            }
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fb.getBody().getUserData();
            if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.CELL)){
                if (c.isCrouching() && c.isForceCrouched()){
                    c.forceCrouch(false);
                }
            }
        }
        //Slide action
        if(fa.getUserData() !=null && fa.getUserData().equals(Constants.DATA.BODY)){
            Character c = (Character)fa.getBody().getUserData();
            if(fb.getUserData().equals(Constants.DATA.CELL)){
                if(fb.getFriction() == 0){
                    fb.setFriction(1);
                }
            }
        }
        if(fb.getUserData() !=null && fb.getUserData().equals(Constants.DATA.BODY)){
            Character c = (Character)fb.getBody().getUserData();
            if(fa.getUserData().equals(Constants.DATA.CELL)){
                if(fa.getFriction() == 0){
                    fa.setFriction(1);
                }
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character c = (Character) fb.getBody().getUserData();
                c.removeInteractive();
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character c = (Character) fb.getBody().getUserData();
                c.removeInteractive();
            }
        }
        //Throw Weapon
        if(fa.getUserData() !=  null && fa.getUserData().equals(Constants.DATA.EXPLOSION)){
            Ammo a = (Ammo) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fb.getBody().getUserData();
                a.getTargets().removeValue(defender, true);
            }
        }
        if(fb.getUserData() !=  null && fb.getUserData().equals(Constants.DATA.EXPLOSION)){
            Ammo a = (Ammo) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Character defender = (Character) fa.getBody().getUserData();
                a.getTargets().removeValue(defender, true);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}