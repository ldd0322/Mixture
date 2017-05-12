package com.ldd.mixture.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ldd.mixture.R;
import com.ldd.mixture.bean.ResultJoke;
import com.ldd.mixture.bean.ResultJoke.ResultBean.Joke;
import com.ldd.mixture.common.Common;
import com.ldd.mixture.common.ServerConfig;
import com.ldd.mixture.ui.adapter.JokeAdapter;
import com.ldd.mixture.utils.TimeUtil;
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

public class JokeFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;

    @BindView(R.id.prf_listView)
    PullToRefreshListView prfListView;
    private Unbinder unbinder;
    private View rootView;

    private int page = 1;
    private List<Joke> data = new ArrayList<>();
    private JokeAdapter jokeAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listview, null);
        unbinder = ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        //设置列表的刷新加载
        prfListView.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
       jokeAdapter = new JokeAdapter(data);
        //绑定适配器
        prfListView.setAdapter(jokeAdapter);
        prfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),data.get(i-1).getContent(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ((Joke)jokeAdapter.getItem(i)).getContent(),Toast.LENGTH_SHORT).show();
            }
        });
        prfListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getAsyncData(page,TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getAsyncData(page++,TYPE_LOADMORE);
            }
        });

    }

    private void initData() {
        getAsyncData(1,TYPE_REFRESH);

    }
    private void getAsyncData(int page, final int type ){
        OkHttpUtils
                .get()
                .url(ServerConfig.JOKE_URL)
                .addParams("sort", "desc")
                .addParams("page", String.valueOf(page))
                .addParams("pagesize", Common.PAGE_SIZE)
                .addParams("time", TimeUtil.getTime())
                .addParams("key", Common.API_JOKE_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        prfListView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prfListView.onRefreshComplete();
                        //解析数据
                        ResultJoke resultJoke = JSON.parseObject(response, ResultJoke.class);
                        switch (type){
                            case TYPE_REFRESH:
                                jokeAdapter.setNewData(resultJoke.getResult().getData());
                                break;
                            case TYPE_LOADMORE:
                                jokeAdapter.setMoreData(resultJoke.getResult().getData());
                                break;
                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

