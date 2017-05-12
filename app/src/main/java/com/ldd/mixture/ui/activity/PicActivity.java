package com.ldd.mixture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.TypedValue;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ldd.mixture.R;
import com.ldd.mixture.bean.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicActivity extends AppCompatActivity {

    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.grid_recycler)
    RecyclerView gridRecycler;
    @BindView(R.id.grid_swipe_refresh)
    SwipeRefreshLayout gridSwipeRefresh;
    private Intent intent;
    private Picture picture;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerview;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);

        initView();//初始化布局
        setListener();//设置监听事件
        //setEvent();
    }

    private void setListener() {
        //swipeRefreshLayout刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                //new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });
    }

    private void initView() {
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.grid_coordinatorLayout);

        recyclerview=(RecyclerView)findViewById(R.id.grid_recycler);
        mLayoutManager=new GridLayoutManager(PicActivity.this,3,GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.grid_swipe_refresh) ;
        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }
/*
    private void setEvent() {
        //图片详情页面
        intent = getIntent();
        Bundle bundle = new Bundle();
        picture = intent.getExtras().getParcelable("pics");
        //wvDetail.loadUrl(picture.getUrl());
        WebSettings setting = wvDetail.getSettings();
        setting.setJavaScriptEnabled(true);
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
    }*/

}
