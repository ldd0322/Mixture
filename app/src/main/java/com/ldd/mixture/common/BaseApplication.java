package com.ldd.mixture.common;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by S01 on 2017/5/2.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initShareSDK();
        initOkhttp();
    }

    private void initShareSDK() {
        ShareSDK.initSDK(this);
    }

    private void initOkhttp() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .cookieJar(cookieJar)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
