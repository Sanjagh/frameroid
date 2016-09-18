package ir.saverin.frameroid.widgets.slider;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.widget.SpriteClickListener;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * @author AliReza
 * @since 9/23/15.
 */

/**
 * a slider that can have {@link ir.saverin.frameroid.widgets.AnimatedButtonSprite} as widgets.
 * the {@link ir.saverin.frameroid.widgets.slider.BasicSlider} can not have(it can contain but it has bug
 * when the user hold his/her hand on that widget and move it and then leave it).
 * this kind of sprite can not be implemented well unless to override the
 * {@link ir.saverin.frameroid.widgets.slider.AnimatableBasicSlider#touchUp(int, float, float)}
 * method.
 *
 * @param <T>
 */
public class AnimatableBasicSlider<T extends LoadableImage> extends BasicSlider<T> {
    public AnimatableBasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound);
    }

    public AnimatableBasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound, float gap) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound, gap);
    }

    @Override
    public synchronized boolean touchUp(int index, float x, float y) {
        if (!(isVisible && isTouchDown))
            return false;
        /*
         *  If the any finger other than the first finger on slider is up, it is ignored.
         */
        if (index == touchIndex) {
            if (isTouchDownOnAWidget) {
                float d = distanceFromTouch(x, y);
                if (d < CLICK_THRESHOLD) {
                    for (SpriteClickListener<T> l : listeners) {
                        if (selectedWidget.isInside(x, y)) {
                            selectedWidget.up(true, x, y);
                        }
                        l.widgetUp(selectedWidget, selectedWidget.isInside(x, y), x, y);
                    }
                } else {
                    /**
                     * this is where is different from the {@link ir.saverin.frameroid.widgets.slider.BasicSlider}
                     * we have to call the {@link ir.saverin.frameroid.widgets.LoadableImage#up(boolean, float, float)}
                     * method because the {@link ir.saverin.frameroid.widgets.AnimatedButtonSprite} needs its
                     * {@link ir.saverin.frameroid.widgets.AnimatedButtonSprite#up(boolean, float, float)} method
                     * to be called to reset the size of the bitmap.
                     */
                    selectedWidget.up(true, x, y);
                }
            }

            if (widgetsAreDraggable) {
                if (isWidgetDragging) {
                    for (SpriteDragListener<T> l : dragListeners) {
                        l.widgetDragEnd(selectedWidget, x, y);
                    }
                }
            }

            isWidgetDragging = false;
            isTouchDown = false;
            isTouchDownOnAWidget = false;
            selectedWidget = null;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean touchDown(int index, float x, float y) {
        if (!isVisible)
            return false;

        /*
         *  First checks to ensure that touch is not already done.
         */
        if (!isTouchDown) {
            isDragDetectionDone = false;
            isWidgetDragging = false;
            if (isInside(x, y)) {
                touchX = x;
                touchY = y;
                touchIndex = index;
                lastProcessedX = x;
                lastProcessedY = y;

                for (T t : widgets) {
                    if (t.isInside(x, y)) {
                        selectedWidget = t;
                        isTouchDownOnAWidget = true;
                        for (SpriteClickListener<T> l : listeners) {
                            l.widgetDown(t, x, y, index);
                        }
                        selectedWidget.down(x, y);
                        break;
                    }

                }
                isTouchDown = true;
                return isTouchDown;
            }
        }

        return false;
    }
}
