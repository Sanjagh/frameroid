package ir.saverin.frameroid.widgets.slider;

/**
 * @author AliReza
 * @since 7/26/15.
 */

/**
 * A listener that is used to inform upper level that the slider's widgets are at the specified maximum amount in corners
 */
public interface SliderSideListener<T> {
    /**
     * when the last widget has the specified maximum distance from right side of slider if its horizontal
     * or when the last widget has the specified maximum distance from bottom side of slider if its not.
     *
     * @param t
     */
    void widgetAtMaxSide(T t);

    /**
     * when the first widget has the specified maximum distance from left side of slider if its horizontal
     * or when the first widget has the specified maximum distance from up side of slider if its not.
     *
     * @param t
     */
    void widgetAtMinSide(T t);
}
