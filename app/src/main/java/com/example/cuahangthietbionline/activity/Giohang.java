    package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.GiohangAdapter;
import com.example.cuahangthietbionline.until.CheckConnection;

import java.text.DecimalFormat;

    public class Giohang extends AppCompatActivity {
    ListView lv_giohang;
    TextView txt_thongbao;
    static TextView txt_tongtien;
    Button btn_thanhtoan,btn_tieptucmua;
    Toolbar toolbar_giohang;
    GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolBar();
        Checkdata();
        EventUntil();
        CatchOnItemListView();
        EventButton();
    }

        private void EventButton() {
        btn_tieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.array_giohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"giỏ hàng của bạn chưa có sản phẩm để thanh toán");

                }
            }
        });
        }

        private void CatchOnItemListView() {
        lv_giohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.array_giohang.size() <=0){
                            txt_thongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.array_giohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUntil();
                            if(MainActivity.array_giohang.size()<=0){
                                txt_thongbao.setVisibility(View.VISIBLE);
                            }else{
                                txt_thongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUntil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUntil();
                    }
                });
                builder.show();
                return true;
            }
        });
        }

        public static void EventUntil() {
        long tongtien = 0;
        for(int i = 0 ;i<MainActivity.array_giohang.size();i++){
            tongtien += MainActivity.array_giohang.get(i).getGisp();
        }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txt_tongtien.setText(decimalFormat.format(tongtien)+"Đ");
        }

        private void Checkdata() {
            if(MainActivity.array_giohang.size() <=0){
                giohangAdapter.notifyDataSetChanged();
                txt_thongbao.setVisibility(View.VISIBLE);
                lv_giohang.setVisibility(View.INVISIBLE);
            }else{
                giohangAdapter.notifyDataSetChanged();
                txt_thongbao.setVisibility(View.INVISIBLE);
                lv_giohang.setVisibility(View.VISIBLE);
            }
        }

        private void ActionToolBar() {
            setSupportActionBar(toolbar_giohang);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar_giohang.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        private void Anhxa() {
            lv_giohang = (ListView)findViewById(R.id.list_giohang);
            txt_thongbao= (TextView)findViewById(R.id.txt_thongbao);
            txt_tongtien= (TextView)findViewById(R.id.tongtien);
            btn_thanhtoan= (Button)findViewById(R.id.btn_thanhtoangiohang);
            btn_tieptucmua = (Button)findViewById(R.id.btn_tieptucmuahang);
            toolbar_giohang = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar_giohang);
            giohangAdapter = new GiohangAdapter(Giohang.this,MainActivity.array_giohang);
            lv_giohang.setAdapter(giohangAdapter);

        }
    }
