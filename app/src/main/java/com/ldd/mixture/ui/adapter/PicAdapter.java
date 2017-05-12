package com.ldd.mixture.ui.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ldd.mixture.R;
import com.ldd.mixture.bean.Picture;
import com.ldd.mixture.bean.Picture.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.ldd.mixture.bean.ResultJoke;

import java.util.List;

/**
 * Created by S01 on 2017/5/9.
 */

public class PicAdapter extends BaseAdapter {
    private List<ContentlistBean> data;
    private ViewHolder holder;

    public PicAdapter(List<ContentlistBean> data) {
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pic,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder  = (ViewHolder) view.getTag();
        }
        //加载网络缓存图片
        Glide.with(viewGroup.getContext())
                .load(data.get(i).getList().get(0).getBig())
                .placeholder(R.mipmap.aio_image_default)
                .error(R.mipmap.aio_image_fail)
                .into(holder.iv_image);
        holder.tv_title.setText(data.get(i).getTitle());
        return view;
    }
    class ViewHolder{
        ImageView iv_image;
        TextView tv_title;
        public ViewHolder(View view){
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
    public void setNewData(List<ContentlistBean> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<ContentlistBean> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
