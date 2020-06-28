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

public class LaptopAdapter extends BaseAdapter {
    Context context;

    public LaptopAdapter(Context context, ArrayList<Sanpham> array_laptop) {
        this.context = context;
        this.array_laptop = array_laptop;
    }

    ArrayList<Sanpham> array_laptop;



    @Override
    public int getCount() {
        return array_laptop.size();
    }

    @Override
    public Object getItem(int position) {
        return array_laptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Viewholder{
        public TextView txt_tenlap,txt_gialap,txt_motalap;
        public ImageView img_lap;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if(viewholder == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            viewholder.txt_tenlap=convertView.findViewById(R.id.txt_tenlap);
            viewholder.txt_gialap=convertView.findViewById(R.id.txt_gialap);
            viewholder.txt_motalap=convertView.findViewById(R.id.txt_motalap);
            viewholder.img_lap= convertView.findViewById(R.id.img_lap);
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewholder.txt_tenlap.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholder.txt_gialap.setText("Giá : "+ decimalFormat.format(sanpham.getGiasp())+"Đ");
        viewholder.txt_motalap.setMaxLines(2);
        viewholder.txt_motalap.setEllipsize(TextUtils.TruncateAt.END);
        viewholder.txt_motalap.setText(sanpham.getMotasp());
        Picasso.with(context).load(sanpham.getImgsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewholder.img_lap);


        return convertView;
    }
}
