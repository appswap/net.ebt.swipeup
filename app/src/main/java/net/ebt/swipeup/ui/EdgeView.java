package net.ebt.swipeup.ui;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import net.ebt.swipeup.R;

/**
 * Created by eboudrant on 8/23/14.
 */
public class EdgeView extends View {

    private static final String TAG = "EdgeView";
    private final GestureDetector detector;
    private final EdgeOnGestureListener edgeOnGestureListener;
    private EdgeOverlay overlay;

    public EdgeView(Context context, int gravity, WindowManager.LayoutParams params) {
        super(context);
        detector = new GestureDetector(context, edgeOnGestureListener = new EdgeOnGestureListener(this));

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        switch (gravity) {
            case Gravity.BOTTOM: {
                params.x = 0;
                params.y = metrics.heightPixels - (int) getResources().getDimension(R.dimen.zone_height);
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = (int) getResources().getDimension(R.dimen.zone_height);
                break;
            }
            case Gravity.START:
            case Gravity.LEFT: {
                params.gravity = Gravity.TOP | Gravity.START;
                params.x = 0;
                params.y = 0;
                params.width = (int) getResources().getDimension(R.dimen.zone_height);
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                break;
            }
            case Gravity.END:
            case Gravity.RIGHT: {
                params.gravity = Gravity.TOP | Gravity.END;
                params.x = metrics.widthPixels - (int) getResources().getDimension(R.dimen.zone_height);
                params.y = 0;
                params.width = (int) getResources().getDimension(R.dimen.zone_height);
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                break;
            }
        }

        boolean overlay = PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .getBoolean("overlay", true);
        if(overlay) {
            setBackgroundResource(R.color.circle_focus);
        } else {
            setBackground(null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = detector.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            overlay.hideCircle();
            if(event.getAction() == MotionEvent.ACTION_UP) {
                edgeOnGestureListener.onUp(event);
            }
        }
        return handled;
    }

    void displayCircle(float x, float y) {
        if(overlay != null) {
            overlay.displayCircle(x, y);
        }
    }

    void hideCircle() {
        if (overlay != null) {
            overlay.hideCircle();
        }
    }

    public void setOverlay(EdgeOverlay overlay) {
        this.overlay = overlay;
    }

}
