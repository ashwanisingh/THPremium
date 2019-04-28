package com.ns.userfragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ns.thpremium.R;

public class TCFragment extends BaseFragmentTHP {

    private WebView tc_webView;
    private String mWebUrl;
    private ProgressBar mProgressBar;

    public static TCFragment getInstance(String url) {
        TCFragment fragment = new TCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mWebUrl = getArguments().getString("url");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tc_webView = view.findViewById(R.id.tc_webView);
        mProgressBar = view.findViewById(R.id.progressBar);

        tc_webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //javascript support
        tc_webView.getSettings().setJavaScriptEnabled(true);
        //html5 support
        tc_webView.getSettings().setDomStorageEnabled(true);
        tc_webView.getSettings().setAllowContentAccess(true);
        tc_webView.getSettings().setLoadWithOverviewMode(true);

        tc_webView.loadUrl(mWebUrl);


        tc_webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }


        });

    }


}
