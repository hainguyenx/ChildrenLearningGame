package com.hnguyen.childrenlearninggame;

/**
 * Created by hnguyen on 12/11/15.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Particle {

    public static final int STATE_ALIVE = 0;	// particle is alive
    public static final int STATE_DEAD = 1;		// particle is dead

    public static final int DEFAULT_LIFETIME 	= 300;	// play with this
    public static final int MAX_RADIUS		= 40;	// the maximum width or height
    public static final int MAX_SPEED			= 4;	// maximum speed (per update)

    private int state;			// particle is alive or dead



    private float radius;		// width of the particle
    private float x, y;			// horizontal and vertical position
    private double xv, yv;		// vertical and horizontal velocity
    private int age;			// current age of the particle
    private int lifetime;		// particle dies when it reaches this value
    private int color;			// the color of the particle
    private Paint paint;		// internal use to avoid instantiation

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = Particle.STATE_ALIVE;
        this.radius = rndInt(1, MAX_RADIUS);
        this.lifetime = DEFAULT_LIFETIME;
        this.age = 0;
        this.xv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
        this.yv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
        // smoothing out the diagonal speed
        if (xv * xv + yv * yv > MAX_SPEED * MAX_SPEED) {
            xv *= 0.7;
            yv *= 0.7;
        }
        this.color = Color.argb(255, rndInt(0, 255), rndInt(0, 255), rndInt(0, 255));
        this.paint = new Paint(this.color);
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getXv() {
        return xv;
    }

    public void setXv(double xv) {
        this.xv = xv;
    }

    public double getYv() {
        return yv;
    }

    public void setYv(double yv) {
        this.yv = yv;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    // helper methods -------------------------
    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }



    /**
     * Resets the particle
     * @param x
     * @param y
     */
    public void reset(float x, float y) {
        this.state = Particle.STATE_ALIVE;
        this.x = x;
        this.y = y;
        this.age = 0;
    }

    // Return an integer that ranges from min inclusive to max inclusive.
    static int rndInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    static double rndDbl(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public void update() {
        if (this.state != STATE_DEAD) {
            this.x += this.xv;
            this.y += this.yv;

            // extract alpha
            int a = this.color >>> 24;
            a -= 2;								// fade by 5
            if (a <= 0) {						// if reached transparency kill the particle
                this.state = STATE_DEAD;
            } else {
                this.color = (this.color & 0x00ffffff) + (a << 24);		// set the new alpha
                this.paint.setAlpha(a);
                this.age++;						// increase the age of the particle
            }
            if (this.age >= this.lifetime) {	// reached the end if its life
                this.state = STATE_DEAD;
            }

        }
    }

    public void draw(Canvas canvas) {
        paint.setColor(this.color);
        canvas.drawCircle(this.x, this.y, radius, paint);
    }

}