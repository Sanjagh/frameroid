package ir.saverin.frameroid.api.util;

/**
 * Some config items that are required by AndroidGames.
 * <p/>
 * Created by fahim on 2/14/15.
 */
public interface FrameroidConfig {


    String getAppName();

    /**
     * Application's default path on sdcard for axillary files. (like /company/appname)
     *
     * @return address
     */
    String getAppAddress();
}
