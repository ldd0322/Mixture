package com.ldd.mixture.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ldd.mixture.R;
import com.ldd.mixture.common.Common;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_Newname)
    EditText etNewname;
    @BindView(R.id.et_Newpwd)
    EditText etNewpwd;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.btn_resg)
    Button btnResg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        Bmob.initialize(this, Common.BMOB_ID);
    }
    @OnClick(R.id.btn_resg)
    public void onViewClicked() {
        final String name = etNewname.getText().toString();
        final String pwd = etNewpwd.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(pwd);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                if (name.equals("") || pwd.equals("")) {
                    Toast.makeText(RegisterActivity.this, "账户和密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (name.length() < 7 || pwd.length() < 7) {
                    Toast.makeText(RegisterActivity.this, "账户和密码不能小于6位数",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterActivity.this, "注册失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
