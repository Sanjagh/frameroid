package ir.saverin.frameroid.api.widget.slider;

import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.util.TouchEventListener;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * @author S.Hosein Ayat
 */
public interface Slider<T extends LoadableImage> extends Sprite, TouchEventListener, LoadableImage {
}
