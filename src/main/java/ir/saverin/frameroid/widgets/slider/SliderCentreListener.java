package ir.saverin.frameroid.widgets.slider;

/**
 * A listener that is used to inform upper level that one of the widgets in the slider crossed the slider's center.
 *
 * @author Fahim Ayat
 */
public interface SliderCentreListener<T> {

    /**
     * Inform that the widget t crossed slider's center
     *
     * @param t subject
     */
    void widgetEnteredCenter(T t);
}
