package org.aimos.abstractg.handlers;

/**
 * Created by Alumno on 05/11/2015.
 */
public interface SocialMedia {
    /**
     * Comparte en facebook
     * @param text
     * @param score
     */
    public void shareOnFacebook(String text, String score);

    /**
     * Comparte en facebook
     * @param text
     * @param img
     */
    public void shareOnFacebook(String text, Object img);

    /**
     * Comparte en facebook
     * @param text
     */
    public void shareOnFacebook(String text);

    /**
     * Comparte en Twitter
      * @param text
     * @param score
     */
    public void shareOnTwitter(String text,String score);

    /**
     * Comparte en Twitter
     * @param text
     * @param img
     */
    public void shareOnTwitter(String text, Object img);

    /**
     * Comparte en Twitter
     * @param text
     */
    public void shareOnTwitter(String text);
}
