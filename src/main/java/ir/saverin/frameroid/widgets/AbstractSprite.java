package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.widget.Sprite;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim.Ayat@gmail.com</a>
 */
public abstract class AbstractSprite implements Sprite {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean isVisible = true;
    protected boolean isMovable = false;

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public boolean isMovable() {
        return isMovable;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isInside(float x, float y) {
        return isVisible && (this.x < x) && (this.y < y) && (this.x + width > x) && (this.y + height > y);
    }

    @Override
    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
    }
}
