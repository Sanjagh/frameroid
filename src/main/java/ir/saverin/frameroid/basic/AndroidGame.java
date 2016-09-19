package ir.saverin.frameroid.basic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import ir.saverin.frameroid.api.io.FileStorage;
import ir.saverin.frameroid.api.media.Game;
import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.media.Screen;
import ir.saverin.frameroid.api.util.FrameroidConfig;
import ir.saverin.frameroid.util.Scaler;

public abstract class AndroidGame extends Activity implements Game {

    private static final String TAG = AndroidGame.class.getName();

    AndroidFastRenderView renderView;
    Graphics graphics;
    FileStorage fileStorage;
    Screen screen;
    Screen nextScreen;
    BasicTouchHandler touchHandler;
    protected BasicScreenSizeImpl screenSize;

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    /**
     * Override this method to change default values
     *
     * @return target width
     */
    public float getIdealWidth() {
        return 1000f;
    }

    public float getIdealHeight() {
        return 600f;
    }

    public abstract void initialize();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setDecorViewFlags();
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    setDecorViewFlags();
                }
            });

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

            float tw = getIdealWidth();
            float th = getIdealHeight();
            Point point = new Point();
            try {
                getWindowManager().getDefaultDisplay().getRealSize(point);
            } catch (Exception e) {
                getWindowManager().getDefaultDisplay().getSize(point);
            }

            int w = point.x;
            int h = point.y;
            Log.i(TAG, "Starting android game on " + w + "x" + h + "[" + tw + "," + th + "]");

            screenSize = new BasicScreenSizeImpl();
            screenSize.init(w, h, tw, th);

            Scaler.set(screenSize);
            Bitmap frameBuffer = Bitmap.createBitmap((int) screenSize.targetW, (int) screenSize.targetH, Config.RGB_565);


            renderView = new AndroidFastRenderView(this, frameBuffer, screenSize);
            touchHandler = new BasicTouchHandler(renderView, screenSize);
            fileStorage = new BasicFileStorage(getAssets(), getApplicationContext(), getConfig());
            graphics = new AndroidGraphics(frameBuffer);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


            initialize();

            screen = getStartScreen();
            setContentView(renderView);

        } catch (Exception e) {
            Log.e(TAG, "Initiation failed", e);
        }
    }

    @Override
    public void onResume() {
        try {
            Log.i(TAG, "Resuming system");
            super.onResume();
            setDecorViewFlags();
            screen.resume();
            renderView.resume();
        } catch (Exception e) {
            Log.e(TAG, "Could not resume program", e);
        }
    }

    @Override
    public void onPause() {
        try {
            Log.i(TAG, "Pausing system");
            super.onPause();
            renderView.pause();
            screen.pause();
            if (isFinishing()) {
                screen.dispose();
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not safely pause system", e);
        }
    }


    @Override
    public FileStorage getFileStorage() {
        return fileStorage;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }


    @Override
    public void setScreen(Screen screen) {

        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }
        Log.i(TAG, "Changing screen" + screen.toString());
        screen.resume();
        screen.update(0);
        this.nextScreen = screen;
        this.screen.pause();
        this.screen = screen;
    }


    public ir.saverin.frameroid.api.ScreenSize getScreenSize() {
        return screenSize;
    }

    @Override
    public BasicTouchHandler getTouchHandler() {
        return touchHandler;
    }

    protected void setDecorViewFlags() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public abstract FrameroidConfig getConfig();

    @Override
    public Screen getNextScreen() {
        if (nextScreen == null) {
            throw new IllegalArgumentException("the next screen is not set yet");
        }
        return nextScreen;
    }
}