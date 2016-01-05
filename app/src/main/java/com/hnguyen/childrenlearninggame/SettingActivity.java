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
    private int volume;
    private int speed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        volumeSeek = (SeekBar) findViewById(R.id.volumeControlBar);
        balloonSpeedSeek = (SeekBar) findViewById(R.id.balloonSpeedBar);
        volumeSeek.setProgress(intent.getIntExtra("volume",MainActivity.DEFAULT_VOLUME));
        balloonSpeedSeek.setProgress(intent.getIntExtra("speed",MainActivity.DEFAULT_SPEED));
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
    }

    public void okClick(View v){
        Intent intent = new Intent();
        intent.putExtra("volume", volume);
        intent.putExtra("speed", speed);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void defaultClick(View v){
        Intent intent = new Intent();
        intent.putExtra("volume",MainActivity.DEFAULT_VOLUME);
        intent.putExtra("speed",MainActivity.DEFAULT_SPEED);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
