package com.hnguyen.childrenlearninggame.model.Components;

/**
 * Created by hnguyen on 10/3/15.
 */
public class Speed {

    public static final int DIRECTION_RIGHT	= 1;
    public static final int DIRECTION_LEFT	= -1;
    public static final int DIRECTION_UP	= -1;
    public static final int DIRECTION_DOWN	= 1;
    private static final int MAX_SPEED = 3;

    private double xv = 1;	// velocity value on the X axis
    private double yv = 1;	// velocity value on the Y axis

    private int xDirection = DIRECTION_RIGHT;
    private int yDirection = DIRECTION_UP;

    public Speed() {
        this.xv = rndDbl(0, MAX_SPEED);
        this.yv = rndDbl(0, MAX_SPEED);
    }

    public Speed(float xv, float yv) {
        this.xv = xv;
        this.yv = yv;
    }

    public void resetSpeed(){
        this.xv = rndDbl(0, MAX_SPEED);
        this.yv = rndDbl(0, MAX_SPEED);
    }

    public double getXv() {
        return xv;
    }
    public void setXv(float xv) {
        this.xv = xv;
    }
    public double getYv() {
        return yv;
    }
    public void setYv(float yv) {
        this.yv = yv;
    }

    public int getxDirection() {
        return xDirection;
    }
    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }
    public int getyDirection() {
        return yDirection;
    }
    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    // changes the direction on the X axis
    public void toggleXDirection() {
        xDirection = xDirection * -1;
    }

    // changes the direction on the Y axis
    public void toggleYDirection() {
        yDirection = yDirection * -1;
    }
    static double rndDbl(double min, double max) {
        return min + (max - min) * Math.random();
    }

}

