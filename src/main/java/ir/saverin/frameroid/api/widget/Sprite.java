package ir.saverin.frameroid.api.widget;

import ir.saverin.frameroid.api.media.Graphics;

/**
 * Base widget class in frameroid.
 *
 * @author S.Hosein Ayat
 */
public interface Sprite {

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    void setX(float x);

    void setY(float y);

    void setLocation(float x, float y);

    void setSize(float w, float h);

    boolean isInside(float x, float y);

    void draw(Graphics g);

    boolean isVisible();

    void down(float x, float y);

    /**
     * Informs that the finger that was holding the sprite is no more.
     *
     * @param inside shows whether the finger was on the sprite (at the moment of disconnection) or not.
     * @param x
     * @param y
     */
    void up(boolean inside, float x, float y);

    boolean isMovable();
}
