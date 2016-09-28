package ir.saverin.frameroid.widgets.slider;

import android.util.Log;

import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.SpriteClickListener;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.api.widget.slider.Slider;
import ir.saverin.frameroid.util.Scaler;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * Basic implementation of {@link Slider} which contains basic logic and rendering.
 *
 * @author S.Hosein Ayat
 */
public class BasicSlider<T extends LoadableImage> extends AbstractSlider<T> {

    private static final String TAG = BasicSlider.class.getName();
    protected float SPEED_REDUCER = 0.95f;

    protected static final float CLICK_THRESHOLD = Scaler.scale(10);
    protected static final float WIDGET_DRAG_DETECTION_THRESHOLD = Scaler.scale(20);
    /**
     * Shows whether the sprites are draggable.
     */
    protected boolean widgetsAreDraggable = false;
    protected boolean isTouchDown = false;
    protected boolean isTouchDownOnAWidget = false;
    protected boolean isWidgetDragging = false;
    protected T selectedWidget;
    protected float touchX = 0, touchY = 0;
    protected float lastProcessedX = 0, lastProcessedY = 0;
    protected int touchIndex = 0;
    protected float currentSpeed = 0;
    protected boolean isDragDetectionDone = true;
    protected boolean isHorizontal;
    protected float speedThreshold = 0.9f;
    protected float allowedEmptySpace = 0;
    private float maxSidesDistance;
    protected float maximumSpeedInEmptySpace = 20;
    protected T currentCenter = null;

