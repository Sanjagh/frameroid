package ir.saverin.frameroid.widgets;


import android.graphics.Bitmap;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;
import ir.saverin.frameroid.util.sound.SoundPlayer;

/**
 * @author AliReza
 * @since 6/6/15.
 */
public class AnimatedButtonSprite extends ImageSprite {
    protected Bitmap bitmap2 = null;
    protected boolean pressed;
    protected boolean isDefaultStretchSize;
    protected float strechtWidth;
    protected float strechtHeight;
    private SoundPlayer.SoundType soundType;

    public AnimatedButtonSprite(FileResource resource) {
        super(resource);
        isDefaultStretchSize = true;
        playSound = true;
    }

    public AnimatedButtonSprite(FileResource resource, float strechtWidth, float strechtHeight) {
        super(resource);
        this.strechtWidth = strechtWidth;
        this.strechtHeight = strechtHeight;
        isDefaultStretchSize = false;

    }

    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            if (pressed) {
                g.drawImage(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2);
            } else {
                g.drawImage(bitmap, x, y);
            }
        }
    }

    @Override
    public void initImage(Game game) throws Exception {
        super.initImage(game);
        if (bitmap2 == null) {
            if (isDefaultStretchSize) {
                strechtHeight = height / 8;
                strechtWidth = width / 8;
            }
            bitmap2 = ResourceHelper.createBitmap(resource, game, (int) (width + strechtWidth), (int) (height + strechtHeight));
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        if (bitmap2 != null)
            bitmap2.recycle();
        bitmap2 = null;
    }

    @Override
    public void down(float x, float y) {
        super.down(x, y);
        pressed = true;
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        pressed = false;
    }

    public float getStretchWidth() {
        return strechtWidth;
    }

    public float getStretchHeight() {
        return strechtHeight;
    }

}
