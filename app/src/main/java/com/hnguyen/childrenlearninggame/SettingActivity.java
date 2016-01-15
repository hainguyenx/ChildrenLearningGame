package com.hnguyen.childrenlearninggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;

public class SettingActivity extends Activity {

    private SeekBar volumeSeek;
    private SeekBar balloonSpeedSeek;
    private SeekBar balloonRateSeek;
    private int volume = MainActivity.DEFAULT_VOLUME;
    private int speed = MainActivity.DEFAULT_SPEED;
    private int balloonRate = MainActivity.DEFAULT_BALLOON_RATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        volumeSeek = (SeekBar) findViewById(R.id.volumeControlBar);
        balloonSpeedSeek = (SeekBar) findViewById(R.id.balloonSpeedBar);
        balloonRateSeek = (SeekBar) findViewById(R.id.balloonRespawnTimeBar);
        volumeSeek.setProgress(intent.getIntExtra("volume",MainActivity.DEFAULT_VOLUME));
        balloonSpeedSeek.setProgress(intent.getIntExtra("speed", MainActivity.DEFAULT_SPEED));
        balloonRateSeek.setProgress(intent.getIntExtra("balloonRate", MainActivity.DEFAULT_BALLOON_RATE));

        //Listener for seek volume bar
        volumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Listener for seek speed bar
        balloonSpeedSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Listener for seek rate bar
        balloonRateSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                balloonRate = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void okClick(View v){
        Intent intent = new Intent();
        intent.putExtra("volume", volume);
        intent.putExtra("speed", speed);
        intent.putExtra("balloonRate",balloonRate);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void defaultClick(View v){
        volumeSeek.setProgress(MainActivity.DEFAULT_VOLUME);
        balloonSpeedSeek.setProgress(MainActivity.DEFAULT_SPEED);
        balloonRateSeek.setProgress(MainActivity.DEFAULT_BALLOON_RATE);
        this.volume = MainActivity.DEFAULT_VOLUME;
        this.speed = MainActivity.DEFAULT_SPEED;
        this.balloonRate = MainActivity.DEFAULT_BALLOON_RATE;
    }

}
