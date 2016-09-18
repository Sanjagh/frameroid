package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Graphics;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim.Ayat@gmail.com</a>
 */
public class SimpleSprite extends SoundPlayerSprite {

    private static final String TAG = SimpleSprite.class.getName();
    protected int color;
    protected int color2;
    protected boolean isDown = false;
    protected boolean isMovable = false;

    public SimpleSprite(int color1, int color2) {
        this(color1, color2, false);
    }

    public SimpleSprite(int color, int color2, boolean isMovable) {
        this.color = color;
        this.color2 = color2;
        this.isMovable = isMovable;
    }


    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (isDown) {
                g.drawRect(x, y, width, height, color2);
            } else {
                g.drawRect(x, y, width, height, color);
            }
        }
    }


    @Override
    public void down(float x, float y) {
        isDown = true;
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        isDown = false;
    }

    @Override
    public boolean isMovable() {
        return isMovable;
    }


}
