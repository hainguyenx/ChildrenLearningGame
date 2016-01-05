package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int SETTING_ACTIVITY_CODE=1;
    public static final int DEFAULT_VOLUME=10;
    public static final int DEFAULT_SPEED=10;

    private int backButtonCount=0;

    //Default volume and speed
    private int volume=DEFAULT_VOLUME;
    private int speed=DEFAULT_VOLUME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        TextView play = (TextView)findViewById(R.id.play);
        TextView quit = (TextView)findViewById(R.id.quit);
        TextView settings = (TextView)findViewById(R.id.settings);
        Typeface font = Typeface.createFromAsset(getAssets(),"njnaruto.ttf");

        play.setTypeface(font);
        quit.setTypeface(font);
        settings.setTypeface(font);
        Log.d(TAG, "View added");
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SETTING_ACTIVITY_CODE){
            if(resultCode==Activity.RESULT_OK){
                this.volume = data.getIntExtra("volume",DEFAULT_VOLUME);
                this.speed = data.getIntExtra("speed",DEFAULT_SPEED);
            }
        }
    }


    public void startGame(View v) {
        Intent intent = new Intent(MainActivity.this, GamePanelActivity.class);
        intent.putExtra("volume",this.volume);
        intent.putExtra("speed",this.speed);
        startActivity(intent);
    }

    public void setting(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        intent.putExtra("volume",this.volume);
        intent.putExtra("speed", this.speed);
        startActivityForResult(intent, SETTING_ACTIVITY_CODE);
    }

    public void quitGame(View view){
        finish();
        System.exit(0);
    }
}