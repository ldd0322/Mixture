package com.ldd.mixture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.ldd.mixture.MainActivity;
import com.ldd.mixture.R;
import cn.bmob.v3.BmobUser;

import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity implements PlatformActionListener {
    private EditText et_name, et_pwd;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBmob();
        findView();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ibtn_sinaw:
                loginBySina();
                break;
            case R.id.ibtn_qq:
                loginByQQ();
                break;
            case R.id.ibtn_tenweibo:
                loginByTenwei();
                break;
            case R.id.ibtn_wechat:
                loginByWechat();
                break;

        }
    }


    private void login() {
        String Name = et_name.getText().toString();
        String Pwd = et_pwd.getText().toString();
        // 非空验证
        if (Name.isEmpty() || Pwd.isEmpty()) {
            Toast.makeText(this, "账号名密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 使用BmobSDK提供的登录功能
        BmobUser user = new BmobUser();
        user.setUsername(Name);
        user.setPassword(Pwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginByQQ() {
        //获取授权平台对象
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //设置授权监听器
        qq.setPlatformActionListener(this);
        //authorize与showUser单独调用一个即可
        qq.authorize();
    }

    private void loginByWechat() {
        //获取授权平台对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //设置授权监听器
        wechat.setPlatformActionListener(this);
        //authorize与showUser单独调用一个即可
        wechat.authorize();
    }
    private void loginBySina() {
        //获取授权平台对象
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //设置授权监听器
        weibo.setPlatformActionListener(this);
        //authorize与showUser单独调用一个即可
        weibo.authorize();
    }

    private void loginByTenwei() {
        //获取授权平台对象
        Platform tenweibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //设置授权监听器
        tenweibo.setPlatformActionListener(this);
        //authorize与showUser单独调用一个即可
        tenweibo.authorize();
    }

    private void findView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
    }

    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                ////设置appkey
                .setApplicationId("d6a0d2221932d20a021a270271390772")
                ////请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(10)
                ////文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            PlatformDb userDB = platform.getDb();
            String result = userDB.exportData();
            Log.i("TAG", result);
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
            //TODO 根据获取的信息完成当前系统的注册功能，如果已经授权过直接登录
            String userID = userDB.getUserId();
            String icon = userDB.getUserIcon();
            String token = userDB.getToken();
            String nickname = userDB.getUserName();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            throwable.printStackTrace();
            Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }
    };
