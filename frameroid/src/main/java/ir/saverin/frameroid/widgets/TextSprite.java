package ir.saverin.frameroid.widgets;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import ir.saverin.frameroid.api.media.Graphics;

/**
 * @author AliReza
 * @since 8/26/15.
 */
public class TextSprite extends DummySprite {
    protected String text;
    protected Paint hoverPaint;
    protected RectF rectF;
    protected boolean isDown;
    protected Paint textPaint;
    protected float radiusX;
    protected float radiusY;

    /**
     * @param x         the x of the text
     * @param y         the y of the text
     * @param isMovable
     * @param text
     * @param textPaint the paint of the text. its default alignment must be set to left
     *                  or it doesn't work properly because the default alignment in Paint
     *                  is set to the Left and the best result for listening to click is when the
     *                  text paint alignment is set to left and all the locations is based on Left
     *                  alignment. you can set it to Right or Centre just in case of showing the text
     *                  without any hover.
     */
    public TextSprite(float x, float y, boolean isMovable, String text, Paint textPaint) {
        super(x, y, 0, 0, isMovable);
        playSound = true;
        this.text = text;
        Rect bounds = new Rect();
        this.textPaint = textPaint;

        hoverPaint = new Paint();
        hoverPaint.setColor(0x44736F6E);
        hoverPaint.setAntiAlias(true);
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        setSize(bounds.width(), bounds.height());

        /**
         * the calculation must be dependent to the text size to be easy to use.
         */
        float hoverStartX = this.x - getWidth() / 6;
        float hoverEndX = this.x + getWidth() + getWidth() / 4;
        float hoverStartY = this.y - this.getHeight();
        float hoverEndY = this.y + this.getHeight() / 2 + getHeight() / 5;

        rectF = new RectF(hoverStartX, hoverStartY, hoverEndX, hoverEndY);
        radiusX = getWidth() * 0.8f;
        radiusY = getHeight() * 3f;


    }

    /**
     * @param x         the x of the text
     * @param y         the y of the text
     * @param isMovable
     * @param text
     * @param textPaint the paint of the text. its default alignment must be set to left
     *                  or it doesn't work properly because the default alignment in Paint
     *                  is set to the Left and the best result for listening to click is when the
     *                  text paint alignment is set to left and all the locations is based on Left
     *                  alignment. you can set it to Right or Centre just in case of showing the text
     *                  without any hover.
     * @param rectF     the hover area when user put his/her finger on the text.
     */
    public TextSprite(float x, float y, boolean isMovable, String text, Paint textPaint, RectF rectF) {
        this(x, y, isMovable, text, textPaint);
        this.rectF = rectF;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (isDown) {
                g.drawRoundRect(rectF, hoverPaint, radiusX, radiusY);
            }
            g.drawText(text, this.x, this.y, textPaint);
        }
    }

    @Override
    public void down(float x, float y) {
        super.down(x, y);
        isDown = true;
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        isDown = false;
    }

    public void setHoverPaint(Paint hoverPaint) {
        this.hoverPaint = hoverPaint;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
