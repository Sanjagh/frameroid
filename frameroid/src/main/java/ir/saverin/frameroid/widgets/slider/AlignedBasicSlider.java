package ir.saverin.frameroid.widgets.slider;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * @author AliReza
 * @since 4/3/16.
 */

/**
 * a basic slider for the time when the height/width(horizontal/vertical) of the widgets are not equal but we want the widgets to be in the centre
 * of the slider based on each widget's size.
 *
 * @param <T>
 */
public class AlignedBasicSlider<T extends LoadableImage> extends BasicSlider<T> {
    public AlignedBasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound, float gap) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound, gap);
    }

    public AlignedBasicSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, playSound);
    }

    @Override
    protected void updateWidgetLocations() {
        float min = widgetW * 2;
        T current = null;

        for (int counter = 0; counter < widgets.size(); counter++) {
            if (isHorizontal) {
                T t = widgets.get(counter);
                float x = currentOffset + counter * (gap + widgetW) + gap + this.x;
                float y = this.y + ((height - t.getHeight()) / 2);// sets widget's y to centre based on it's height
                t.setX(x);
                t.setY(y);
                float distance = Math.abs(x - sliderCenter);
                if (min > distance) {
                    min = distance;
                    current = t;
                }
            } else {
                T t = widgets.get(counter);
                float x = this.x + ((width - t.getWidth()) / 2);// sets widget's x to centre based on it's width
                float y = currentOffset + counter * (gap + widgetH) + gap + this.y;
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
        controlSliderSides();
    }
}
