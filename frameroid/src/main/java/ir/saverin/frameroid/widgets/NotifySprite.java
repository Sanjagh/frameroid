package ir.saverin.frameroid.widgets;

import android.graphics.Bitmap;
import android.graphics.Paint;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.resource.ResourceHelper;

import static ir.saverin.frameroid.util.Scaler.scale;

/**
 * @author AliReza
 * @since 5/6/15.
 */
public class NotifySprite extends SoundPlayerSprite implements LoadableImage {
    private static final String TAG = NotifySprite.class.getName();
    private FileResource resource;
    private Bitmap bitmap = null;
    private String notifyMessage;
    private Paint paint;
    private float startY;
    private float finishY;
    private float speed = scale(3);
    private float clickX;
    private float clickY;
    private float clickWidth;
    private float clickHeight;

    private long startTime;
    private long stopTime = 2000;

    public NotifySprite(FileResource resource, String notifyMessage, Paint paint) {
        this.resource = resource;
        this.notifyMessage = notifyMessage;
        this.paint = paint;
        setVisible(false);
    }


    @Override
    public void initImage(Game game) throws Exception {
        if (bitmap == null) {
            bitmap = ResourceHelper.createBitmap(resource, game);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            startY = y - height;
            x = (scale(1000) - width) / 2;
            y = startY;
            finishY = scale(-7);
            clickX = x + scale(527);
            clickY = y;
            clickWidth = scale(70);
            clickHeight = height;
        }
    }

    @Override
    public void recycle() {
        if (bitmap != null) {
            bitmap.recycle();
            setVisible(false);
        }
        bitmap = null;

    }

    @Override
    public boolean isRecycled() {
        return bitmap == null;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible) {
            if (y < finishY) {
                y += speed;
                startTime = System.currentTimeMillis();
            } else {
                long currTime = System.currentTimeMillis();
                if ((currTime - startTime) >= stopTime)
                    setVisible(false);
            }
            g.drawImage(bitmap, x, y);
            g.drawText(notifyMessage, x + (width / 2), y + 6 * height / 10, paint);
        } else {
            if (y >= startY) {
                y -= speed;
                g.drawImage(bitmap, x, y);
                g.drawText(notifyMessage, x + (width / 2), y + 6 * height / 10, paint);
            }
        }
    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        super.up(inside, x, y);
        if (x > clickX && x < clickX + clickWidth && y > clickY && y < clickHeight + clickHeight) {
            setVisible(false);
        }
    }

    public FileResource getResource() {
        return resource;
    }

    public void setTextPaint(Paint textPaint) {
        this.paint = textPaint;
    }

    public void setMessage(String notifyMessage) {
        this.notifyMessage = notifyMessage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setClickLocation(float clickX, float clickY) {
        this.clickX = clickX;
        this.clickY = clickY;
    }

    public void setClickSize(float clickWidth, float clickHeight) {
        this.clickWidth = clickWidth;
        this.clickHeight = clickHeight;
    }

    public void resetTime() {
        startTime = System.currentTimeMillis();
    }


}
