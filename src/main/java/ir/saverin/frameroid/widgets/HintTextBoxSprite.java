package ir.saverin.frameroid.widgets;

import android.graphics.Paint;

import ir.saverin.frameroid.TextBoxSprite;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 1/19/16.
 */
public class HintTextBoxSprite extends TextBoxSprite {
    protected StringBuffer textStringBuffer;
    protected String hint;
    protected Paint hintPaint;
    private float hintRatioX;
    private float hintRatioY;

    public HintTextBoxSprite(FileResource resource, StringBuffer textStringBuffer, String hint, Paint hintPaint) {
        super(resource);
        this.textStringBuffer = textStringBuffer;
        this.hint = hint;
        this.hintPaint = hintPaint;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (textStringBuffer.length() == 0 && isVisible) {
            g.drawText(hint, getHintX(), getHintY(), hintPaint);
        }
    }

    public void setHintRatioToTextBox(float hintRatioX, float hintRatioY) {
        this.hintRatioX = hintRatioX;
        this.hintRatioY = hintRatioY;
    }

    public void setTextStringBuffer(StringBuffer textStringBuffer) {
        this.textStringBuffer = textStringBuffer;
    }

    public StringBuffer getTextStringBuffer() {
        return textStringBuffer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public float getHintX() {
        return this.x + hintRatioX;
    }


    public float getHintY() {
        return this.y + hintRatioY;
    }
}
