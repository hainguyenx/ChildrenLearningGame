package com.hnguyen.childrenlearninggame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.hnguyen.childrenlearninggame.MainGamePanel;

/**
 * Created by hnguyen on 12/2/15.
 */
public class Background {

    private Bitmap image;
    private int x,y,dx;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Background(Bitmap image)
    {
        this.image = image;
        x =0;
        y =0;
    }


    public void update()
    {
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }

    public void setVector(int dx)
    {
        this.dx = dx;
    }
}
