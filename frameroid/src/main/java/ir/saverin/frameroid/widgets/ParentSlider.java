package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.widgets.slider.SteppingSlider;

import static ir.saverin.frameroid.util.Scaler.scale;

/**
 * @author AliReza
 * @since 8/10/15.
 */

/**
 * a slider which manages many ChildSliders
 */
public class ParentSlider extends SteppingSlider<ChildSlider> {
    private static final String TAG = ParentSlider.class.getName();

    public ParentSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, scale(37), playSound);
    }

    public ParentSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean areWidgetsDraggable, boolean isHorizontal, boolean playSound, float gap) {
        super(x, y, w, h, widgetW, widgetH, game, areWidgetsDraggable, isHorizontal, scale(20), 2, 20f, false);
        //sliderCenter -= scale(90);
        SPEED_REDUCER = 0.85f;

    }

    public void addWidget(ChildSlider slider) {
        super.addWidget(slider);
        slider.addDragListener(new DragListener());
        checkChildSliderOrientation(slider);
    }

    @Override
    public void addWidget(int index, ChildSlider childSlider) {
        super.addWidget(index, childSlider);
        childSlider.addDragListener(new DragListener());
        checkChildSliderOrientation(childSlider);
    }

    private void checkChildSliderOrientation(ChildSlider slider) {
        if (isHorizontal() == slider.isHorizontal()) {
            throw new RuntimeException("Both parent and child can't be horizontal or vertical at the same time");
        }
    }

    private class DragListener implements SpriteDragListener<LoadableImage> {

        @Override
        public void widgetDragging(LoadableImage loadableImage, float x, float y) {
            touchDown(0, x, y);
        }

        @Override
        public void widgetDragStart(LoadableImage loadableImage, float x, float y) {
            touchDown(0, x, y);

        }

        @Override
        public void widgetDragEnd(LoadableImage loadableImage, float x, float y) {
            touchUp(0, x, y);
        }
    }

    boolean isChange = false;

    @Override
    protected void userMovementEnded() {
        super.userMovementEnded();
        if (!isChange) {
            sliderCenter -= scale(90);
            isChange = true;
        }
    }
}
