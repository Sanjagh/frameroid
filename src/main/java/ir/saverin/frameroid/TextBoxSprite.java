package ir.saverin.frameroid;


import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.util.Scaler;
import ir.saverin.frameroid.widgets.ImageSprite;

/**
 * @author AliReza
 * @since 5/5/15.
 */
public class TextBoxSprite extends ImageSprite {

    private int focusColor = 0xffd28b89;

    private float focusX;
    private float focusY;
    private float focusWidth;
    private float focusHeight;

    private boolean showFocus = false;

    public TextBoxSprite(FileResource resource) {
        super(resource, true);
    }


    public TextBoxSprite(FileResource resource, float focusX, float focusY) {
        super(resource, true);
        this.focusX = focusX;
        this.focusY = focusY;
    }

    public TextBoxSprite(FileResource resource, float focusX, float focusY, float focusWidth, float focusHeight) {
        super(resource);
        this.focusX = focusX;
        this.focusY = focusY;
        this.focusWidth = focusWidth;
        this.focusHeight = focusHeight;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (isVisible() && showFocus)
            g.drawRect(focusX, focusY, focusWidth, focusHeight, focusColor);
    }

    public void setFocusColor(int focusColor) {
        this.focusColor = focusColor;
    }

    public int getFocusColor() {
        return focusColor;
    }

    public void setFocusX(float focusX) {
        this.focusX = focusX;
    }

    public float getFocusX() {
        return focusX;
    }

    public void setFocusY(float focusY) {
        this.focusY = focusY;
    }

    public float getFocusY() {
        return focusY;
    }

    public void setFocusLocation(float focusX, float focusY) {
        this.focusX = focusX;
        this.focusY = focusY;
    }

    public void setFocusWidth(float focusWidth) {
        this.focusWidth = focusWidth;
    }

    public float getFocusWidth() {
        return focusWidth;
    }

    public void setFocusHeight(float focusHeight) {
        this.focusHeight = focusHeight;
    }

    public float getFocusHeight() {
        return focusHeight;
    }

    public void setFocusSize(float focusWidth, float focusHeight) {
        this.focusWidth = focusWidth;
        this.focusHeight = focusHeight;
    }

    public void setFocus(boolean showFocus) {
        this.showFocus = showFocus;
    }

    public void setDefaultTextBoxPosition() {
        //it is called when the Image is like texboxes in register screens
        focusX = x + width / 8;
        focusY = y + height + (height / 22) - height / 3;
        focusWidth = width - 2 * width / 8;
        focusHeight = Scaler.scale(2);
    }

    public boolean isFocused() {
        return showFocus;
    }
}
