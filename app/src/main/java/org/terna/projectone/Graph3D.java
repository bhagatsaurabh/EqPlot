package org.terna.projectone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class Graph3D extends AppCompatActivity {

    WebView myWebView;
    String equation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph3d);

        equation = getIntent().getStringExtra("equation");
        myWebView = findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(this, "Android");
    }

    @Override
    protected void onResume() {
        myWebView.loadUrl("file:///android_asset/graph3D.html");
        super.onResume();
    }

    @JavascriptInterface
    public void check(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void pageLoaded() {
        myWebView.post(new Runnable() {
            @Override
            public void run() {
                myWebView.evaluateJavascript("plot('" + equation + "');", null);
            }
        });
    }
}
