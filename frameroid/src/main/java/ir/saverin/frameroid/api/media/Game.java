package ir.saverin.frameroid.api.media;

import ir.saverin.frameroid.api.ScreenSize;
import ir.saverin.frameroid.api.io.FileStorage;
import ir.saverin.frameroid.api.io.TouchHandler;

public interface Game {
    public TouchHandler getTouchHandler();

    public FileStorage getFileStorage();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getStartScreen();

    public ScreenSize getScreenSize();

    public Screen getNextScreen();

    public Screen getCurrentScreen();
}