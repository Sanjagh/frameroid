package ir.saverin.frameroid.util;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * <p/>
 * A simple utility class that is used to record voices from MIC
 * <p/>
 * Created by fahim on 2/18/15.
 *
 * @author S.Hosein Ayat
 */
public class VoiceRecorder {

    private static final String TAG = VoiceRecorder.class.getName();
    private MediaRecorder recorder;
    private String address;


    public VoiceRecorder(String address) {
        this.address = address;
        recorder = new MediaRecorder();
        config();
    }

    public synchronized void start() {
        recorder.start(); // Recording is now started
        Log.i(TAG, "Recorder started at : " + System.currentTimeMillis());
    }

    private void config() {
        Log.i(TAG, "Preparing voice recorder");
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(address);
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.e(TAG, "Could not prepare recorder", e);
        } catch (IOException e) {
            Log.e(TAG, "Could not prepare recorder", e);
        }

        Log.i(TAG, "Done preparing voice recorder");
    }

    public synchronized void finish() {
        Log.i(TAG, "Stopping Recorder at : " + System.currentTimeMillis());
        recorder.stop();
        recorder.release(); // Now the object cannot be reused
        recorder = null;
        Log.i(TAG, "Recorder stopped at : " + System.currentTimeMillis());

    }


}


