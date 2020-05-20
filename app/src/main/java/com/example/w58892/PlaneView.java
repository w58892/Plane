package com.example.w58892;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class PlaneView extends View {

    private Bitmap plane;
    private Bitmap cloud;

    private int planeX = 200;
    private int planeY;
    private int planeSpeed;
    private int score = 0, lifeCounter =3;

    boolean cloudHit = false;

    private int cloudX, cloudY, cloudSpeed;
    private int cloud2X, cloud2Y;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();

    private int screenHeight;
    private int screenWidth;

    private float[] axis;

    private Context con;

    private Bitmap life[] = new Bitmap[2];

    public PlaneView(Context context) {
        super(context);
        con = context;

        plane = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        cloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.niebo);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        AccelerometerClass ac = new AccelerometerClass();
        axis = ac.AccelerometerInit(con);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        screenHeight = canvas.getHeight();
        screenWidth = canvas.getWidth();

        cloudSpeed = canvas.getWidth()/25;

        canvas.drawBitmap(backgroundImage,0,0,null);
        canvas.drawBitmap(plane, planeX, planeY, null);
        planeY = canvas.getHeight() - plane.getHeight();
        int minPlaneX = 0;
        int maxPlaneX = screenWidth - plane.getWidth();

        if(axis[0] > 1) {
            planeSpeed = -(canvas.getWidth() / 36);
        }else if(axis[0] < -1){
            planeSpeed = (canvas.getWidth() / 36);
        }else{
            planeSpeed = 0;
        }

        planeX += planeSpeed;

        if(planeX < minPlaneX){
            planeX = minPlaneX;
        }
        if(planeX > maxPlaneX){
            planeX = maxPlaneX;
        }

        cloudY = cloudY + cloudSpeed;
        cloud2Y = cloud2Y + cloudSpeed;

        if(hitBallChecker(cloudX, cloudY) || hitBallChecker(cloud2X, cloud2Y)) {

            if(cloudHit == false)
                lifeCounter--;
                cloudHit = true;

            if(lifeCounter == 0){
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if(cloudY > screenHeight){
            cloudY = 0;
            cloudHit = false;
            score++;
            cloudX = (int) Math.floor(Math.random() * 4) * canvas.getWidth() / 4;
        }

        if(cloud2Y > screenHeight){
            cloud2Y = 0;
            cloud2X = (int) Math.floor(Math.random() * 4) * canvas.getWidth() / 4;
        }

        canvas.drawBitmap(cloud, cloudX, cloudY, null);
        canvas.drawBitmap(cloud, cloud2X, cloud2Y, null);

        for(int i=1; i<=3; i++)
        {
            int x =  screenWidth - (int) (  life[0].getWidth() * i * 1.2);
            int y = 10;

            if(i<=lifeCounter){
                canvas.drawBitmap(life[0], x, y, null);
            }else{
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
        canvas.drawText("Punkty: "+ score + " " +canvas.getHeight() + " " + canvas.getWidth(), 20, 60, scorePaint);
    }

    public boolean hitBallChecker(int x, int y){

        if(planeX < (x + plane.getWidth()) && (planeX + plane.getWidth()) > x  &&
            planeY < (y + plane.getHeight()) && (planeY + plane.getHeight()) > y ){
                 return true;
        } else{
             return false;
        }
    }
}