package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * @author AliReza
 * @since 4/4/16.
 */

/**
 * an image sprite for the sprites that first has a desired loading image until its original image(which will be available
 * later eg:downloaded) becomes ready to be shown
 */
public class LoadingImageSprite extends ScaledAnimatedButtonSprite {
    protected LoadableImage loadingImage;
    protected boolean isDownloaded;
    protected boolean isDefaultSize;

    /**
     * the size of sprite will be based on desired size
     *
     * @param downloadedWidth  downloaded image's width
     * @param downloadedHeight downloaded image's height
     * @param loadingImage     the loadable image for loading
     */
    public LoadingImageSprite(float downloadedWidth, float downloadedHeight, LoadableImage loadingImage) {
        super(null, 0, 0);
        this.loadingImage = loadingImage;
        this.width = downloadedWidth;
        this.height = downloadedHeight;
        isDefaultSize = false;
    }

    /**
     * the size of the sprite is based on the bitmap itself
     *
     * @param loadingImage
     */
    public LoadingImageSprite(LoadableImage loadingImage) {
        this(0, 0, loadingImage);
        isDefaultSize = true;
    }


    @Override
    public void draw(Graphics g) {
        if (isDownloaded) {
            super.draw(g);
        } else {
            if (isVisible && !loadingImage.isRecycled()) {
                loadingImage.draw(g);
            }
        }
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (resource != null) {
            if (!isDefaultSize) {
                super.initImage(game);
            } else {
                if (bitmap == null) {
                    bitmap = ResourceHelper.createBitmap(resource, game);
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
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
        if (loadingImage.isRecycled()) {
            loadingImage.initImage(game);
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        if (!loadingImage.isRecycled()) {
            loadingImage.recycle();
        }
    }

    @Override
    public boolean isRecycled() {
        if (resource != null) {
            return super.isRecycled();
        }
        return loadingImage.isRecycled();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        loadingImage.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        loadingImage.setX(x);
    }

    @Override
    public void setLocation(float x, float y) {
        super.setLocation(x, y);
        loadingImage.setLocation(x, y);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        isDefaultSize = false;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
