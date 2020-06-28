package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienthoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> array_dt;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> array_dt) {
        this.context = context;
        this.array_dt = array_dt;
    }


    @Override
    public int getCount() {
        return array_dt.size();
    }

    @Override
    public Object getItem(int position) {
        return array_dt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txt_tendt,txt_giadt,txt_mota;
        public ImageView img_dt;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txt_tendt = convertView.findViewById(R.id.txt_tendt);
            viewHolder.txt_giadt = convertView.findViewById(R.id.txt_giadt);
            viewHolder.txt_mota = convertView.findViewById(R.id.txt_motadt);
            viewHolder.img_dt=convertView.findViewById(R.id.img_dt);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txt_tendt.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txt_giadt.setText("Giá : "+ decimalFormat.format(sanpham.getGiasp())+"Đ");
        viewHolder.txt_mota.setMaxLines(2);
        viewHolder.txt_mota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txt_mota.setText(sanpham.getMotasp());
        Picasso.with(context).load(sanpham.getImgsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.img_dt);


        return convertView;
    }
}
