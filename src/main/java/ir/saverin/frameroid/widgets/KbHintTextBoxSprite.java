package ir.saverin.frameroid.widgets;

import android.graphics.Paint;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.widgets.keyboard.KeyboardManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AliReza
 * @since 3/14/16.
 */
public class KbHintTextBoxSprite extends HintTextBoxSprite {
    private KeyboardManager keyboardManager;
    protected Paint textPaint;
    protected float textX;
    protected float textY;
    protected List<AbstractSprite> contrastItems;

    public KbHintTextBoxSprite(FileResource resource, StringBuffer textStringBuffer, String hint, Paint hintPaint, Paint textPaint, KeyboardManager keyboardManager) {
        super(resource, textStringBuffer, hint, hintPaint);
        this.keyboardManager = keyboardManager;
        this.textPaint = textPaint;
        contrastItems = new ArrayList<>();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (textStringBuffer.length() > 0 && isVisible) {
            g.drawText(textStringBuffer.toString(), textX, textY, textPaint);
        }
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        if (inside) {
            if (!isFocused()) {
                toggle(true);
            }
        }
    }

    @Override
    public void setDefaultTextBoxPosition() {
        super.setDefaultTextBoxPosition();
        textX = this.x + (this.width / 2);
        textY = this.y + (this.height / 2) + (this.height / 8);
        setHintRatioToTextBox(width / 2, (height / 2) + (height / 8));
    }

    public void toggle(boolean show) {
        setFocus(show);
        keyboardManager.toggle(show);
        for (AbstractSprite contrastItem : contrastItems) {
            contrastItem.setVisible(!show);
        }
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    /**
     * adds items that must be invisible when the keyboard is visible
     *
     * @param sprite the item that must be invisible when keyboard is visible
     */
    public void addContrastItem(AbstractSprite sprite) {
        contrastItems.add(sprite);
    }
}
