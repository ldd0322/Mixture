package com.ldd.mixture.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ldd.mixture.R;
import com.ldd.mixture.ui.activity.LoginActivity;
import com.ldd.mixture.ui.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by S01 on 2017/5/6.
 */

public class MeFragment extends Fragment {


    @BindView(R.id.btn_log)
    Button btnLog;
    @BindView(R.id.btn_res)
    Button btnRes;
    Unbinder unbinder;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, null);
        unbinder = ButterKnife.bind(this, rootView);

        initView();
        return rootView;
    }

    private void initView() {
        btnLog = (Button) rootView.findViewById(R.id.btn_log);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_log:
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        break;
                }
            }
        });
                btnRes = (Button) rootView.findViewById(R.id.btn_res);
                btnRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_res:
                                startActivity(new Intent(getActivity(), RegisterActivity.class));
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