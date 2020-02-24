package com.example.tag_game;

import android.annotation.TargetApi;
import android.content.Context;
import android.gesture.Gesture;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.lang.annotation.Target;

public class GestureDetectGridView extends GridView {

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;//порог скорости

    private GestureDetector gDetector;//детектор движения
    private  boolean mFlingConfirmed = false;//разрешение на перемещение
    private float mTouchX;//совпадение по x
    private float mTouchY;//совпадение по y

    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
         @Override
         public boolean onDown(MotionEvent event){
             return  true;
         }

         @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
             final  int position = GestureDetectGridView.this.pointToPosition
                     (Math.round(e1.getX()), Math.round(e1.getY()));

             if (Math.abs(e1.getY()-e2.getY())>SWIPE_MAX_OFF_PATH){
                 if ((Math.abs(e1.getX()-e2.getX())>SWIPE_MAX_OFF_PATH)||
                         Math.abs(velocityY)<SWIPE_THRESHOLD_VELOCITY){
                     return false;
                 }
                 if ((e1.getY()-e2.getY())>SWIPE_MAX_OFF_PATH){
                    //Добавить код для свайпа вверх
                     GameActivity.moveTiles(context, GameActivity.UP, position);
                 } else if ((e2.getY()-e1.getY())>SWIPE_MAX_OFF_PATH){
                     //Добавить код для свайпа вниз
                     GameActivity.moveTiles(context, GameActivity.DOWN, position);
                 }
             } else {
                 if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                     return false;
                 }
                 if ((e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH) {
                     //Добавить код для свайпа влево
                     GameActivity.moveTiles(context, GameActivity.LEFT, position);
                 } else if ((e2.getX() - e1.getX()) > SWIPE_MAX_OFF_PATH) {
                     //Добавить код для свайпа вправо
                     GameActivity.moveTiles(context, GameActivity.RIGHT , position);
                 }
             }
             return super.onFling(e1, e2, velocityX, velocityY);
         }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
            mFlingConfirmed = false;
        } else if(action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {
            if(mFlingConfirmed){
                return true;
            }

            float dx = (Math.abs(ev.getX() - mTouchX));
            float dy = (Math.abs(ev.getY() - mTouchY));
            if((dx > SWIPE_MIN_DISTANCE)||(dy>SWIPE_MIN_DISTANCE)){
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public  boolean onTouchEvent(MotionEvent ev){
        return gDetector.onTouchEvent(ev);
    }

}
