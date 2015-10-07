package org.aimos.abstractg.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;
import org.aimos.abstractg.physics.Interactive;

/**
 * Created by EinarGretch on 25/09/2015.
 */
public class GameContactListener implements ContactListener {

    private Array<Body> bodiesToRemove;

    public GameContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Jump
        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();
            if (p != null) p.onGround();
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();
            if (p != null) p.onGround();
        }
        //Crouch
        if (fa.getUserData() != null && fa.getUserData().equals("head")) {
            Player p = (Player) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals("cell")) {
                if (p.isCrouching()) p.forceCrouch(true);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals("head")) {
            Player p = (Player) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals("cell")) {
                if (p.isCrouching()) p.forceCrouch(true);
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals("interact")) {
            Player p = (Player) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals("interactive")) {
                Interactive i = (Interactive) fb.getBody().getUserData();
                p.setInteractive(i);
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals("interact")) {
            Player p = (Player) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals("interactive")) {
                Interactive i = (Interactive) fa.getBody().getUserData();
                p.setInteractive(i);
            }
        }
        //Pick-up
        if (fa.getUserData() != null && fa.getUserData().equals("interact")) {
            Player p = (Player) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals("pickup")) {
                Interactive i = (Interactive) fb.getBody().getUserData();
                if(i instanceof Coin) {
                    p.addMoney((Coin) i);
                }else if(i instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) i);
                }
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals("interact")) {
            Player p = (Player) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals("pickup")) {
                Interactive i = (Interactive) fa.getBody().getUserData();
                if(i instanceof Coin) {
                    p.addMoney((Coin) i);
                }else if(i instanceof DroppedWeapon) {
                    p.addWeapon((DroppedWeapon) i);
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //Fall
        /*if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();

        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();


        }*/
        //Crouch
        if(fa.getUserData() != null && fa.getUserData().equals("head")) {
            Player p = (Player) fa.getBody().getUserData();
            if(fb.getUserData() != null && fb.getUserData().equals("cell")) {
                if (p.isCrouching() && p.isForceCrouched()) p.forceCrouch(false);
            }
        }
        if(fb.getUserData() != null && fb.getUserData().equals("head")) {
            Player p = (Player) fb.getBody().getUserData();
            if(fa.getUserData() != null && fa.getUserData().equals("cell")){
                if (p.isCrouching() && p.isForceCrouched()) p.forceCrouch(false);
            }
        }
        //Interact
        if (fa.getUserData() != null && fa.getUserData().equals("interact")) {
            Player p = (Player) fa.getBody().getUserData();
            if (fb.getUserData() != null && fb.getUserData().equals("interactive")) {
                Interactive i = (Interactive) fb.getBody().getUserData();
                p.removeInteractive();
            }
        }
        if (fb.getUserData() != null && fb.getUserData().equals("interact")) {
            Player p = (Player) fb.getBody().getUserData();
            if (fa.getUserData() != null && fa.getUserData().equals("interactive")) {
                Interactive i = (Interactive) fa.getBody().getUserData();
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

    public Array<Body> getBodies() {
        return bodiesToRemove;
    }

}