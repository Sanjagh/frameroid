package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.util.TouchEventListener;

import java.lang.reflect.Array;

/**
 * A simplified version of Sprite manager, in the sense that it does not support features in button manager.
 * Note that in comparison with regular {@link ir.saverin.frameroid.widgets.SpriteManager} , it manages sprites and their order differently.
 * On every touch , brings touched sprite to the top, and also the rendering sequence is vise versa.
 * <p/>
 * Created by Fahim on 4/9/15.
 */
public class OrderedSpriteMover<T extends Sprite> implements TouchEventListener {

    private final T[] items;
    private final int[] indices;
    public final int size;
    private final float[] xOffset;
    private final float[] yOffset;
    public final int[] actorsByOrder;
    public final boolean[] isAccessories;

    public OrderedSpriteMover(int size, Class<T> clas, int[] orders) {
        @SuppressWarnings("unchecked")
        final T[] a = (T[]) Array.newInstance(clas, size);
        this.items = a;
        isAccessories = new boolean[size];
        this.indices = new int[size];
        this.size = size;
        this.xOffset = new float[size];
        this.yOffset = new float[size];
        this.actorsByOrder = orders;
        for (int counter = 0; counter < size; counter++) {
            indices[counter] = -1;
            xOffset[counter] = 0;
            yOffset[counter] = 0;
            actorsByOrder[counter] = counter;
        }
    }

    public void draw(Graphics g) {
        for (int counter = size - 1; counter >= 0; counter--) {
            T t = items[actorsByOrder[counter]];
            if (t != null) {
                t.draw(g);
            }
        }
    }

    @Override
    public boolean touchDown(int index, float x, float y) {

        for (int order = 0; order < size; order++) {
            int counter = actorsByOrder[order];
            T t = items[counter];

            if (t != null && t.isInside(x, y) && (indices[counter] == -1)) {
                t.down(x, y);
                int next = actorsByOrder[0];
                for (int current = 1; current < size && next != counter; current++) {
                    int temp = actorsByOrder[current];
                    actorsByOrder[current] = next;
                    next = temp;
                }
                actorsByOrder[0] = next;
                orderActors();
                indices[counter] = index;
                xOffset[counter] = t.getX() - x;
                yOffset[counter] = t.getY() - y;
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean touchUp(int index, float x, float y) {
        for (int counter = 0; counter < size; counter++) {
            if (indices[counter] == index) {
                indices[counter] = -1;
            }
        }
        return false;
    }

    public T get(int index) {
        return items[index];
    }

    /**
     * Set or unset an element in the array
     *
     * @param t     desired element, or null to unset
     * @param index index of the array
     */
    public void set(T t, int index, boolean isAccessory) {
        synchronized (items) {
            this.items[index] = t;
            this.isAccessories[index] = isAccessory;
            orderActors();
        }
    }

    private void orderActors() {
        int index = 0;
        for (int i = 0; i < actorsByOrder.length; i++) {
            if (isAccessories[actorsByOrder[i]]) {
                int temp = actorsByOrder[i];
                for (int j = i; j >= index + 1; j--) {
                    actorsByOrder[j] = actorsByOrder[j - 1];
                }
                actorsByOrder[index] = temp;
                index++;

            }

        }

    }

    public void unset(T t) {
        for (int counter = 0; counter < size; counter++) {
            if (items[counter] == t) {
                synchronized (items) {
                    items[counter] = null;
                    isAccessories[counter] = false;
                }
                return;
            }
        }
    }


    public void updateLocations(TouchHandler touchHandler) {
        for (int counter = 0; counter < size; counter++) {
            if (indices[counter] != -1) {
                T t = items[counter];
                int index = indices[counter];
                t.setLocation(touchHandler.getX(index) + xOffset[counter], touchHandler.getY(index) + yOffset[counter]);
            }
        }
    }

}
