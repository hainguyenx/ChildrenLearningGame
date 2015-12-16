package com.hnguyen.childrenlearninggame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.hnguyen.childrenlearninggame.Explosion;
import com.hnguyen.childrenlearninggame.R;
import com.hnguyen.childrenlearninggame.model.Components.Speed;
import com.hnguyen.childrenlearninggame.model.Components.Voice;

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
    private Character letter;
    private Voice voice;

    public Balloon(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
        this.voice = new Voice(this.context);
    }
    public Balloon(Bitmap bitmap,Context context,Voice voice)
    {
        this.context = context;
        this.bitmap = bitmap;
        x = getXRandomNumber();
        y = getYRandomNumber();
        this.speed = new Speed();
        letter = randomChar();
        this.voice = voice;

    }
    public void reset()
    {
        this.y = getYRandomNumber();
        this.x = getXRandomNumber();
        this.letter = randomChar();
    }
    private Character randomChar()
    {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        Character ch = SALTCHARS.charAt(index);
        Log.d(TAG, "randomString=" + ch);

        return ch;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        //glyphs.drawChar(canvas,letter,this.x,this.y);
        drawTextToBitmap(canvas);
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
                reset();
                if(explosion==null || explosion.isDead())
                    explosion = new Explosion(NUMBER_PARTICLE,eventX,eventY);

                voice.saidCommand(letter);

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
       // Random r = new Random();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels + bitmap.getHeight()/2;
    }

    public void drawTextToBitmap(Canvas canvas) {

        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();

        int fontSize = (int) context.getResources().getDimension(R.dimen.letter_size);
        //convert sp to px
        int xOffset = (int)metrics.scaledDensity * fontSize;

        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(255, 255, 255));
        // text size in pixels
        paint.setTextSize((int) (fontSize));

        // text shadow
        //paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
       // Rect bounds = new Rect();
        String text = Character.toString(letter.charValue());
        //paint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText(text,this.x-fontSize/2, this.y, paint);

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
    public  void setContext(Context context) {
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

}
