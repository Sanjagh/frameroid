package ir.saverin.frameroid.api.widget;

import ir.saverin.frameroid.api.io.TouchHandler;
import ir.saverin.frameroid.api.media.Graphics;

import java.util.List;

/**
 * @author S.Hosein Ayat
 */
public interface WidgetContainer<T extends Sprite> {

    public void addWidget(T t);

    /**
     * @param index index of the widget
     * @param t
     */
    public void addWidget(int index, T t);

    public boolean removeWidget(T t);

    /**
     * @param index index of the widget
     */
    public void removeWidget(int index);

    public void addClickListener(SpriteClickListener<T> tl);

    public boolean removeClickListener(SpriteClickListener<T> tl);

    public boolean removeDragListener(SpriteDragListener<T> tl);

    public void addDragListener(SpriteDragListener<T> tl);

    /**
     * Consumes events, returns true if the events are consumed.
     *
     * @param touchHandler touchHandler instance
     * @return true if no other container needs to check for event
     */
    public void updateLocations(TouchHandler touchHandler);

    public void draw(Graphics g);

    public List<T> getSprites();
}
