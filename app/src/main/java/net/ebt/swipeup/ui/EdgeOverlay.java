package net.ebt.swipeup.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import net.ebt.swipeup.R;

/**
 * Created by eboudrant on 8/24/14.
 */
public class EdgeOverlay extends View {

    private static final String TAG = "EdgeOverlay";
    private final Paint circlePaint;
    private boolean displayCircle = false;
    private float circleX, circleY, circleRadius;
    private final PointCloud pointCloud;
    private float xOffset = 0;
    private float yOffset = 0;

    public EdgeOverlay(Context context, int gravity, WindowManager.LayoutParams params) {
        super(context);
        float radius = getResources().getDimension(R.dimen.glowpadview_glow_radius);
        circlePaint = new Paint();
        circlePaint.setColor(getResources().getColor(R.color.circle_stroke));
        circlePaint.setStrokeWidth(getResources().getDimension(R.dimen.circle_stroke));
        circlePaint.setStyle(Paint.Style.STROKE);
        setVisibility(GONE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        switch (gravity) {
            case Gravity.BOTTOM: {
                circleRadius = metrics.widthPixels / 2 - metrics.widthPixels / 20;
                params.x = 0;
                params.y = metrics.heightPixels - (int) getResources().getDimension(R.dimen.overlay_height);
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = (int) (circleRadius + radius);
                circleX = metrics.widthPixels / 2;
                circleY = params.height;
                yOffset = getResources().getDimension(R.dimen.overlay_height) + radius;
                break;
            }
            case Gravity.START:
            case Gravity.LEFT: {
                circleX = 0;
                circleY = metrics.heightPixels / 2;
                circleRadius = metrics.heightPixels / 4;
                params.gravity = Gravity.TOP | Gravity.START;
                params.x = 0;
                params.y = 0;
                params.width = (int) (circleRadius + radius);
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                break;
            }
            case Gravity.END:
            case Gravity.RIGHT: {
                circleX = metrics.widthPixels;
                circleY = metrics.heightPixels / 2;
                circleRadius = metrics.heightPixels / 4;
                params.gravity = Gravity.TOP | Gravity.END;
                params.x = metrics.widthPixels - (int) getResources().getDimension(R.dimen.overlay_height);
                params.y = 0;
                params.width = (int) (circleRadius + radius);
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                break;
            }
        }
        pointCloud = new PointCloud(getResources().getDrawable(R.drawable.ic_lockscreen_glowdot));
        pointCloud.makePointCloud(getResources().getDimension(R.dimen.glowpadview_inner_radius), circleRadius);
        pointCloud.glowManager.setRadius(radius);
        pointCloud.glowManager.setAlpha(1.0f);
        pointCloud.waveManager.setAlpha(1.0f);
    }

    void displayCircle(float xp, float yp) {
        if (!displayCircle) {
            setVisibility(VISIBLE);
            displayCircle = true;
        }
        pointCloud.setCenter(xp + xOffset, yp + yOffset);
        pointCloud.setScale(1.0f);
        invalidate();
    }

    void hideCircle() {
        if (displayCircle) {
            setVisibility(GONE);
            displayCircle = false;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (displayCircle) {
            canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);
            pointCloud.draw(canvas);
        }
    }

}
