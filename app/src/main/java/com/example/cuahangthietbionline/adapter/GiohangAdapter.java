package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.activity.MainActivity;
import com.example.cuahangthietbionline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> array_giohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> array_giohang) {
        this.context = context;
        this.array_giohang = array_giohang;
    }


    @Override
    public int getCount() {
        return array_giohang.size();
    }

    @Override
    public Object getItem(int position) {
        return array_giohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Viewholder{
        public TextView tensp_giohang,giasp_giohang;
        public ImageView img_giohang;
         public Button btn_cong,btn_giatri,btn_tru;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if(viewholder == null){
            viewholder = new Viewholder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_giohang,null);
            viewholder.tensp_giohang = convertView.findViewById(R.id.txt_tengiohang);
            viewholder.giasp_giohang = convertView.findViewById(R.id.txt_giagiohang);
            viewholder.img_giohang = convertView.findViewById(R.id.img_giohang);
            viewholder.btn_cong = convertView.findViewById(R.id.btn_plus);
            viewholder.btn_giatri = convertView.findViewById(R.id.btn_value);
            viewholder.btn_tru = convertView.findViewById(R.id.btn_minus);
            convertView.setTag(viewholder);
        }else{
           viewholder= (Viewholder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewholder.tensp_giohang.setText(giohang.getTensp());
        DecimalFormat  decimalFormat =  new DecimalFormat("###,###,###");
        viewholder.giasp_giohang.setText(decimalFormat.format(giohang.getGisp())+"Đ");
        Picasso.with(context).load(giohang.getHinhsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewholder.img_giohang);
        viewholder.btn_giatri.setText(giohang.getSoluongsp() +"");
        int sl = Integer.parseInt(viewholder.btn_giatri.getText().toString());
        if(sl>=10){
            viewholder.btn_cong.setVisibility(View.INVISIBLE);
            viewholder.btn_tru.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewholder.btn_tru.setVisibility(View.INVISIBLE);
        }else if(sl>1){
            viewholder.btn_cong.setVisibility(View.VISIBLE);
            viewholder.btn_tru.setVisibility(View.VISIBLE);
        }
        final Viewholder finalviewholder = viewholder;
        final Viewholder finalviewholder1 = viewholder;
        viewholder.btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalviewholder.btn_giatri.getText().toString())+1;
                int slhientai = MainActivity.array_giohang.get(position).getSoluongsp();
                long giaht = MainActivity.array_giohang.get(position).getGisp();
                MainActivity.array_giohang.get(position).setSoluongsp(slmoinhat);
                long giatienmoinhat = (giaht *slmoinhat)/slhientai;
                MainActivity.array_giohang.get(position).setGisp(giatienmoinhat);
                DecimalFormat  decimalFormat =  new DecimalFormat("###,###,###");
                finalviewholder.giasp_giohang.setText(decimalFormat.format(giatienmoinhat)+"Đ");
                com.example.cuahangthietbionline.activity.Giohang.EventUntil();
                if(slmoinhat>9){
                    finalviewholder.btn_cong.setVisibility(View.INVISIBLE);
                    finalviewholder.btn_tru.setVisibility(View.VISIBLE);
                    finalviewholder.btn_giatri.setText(String.valueOf(slmoinhat));
                }else{
                    finalviewholder.btn_tru.setVisibility(View.VISIBLE);
                    finalviewholder.btn_cong.setVisibility(View.VISIBLE);
                    finalviewholder.btn_giatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewholder.btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalviewholder.btn_giatri.getText().toString())-1;
                int slhientai = MainActivity.array_giohang.get(position).getSoluongsp();
                long giaht = MainActivity.array_giohang.get(position).getGisp();
                MainActivity.array_giohang.get(position).setSoluongsp(slmoinhat);
                long giatienmoinhat = (giaht *slmoinhat)/slhientai;
                MainActivity.array_giohang.get(position).setGisp(giatienmoinhat);
                DecimalFormat  decimalFormat =  new DecimalFormat("###,###,###");
                finalviewholder.giasp_giohang.setText(decimalFormat.format(giatienmoinhat)+"Đ");
                com.example.cuahangthietbionline.activity.Giohang.EventUntil();
                if(slmoinhat<2){
                    finalviewholder1.btn_tru.setVisibility(View.INVISIBLE);
                    finalviewholder1.btn_cong.setVisibility(View.VISIBLE);
                    finalviewholder.btn_giatri.setText(String.valueOf(slmoinhat));
                }else{
                    finalviewholder1.btn_tru.setVisibility(View.VISIBLE);
                    finalviewholder1.btn_cong.setVisibility(View.VISIBLE);
                    finalviewholder.btn_giatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
