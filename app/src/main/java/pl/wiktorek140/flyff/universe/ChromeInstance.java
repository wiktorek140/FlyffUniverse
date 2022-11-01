package pl.wiktorek140.flyff.universe;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

public class ChromeInstance extends WebChromeClient {

    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;
    private int mOriginalSystemUiVisibility;

    private final Activity activity;

    ChromeInstance(Activity activity) {
        this.activity = activity;
    }

    public void onHideCustomView() {
        ((FrameLayout) activity.getWindow().getDecorView()).removeView(this.mCustomView);
        this.mCustomView = (View) null;
        activity.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
        activity.setRequestedOrientation(this.mOriginalOrientation);
        this.mCustomViewCallback.onCustomViewHidden();
        this.mCustomViewCallback = (WebChromeClient.CustomViewCallback) null;
    }

    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
        if (this.mCustomView != null) {
            onHideCustomView();
            return;
        }

        this.mCustomView = paramView;
        this.mOriginalSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        this.mOriginalOrientation = activity.getRequestedOrientation();
        this.mCustomViewCallback = paramCustomViewCallback;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        ((FrameLayout) activity.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
