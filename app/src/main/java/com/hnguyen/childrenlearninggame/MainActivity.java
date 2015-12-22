package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
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
    private int backButtonCount=0;
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
        //Typeface font = Typeface.createFromAsset(getAssets(),"chlorinr.ttf");

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


    public void startGame(View v) {
        Intent intent = new Intent(MainActivity.this, GamePanelActivity.class);
        startActivity(intent);
    }

    public void setting(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void quitGame(View view){
        finish();
        System.exit(0);
    }
}