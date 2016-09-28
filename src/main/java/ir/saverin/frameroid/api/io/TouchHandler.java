package ir.saverin.frameroid.api.io;

import ir.saverin.frameroid.util.Pointer;
import ir.saverin.frameroid.util.TouchEventListener;

/**
 * @author S.Hosein Ayat
 */
public interface TouchHandler {
    int getMaxPointers();

    float getX(int pointer);

    float getY(int pointer);

    boolean isUp(int pointer);

    void addTouchListener(TouchEventListener listener);

    boolean removeTouchListener(TouchEventListener listener);

    void updatePointers(Pointer[] pointers);

    /**
     * Enables or disable touch handler
     *
     * @param enabled new touch handler state
     */
    void toggle(boolean enabled);

}
