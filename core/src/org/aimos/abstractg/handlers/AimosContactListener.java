package org.aimos.abstractg.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Player;

/**
 * Created by EinarGretch on 25/09/2015.
 */
public class AimosContactListener implements ContactListener {

    private Array<Body> bodiesToRemove;

    public AimosContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();
            if (p != null) p.onGround();
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();
            if (p != null) p.onGround();
        }

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
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        /*if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();

        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();


        }*/

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