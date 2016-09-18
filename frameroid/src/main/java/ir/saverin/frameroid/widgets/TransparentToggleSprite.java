package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 12/27/15.
 */

/**
 * a sprite that has two modes. an enable mode and a disable mode. the disable mode should have less transparency.
 */
public class TransparentToggleSprite extends AnimatedButtonSprite {
    private boolean isEnabled;
    private boolean isAutoToggle;
    private int transparency;

    public TransparentToggleSprite(FileResource resource) {
        super(resource);
        transparency = 100;
        isEnabled = true;
    }


    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (!isRecycled()) {
                if (pressed) {
                    g.drawImageWithAlpha(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2, getDrawTransparency());
                } else {
                    g.drawImageWithAlpha(bitmap, x, y, getDrawTransparency());
                }
            }
        }
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        if (inside) {
            if (isAutoToggle) {
                isEnabled = !isEnabled;
            }
        }
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public int getDrawTransparency() {
        int transparency;
        if (isEnabled) {
            transparency = 255;
        } else {
            transparency = this.transparency;
        }
        return transparency;
    }

    public void setAutoToggle(boolean isAutoToggle) {
        this.isAutoToggle = isAutoToggle;
    }
}
