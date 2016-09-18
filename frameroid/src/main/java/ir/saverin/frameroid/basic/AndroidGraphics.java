package ir.saverin.frameroid.basic;

import android.graphics.*;
import android.graphics.Paint.Style;
import android.util.Log;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.util.Scaler;

public class AndroidGraphics implements Graphics {

    private static final String TAG = AndroidGraphics.class.getName();
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint;
    private Paint imagePaint;
    private Paint gifPaint;


    private float translationX = 0;
    private float translationY = 0;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public AndroidGraphics(Bitmap frameBuffer) {
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        paint.setAlpha(125);

        imagePaint = new Paint();
//        imagePaint.setAntiAlias(true);
//        imagePaint.setFilterBitmap(true);
//        imagePaint.setDither(true);
        gifPaint = new Paint();
        gifPaint.setAntiAlias(true);
        gifPaint.setFilterBitmap(true);
    }

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    public int getAlpha() {
        return paint.getAlpha();
    }

    @Override
    public void clear() {
        canvas.drawRGB(0, 0, 0);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(float x, float y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(float x, float y, float x2, float y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, Paint paint) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void drawCircle(float x, float y, float radius, Paint paint) {
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void drawImage(Bitmap image, float x, float y, float srcX, float srcY, float srcWidth, float srcHeight) {
        srcRect.left = (int) srcX;
        srcRect.top = (int) srcY;
        srcRect.right = (int) (srcX + srcWidth - 1);
        srcRect.bottom = (int) (srcY + srcHeight - 1);

        dstRect.left = (int) x;
        dstRect.top = (int) y;
        dstRect.right = (int) (x + srcWidth - 1);
        dstRect.bottom = (int) (y + srcHeight - 1);

        canvas.drawBitmap(image, srcRect, dstRect, paint);
    }


    public void drawImageWithAlpha(Bitmap image, float x, float y, int alpha) {
        int lastAlpha = paint.getAlpha();
        paint.setAlpha(alpha);
        canvas.drawBitmap(image, x, y, paint);
        paint.setAlpha(lastAlpha);
    }

    @Override
    public void drawRoundRect(RectF rectF, Paint paint, float xRadius, float yRadius) {
        canvas.drawRoundRect(rectF, xRadius, yRadius, paint);
    }

    @Override
    public void drawImage(Bitmap image, float x, float y) {
        if (image == null || image.isRecycled()) {
            Log.e(TAG, "Could not draw null Image");
        } else {
//            canvas.drawRect(x , y , x+ image.getWidth() , y + image.getHeight() , paint);
            canvas.drawBitmap(image, x, y, imagePaint);
        }
    }

    @Override
    public void drawImage(Bitmap image, float x, float y, float degree) {
        if (image == null || image.isRecycled()) {
            Log.e(TAG, "Could not draw null Image");
        } else {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, x + image.getWidth() / 2, y + image.getHeight() / 2);
            canvas.setMatrix(matrix);
            drawImage(image, x, y);
            canvas.setMatrix(null);
        }
    }

    public void drawGif(Movie movie, float x, float y, float scale) {
        canvas.save();
        canvas.scale(scale, scale);
        movie.draw(canvas, x / scale, y / scale, gifPaint);
        canvas.restore();
    }

    @Override
    public float getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public float getHeight() {
        return frameBuffer.getHeight();
    }

    @Override
    public void clear(int a, int r, int g, int b) {
        canvas.drawARGB(a, r, g, b);
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.media.Graphics#translate(int, int)
     */
    @Override
    public void translate(float x, float y) {
        translationX += x;
        translationY += y;
        canvas.translate(x, y);
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.media.Graphics#resetTranslation()
     */
    @Override
    public void resetTranslation() {
        canvas.translate(-1 * translationX, -1 * translationY);
        translationX = 0;
        translationY = 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.media.Graphics#getTranslationX()
     */
    @Override
    public float getTranslationX() {
        return translationX;
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.media.Graphics#getTranslationY()
     */
    @Override
    public float getTranslationY() {
        // TODO Auto-generated method stub
        return translationY;
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.media.Graphics#setAbsoluteTranslation(float, float)
     */
    @Override
    public void setAbsoluteTranslation(float x, float y) {
        translate(x - translationX, y - translationY);
    }

    public Bitmap getBitmap() {
        return frameBuffer;
    }

    @Override
    public void drawText(String text, float x, float y, Paint p) {
        canvas.drawText(text, x, y, p);
    }

    @Override
    public void drawStrokeText(String text, float x, float y, Paint p, int strokeColor, float strokeWidth) {
        float textStroke = p.getStrokeWidth();
        int textColor = p.getColor();
        int textTransparency = p.getAlpha();
        Style textStyle = p.getStyle();
        p.setColor(strokeColor);
        p.setStrokeWidth(strokeWidth);
        p.setStyle(Style.STROKE);
        p.setAlpha(textTransparency);
        canvas.drawText(text, x, y, p);
        p.setStrokeWidth(textStroke);
        p.setColor(textColor);
        p.setStyle(textStyle);
        p.setAlpha(textTransparency);
        canvas.drawText(text, x, y, p);
    }

    @Override
    public void drawText(String text, float x, float y, Paint p, float degree) {
        canvas.rotate(degree, x, y);
        canvas.drawText(text, x, y, p);
        canvas.rotate(-degree, x, y);
    }

    @Override
    public void save() {
        canvas.save();
    }

    @Override
    public void restore() {
        canvas.restore();
    }

    @Override
    public void rotate(float degrees) {
        canvas.rotate(degrees);
    }

    @Override
    public void rotate(float degrees, float px, float py) {
        canvas.rotate(degrees, px, py);
    }

}
