package ir.saverin.frameroid.widgets.keyboard;

import android.graphics.Point;

/**
 * @author S.Hosein Ayat
 */
public class KeyWrapper {

    protected Key key;
    protected float x, y, w, h;

    public KeyWrapper(Key key, float x, float y, float w, float h) {
        this.key = key;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isInside(Point start, Point end) {
        return isInside(start) && isInside(end);
    }

    public boolean isInside(Point p) {
        return p.x > x && p.x < (x + w) && p.y > y && p.y < (y + h);
    }

    public static KeyWrapper key(Key key, float x, float y, float w, float h) {
        return new KeyWrapper(key, x, y, w, h);
    }
}
