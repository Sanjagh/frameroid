package ir.saverin.frameroid.widgets.keyboard;

import android.graphics.Point;
import ir.saverin.frameroid.api.io.FileStorage;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.util.TouchEventListener;
import ir.saverin.frameroid.util.sound.SoundPlayer;
import ir.saverin.frameroid.widgets.LoadableImage;
import ir.saverin.frameroid.widgets.SoundPlayerSprite;

import java.util.HashMap;
import java.util.Map;

import static ir.saverin.frameroid.util.Scaler.scale;
import static ir.saverin.frameroid.util.Scaler.unscale;

/**
 * @author S.Hosein Ayat
 */
public abstract class Keyboard extends SoundPlayerSprite implements TouchEventListener, LoadableImage {

    private static final String TAG = Keyboard.class.getName();

    protected StringBuffer buffer;
    protected LayoutKey.KeyboardLayout currentLayout;
    protected FileStorage storage;
    protected Map<String, Point> touches;
    protected KeyboardActionListener listener;
    protected int maxLength = -1;

    public Keyboard(LayoutKey.KeyboardLayout defaultLayout, FileStorage storage) {
        this(defaultLayout, storage, 1000, 290);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public Keyboard(LayoutKey.KeyboardLayout layout, FileStorage storage, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 600 - this.height;

        this.width = scale(this.width);
        this.height = scale(this.height);
        this.x = scale(this.x);
        this.y = scale(this.y);

        buffer = new StringBuffer();
        this.storage = storage;
        currentLayout = layout;
        touches = new HashMap<>();
    }

    public void setBuffer(StringBuffer buffer) {
        this.buffer = buffer;
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public void setActionListener(KeyboardActionListener listener) {
        this.listener = listener;
    }

    @Override
    public abstract void draw(Graphics g);

    public void toggle(boolean show) {
        if (show) {
            initResources();
            isVisible = true;
        } else {
            isVisible = false;
            recycleResources();
        }
    }

    protected abstract void initResources();

    protected abstract void recycleResources();

    @Override
    public boolean touchDown(int index, float x, float y) {
        if (isInside(x, y)) {
            x = x - this.x;
            y = y - this.y;
            x = unscale(x);
            y = unscale(y);
            touches.put(index + "", new Point((int) x, (int) y));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int index, float x, float y) {
        x = x - this.x;
        y = y - this.y;
        x = unscale(x);
        y = unscale(y);

        Point start = touches.get(index + "");
        Point end = new Point((int) x, (int) y);

        if (start != null) {
            processTap(start, end);
        }

        touches.remove(index + "");
        return isInside(x, y) && start != null;

    }

    protected void processTap(Point start, Point end) {

        Key key = getKey(start, end);
        if (key != null) {
            SoundPlayer.getInstance().play(SoundPlayer.SoundType.KEYBOARD);
            switch (key.getKeyType()) {
                case BACKSPACE:
                    backspace();
                    break;
                case SPACE:
                    space();
                    break;
                case DONE:
                    done();
                    break;
                case CODE:
                    code(key);
                    break;
                case CHANGE_LAYOUT:
                    changeLayout(key);
                    break;
            }
        }

    }

    private void changeLayout(Key key) {
        if (listener != null) {
            LayoutKey layoutKey = (LayoutKey) key;
            listener.layoutChanged(layoutKey.layout);
        }
    }

    private void code(Key key) {
        CodeKey codeKey = (CodeKey) key;
        int code = codeKey.getCode();
        char ch = (char) code;
        if (maxLength <= 0 || buffer.length() <= maxLength)
            buffer.append(ch);
    }

    protected void done() {
        if (listener != null) {
            listener.doneCalled();
        }
    }

    protected void space() {
        buffer.append(' ');
    }

    protected void backspace() {
        int len = buffer.length();
        if (len >= 1)
            buffer.deleteCharAt(len - 1);
    }

    protected abstract KeyWrapper[] getWrappers();

    protected Key getKey(Point start, Point end) {
        for (KeyWrapper wrapper : getWrappers()) {
            if (wrapper.isInside(start, end))
                return wrapper.key;
        }
        return null;
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (isVisible)
            initResources();
    }

    @Override
    public void recycle() {
        isVisible = false;
        recycleResources();
    }
}