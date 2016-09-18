package ir.saverin.frameroid.widgets.slider;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * Created by fahim on 3/10/15.
 *
 * @author <a href="mailto:fahim.ayat@gmail.com">Fahim Ayat</a>
 */
public class SteppingSlider<T extends LoadableImage> extends BasicSlider<T> {

    protected static final int DEFAULT_ALLOWED_EMPTY_SPACE_MULTIPLIER = 6;

    protected static final float DEFAULT_SPEED_THRESHOLD = 1f;
    protected T currentWidget = null;


    public SteppingSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game,
                          boolean areWidgetsDraggable, boolean isHorizontal, float gap, boolean playSound) {
        this(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, gap,
                DEFAULT_ALLOWED_EMPTY_SPACE_MULTIPLIER, DEFAULT_SPEED_THRESHOLD, playSound);
    }


    public SteppingSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game,
                          boolean areWidgetsDraggable, boolean isHorizontal, float gap, float allowedEmptySpaceMultiplier,
                          float speedThreshold, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound);
        this.gap = gap;
        if (isHorizontal) {
            allowedEmptySpace = allowedEmptySpaceMultiplier * widgetW;
        } else {

            allowedEmptySpace = allowedEmptySpaceMultiplier * widgetH;
        }
        this.speedThreshold = speedThreshold;
    }

    @Override
    protected void userMovementEnded() {

        float minDistance = Integer.MAX_VALUE;
        float absMinDistance = minDistance;
        T currentWidget = null;
        if (isHorizontal) {
            for (T t : widgets) {
                float distance = sliderCenter - t.getX();
                float absDistance = Math.abs(distance);
                if (absDistance < absMinDistance) {
                    minDistance = distance;
                    absMinDistance = absDistance;
                    currentWidget = t;
                }
            }
        } else {
            for (T t : widgets) {
                float distance = sliderCenter - t.getY();
                float absDistance = Math.abs(distance);
                if (absDistance < absMinDistance) {
                    minDistance = distance;
                    absMinDistance = absDistance;
                    currentWidget = t;
                }
            }
        }

        if (absMinDistance > speedThreshold) {
            this.currentWidget = null;
            if (minDistance > 0) {
                currentSpeed = speedThreshold;
            } else {
                currentSpeed = -1 * speedThreshold;
            }
            currentOffset += (currentSpeed);
            update();
        } else {
            this.currentWidget = currentWidget;
            currentSpeed = 0;
        }
    }

    /**
     * Stable is the state that the slider is not moving and a widget is standing still at the center of the slider.
     *
     * @return true if the slider is stable.
     */
    public boolean isStable() {
        return currentSpeed == 0 && !isTouchDown;
    }

    /**
     * This method is used to get the widget that is currently stayed at the center of the screen.
     *
     * @return null if the slider is not stable.
     */
    public T getCurrentWidget() {
        return currentWidget;
    }

    public float getSliderCenter() {
        return sliderCenter + widgetW / 2;
    }
}
