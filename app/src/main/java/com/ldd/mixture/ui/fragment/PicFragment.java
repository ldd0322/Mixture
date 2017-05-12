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
import com.ldd.mixture.bean.Picture;
import com.ldd.mixture.bean.Picture.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.ldd.mixture.common.Common;
import com.ldd.mixture.common.ServerConfig;
import com.ldd.mixture.ui.adapter.PicAdapter;
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

public class PicFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;
    @BindView(R.id.prf_pic)
    PullToRefreshListView prfPic;


    private List<ContentlistBean> data = new ArrayList<>();
    private View rootView;
    private Unbinder unbinder;
    private PicAdapter picAdapter;
    private static int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pic, null);
        unbinder = ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    private void initView() {
        //设置列表的刷新加载
        prfPic.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
        picAdapter = new PicAdapter(data);
        //绑定适配器
        prfPic.setAdapter(picAdapter);
        prfPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), data.get(i - 1).getTitle(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ((Joke)jokeAdapter.getItem(i)).getContent(),Toast.LENGTH_SHORT).show();
            }
        });
        prfPic.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getAsyncData(page, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
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
                .get()
                .url(ServerConfig.PIC_URL)
                .addParams("showapi_sign", Common.API_PIC_KEY)
                .addParams("showapi_appid", "37544")
                .addParams("type", "4001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        prfPic.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prfPic.onRefreshComplete();
                        //解析数据
                        Picture picture = JSON.parseObject(response, Picture.class);
                        switch (type) {
                            case TYPE_REFRESH:
                                picAdapter.setNewData(picture.getShowapi_res_body().getPagebean().getContentlist());
                                break;
                            case TYPE_LOADMORE:
                                picAdapter.setMoreData(picture.getShowapi_res_body().getPagebean().getContentlist());
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

