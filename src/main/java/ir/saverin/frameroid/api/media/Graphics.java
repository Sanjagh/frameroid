package ir.saverin.frameroid.api.media;

import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.RectF;

public interface Graphics {

    void clear();

    void clear(int color);

    void clear(int a, int r, int g, int b);

    void setAlpha(int alpha);

    int getAlpha();

    void drawPixel(float x, float y, int color);

    void drawLine(float x, float y, float x2, float y2, int color);

    void drawRect(float x, float y, float width, float height, int color);

    void drawRect(float x, float y, float width, float height, Paint paint);

    void drawCircle(float x, float y, float radius, Paint paint);

    void drawImage(Bitmap image, float x, float y, float srcX, float srcY, float srcWidth, float srcHeight);

    void drawImage(Bitmap image, float x, float y);

    void drawImage(Bitmap image, float x, float y, float degree);

    void drawImageWithAlpha(Bitmap image, float x, float y, int alpha);

    public void drawGif(Movie movie, float x, float y, float scale);

    void drawRoundRect(RectF rectF, Paint paint, float xRadius, float yRadius);

    float getWidth();

    float getHeight();

    void translate(float x, float y);

    void setAbsoluteTranslation(float x, float y);

    void resetTranslation();

    float getTranslationX();

    float getTranslationY();

    Bitmap getBitmap();

    void drawText(String text, float x, float y, Paint p);

    void drawStrokeText(String text, float x, float y, Paint p, int strokeColor, float strokeWidth);

    void drawText(String text, float x, float y, Paint p, float degree);

    void save();

    void restore();

    void rotate(float degrees);

    void rotate(float degrees, float px, float py);

}
