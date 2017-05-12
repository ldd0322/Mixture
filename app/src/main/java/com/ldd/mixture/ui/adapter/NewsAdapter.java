package com.ldd.mixture.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ldd.mixture.R;
import com.ldd.mixture.bean.News;

import java.util.List;

/**
 * Created by S01 on 2017/5/4.
 */

public class NewsAdapter extends BaseAdapter {
    private List<News> data;
    private ViewHolder holder;

    public NewsAdapter(List<News> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        //加载缓存网络图片
        Glide.with(viewGroup.getContext())
                .load(data.get(i).getThumbnail_pic_s())
                .placeholder(R.mipmap.aio_image_default)
                .error(R.mipmap.aio_image_fail)
                .into(holder.iv_image);

        holder.tv_title.setText(data.get(i).getTitle());
        return view;
    }
    class ViewHolder{
        ImageView iv_image;
        TextView tv_title;
        public ViewHolder(View view) {
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
