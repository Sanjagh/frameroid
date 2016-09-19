package ir.saverin.frameroid.widgets;

import android.graphics.Bitmap;
import android.graphics.Paint;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * @author AliReza
 * @since 6/18/16.
 */

/**
 * an image sprite which has a text and it is animated clickable like {@link AnimatedButtonSprite}
 */
public class AnimatedTextCImageSprite extends TextContainedImageSprite {
    protected Bitmap bitmap2 = null;
    protected boolean pressed;
    protected boolean isDefaultIStretchSize;
    protected float imageStrechtWidth;
    protected float imageStrechtHeight;

    protected float textStretchSize;

    public AnimatedTextCImageSprite(FileResource resource, String text, Paint textPaint) {
        super(resource, text, textPaint);
        textStretchSize = textPaint.getTextSize() / 8;
        isDefaultIStretchSize = true;
    }


    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            if (pressed) {
                g.drawImage(bitmap2, x - imageStrechtWidth / 2, y - imageStrechtHeight / 2);
                g.drawText(text, getTextX() - (textStretchSize / 8),
                        getTextY() - (textStretchSize / 8), textPaint);
            } else {
                g.drawImage(bitmap, x, y);
                g.drawText(text, getTextX(), getTextY(), textPaint);
            }
        }
    }


    @Override
    public void initImage(Game game) throws Exception {
        super.initImage(game);
        if (bitmap2 == null) {
            if (isDefaultIStretchSize) {
                imageStrechtHeight = height / 8;
                imageStrechtWidth = width / 8;
            }
            bitmap2 = ResourceHelper.createBitmap(resource, game, (int) (width + imageStrechtWidth), (int) (height + imageStrechtHeight));
        }
    }

    @Override
    public void down(float x, float y) {
        super.down(x, y);
        pressed = true;
        getTextPaint().setTextSize(getTextPaint().getTextSize() + textStretchSize);
    }


    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        pressed = false;
        getTextPaint().setTextSize(getTextPaint().getTextSize() - textStretchSize);
    }

    @Override
    public void recycle() {
        super.recycle();
        if (bitmap2 != null)
            bitmap2.recycle();
        bitmap2 = null;
    }

    public void setImageStrechtHeight(float imageStrechtHeight) {
        this.imageStrechtHeight = imageStrechtHeight;
        isDefaultIStretchSize = false;
    }


    public void setImageStrechtWidth(float imageStrechtWidth) {
        this.imageStrechtWidth = imageStrechtWidth;
        isDefaultIStretchSize = false;
    }

    public float getImageStrechtWidth() {
        return imageStrechtWidth;
    }

    public float getImageStrechtHeight() {
        return imageStrechtHeight;
    }

}
