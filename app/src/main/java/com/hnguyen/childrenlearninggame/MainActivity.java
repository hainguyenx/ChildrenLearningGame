package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private int backButtonCount=0;
    private MediaPlayer backgroundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this));
        backgroundPlayer = MediaPlayer.create(MainActivity.this,R.raw.background_music);
        backgroundPlayer.setLooping(true);
        backgroundPlayer.start();
        Log.d(TAG, "View added");
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
        if(backgroundPlayer!=null)
            backgroundPlayer.release();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    protected void onPause(){
        super.onPause();
        backgroundPlayer.release();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else
        {
            Toast.makeText(this,"Press the back button once again to close the application",Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }

    }
}