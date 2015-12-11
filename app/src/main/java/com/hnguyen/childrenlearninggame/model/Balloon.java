package com.hnguyen.childrenlearninggame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.hnguyen.childrenlearninggame.Explosion;
import com.hnguyen.childrenlearninggame.model.Components.Speed;

import java.util.Random;

/**
 * Created by hnguyen on 10/3/15.
 */
public class Balloon {
    private static String TAG = Balloon.class.getSimpleName();
    private Bitmap bitmap;
    int x,y;
    private boolean touched;
    private Speed speed;
    private Context context;
    private Explosion explosion;
    private static int NUMBER_PARTICLE=10;

    public Balloon(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
    }
    public Balloon(Bitmap bitmap,Context context)
    {
        this.context = context;
        this.bitmap = bitmap;
        x = getXRandomNumber();
        y = getYRandomNumber();
        this.speed = new Speed();

    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public  void setContext(Context context)
    {
        this.context = context;
    }
    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        if(explosion !=null && explosion.isAlive())
            explosion.draw(canvas);
    }

    /**
     * Method which updates the droid's internal state every tick
     */
    public void update() {
        if (!touched) {
            y += (speed.getYv() * speed.getyDirection());
        }
        if (explosion != null && explosion.isAlive()) {
            explosion.update();
        }

    }

    /**
     * Handles the {@link MotionEvent.ACTION_DOWN} event. If the event happens on the
     * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
     * @param eventX - the event's X coordinate
     * @param eventY - the event's Y coordinate
     */
    public void handleTouch(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
                // droid touched
                setTouched(true);
                Log.d(TAG, "Balloon is touched!!");
                //putting balloon out of sight
                this.y = -1000;
                if(explosion==null || explosion.isDead())
                    explosion = new Explosion(NUMBER_PARTICLE,eventX,eventY);

            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

    }

    public int getXRandomNumber()
    {
        Random r = new Random();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return getBitmap().getWidth() + r.nextInt(metrics.widthPixels - getBitmap().getWidth()*2);
    }

    public int getYRandomNumber()
    {
        Random r = new Random();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels + bitmap.getHeight();
    }
}
