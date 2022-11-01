package pl.wiktorek140.flyff.universe;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jetbrains.annotations.Nullable;

import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

public class FlyffClient extends WebViewClient {
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return WebViewCacheInterceptorInst.getInstance().interceptRequest(request);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return WebViewCacheInterceptorInst.getInstance().interceptRequest(url);
    }
}
