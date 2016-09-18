package ir.saverin.frameroid.util;

import ir.saverin.frameroid.api.ScreenSize;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public final class Scaler {

    private static double scale = 1;
    private static float xOffset = 0;
    private static float yOffset = 0;

    public static float scale(float original) {
        return (float) (scale * original);
    }

    public static float getScale() {
        return (float) scale;
    }

    public static float unscale(float onScreen) {
        return (int) (onScreen / scale);
    }

    public static void set(ScreenSize screenSize) {
        scale = screenSize.getScale();
        xOffset = screenSize.getXOffset();
        yOffset = screenSize.getYOffset();
    }
}
