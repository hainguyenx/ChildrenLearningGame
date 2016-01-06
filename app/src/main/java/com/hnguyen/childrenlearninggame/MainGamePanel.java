package com.hnguyen.childrenlearninggame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hnguyen.childrenlearninggame.model.Background;
import com.hnguyen.childrenlearninggame.model.Balloon;
import com.hnguyen.childrenlearninggame.model.Components.Speed;
import com.hnguyen.childrenlearninggame.model.Components.Voice;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hnguyen on 10/3/15.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;
    private Balloon[] balloons;
    private String avgFps;
    private Background bg;
    private int currentBalloon=0;
    private long lastSpawnTime=0;
    private int spawnInterval =MainActivity.DEFAULT_BALLOON_RATE;
    private static int TOTAL_BALLOONS=5;
    private Voice voice;
    private Queue<Balloon> respawnQueue;
    private int maxSpeed;

    public MainGamePanel( Context context, int balloonSpeed, int balloonRate) {
        super(context);
        getHolder().addCallback(this);
        // create droid and load bitmap
        this.maxSpeed = balloonSpeed;
        this.spawnInterval = balloonRate;
        balloons = new Balloon[5];
        lastSpawnTime = System.currentTimeMillis();
        spawnBalloon();
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background1));

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        voice = new Voice(context);

        respawnQueue = new LinkedList<Balloon>();

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }


    private void spawnBalloon() {
        if( (System.currentTimeMillis() - lastSpawnTime) > spawnInterval) {
            balloons[currentBalloon++] = new Balloon(this,voice,this.maxSpeed);
            lastSpawnTime = System.currentTimeMillis();

        }
    }

    private void respawnBalloon(Balloon b) {
        if(!respawnQueue.contains(b))
            respawnQueue.add(b);
        if( (System.currentTimeMillis() - lastSpawnTime) > spawnInterval) {
            if(!respawnQueue.isEmpty()) {
                respawnQueue.remove().reset();
                lastSpawnTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    //if it is the first time the thread starts
        if(thread.getState() == Thread.State.NEW){
            thread.setRunning(true);
            thread.start();
        }

    //after a pause it starts the thread again
        else if (thread.getState() == Thread.State.TERMINATED){
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            thread.start(); // Start a new thread
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
                Log.d(TAG,"Trying to shutdown thread");
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "UP EVENT!!!!!!");
            for (Balloon b :balloons)
            {
                    if(b!=null && !b.isHidden())
                         b.handleTouch(Math.round(event.getX()),Math.round(event.getY()) );
            }
        }
        return true;
    }

    public void render(Canvas canvas) {
        if(canvas!=null) {
            final int saveState = canvas.save();
           // canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
           // canvas.restoreToCount(saveState);
            for (Balloon b :balloons) {
                if (b != null)
                    b.draw(canvas);
            }
            displayFps(canvas, "avgFps:"+avgFps);
        }

    }

    public void update() {
        if(currentBalloon<TOTAL_BALLOONS)
            spawnBalloon();

        for (Balloon balloon :balloons) {
            if (balloon != null)
                balloon.update();
            if (balloon!=null && balloon.getSpeed().getyDirection() == Speed.DIRECTION_UP &&
                    (balloon.getY()+balloon.getBitmap().getHeight()/2) <= 0) {
                respawnBalloon(balloon);
            }
        }
        bg.update();

    }

    public void setAvgFps(String avgFps){
        this.avgFps = avgFps;
    }

    private void displayFps(Canvas canvas, String fps){
     //   Log.d(TAG,"displayFps()");
        if(canvas != null && fps != null){
            Paint paint =  new Paint();
            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawText(fps,this.getWidth()-50,20,paint);
        }
    }


    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public void setLastSpawnTime(long lastSpawnTime) {
        this.lastSpawnTime = lastSpawnTime;
    }

}

