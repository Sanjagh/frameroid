package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * @author AliReza
 * @since 1/16/16.
 */
public class ScaledAnimatedButtonSprite extends AnimatedButtonSprite {

    public ScaledAnimatedButtonSprite(FileResource resource, float width, float height) {
        super(resource);
        this.width = width;
        this.height = height;
        isDefaultStretchSize = true;
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (bitmap == null) {
            bitmap = ResourceHelper.createBitmap(resource, game, (int) width, (int) height);
        }
        if (bitmap2 == null) {
            if (isDefaultStretchSize) {
                strechtHeight = height / 8;
                strechtWidth = width / 8;
            }
            bitmap2 = ResourceHelper.createBitmap(resource, game, (int) (width + strechtWidth), (int) (height + strechtHeight));
        }
    }
}
