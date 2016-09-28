package ir.saverin.frameroid.resource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.util.FileResource;

import java.io.IOException;

/**
 * @author S.Hosein Ayat
 */
public class ResourceHelper {

    public static final String SEPARATOR = "@";

    public static Bitmap createBitmap(FileResource resource, Game game) throws IOException {

        Bitmap bmp = BitmapFactory.decodeStream(game.getFileStorage().createInputStream(resource));
        double d = game.getScreenSize().getScale();
        int w = (int) (bmp.getWidth() * d);
        int h = (int) (bmp.getHeight() * d);
        Bitmap img = Bitmap.createScaledBitmap(bmp, w, h, true);
        if (img != bmp)
            bmp.recycle();
        return img;
    }

    public static Bitmap createBitmap(FileResource resource, Game game, int targetW, int targetH) throws IOException {
        Bitmap bmp = BitmapFactory.decodeStream(game.getFileStorage().createInputStream(resource));
        Bitmap img = Bitmap.createScaledBitmap(bmp, targetW, targetH, true);
        if (bmp != img)
            bmp.recycle();
        return img;
    }
}
