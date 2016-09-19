package ir.saverin.frameroid.api.widget;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim.Ayat@gmail.com</a>
 */
public interface SpriteClickListener<T extends Sprite> {
    public void widgetDown(T t, float x, float y, int index);

    /**
     * Shows that the finger holding this widget is up (whether it was on the widget at the moment or not)
     *
     * @param t      subject widget
     * @param inside is true, if the finger was on the widget at the moment of detachment.
     * @param x
     * @param y
     */
    public void widgetUp(T t, boolean inside, float x, float y);
}
