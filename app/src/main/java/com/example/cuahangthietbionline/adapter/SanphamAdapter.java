package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.activity.ChitietSanpham;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.until.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txtTensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasp.setText("Giá : "+ decimalFormat.format(sanpham.getGiasp())+"Đ");
        Picasso.with(context).load(sanpham.getImgsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(holder.imgsanpham);
    }

    @Override
    public int getItemCount() {
            return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgsanpham;
        public TextView txtTensp,txtGiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgsanpham = itemView.findViewById(R.id.imgsanpham);
            txtGiasp = itemView.findViewById(R.id.txtgiasp);
            txtTensp=itemView.findViewById(R.id.txttensp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChitietSanpham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
