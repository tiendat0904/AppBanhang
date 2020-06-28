package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayList_loaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayList_loaisp, Context context) {
        this.arrayList_loaisp = arrayList_loaisp;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList_loaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_loaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp= view.findViewById(R.id.textview_loaisp);
            viewHolder.imgloaisp= view.findViewById(R.id.imageview_loaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();

        }
        Loaisp loaisp= (Loaisp) getItem(position);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhAnhloaisp())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(viewHolder.imgloaisp);

        return view;
    }
}
