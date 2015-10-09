package org.aimos.abstractg.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;
import org.aimos.abstractg.physics.Interactive;
import org.aimos.abstractg.physics.PickUp;
import org.aimos.abstractg.physics.Weapon;

/**
 * Created by EinarGretch on 25/09/2015.
 */
public class GameContactListener implements ContactListener {

    public GameContactListener() {
        super();
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Jump
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.FOOT)) {
            Character c = (Character) fa.getBody().getUserData();
            if (c != null) c.onGround();//Corregir fixture del pie
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.FOOT)) {
            Character c = (Character) fb.getBody().getUserData();
            if (c != null) c.onGround();
        }
        //Crouch
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.CELL)) {
                if (c.isCrouching()) c.forceCrouch(true);
                System.out.println("force");
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.HEAD)) {
            Character c = (Character) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.CELL)) {
                if (c.isCrouching()) c.forceCrouch(true);
                System.out.println("force");
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fb.getBody().getUserData();
                p.setInteractive(i);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fa.getBody().getUserData();
                p.setInteractive(i);
            }
        }
        //Pick-up
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.PICKUP)) {
            PickUp pi = (PickUp) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fb.getBody().getUserData();
                System.out.println("Recoger");
                if(pi instanceof Coin) {
                    p.addMoney((Coin) pi);
                }else if(pi instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) pi);
                }
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.PICKUP)) {
            PickUp pi = (PickUp) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fa.getBody().getUserData();
                System.out.println("Recoger");
                if(pi instanceof Coin) {
                    p.addMoney((Coin) pi);
                }else if(pi instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) pi);
                }
            }
        }
        //weapon
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
            Character c = (Character) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.WEAPON)) {
                Weapon w = (Weapon) fb.getBody().getUserData();
                w.damage(c);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
            Character c = (Character) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.WEAPON)) {
                Weapon w = (Weapon) fa.getBody().getUserData();
                w.damage(c);
            }
        }

        //add explosion
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Fall
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.FOOT)) {
            Player p = (Player) fa.getBody().getUserData();
            if(!(p.isJumping() || p.isInTransition())){
                //p.fall(); //needs fixing
            }
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.FOOT)) {
            Player p = (Player) fb.getBody().getUserData();
            if(!(p.isJumping() || p.isInTransition())){
                //op.fall(); //needs fixing
            }
        }
        //Crouch
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.HEAD)) {
            Player p = (Player) fa.getBody().getUserData();
            if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.CELL)) {
                if (p.isCrouching() && p.isForceCrouched()){
                    p.forceCrouch(false);
                }
            }
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.HEAD)) {
            Player p = (Player) fb.getBody().getUserData();
            if(fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.CELL)){
                if (p.isCrouching() && p.isForceCrouched()){
                    p.forceCrouch(false);
                }
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fb.getBody().getUserData();
                p.removeInteractive();
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals(Constants.DATA.INTERACTIVE)) {
            Interactive i = (Interactive) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals(Constants.DATA.BODY)) {
                Player p = (Player) fb.getBody().getUserData();
                p.removeInteractive();
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