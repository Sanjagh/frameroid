package ir.saverin.frameroid.widgets.slider;

import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.widgets.ScaledImageSprite;

/**
 * Created by fahim on 3/10/15.
 *
 * @author S.Hosein Ayat
 */

public class TaggedScaledImage<T> extends ScaledImageSprite {

    public TaggedScaledImage(FileResource resource, float width, float height, T t) {
        super(resource, width, height);
        this.value = t;
    }

    public T value;
}
