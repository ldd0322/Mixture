package com.ldd.mixture.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ldd.mixture.R;
import com.ldd.mixture.bean.News;
import com.ldd.mixture.common.Common;
import com.ldd.mixture.common.ServerConfig;
import com.ldd.mixture.ui.activity.NewsDataActivity;
import com.ldd.mixture.ui.adapter.NewsAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * Created by S01 on 2017/5/6.
 */

public class NewsFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;
    @BindView(R.id.lv_news)
    PullToRefreshListView lvNews;
    Unbinder unbinder;
    @BindView(R.id.rv_pic)
    RecyclerView rvPic;
    private View rootView;

    private List<News> data = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private static int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, null);
        unbinder = ButterKnife.bind(this, rootView);

        setEvent();
        return rootView;
    }

    private void setEvent() {
        //初始化适配器
        newsAdapter = new NewsAdapter(data);
        //绑定适配器
        lvNews.setAdapter(newsAdapter);
        //添加监听事件
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), data.get(position).getDate(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewsDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news", data.get(position - 1));
                intent.putExtras(bundle);
                NewsFragment.this.startActivity(intent);
            }
        });
        lvNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //x下拉刷新
                getAsyncData(1, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                getAsyncData(page++, TYPE_LOADMORE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        getAsyncData(1, TYPE_REFRESH);
    }

    private void getAsyncData(int page, final int type) {
        OkHttpUtils
                .post()
                .url(ServerConfig.BASE_URL)
                .addParams("key", Common.API_NEWS_KEY)
                .addParams("type", "top")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求数据失败!", Toast.LENGTH_SHORT).show();
                        lvNews.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        lvNews.onRefreshComplete();
                        //把返回的结果JSON数据字符串转为json对象
                        JSONObject jsonobject = JSONObject.parseObject(response);
                        JSONArray jsonArray = jsonobject.getJSONObject("result").getJSONArray("data");
                        //data = jsonArray.toJavaList(News.class);
                        data.addAll(JSONArray.parseArray(jsonArray.toJSONString(), News.class));
                        newsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
