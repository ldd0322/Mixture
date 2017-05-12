package com.ldd.mixture;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ldd.mixture.bean.News;
import com.ldd.mixture.ui.adapter.NewsAdapter;
import com.ldd.mixture.ui.fragment.JokeFragment;
import com.ldd.mixture.ui.fragment.MeFragment;
import com.ldd.mixture.ui.fragment.NewsFragment;
import com.ldd.mixture.ui.fragment.PicFragment;
import com.ldd.mixture.ui.helper.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lv_news)
    ListView lvNews;
    @BindView(R.id.vp_main_container)
    NoSlidingViewPaper vpMainContainer;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    LinearLayout container;
    private NoSlidingViewPaper mViewPager;
    private List<News> data = new ArrayList<>();
    private NewsAdapter newAdapter;

    private NewsFragment newsFragment;
    private JokeFragment jokeFragment;
    private PicFragment picFragment;
    private MeFragment meFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //设置默认显示的fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        newsFragment = new NewsFragment();
        fragmentTransaction.add(R.id.content, newsFragment);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    newsFragment = new NewsFragment();
                    setTitle("新闻");
                    replace(newsFragment);
                    return true;
                case R.id.navigation_joke:
                    jokeFragment = new JokeFragment();
                    setTitle("笑话");
                    replace(jokeFragment);
                    return true;
                case R.id.navigation_picture:
                    picFragment = new PicFragment();
                    setTitle("图片");
                    replace(picFragment);
                    return true;
                case R.id.navigation_me:
                    meFragment = new MeFragment();
                    setTitle("我的");
                    replace(meFragment);
                    return true;
            }
            return false;
        }
    };

    public void replace(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

}