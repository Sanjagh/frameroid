package ir.saverin.frameroid.widgets;

import android.graphics.*;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

/**
 * Created by fahim on 4/19/15.
 */
public class CroppedImageSprite extends ImageSprite {

    protected Bitmap bitmap;
    protected final FileResource resource;

    public CroppedImageSprite(FileResource resource, float width, float height) {
        super(resource);
        this.resource = resource;
        this.width = width;
        this.height = height;
        playSound = true;
    }

    @Override
    public void initImage(Game game) throws Exception {
        if (bitmap == null) {
            Bitmap original = ResourceHelper.createBitmap(resource, game, (int) width, (int) height);
            this.bitmap = getCroppedBitmap(original);
            original.recycle();
        }
    }


    @Override
    public void recycle() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public boolean isRecycled() {
        return bitmap == null;
    }

    @Override
    public void draw(Graphics g) {
        if (bitmap != null) {
            g.drawImage(bitmap, x, y);
        }
    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
