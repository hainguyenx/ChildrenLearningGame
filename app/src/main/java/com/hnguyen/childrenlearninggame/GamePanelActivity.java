package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hnguyen on 12/19/15.
 */
public class GamePanelActivity extends Activity {


    public static final String TAG = GamePanelActivity.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this));
        mediaPlayer = MediaPlayer.create(GamePanelActivity.this,R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Log.d(TAG, "View added");
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
        if(mediaPlayer !=null)
            mediaPlayer.release();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        if(mediaPlayer !=null)
            mediaPlayer.release();
        super.onBackPressed();
    }

}
