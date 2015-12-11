package com.hnguyen.childrenlearninggame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hnguyen.childrenlearninggame.model.Background;
import com.hnguyen.childrenlearninggame.model.Balloon;
import com.hnguyen.childrenlearninggame.model.Components.Speed;

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
    private static int SPAWN_INTERVAL=2000;
    private static int TOTAL_BALLOONS=5;

    public MainGamePanel( Context context) {
        super(context);
        getHolder().addCallback(this);
        // create droid and load bitmap
        balloons = new Balloon[5];
        lastSpawnTime = System.currentTimeMillis();
        spawnBalloon();
        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.cloud_background));

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    private void spawnBalloon() {
        Log.d(TAG,"spawnBalloon()");
        Log.d(TAG,String.valueOf(lastSpawnTime));
        Log.d(TAG,String.valueOf(System.currentTimeMillis()));
        Log.d(TAG,"currentBalloon="+String.valueOf(currentBalloon));



        if( (System.currentTimeMillis() - lastSpawnTime) > SPAWN_INTERVAL) {
            balloons[currentBalloon++] = new Balloon(BitmapFactory.decodeResource(getResources(), R.drawable.balloon1), getContext());
            lastSpawnTime = System.currentTimeMillis();
            Log.d(TAG,"currentBalloonINside="+String.valueOf(currentBalloon));

        }
    }

    private void respawnBalloon(Balloon b) {
        Log.d(TAG, "respawnBalloon()");
        if( (System.currentTimeMillis() - lastSpawnTime) > SPAWN_INTERVAL) {
            b.setY(b.getYRandomNumber());
            b.setX(b.getXRandomNumber());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
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
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
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
                    if(b!=null)
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
            displayFps(canvas, "avgFps");



        }

    }

    public void update() {
       // Log.d(TAG,"update()");
//        // check collision with right wall if heading right
//        if (balloon.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
//                && balloon.getX() + balloon.getBitmap().getWidth() / 2 >= getWidth()) {
//            balloon.getSpeed().toggleXDirection();
//        }
//        // check collision with left wall if heading left
//        if (balloon.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
//                && balloon.getX() - balloon.getBitmap().getWidth() / 2 <= 0) {
//            balloon.getSpeed().toggleXDirection();
//        }
//        // check collision with bottom wall if heading down
//        if (balloon.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
//                && balloon.getY() + balloon.getBitmap().getHeight() / 2 >= getHeight()) {
//            balloon.getSpeed().toggleYDirection();
//        }
//        // check collision with top wall if heading up
        if(currentBalloon<TOTAL_BALLOONS)
            spawnBalloon();

        // Update the lone droid
        for (Balloon balloon :balloons) {
            if (balloon != null)
                balloon.update();
            if (balloon!=null && balloon.getSpeed().getyDirection() == Speed.DIRECTION_UP &&
                    (balloon.getY()+balloon.getBitmap().getHeight()/2) <= 0)
                respawnBalloon(balloon);
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
            paint.setARGB(111,111,111,222);
            canvas.drawText(fps,this.getWidth()-50,20,paint);
        }
    }

}

