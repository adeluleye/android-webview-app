package com.appdevchannel.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    RelativeLayout mWebViewLoadingScreen, mWebViewErrorScreen, mWebViewContentScreen;

    WebView mWebView;

    String url = "https://www.appdevchannel.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebViewErrorScreen = findViewById(R.id.webViewErrorScreen);
        mWebViewLoadingScreen = findViewById(R.id.webViewLoadingScreen);
        mWebViewContentScreen = findViewById(R.id.webViewContentScreen);

        mWebView = findViewById(R.id.webView);

        Button reloadButton = findViewById(R.id.btnReload);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebPage();
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                showLoadingScreen();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                showContentScreen();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                showErrorScreen();
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);

        loadWebPage();
    }

    private void loadWebPage() {
        if (isConnected()) {

            mWebView.loadUrl(url);
        } else {
            showErrorScreen();
        }
    }

    private void showLoadingScreen() {

        mWebViewErrorScreen.setVisibility(View.GONE);
        mWebViewContentScreen.setVisibility(View.GONE);
        mWebViewLoadingScreen.setVisibility(View.VISIBLE);
    }

    private void showContentScreen() {

        mWebViewErrorScreen.setVisibility(View.GONE);
        mWebViewLoadingScreen.setVisibility(View.GONE);
        mWebViewContentScreen.setVisibility(View.VISIBLE);
    }

    private void showErrorScreen() {

        mWebViewLoadingScreen.setVisibility(View.GONE);
        mWebViewContentScreen.setVisibility(View.GONE);
        mWebViewErrorScreen.setVisibility(View.VISIBLE);
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        }

        return false;
    }
}
