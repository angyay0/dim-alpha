package org.aimos.abstractg.handlers;

/**
 * Created by Alumno on 05/11/2015.
 */
public interface SocialMedia {
    public void shareOnFacebook(String text, String score);
    public void shareOnFacebook(String text, Object img);
    public void shareOnFacebook(String text);
    public void shareOnTwitter(String text,String score);
    public void shareOnTwitter(String text, Object img);
    public void shareOnTwitter(String text);
}
