package com.ldd.mixture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.ldd.mixture.R;
import com.ldd.mixture.bean.News;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDataActivity extends AppCompatActivity {

    @BindView(R.id.wv_detail)
    WebView wvDetail;
    private Intent intent;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_data);
        ButterKnife.bind(this);
        setEvent();
    }

    private void setEvent() {
        //新闻详情页面
        intent = getIntent();
        Bundle bundle = new Bundle();
        news = intent.getExtras().getParcelable("news");
        wvDetail.loadUrl(news.getUrl());
        WebSettings setting = wvDetail.getSettings();
        setting.setJavaScriptEnabled(true);
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
    }
}

