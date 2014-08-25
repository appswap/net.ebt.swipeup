package net.ebt.swipeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by eboudrant on 8/13/14.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot received");
        boolean enable = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean("enable", true);
        if (enable && Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, SwipeUpService.class);
            context.startService(serviceIntent);
        }
    }

}
