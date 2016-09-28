package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.widget.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author S.Hosein Ayat
 */
public class SpriteManager<T extends Sprite> extends ButtonManager<T> {

    Map<Integer, Float> downXs = new HashMap<Integer, Float>();
    Map<Integer, Float> downYs = new HashMap<Integer, Float>();

    @Override
    public void updateLocations(TouchHandler touchHandler) {
        for (Object key : downButtons.keySet()) {
            Integer pointerIndex = (Integer) key;
            T sprite = downButtons.get(key);
            if (sprite.isMovable()) {
                float x = touchHandler.getX(pointerIndex) + downXs.get(key);
                float y = touchHandler.getY(pointerIndex) + downYs.get(key);
                sprite.setLocation(x, y);
            }
        }
    }

    @Override
    protected void widgetDown(int index, T widget, float x, float y) {
        super.widgetDown(index, widget, x, y);

        downXs.put(index, widget.getX() - x);
        downYs.put(index, widget.getY() - y);
    }

    @Override
    protected void widgetUp(int index, float x, float y, T widget) {
        super.widgetUp(index, x, y, widget);

        downXs.remove(index);
        downYs.remove(index);
    }
}

