package ir.saverin.frameroid.util.sound;

import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AliReza
 * @since 6/10/15.
 */
public class SoundPlayer {

    private static final String TAG = SoundPlayer.class.getName();

    private SoundPlayer() {
    }

    private static volatile SoundPlayer instance = null;
    private static Map<SoundType, Integer> soundMaps = new HashMap<>();
    private static Map<SoundType, Boolean> soundMapsComplete = new HashMap<>();
    private static SoundPool soundPool;
    private static boolean enable = false;

    public static void setEnable(boolean enable) {
        SoundPlayer.enable = enable;
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            synchronized (SoundPlayer.class) {
                if (instance == null) {
                    instance = new SoundPlayer();
                }
            }
        }
        return instance;
    }


    public void init(AssetManager assetManager) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        for (final SoundType soundType : SoundType.values()) {
            soundMaps.put(soundType, soundPool.load(assetManager.openFd(soundType.getFileAddress()), 1));
            soundMapsComplete.put(soundType, false);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                SoundType soundType = null;
                for (Map.Entry<SoundType, Integer> soundTypeIntegerEntry : soundMaps.entrySet()) {
                    if (soundTypeIntegerEntry.getValue() == sampleId) {
                        soundType = soundTypeIntegerEntry.getKey();
                    }
                }
                Log.i(TAG, "sound with id: " + sampleId + " and type " + soundType + " is load with status : " + status);
                soundMapsComplete.put(soundType, status == 0);
            }
        });
        enable = true;

    }

    public void play(SoundType soundType) {
        try {
            if (enable) {
                if (soundMapsComplete.get(soundType)) {
                    soundPool.play(soundMaps.get(soundType), 1.0f, 1.0f, 1, 0, 1.0f);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error when play sound", e);
        }
    }

    public void releaseSound() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
            enable = false;
        }
    }

    public enum SoundType {
        CLICK_DEFAULT("sounds/click_default.ogg"),
        KEYBOARD("sounds/keyboard.ogg");

        SoundType(String fileAddress) {
            this.fileAddress = fileAddress;
        }

        private String fileAddress;

        private String getFileAddress() {
            return fileAddress;
        }
    }


}
