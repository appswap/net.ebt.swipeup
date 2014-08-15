package net.ebt.swipeup;

import android.app.Application;
import android.util.Log;

/**
 * Created by eboudrant on 8/15/14.
 */
public class SwipeUpApplication extends Application {

    private static final String TAG = "SwipeUpApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "create SwipeUpApplication");
    }
}
