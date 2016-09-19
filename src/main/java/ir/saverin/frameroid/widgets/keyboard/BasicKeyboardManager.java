package ir.saverin.frameroid.widgets.keyboard;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.widgets.SoundPlayerSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public class BasicKeyboardManager extends SoundPlayerSprite implements KeyboardManager, KeyboardActionListener {

    protected LayoutKey.KeyboardLayout currentLayout;
    protected Map<LayoutKey.KeyboardLayout, Keyboard> keyboards;
    protected StringBuffer buffer;
    protected Keyboard currentKeyboard = null;
    protected KeyboardActionListener listener;
    protected boolean isRecycled = true;

    public BasicKeyboardManager(LayoutKey.KeyboardLayout defaultLayout) {
        this.currentLayout = defaultLayout;
        keyboards = new HashMap<>();
        this.buffer = new StringBuffer("");
    }

    public void put(LayoutKey.KeyboardLayout layout, Keyboard keyboard) {
        keyboards.put(layout, keyboard);
        keyboard.setBuffer(buffer);
        keyboard.setActionListener(this);
    }


    @Override
    public void toggle(boolean show) {
        isVisible = false;
        if (show) {
            showKeyboard(currentLayout);
        } else {
            if (currentKeyboard != null) {
                currentKeyboard.toggle(false);
            }
        }
    }

    private void showKeyboard(LayoutKey.KeyboardLayout currentLayout) {
        if (currentKeyboard != null && currentKeyboard.isVisible()) {
            currentKeyboard.toggle(false);
        }
        currentKeyboard = keyboards.get(currentLayout);
        if (currentKeyboard != null) {
            isVisible = true;
            currentKeyboard.toggle(true);
        }
    }

    @Override
    public void setBuffer(StringBuffer buffer) {
        this.buffer = buffer;
        for (Keyboard k : keyboards.values()) {
            k.setBuffer(buffer);
        }
    }

    @Override
    public StringBuffer getBuffer() {
        return buffer;
    }

    @Override
    public void changeLayout(LayoutKey.KeyboardLayout layout) {
        this.currentLayout = layout;
        if (currentKeyboard != null && currentKeyboard.isVisible()) {
            showKeyboard(layout);
        }
    }

    @Override
    public void setKeyboardListener(KeyboardActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void initImage(Game game) throws Exception {
        for (Keyboard k : keyboards.values()) {
            k.initImage(game);
        }
        isRecycled = false;
    }

    @Override
    public void recycle() {
        for (Keyboard k : keyboards.values()) {
            k.recycle();
        }
        isRecycled = true;
    }

    @Override
    public boolean isRecycled() {
        return isRecycled;
    }

    @Override
    public void doneCalled() {
        if (listener != null)
            listener.doneCalled();
    }

    @Override
    public void layoutChanged(LayoutKey.KeyboardLayout layout) {
        changeLayout(layout);
        if (listener != null)
            listener.layoutChanged(layout);
    }

    @Override
    public void draw(Graphics g) {
        if (currentKeyboard != null) {
            currentKeyboard.draw(g);
        }
    }

    @Override
    public void setMaxLength(LayoutKey.KeyboardLayout layout, int maxLength) {
        Keyboard keyboard = keyboards.get(layout);
        if (keyboard != null)
            keyboard.setMaxLength(maxLength);
    }

    @Override
    public void setMaxLength(int maxLength) {
        for (Keyboard keyboard : keyboards.values()) {
            keyboard.setMaxLength(maxLength);
        }
    }

    @Override
    public void down(float x, float y) {
        super.down(x, y);
    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean touchDown(int index, float x, float y) {
        if (currentKeyboard != null && isVisible) {
            return currentKeyboard.touchDown(index, x, y);
        }
        return false;
    }

    @Override
    public boolean touchUp(int index, float x, float y) {
        if (currentKeyboard != null && isVisible) {
            return currentKeyboard.touchUp(index, x, y);
        }
        return false;
    }
}
