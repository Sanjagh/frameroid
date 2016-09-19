package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.api.widget.SpriteClickListener;
import ir.saverin.frameroid.util.TouchEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim.Ayat@gmail.com</a>
 */
public class ButtonManager<T extends Sprite> extends AbstractSpriteContainer<T> implements TouchEventListener {

    private static final String TAG = ButtonManager.class.getName();

    Map<Integer, T> downButtons;

    public ButtonManager() {
        super();
        downButtons = new HashMap<Integer, T>();
    }

    @Override
    public void updateLocations(TouchHandler touchHandler) {
    }

    @Override
    public void draw(Graphics g) {
        for (T t : sprites) {
            t.draw(g);
        }
    }

    @Override
    public boolean touchDown(int index, float x, float y) {
        for (T widget : sprites) {
            if (widget.isInside(x, y) && widget.isVisible()) {
                widgetDown(index, widget, x, y);
                return true;
            }
        }
        return false;
    }

    protected void widgetDown(int index, T widget, float x, float y) {
        downButtons.put(index, widget);
        widget.down(x, y);
        for (SpriteClickListener<T> listener : clickListeners) {
            listener.widgetDown(widget, x, y, index);
        }
    }

    @Override
    public boolean touchUp(int index, float x, float y) {
        if (downButtons.containsKey(index)) {
            T widget = downButtons.remove(index);

            widgetUp(index, x, y, widget);

            return true;
        }
        return false;
    }

    protected void widgetUp(int index, float x, float y, T widget) {
        boolean clicked = widget.isInside(x, y);
        widget.up(clicked, x, y);
        for (SpriteClickListener<T> listener : clickListeners) {
            listener.widgetUp(widget, clicked, x, y);
        }
    }
}
