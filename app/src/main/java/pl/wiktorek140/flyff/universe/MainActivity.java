package pl.wiktorek140.flyff.universe;

import android.app.*;
import android.os.*;
import android.webkit.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.widget.RelativeLayout.*;
import ren.yale.android.cachewebviewlib.*;
import android.annotation.*;
import android.support.annotation.*;

public class MainActivity extends Activity 
{
    WebView mWebView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES ,
							 WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES | WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
   
		WebViewCacheInterceptorInst.getInstance().
			init(new WebViewCacheInterceptor.Builder(this));
			
		mWebView = findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient(){

				@TargetApi(Build.VERSION_CODES.LOLLIPOP)
				@Nullable
				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
					return  WebViewCacheInterceptorInst.getInstance().interceptRequest(request);
				}
@Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return  WebViewCacheInterceptorInst.getInstance().interceptRequest(url);
            }
			});
	
		mWebView.setWebChromeClient(new MyChrome());
		
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		//webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
		
		
		mWebView.loadUrl("https://universe.flyff.com/play");
		return;
    }
	
	private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = (View) null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = (WebChromeClient.CustomViewCallback) null;
			return;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
			getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES | WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES | WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			
			((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
			
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
															 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
															 | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
															 | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
															 | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
															 | View.SYSTEM_UI_FLAG_IMMERSIVE);
		return;
        }
    }
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
		return;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWebView.restoreState(savedInstanceState);
		return;
    }
	
	@Override
	public void onBackPressed() {
        this.moveTaskToBack(true);
		return;
	}
	
}
