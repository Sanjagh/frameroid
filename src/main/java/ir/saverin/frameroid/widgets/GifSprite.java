package ir.saverin.frameroid.widgets;

import android.graphics.Movie;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.util.Scaler;

/**
 * @author AliReza
 * @since 2/21/16.
 */
public class GifSprite extends DummySprite implements LoadableImage {
    private static final String TAG = GifSprite.class.getName();
    private FileResource gifResource;
    private Movie gifMovie;
    private InputStream gifStream;
    private float gifScale;

    private long gifStartTime;

    public GifSprite(FileResource gifResource) {
        super(0, 0, 0, 0, false, true);
        this.gifResource = gifResource;
        this.gifScale = Scaler.scale(1);
    }

    @Override
    public void initImage(Game game) throws Exception {
        gifStream = game.getFileStorage().createInputStream(gifResource);
        gifMovie = Movie.decodeStream(gifStream);
        this.width = gifMovie.width() * gifScale;
        this.height = gifMovie.height() * gifScale;
    }

    @Override
    public void recycle() {
        try {
            if (gifStream != null) {
                gifStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "error in loading gif stream " + e.getMessage());
        }
    }

    @Override
    public boolean isRecycled() {
        return gifStream == null;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            drawGif(g);
        }
    }

    private void drawGif(Graphics g) {
        long now = android.os.SystemClock.uptimeMillis();
        if (gifStartTime == 0)
            gifStartTime = now;
        long relTime;
        relTime = ((now - gifStartTime) % gifMovie.duration());
        gifMovie.setTime((int) relTime);
        g.drawGif(gifMovie, x, y, gifScale);
    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
    }

    public float getGifScale() {
        return gifScale;
    }

    public void setGifScale(float gifScale) {
        this.gifScale = gifScale;
    }

}
