package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Giohang;
import com.example.cuahangthietbionline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChitietSanpham extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar_ctsp;
    ImageView img_ctsp1;
    TextView txt_ten_ctsp,txt_gia_ctsp,txt_mota_ctsp;
    Spinner spinner;
    Button btn_giohang;
    int id = 0;
    String ten_ctsp="";
    int gia_ctsp=0;
    String img_ctsp="";
    String mota_ctsp="";
    int id_sp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        Anhxa();
        ACtionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.cuahangthietbionline.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btn_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.array_giohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist= false;
                    for(int i = 0 ;i<MainActivity.array_giohang.size();i++){
                        if(MainActivity.array_giohang.get(i).getIdsp() ==id){
                            MainActivity.array_giohang.get(i).setSoluongsp(MainActivity.array_giohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.array_giohang.get(i).getSoluongsp()>10){
                                MainActivity.array_giohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.array_giohang.get(i).setGisp(gia_ctsp * MainActivity.array_giohang.get(i).getSoluongsp());
                            exist = true;
                        }
                    }
                    if(exist == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong*gia_ctsp;
                        MainActivity.array_giohang.add(new Giohang(id,ten_ctsp,Giamoi,img_ctsp,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong*gia_ctsp;
                    MainActivity.array_giohang.add(new Giohang(id,ten_ctsp,Giamoi,img_ctsp,soluong));
                }
                Intent intent  = new Intent(getApplicationContext(), com.example.cuahangthietbionline.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);

    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        ten_ctsp=sanpham.getTensp();
        gia_ctsp=sanpham.getGiasp();
        img_ctsp=sanpham.getImgsp();
        mota_ctsp=sanpham.getMotasp();
        id_sp=sanpham.getId_sp();
        txt_ten_ctsp.setText(ten_ctsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_gia_ctsp.setText("Giá : "+ decimalFormat.format(sanpham.getGiasp())+"Đ");
        txt_mota_ctsp.setText(mota_ctsp);
        Picasso.with(getApplicationContext()).load(img_ctsp).placeholder(R.drawable.noimage).error(R.drawable.error).into(img_ctsp1);
    }

    private void ACtionToolbar() {
        setSupportActionBar(toolbar_ctsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_ctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar_ctsp=(Toolbar) findViewById(R.id.toolbar_ctsp);
        img_ctsp1=(ImageView)findViewById(R.id.img_ctsp);
        txt_ten_ctsp=(TextView)findViewById(R.id.txt_tenctsp);
        txt_gia_ctsp=(TextView)findViewById(R.id.txt_gia_ctsp);
        txt_mota_ctsp=(TextView)findViewById(R.id.txt_mota_ctsp);
        spinner=(Spinner)findViewById(R.id.spinner);
        btn_giohang=(Button)findViewById(R.id.btn_giohang);
    }
}
