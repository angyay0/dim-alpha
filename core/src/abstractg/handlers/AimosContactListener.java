package abstractg.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import abstractg.character.Player;

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

        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();
            if(p != null) p.onGround();
        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();
            if(p != null) p.onGround();
        }

        if(fa.getUserData() != null && fa.getUserData().equals("head")){
            Player p = (Player) fa.getBody().getUserData();
            if ((fb.getUserData().equals("cell"))) {
                if (p.isCrouching()) {
                    if (!p.isCellOfCollision()) {
                        p.setCellCollision(fb);
                    }
                }
            }
        }

        if(fb.getUserData() != null && fb.getUserData().equals("head")){
            Player p = (Player) fb.getBody().getUserData();
            if ((fa.getUserData().equals("cell"))) {
                if (p.isCrouching()) {
                    if (!p.isCellOfCollision()) {
                        p.setCellCollision(fa);
                    }
                }
            }
        }

        /*if(fa.getUserData() != null && fa.getUserData().equals("head")){
            Player p = (Player) fa.getBody().getUserData();
            if(fb.getUserData() != null && fb.getUserData().equals("cell")){
                if(!p.getHeadCollision()) {
                    if (p.isCrouching()) p.forceCrouch(true);
                    else p.forceCrouch(false);
                    p.setHeadCollision(true);
                }
                System.out.println("Entro");
            }
        }

        if(fb.getUserData() != null && fb.getUserData().equals("head")){
            Player p = (Player) fb.getBody().getUserData();
            if(fa.getUserData() != null && fa.getUserData().equals("cell")){
                if(!p.getHeadCollision()) {
                    if (p.isCrouching()) p.forceCrouch(true);
                    else p.forceCrouch(false);
                    p.setHeadCollision(true);
                }
                System.out.println("Entro");
            }
        }*/
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        /*if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            Player p = (Player) fa.getBody().getUserData();

        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            Player p = (Player) fb.getBody().getUserData();


        }

        if(fa.getUserData() != null && fa.getUserData().equals("head")) {
            Player p = (Player) fa.getBody().getUserData();
            if(fb.getUserData() != null && fb.getUserData().equals("cell")){
                if(p.getHeadCollision()) {
                    if (p.isCrouching() && p.isForceCrouched()) p.forceCrouch(false);
                    p.setHeadCollision(false);
                }
                System.out.println("Salio");
            }
        }
        if(fb.getUserData() != null && fb.getUserData().equals("head")) {
            Player p = (Player) fb.getBody().getUserData();
            if(fa.getUserData() != null && fa.getUserData().equals("cell")){
                if(p.getHeadCollision()) {
                    if (p.isCrouching() && p.isForceCrouched()) p.forceCrouch(false);
                    p.setHeadCollision(false);
                }
                System.out.println("Salio");
            }
        }*/
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