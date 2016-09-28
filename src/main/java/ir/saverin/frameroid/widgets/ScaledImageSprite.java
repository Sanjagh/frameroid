package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * @author S.Hosein Ayat
 */
public class ScaledImageSprite extends ImageSprite {
    public ScaledImageSprite(FileResource resource, float width, float height) {
        super(resource);
        this.width = width;
        this.height = height;
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (bitmap == null) {
            bitmap = ResourceHelper.createBitmap(resource, game, (int) width, (int) height);
        }

    }
}
