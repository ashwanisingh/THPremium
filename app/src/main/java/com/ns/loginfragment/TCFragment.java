package com.ns.loginfragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.view.CustomProgressBar;

public class TCFragment extends BaseFragmentTHP {

    private WebView tc_webView;
    private ImageButton backBtn;
    private String mWebUrl;
    private CustomProgressBar mProgressBar;
    private String mFrom;

    public static TCFragment getInstance(String url, String from) {
        TCFragment fragment = new TCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("from", from);
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
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tc_webView = view.findViewById(R.id.tc_webView);
        mProgressBar = view.findViewById(R.id.progressBar);
        backBtn = view.findViewById(R.id.backBtn);

        if(mFrom != null && mFrom.equalsIgnoreCase("crossBackImg")) {
            backBtn.setImageResource(R.drawable.ic_close_ss);
        } else if(mFrom != null && mFrom.equalsIgnoreCase("arrowBackImg")) {
            backBtn.setImageResource(R.drawable.ic_back_copy_42);
        }

        // Back Btn click listener
        backBtn.setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });

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
