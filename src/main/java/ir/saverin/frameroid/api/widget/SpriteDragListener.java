package ir.saverin.frameroid.api.widget;

/**
 * @author S.Hosein Ayat
 */
public interface SpriteDragListener<T extends Sprite> {

    void widgetDragging(T t, float x, float y);

    void widgetDragStart(T t, float x, float y);

    void widgetDragEnd(T t, float x, float y);

}
