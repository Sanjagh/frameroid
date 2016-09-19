package ir.saverin.frameroid.widgets;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.util.GeneralConfig;

import static ir.saverin.frameroid.util.Scaler.scale;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public class DummySprite extends SoundPlayerSprite implements Sprite {

    protected boolean isMovable = true;
    private static final Paint borderPaint = new Paint();

    static {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(0xFF0000FF);
        borderPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
    }

    public DummySprite(float x, float y, float w, float h, boolean isMovable) {
        setLocation(x, y);
        setSize(w, h);
        this.isMovable = isMovable;
    }

    public DummySprite(float x, float y, float w, float h, boolean isMovable, boolean playSound) {
        setLocation(x, y);
        setSize(w, h);
        this.isMovable = isMovable;
        this.playSound = playSound;
    }

    public DummySprite(float w, float h, boolean isMovable) {
        this(0, 0, w, h, isMovable, false);
    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
    }

    @Override
    public boolean isMovable() {
        return isMovable;
    }

    @Override
    public void draw(Graphics g) {
        if (GeneralConfig.DEBUG)
            g.drawRect(x, y, getWidth(), getHeight(), borderPaint);
    }

    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
    }

    public static class DummySpriteBuilder {
        float x = 0, y = 0, w = 1, h = 1;
        boolean isMovabel = false;
        boolean isPlaySound = false;

        public DummySpriteBuilder location(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public DummySpriteBuilder size(float w, float h) {
            this.w = w;
            this.h = h;
            return this;
        }

        public DummySpriteBuilder movable(boolean isMovabel) {
            this.isMovabel = isMovabel;
            return this;
        }

        public DummySpriteBuilder sound(boolean isPlaySound) {
            this.isPlaySound = isPlaySound;
            return this;
        }

        public DummySprite build() {
            return new DummySprite(x, y, w, h, isMovabel, isPlaySound);
        }

        public DummySprite buildScaled() {
            return new DummySprite(scale(x), scale(y), scale(w), scale(h), isMovabel, isPlaySound);
        }
    }
}
