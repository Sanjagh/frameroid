package ir.saverin.frameroid.widgets;

import android.graphics.Bitmap;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * @author S.Hosein Ayat
 */
public class ImageSprite extends SoundPlayerSprite implements LoadableImage {
    private static final String TAG = ImageSprite.class.getName();
    protected FileResource resource;
    protected Bitmap bitmap = null;


    public ImageSprite(FileResource resource) {
        this(resource, false, false);
    }

    public ImageSprite(FileResource resource, boolean soundEnable) {
        this(resource, false, soundEnable);
    }

    public ImageSprite(FileResource resource, boolean isMovable, boolean soundEnale) {
        this.resource = resource;
        this.isMovable = isMovable;
        playSound = soundEnale;
    }


    @Override
    public void draw(Graphics g) {
        if (isVisible)
            g.drawImage(bitmap, x, y);
    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (bitmap == null) {
            bitmap = ResourceHelper.createBitmap(resource, game);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }
    }

    @Override
    public void recycle() {
        if (bitmap != null)
            bitmap.recycle();
        bitmap = null;
    }

    @Override
    public boolean isRecycled() {
        return bitmap == null;
    }

    public FileResource getResource() {
        return resource;
    }

    public void setResource(FileResource resource) {
        this.resource = resource;
    }
}
