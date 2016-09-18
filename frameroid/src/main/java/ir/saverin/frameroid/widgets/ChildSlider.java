package ir.saverin.frameroid.widgets;


import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.util.Scaler;
import ir.saverin.frameroid.widgets.slider.SteppingSlider;

/**
 * @author AliReza
 * @since 8/10/15.
 */
public class ChildSlider<T extends LoadableImage> extends SteppingSlider<T> {


    public ChildSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, float gap, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, gap, playSound);
    }

    public ChildSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, float gap, float allowedEmptySpaceMultiplier, float speedThreshold, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, gap, allowedEmptySpaceMultiplier, speedThreshold, playSound);
    }

    /**
     * same as the super one but with a little changes
     */
    @Override
    protected void updateWidgetLocations() {
        float min = widgetW * 2;
        T current = null;

        for (int counter = 0; counter < widgets.size(); counter++) {
            if (isHorizontal) {
                T t = widgets.get(counter);
                float x = currentOffset + counter * (gap + widgetW) + gap + this.x;
                float y = this.y + ((height - widgetH) / 2);
                t.setX(x);
                t.setY(y);
                float distance = Math.abs(x - sliderCenter);
                if (min > distance) {
                    min = distance;
                    current = t;
                }
            } else {
                float x = this.x + ((width - widgetW) / 2);
                float y = currentOffset + counter * (gap + widgetH) + gap + this.y;
                T t = widgets.get(counter);
                t.setX(x);
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
    }

    /**
     * same as the super one but with a little changes
     *
     * @param touchHandler
     */
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

                if (Math.abs(diffY) > Math.abs(diffX)) {
                    currentSpeed = 0;
                    isDragDetectionDone = true;
                    isWidgetDragging = true;
                    isTouchDownOnAWidget = true;
                    for (SpriteDragListener<T> l : dragListeners) {
                        l.widgetDragStart(selectedWidget, currentX - diffX, currentY - diffY);
                    }
                }

            } else {

                currentSpeed = Math.abs(diffY);
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    currentSpeed = 0;
                    isDragDetectionDone = true;
                    isWidgetDragging = true;
                    isTouchDownOnAWidget = true;
                    for (SpriteDragListener<T> l : dragListeners) {
                        l.widgetDragStart(selectedWidget, currentX - diffX, currentY - diffY);
                    }
                }

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

            if (widgetsAreDraggable) {
                if (!isDragDetectionDone) {
                    float distance = distanceFromTouch(currentX, currentY);
                    if (distance >= Scaler.scale(1)) {
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
}
