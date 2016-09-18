package ir.saverin.frameroid.widgets;

import android.util.Log;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 4/24/16.
 */

/**
 * a sprite that has two modes. an enable mode and a disable mode. each mode has its own image.
 */
public class ImageToggleSprite extends AnimatedButtonSprite {

    private static final String TAG = ImageToggleSprite.class.getName();
    protected FileResource enableResource;
    protected FileResource disableResource;
    protected Game game;
    protected boolean isEnabled;
    protected boolean autoToggle;

    public ImageToggleSprite(FileResource enableResource, FileResource disableResource, boolean isEnabled, Game game) {
        super(isEnabled ? enableResource : disableResource);
        this.enableResource = enableResource;
        this.disableResource = disableResource;
        this.isEnabled = isEnabled;
        this.game = game;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        resource = this.isEnabled ? enableResource : disableResource;
        recycle();
        try {
            initImage(game);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "error in initializing the image", e);
        }
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        if (autoToggle) {
            setEnabled(!isEnabled);
        }
    }

    public void setAutoToggle(boolean autoToggle) {
        this.autoToggle = autoToggle;
    }
}
