package com.example.yiyiapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yiyiapp.R;
import com.example.yiyiapp.present.HistoryListViewPresenter;
import com.example.yiyiapp.util.HistoryInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiamin on 2016/7/31.
 */
public class HistoryListViewAdapter extends BaseAdapter {

    private ListView listView;
    private List<HistoryInfoBean> list;
    private LayoutInflater inflater;
    private HistoryListViewPresenter presenter;

    public HistoryListViewAdapter(ListView listView, Context context)
    {
        this.listView = listView;
        list = new ArrayList<HistoryInfoBean>();
        inflater = LayoutInflater.from(context);
        presenter = new HistoryListViewPresenter();
        presenter.SqliteRead(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item,null);
            viewHolder.src = (TextView) view.findViewById(R.id.tv_item_src);
            viewHolder.result = (TextView) view.findViewById(R.id.tv_item_result);
            viewHolder.button = (Button) view.findViewById(R.id.item_button_delete);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.src.setText(list.get(i).src);
        viewHolder.result.setText(list.get(i).result);

        return view;
    }


    class ViewHolder{
        public TextView src;
        public TextView result;
        public Button button;
    }
}