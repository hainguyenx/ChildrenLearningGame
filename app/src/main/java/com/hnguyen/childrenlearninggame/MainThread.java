package com.hnguyen.childrenlearninggame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

/**
 * Created by hnguyen on 10/3/15.
 */
public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();
    // desired fps
    private final static int MAX_FPS = 60;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 3;
    // the frame period
    private final static int FRAME_PERIOD = 300 / MAX_FPS;

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private MainGamePanel gamePanel;

    // flag to hold game state
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {

        Log.d(TAG, "Starting game loop");
        Canvas canvas;
        long beginTime;        // the time when the cycle begun
        long timeDiff;        // the time it took for the cycle to execute
        int sleepTime;        // ms to sleep (<0 if we're behind)
        int framesSkipped;    // number of frames being skipped

        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;    // resetting the frames skipped
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    // update game state
                    this.gamePanel.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.gamePanel.render(canvas);
                    // calculate how long did the cycle take

                }
                timeDiff = System.currentTimeMillis() - beginTime;
                // calculate sleep time
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    // if sleepTime > 0 we're OK
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }    // end finally


            while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                // we need to catch up
                this.gamePanel.update(); // update without rendering
                sleepTime += FRAME_PERIOD;    // add frame period to check if in next frame
                framesSkipped++;
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

