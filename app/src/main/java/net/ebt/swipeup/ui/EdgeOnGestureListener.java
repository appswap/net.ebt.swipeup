package net.ebt.swipeup.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import net.ebt.swipeup.BuildConfig;
import net.ebt.swipeup.SwipeUpService;

/**
 * Created by eboudrant on 8/23/14.
 */
public class EdgeOnGestureListener implements GestureDetector.OnGestureListener {

    private static final String TAG = "EdgeGestureDetector";

    private final EdgeView edgeView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean fling;

    public EdgeOnGestureListener(EdgeView edgeView) {
        this.edgeView = edgeView;
    }

    public void onUp(MotionEvent e) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!fling) {
                    launchAssistIntent();
                }
            }
        }, 100);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        fling = false;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        edgeView.displayCircle(e2.getX(), e2.getY());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        fling = true;
        if(velocityX > 0) {
            launchAssistIntent();
        }
        edgeView.hideCircle();
        return true;
    }

    private void launchAssistIntent() {
        Intent serviceIntent = new Intent(Intent.ACTION_ASSIST);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        serviceIntent.putExtra(BuildConfig.PACKAGE_NAME, BuildConfig.VERSION_CODE);
        edgeView.getContext().startActivity(serviceIntent);
    }

}
