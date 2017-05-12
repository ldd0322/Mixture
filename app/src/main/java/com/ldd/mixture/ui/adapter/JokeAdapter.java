package com.ldd.mixture.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ldd.mixture.R;
import com.ldd.mixture.bean.ResultJoke.ResultBean.Joke;

import java.util.List;

/**
 * Created by S01 on 2017/5/6.
 */

public class JokeAdapter extends BaseAdapter {
    private List<Joke> data;
    private ViewHolder holder;

    public JokeAdapter(List<Joke> data) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_joke,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_joke.setText(data.get(i).getContent());
        return view;
    }
    class ViewHolder{
        TextView tv_joke;
        public ViewHolder (View view){
            tv_joke = (TextView) view.findViewById(R.id.tv_joke);
        }
    }
    public void setNewData(List<Joke> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<Joke> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
