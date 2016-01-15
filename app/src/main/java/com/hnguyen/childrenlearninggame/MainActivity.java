package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int SETTING_ACTIVITY_CODE=1;
    public static final int DEFAULT_VOLUME=10;
    public static final int DEFAULT_SPEED=3;
    public static final int DEFAULT_BALLOON_RATE =1000;

    //Default volume and speed
    private int volume = DEFAULT_VOLUME;
    private int speed = DEFAULT_SPEED;
    private int balloonRate = DEFAULT_BALLOON_RATE;

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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SETTING_ACTIVITY_CODE){
            if(resultCode==Activity.RESULT_OK){
                this.volume = data.getIntExtra("volume",DEFAULT_VOLUME);
                this.speed = data.getIntExtra("speed",DEFAULT_SPEED);
                this.balloonRate =  data.getIntExtra("balloonRate",DEFAULT_BALLOON_RATE);
            }else{
                this.volume = DEFAULT_VOLUME;
                this.speed = DEFAULT_SPEED;
                this.balloonRate = DEFAULT_BALLOON_RATE;
            }
        }
    }


    public void startGame(View v) {
        Intent intent = new Intent(MainActivity.this, GamePanelActivity.class);
        intent.putExtra("volume",this.volume);
        intent.putExtra("speed",this.speed);
        intent.putExtra("balloonRate",this.balloonRate);
        startActivity(intent);
    }

    public void setting(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        intent.putExtra("volume", this.volume);
        intent.putExtra("speed", this.speed);
        intent.putExtra("balloonRate", this.balloonRate);
        startActivityForResult(intent, SETTING_ACTIVITY_CODE);
    }

    public void quitGame(View view){
        finish();
        System.exit(0);
    }
}