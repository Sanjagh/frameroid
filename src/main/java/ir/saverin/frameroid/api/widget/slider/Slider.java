package ir.saverin.frameroid.api.widget.slider;

import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.util.TouchEventListener;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public interface Slider<T extends LoadableImage> extends Sprite, TouchEventListener, LoadableImage {
}
