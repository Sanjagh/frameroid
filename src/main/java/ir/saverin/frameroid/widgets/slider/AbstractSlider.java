package ir.saverin.frameroid.widgets.slider;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.SpriteClickListener;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.api.widget.WidgetContainer;
import ir.saverin.frameroid.api.widget.slider.Slider;
import ir.saverin.frameroid.widgets.LoadableImage;
import ir.saverin.frameroid.widgets.SoundPlayerSprite;

import java.util.ArrayList;
import java.util.List;

import static ir.saverin.frameroid.util.Scaler.scale;

/**
 * An abstraction over {@link Slider} which aims to hide boiler plate code from slider logic.
 * This class contains
 *
 * @author S.Hosein Ayat
 */
public abstract class AbstractSlider<T extends LoadableImage> extends SoundPlayerSprite implements Slider<T>, WidgetContainer<T> {

    protected List<SpriteClickListener<T>> listeners = null;
    protected List<T> widgets;
    protected List<SpriteDragListener<T>> dragListeners;
    protected SliderCentreListener<T> centreListener;
    protected SliderSideListener<T> sideListener;
    protected int backColor = 0xFFFFFFFF;
    protected float gap = (int) scale(50);
    protected float widgetW;
    protected float widgetH;
    protected int currentOffset = 0;
    protected Game game;
    protected boolean isRecycled = true;
    protected int maxWidgetOnScreen;
    protected float widgetYOffset;
    private boolean isHorizontal;
    private float widgetXOffset;
    protected float sliderCenter;

    public AbstractSlider(float x, float y, float w, float h, float widgetW, float widgetH, Game game, boolean isHorizontal) {
        setLocation(x, y);
        setSize(w, h);
        widgets = new ArrayList<>();
        listeners = new ArrayList<>();
        dragListeners = new ArrayList<>();
        this.widgetW = widgetW;
        this.widgetH = widgetH;
        this.game = game;
        this.isHorizontal = isHorizontal;
        float sliderCenter;
        if (isHorizontal) {
            maxWidgetOnScreen = (int) ((w / (widgetW + gap)) + 1);
            widgetYOffset = (h - widgetH) / 2;
            sliderCenter = x + w / 2;
            sliderCenter -= widgetW / 2;
        } else {
            maxWidgetOnScreen = (int) ((h / (widgetH + gap)) + 1);
            widgetXOffset = (w - widgetW) / 2;
            sliderCenter = y + h / 2;
            sliderCenter -= widgetH / 2;
        }
        this.sliderCenter = sliderCenter;
    }


    @Override
    public void addWidget(T t) {
        manageWidgetsLocation(t);
        t.setSize(widgetW, widgetH);
        widgets.add(t);
    }

    @Override
    public void addWidget(int index, T t) {
        manageWidgetsLocation(t);
        t.setSize(widgetW, widgetH);
        widgets.add(index, t);
    }

    @Override
    public boolean removeWidget(T t) {
        return widgets.remove(t);
    }

    @Override
    public void removeWidget(int index) {
        widgets.remove(index);
    }

    public void removeAllWidgets() {
        widgets.clear();
        currentOffset = 0;
    }

    @Override
    public void addClickListener(SpriteClickListener<T> tl) {
        listeners.add(tl);
    }


    @Override
    public boolean removeClickListener(SpriteClickListener<T> tl) {
        return listeners.remove(tl);
    }

    @Override
    public boolean removeDragListener(SpriteDragListener<T> tl) {
        return dragListeners.remove(tl);
    }

    @Override
    public void addDragListener(SpriteDragListener<T> tl) {
        dragListeners.add(tl);
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            drawBackground(g);
            drawWidgets(g);
        }
    }


    @Override
    public List<T> getSprites() {
        return widgets;
    }

    private void manageWidgetsLocation(T t) {
        float currentX;
        float currentY;
        if (isHorizontal) {
            currentX = gap + (gap + widgetW) * widgets.size() + x;
            currentY = y + widgetYOffset;
        } else {
            currentX = x + widgetXOffset;
            currentY = gap + (gap + widgetH) * widgets.size() + y;
        }
        t.setLocation(currentX, currentY);
    }


    protected void drawBackground(Graphics g) {
        g.drawRect(x, y, width, height, backColor);
    }

    protected abstract void drawWidgets(Graphics g);

    @Override
    public void initImage(Game game) throws Exception {
        updateResourceAllocation();
        isRecycled = false;
    }

    @Override
    public void recycle() {
        dispose();
        isRecycled = true;
    }

    @Override
    public boolean isRecycled() {
        return isRecycled;
    }

    protected abstract void dispose();

    protected abstract void updateResourceAllocation();

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public int getWidgetCount() {
        return widgets.size();
    }

    public List<T> getWidgets() {
        return widgets;
    }

    public void setSliderCentreListener(SliderCentreListener<T> listener) {
        this.centreListener = listener;
    }

    public void setSliderCornerListener(SliderSideListener<T> listener) {
        this.sideListener = listener;
    }

    public abstract void focusOn(T t);

    public abstract void focusOn(int index);
}
