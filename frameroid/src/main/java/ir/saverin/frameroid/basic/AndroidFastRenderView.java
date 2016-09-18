package ir.saverin.frameroid.basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import ir.saverin.frameroid.api.ScreenSize;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
    private static final String TAG = AndroidFastRenderView.class.getName();
    AndroidGame game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;
    private ScreenSize screenSize;
    public static final int FRAME_RATE = 40;
    public static final int DELAY = 1000 / FRAME_RATE;

    // private final static int TRESHOLD = 1000 / 10 ;

    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer, ScreenSize screenSize) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
        this.screenSize = screenSize;
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this, "Render Thread");
        renderThread.start();
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        Rect srcRect = new Rect(0, 0, (int) screenSize.getTargetW(), (int) screenSize.getTargetH());
        int left = (int) screenSize.getXOffset();
        int top = (int) screenSize.getYOffset();
        int bottom = (int) (top + screenSize.getTargetH());
        int right = (int) (left + screenSize.getTargetW());
        Rect screenRect = new Rect(left, top, right, bottom);

        long startTime = System.currentTimeMillis();
        Canvas canvas;

        while (running) {
            try {
                if (!holder.getSurface().isValid()) {
                    continue;
                }
                float deltaTime = (System.currentTimeMillis() - startTime);
                startTime = System.currentTimeMillis();

                try {
                    game.getCurrentScreen().update(deltaTime);
                    game.getCurrentScreen().present();
                } catch (Exception e) {
                    Log.e(TAG, "Could not complete render cycle", e);
                }


                canvas = holder.lockCanvas();

                canvas.drawBitmap(framebuffer, srcRect, screenRect, paint);
                holder.unlockCanvasAndPost(canvas);

                if (deltaTime < DELAY) {
                    try {
                        Thread.sleep((long) (DELAY - deltaTime));
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Render thread sleep interrupted");
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Un-recoverable exception in render threda", e);
            }
        }
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }
}