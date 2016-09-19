package ir.saverin.frameroid.widgets;

import android.graphics.Paint;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 4/24/16.
 */

/**
 * an {@link ImageToggleSprite} that contains a text with.
 */
public class TextIToggleSprite extends ImageToggleSprite {
    protected String text;
    protected Paint textPaint;
    protected float textRatioX;
    protected float textRatioY;


    public TextIToggleSprite(FileResource enableResource, FileResource disableResource, String text, Paint textPaint, boolean isEnabled, Game game) {
        super(enableResource, disableResource, isEnabled, game);
        this.text = text;
        this.textPaint = textPaint;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (isVisible) {
            g.drawText(text, getTextX(), getTextY(), textPaint);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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


}
