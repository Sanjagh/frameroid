package ir.saverin.frameroid.widgets;

import android.graphics.Paint;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 2/28/16.
 */

/**
 * a Text Contained Image Sprite which can be drawn with desired transparency.
 */
public class TransparentTextContainedIS extends TextContainedImageSprite {
    protected int transparency;

    public TransparentTextContainedIS(FileResource resource, String text, Paint textPaint) {
        super(resource, text, textPaint);
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (!isRecycled()) {
                g.drawImageWithAlpha(bitmap, x, y, transparency);
            }
            g.drawText(getText(), getTextX(), getTextY(), textPaint);
        }
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public void setItemsTransparency(int transparency) {
        this.transparency = transparency;
        textPaint.setAlpha(transparency);
    }
}
