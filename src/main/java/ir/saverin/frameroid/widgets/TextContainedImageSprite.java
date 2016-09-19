package ir.saverin.frameroid.widgets;

import android.graphics.Paint;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 12/7/15.
 */

/**
 * it's an ImageSprite that contains a text near it.
 */
public class TextContainedImageSprite extends ImageSprite {
    protected final Paint textPaint;
    protected String text;
    private float textRatioX;
    private float textRatioY;

    public TextContainedImageSprite(FileResource resource, String text, Paint textPaint) {
        super(resource);
        this.text = text;
        this.textPaint = textPaint;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (!isRecycled()) {
                g.drawImage(bitmap, x, y);
            }
            g.drawText(text, getTextX(), getTextY(), textPaint);
        }
    }

    /**
     * sets the text location ratio between location of the image and location of the text. it must be
     * dependent to the location of image to can be used in sliders as well. because in this case the
     * location of the Image is changing as it being slid.
     *
     * @param textRatioX the x ratio between x of the text and x of the image.
     * @param textRatioY the y ratio between y of the text and y of the image.
     */
    public void setTextRatioToImage(float textRatioX, float textRatioY) {
        this.textRatioX = textRatioX;
        this.textRatioY = textRatioY;
    }

    public float getTextX() {
        return this.x + textRatioX;
    }

    public float getTextY() {
        return this.y + textRatioY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Paint getTextPaint() {
        return textPaint;
    }
}
