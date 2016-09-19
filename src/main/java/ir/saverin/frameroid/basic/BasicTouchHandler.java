package ir.saverin.frameroid.basic;

import android.view.MotionEvent;
import android.view.View;
import ir.saverin.frameroid.api.ScreenSize;
import ir.saverin.frameroid.util.Pointer;
import ir.saverin.frameroid.util.TouchEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.*;

public class BasicTouchHandler implements View.OnTouchListener, ir.saverin.frameroid.api.io.TouchHandler {

    /**
     * Maximum fingers managed
     */
    public static final int MAX = 12;
    private static final String TAG = BasicTouchHandler.class.getName();

    private final float[] xs = new float[MAX];
    private final float[] ys = new float[MAX];
    private final boolean[] down = new boolean[MAX];

    private final int[] ids = new int[MAX];
    private int lastFinger = 0;
    private final float scaleX;
    private final float scaleY;
    private final float xOffset;
    private final float yOffset;

    private final List<TouchEventListener> listeners;
    private boolean isEnabled = true;

    public BasicTouchHandler(View view, ScreenSize screenSize) {
        this.listeners = new ArrayList<>();
        view.setOnTouchListener(this);
        this.scaleX = (float) screenSize.getScale();
        this.scaleY = (float) screenSize.getScale();
        this.xOffset = screenSize.getXOffset();
        this.yOffset = screenSize.getYOffset();

        for (int counter = 0; counter < MAX; counter++) {
            xs[counter] = 0;
            ys[counter] = 0;
            down[counter] = false;
            ids[counter] = 0;
            lastFinger = 0;
        }
    }

    @Override
    public void updatePointers(Pointer[] pointers) {
        if (isEnabled)
            for (int counter = 0; counter < MAX && counter < pointers.length; counter++) {
                pointers[counter].x = (int) xs[counter];
                pointers[counter].y = (int) ys[counter];
                pointers[counter].isEnabled = down[counter];
            }
    }

    @Override
    public void toggle(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isEnabled)
            for (int index = 0; index < event.getPointerCount() && index < MAX; index++) {
                int id = event.getPointerId(index);
                float x = event.getX(index);
                float y = event.getY(index);


                int internalIndex = -1;
                for (int current : ids) {
                    if (current == id) {
                        internalIndex = current;
                        break;
                    }
                }

                if (internalIndex == -1) {
                    while (down[lastFinger]) {
                        lastFinger++;
                        if (lastFinger >= MAX)
                            lastFinger = 0;
                    }

                    internalIndex = lastFinger;
                    ids[internalIndex] = id;

                }

//            xs[internalIndex] = (x / scaleX) - xOffset;
//            ys[internalIndex] = (y / scaleY ) - yOffset;
                xs[internalIndex] = (x) - xOffset;
                ys[internalIndex] = (y) - yOffset;

                if (index == event.getActionIndex()) {
                    int action = event.getActionMasked();

                    if (action == ACTION_DOWN || action == ACTION_POINTER_DOWN) {
                        down[internalIndex] = true;
                        for (int counter = 0; counter < listeners.size(); counter++) {
                            TouchEventListener listener = listeners.get(counter);
                            if (listener.touchDown(internalIndex, xs[internalIndex], ys[internalIndex])) {
                                break;
                            }
                        }
                    }

                    if (action == ACTION_UP || action == ACTION_POINTER_UP) {
                        down[internalIndex] = false;

                        for (int counter = 0; counter < listeners.size(); counter++) {
                            TouchEventListener listener = listeners.get(counter);
                            if (listener.touchUp(internalIndex, xs[internalIndex], ys[internalIndex])) {
                                break;
                            }
                        }
                    }
                }
            }


        return true;
    }

    @Override
    public int getMaxPointers() {
        return MAX;
    }

    @Override
    public float getX(int pointer) {
        return xs[pointer];
    }

    @Override
    public float getY(int pointer) {
        return ys[pointer];
    }

    @Override
    public boolean isUp(int pointer) {
        return down[pointer];
    }

    @Override
    public void addTouchListener(TouchEventListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public boolean removeTouchListener(TouchEventListener listener) {
        return listeners.remove(listener);
    }
}
