package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.api.widget.SpriteClickListener;
import ir.saverin.frameroid.api.widget.SpriteDragListener;
import ir.saverin.frameroid.api.widget.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Hosein Ayat
 */
public abstract class AbstractSpriteContainer<T extends Sprite> implements WidgetContainer<T> {

    protected List<T> sprites;
    protected List<SpriteClickListener<T>> clickListeners;
    protected List<SpriteDragListener<T>> dragListeners;

    public AbstractSpriteContainer() {
        sprites = new ArrayList<T>();
        clickListeners = new ArrayList<SpriteClickListener<T>>();
        dragListeners = new ArrayList<SpriteDragListener<T>>();
    }

    @Override
    public void addWidget(T t) {
        sprites.add(t);
    }

    @Override
    public void addWidget(int index, T t) {
        sprites.add(index, t);
    }

    @Override
    public boolean removeWidget(T t) {
        return sprites.remove(t);
    }

    @Override
    public void removeWidget(int index) {
        sprites.remove(index);
    }

    @Override
    public void addClickListener(SpriteClickListener<T> tl) {
        clickListeners.add(tl);
    }

    @Override
    public boolean removeClickListener(SpriteClickListener<T> tl) {
        return clickListeners.remove(tl);
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
    public List<T> getSprites() {
        return sprites;
    }
}
