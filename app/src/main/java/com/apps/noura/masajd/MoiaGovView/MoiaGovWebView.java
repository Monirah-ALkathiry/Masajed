package com.apps.noura.masajd.MoiaGovView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apps.noura.masajd.R;

/**
 * Created by Monirah on 9/4/2018.
 */

public class MoiaGovWebView extends android.support.v4.app.Fragment {

    private WebView myWebView;
    private  View view;

    public MoiaGovWebView(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.content_about_moia_gov,container,false);

        //---------------------web View ----------
        myWebView=(WebView)view.findViewById(R.id.webView);
        myWebView.setWebViewClient(new MoiaGovWebView.MyBrowser());

        final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";

        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl(websiteurl);
        return view;
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