    /**
     * Default constructor
     *
     * @param x                   x of slider
     * @param y                   y of slider
     * @param w                   width of slider
     * @param h                   height of slider
     * @param widgetW             width of sprites in slider
     * @param widgetH             height of sprites in slider
     * @param game                game instance
     * @param areWidgetsDraggable controls whether the sprites are draggable.
     */
    public BasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game,
                       boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, isHorizontal);
        this.widgetsAreDraggable = areWidgetsDraggable;
        this.isHorizontal = isHorizontal;
        this.playSound = playSound;
        if (isHorizontal) {
            maxSidesDistance = w / 2;
        } else {
            maxSidesDistance = h / 2;
        }
    }

    public BasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound, float gap) {
        this(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound);
        this.gap = (int) gap;
    }

    /**
     * shows how many sprites are initialized before (and after) entering screen.
     */
    protected int allocatedOffScreenResources = 4;

    @Override
    protected void drawWidgets(Graphics g) {
        for (int i = 0; i < widgets.size(); i++) {
            if (!widgets.get(i).isRecycled()) {
                widgets.get(i).draw(g);
            }
        }
    }

    @Override
    protected void dispose() {
        for (T t : widgets) {
            t.recycle();
        }
    }


    /**
     * Calculates which sprites are inside screen and which are not. Then initializes sprites that are
     * either inside the screen or about to enter the screen (if they are not already initialized) and
     * recycles the rest(if needed).
     * <p/>
     */
    @Override
    protected void updateResourceAllocation() {
        try {
            if (isHorizontal) {
                float threshold = allocatedOffScreenResources * (gap + widgetW);
                float leftBoundry = x - threshold;
                float rightBoundry = x + width + threshold;

                for (T t : widgets) {
                    if (t.getX() >= leftBoundry && t.getX() <= rightBoundry) {
                        if (t.isRecycled()) {
                            t.initImage(game);
                        }
                    } else {
                        if (!t.isRecycled()) {
                            t.recycle();
                        }
                    }
                }
            } else {
                float threshold = allocatedOffScreenResources * (gap + widgetH);
                float upBoundry = y - threshold;
                float downBoundry = y + height + threshold;

                for (T t : widgets) {
                    if (t.getY() >= upBoundry && t.getY() <= downBoundry) {
                        if (t.isRecycled()) {
                            t.initImage(game);
                        }
                    } else {
                        if (!t.isRecycled()) {
                            t.recycle();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "could not update BasicSlider resources", e);
        }
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
                        break;
                    }

                }
                isTouchDown = true;
                return isTouchDown;
            }
        }

        return false;
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
                            up(true, x, y);
                        }
                        l.widgetUp(selectedWidget, selectedWidget.isInside(x, y), x, y);
                    }
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

    protected float distanceFromTouch(float x, float y) {
        float d = (touchX - x) * (touchX - x) + (touchY - y) * (touchY - y);
        d = (float) Math.sqrt(d);
        return d;
    }

    @Override
    public synchronized void updateLocations(TouchHandler touchHandler) {
        if (!isVisible)
            return;

        if (isTouchDown) {
            float currentX = touchHandler.getX(touchIndex);
            float currentY = touchHandler.getY(touchIndex);
            float diffX = currentX - lastProcessedX;
            float diffY = currentY - lastProcessedY;
            if (isHorizontal) {
                currentSpeed = Math.abs(diffX);
            } else {
                currentSpeed = Math.abs(diffY);
            }


            if (isTouchDownOnAWidget && isWidgetDragging) {
                currentSpeed = 0;
            } else {
                if (isHorizontal) {
                    if (diffX < 0)
                        currentSpeed = currentSpeed * -1;
                    currentOffset += diffX;
                } else {
                    if (diffY < 0)
                        currentSpeed = currentSpeed * -1;
                    currentOffset += diffY;

                }
            }


            lastProcessedX = currentX;
            lastProcessedY = currentY;

            if (widgetsAreDraggable && isTouchDownOnAWidget) {
                if (!isDragDetectionDone) {
                    float distance = distanceFromTouch(currentX, currentY);
                    if (distance >= WIDGET_DRAG_DETECTION_THRESHOLD) {
                        isDragDetectionDone = true;
                        if (isHorizontal) {
                            isWidgetDragging = Math.abs(currentY - touchY) > Math.abs(currentX - touchX);
                        } else {
                            isWidgetDragging = Math.abs(currentX - touchX) > Math.abs(currentY - touchY);
                        }
                        if (isWidgetDragging) {
                            for (SpriteDragListener<T> l : dragListeners) {
                                l.widgetDragStart(selectedWidget, currentX, currentY);
                            }
                        }
                    }
                } else if (isWidgetDragging) {
                    for (SpriteDragListener<T> l : dragListeners) {
                        l.widgetDragging(selectedWidget, currentX, currentY);
                    }
                }
            }

            update();

        } else {

            currentOffset += (currentSpeed);
            currentSpeed = currentSpeed * SPEED_REDUCER;
            if (currentOffset > 0 + allowedEmptySpace) {
                if (currentSpeed > maximumSpeedInEmptySpace) {
                    currentSpeed = maximumSpeedInEmptySpace;
                }
                currentSpeed -= currentOffset / 128;
            }
            if (isHorizontal) {

                float totalW = -1 * ((widgets.size() * (gap + widgetW)) + allowedEmptySpace - width);
                if (currentOffset < totalW) {
                    if (currentSpeed < -1 * maximumSpeedInEmptySpace) {
                        currentSpeed = -1 * maximumSpeedInEmptySpace;
                    }
                    currentSpeed += (totalW - currentOffset) / 128;
                }

            } else {

                float totalH = -1 * ((widgets.size() * (gap + widgetH)) + allowedEmptySpace - height);
                if (currentOffset < totalH) {
                    if (currentSpeed < -1 * maximumSpeedInEmptySpace) {
                        currentSpeed = -1 * maximumSpeedInEmptySpace;
                    }
                    currentSpeed += (totalH - currentOffset) / 256;
                }

            }
            if (Math.abs(currentSpeed) < speedThreshold) {
                userMovementEnded();
            } else {
                update();
            }
        }

    }

    /**
     * controls if the first or last widget is stretched at desired maximum distance
     */
    protected void controlSliderSides() {
        if (sideListener != null) {
            if (widgets != null) {
                for (int i = 0; i < widgets.size(); i++) {
                    T t = widgets.get(i);
                    if (i == widgets.size() - 1) {
                        boolean isStretchedMax;
                        if (isHorizontal) {
                            isStretchedMax = t.getX() < game.getScreenSize().getTargetW() - maxSidesDistance;
                        } else {
                            isStretchedMax = t.getY() < game.getScreenSize().getTargetH() - maxSidesDistance;
                        }
                        if (isStretchedMax) {
                            sideListener.widgetAtMaxSide(t);
                        }
                    } else if (i == 0) {
                        boolean isStretchedMax;
                        if (isHorizontal) {
                            isStretchedMax = t.getX() > maxSidesDistance;
                        } else {
                            isStretchedMax = t.getY() > maxSidesDistance;
                        }
                        if (isStretchedMax) {
                            sideListener.widgetAtMinSide(t);
                        }
                    }
                }
            }
        }
    }

    /**
     * changes the maximum desired distance for a widget to be stretched to call {@link SliderSideListener}
     *
     * @param maxSidesDistance the maximum amount that is needed for a widget to be stretched to call
     *                         the sliderSideListener.
     */
    public void setMaxSidesDistance(float maxSidesDistance) {
        this.maxSidesDistance = maxSidesDistance;
    }

    public void setCurrentOffset(int currentOffset) {
        this.currentOffset = currentOffset;
    }

    protected void userMovementEnded() {
        currentSpeed = 0;
    }

    @Override
    public void focusOn(T t) {
        int index = 0;
        for (int counter = 0; counter < widgets.size(); counter++) {
            if (widgets.get(counter) == t) {
                index = counter;
                break;
            }
        }
        if (isHorizontal) {
            currentOffset = (int) (sliderCenter - (index * (gap + widgetW) + gap + this.x));
        } else {
            currentOffset = (int) (sliderCenter - (index * (gap + widgetH) + gap + this.y));
        }
        update();
    }

    @Override
    public void focusOn(int index) {
        if (isHorizontal) {
            currentOffset = (int) (sliderCenter - (index * (gap + widgetW) + gap + this.x));
        } else {
            currentOffset = (int) (sliderCenter - (index * (gap + widgetH) + gap + this.y));
        }
        update();
    }

    /**
     * updates location of all sprites based on current offset .
     */
    protected void updateWidgetLocations() {
        float min = widgetW * 2;
        T current = null;

        for (int counter = 0; counter < widgets.size(); counter++) {
            if (isHorizontal) {
                T t = widgets.get(counter);
                float x = currentOffset + counter * (gap + widgetW) + gap + this.x;
                t.setX(x);
                float distance = Math.abs(x - sliderCenter);
                if (min > distance) {
                    min = distance;
                    current = t;
                }
            } else {
                float y = currentOffset + counter * (gap + widgetH) + gap + this.y;
                T t = widgets.get(counter);
                t.setY(y);
                float distance = Math.abs(y - sliderCenter);
                if (min > distance) {
                    min = distance;
                    current = t;
                }
            }
        }

        if (current != null && centreListener != null && current != this.currentCenter) {
            centreListener.widgetEnteredCenter(current);
            this.currentCenter = current;
        }
        controlSliderSides();
    }

    /**
     * First updates locations, then updates resource allocation
     */
    public void update() {
        updateWidgetLocations();
        updateResourceAllocation();
    }

    public void resetSlider() {
        setCurrentOffset(0);
        update();
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

    public boolean isHorizontal() {
        return isHorizontal;
    }

}
