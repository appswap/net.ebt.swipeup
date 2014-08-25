package net.ebt.swipeup;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;

import net.ebt.swipeup.ui.EdgeOverlay;
import net.ebt.swipeup.ui.EdgeView;

import java.util.List;

/**
 * Created by eboudrant on 8/13/14.
 */
public class SwipeUpService  extends Service {

    private static final String TAG = "SwipeUpService";
    private WindowManager windowManager;
    private EdgeOverlay overlay;
    private EdgeView edgeView;

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();


        if(ViewConfiguration.get(this).hasPermanentMenuKey() || BuildConfig.DEBUG) {

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            int gravity = Gravity.BOTTOM;

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            overlay = new EdgeOverlay(this, gravity, params);

            windowManager.addView(overlay, params);

            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            edgeView = new EdgeView(this, gravity, params);

            windowManager.addView(edgeView, params);
            edgeView.setOverlay(overlay);

            Log.i(TAG, "SwipeUpService started");
        } else {
            Log.i(TAG, "SwipeUpService started but not active, your device have already Google New gesture");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlay != null) windowManager.removeView(overlay);
        if (edgeView != null) windowManager.removeView(edgeView);
        Log.i(TAG, "SwipeUpService destroyed");
    }


}
