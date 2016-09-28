package ir.saverin.frameroid.widgets;


import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.widget.Sprite;

/**
 * @author S.Hosein Ayat
 */
public interface LoadableImage extends Sprite {

    void initImage(Game game) throws Exception;

    void recycle();

    boolean isRecycled();
}
