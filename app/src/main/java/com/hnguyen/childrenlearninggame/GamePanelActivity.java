package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
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
    private float volume;
    private float speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this));
        Intent intent = getIntent();
        volume = intent.getIntExtra("volume", MainActivity.DEFAULT_VOLUME);
        speed = intent.getIntExtra("speed", MainActivity.DEFAULT_VOLUME);
        mediaPlayer = MediaPlayer.create(GamePanelActivity.this, R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(this.volume/100,this.volume/100);
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
