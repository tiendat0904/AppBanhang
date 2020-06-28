package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.LaptopAdapter;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.until.CheckConnection;
import com.example.cuahangthietbionline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbar_lap;
    ListView lv_lap;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> mang_laptop;
    int id_lap= 0;
    int page = 1;
    View footerview;
    boolean insLoading = false;
    LaptopActivity.mHandler mHandler;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIDloaisp();
            ActionToobar();
            Getdata(page);
            Loadmoredata();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"bạn hãy kiểm tra kết nối");
            finish();
        }

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

    private void Loadmoredata() {
        lv_lap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChitietSanpham.class);
                intent.putExtra("thongtinsanpham",mang_laptop.get(position));
                startActivity(intent);
            }
        });
        lv_lap.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount !=0 && insLoading== false && limitdata==false){
                    insLoading=true;
                    Threaddata threaddata = new Threaddata();
                    threaddata.start();

                }
            }
        });
    }

    private void Getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanlaptop+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenlap="";
                int Gialap=0;
                String imglap="";
                String motalap="";
                int idsp_lap=0;
                if(response != null && response.length() !=2){
                    lv_lap.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenlap = jsonObject.getString("tensp");
                            Gialap = jsonObject.getInt("giasp");
                            imglap= jsonObject.getString("hinhanhsp");
                            motalap = jsonObject.getString("motasp");
                            idsp_lap= jsonObject.getInt("idsanpham");
                            mang_laptop.add(new Sanpham(id,tenlap,Gialap,imglap,motalap,idsp_lap));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitdata = true;
                    lv_lap.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("ID_sanpham",String.valueOf(id_lap));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToobar() {
        setSupportActionBar(toolbar_lap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_lap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIDloaisp() {
        id_lap= getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisp",id_lap+"");

    }

    private void Anhxa() {
        toolbar_lap= (Toolbar)findViewById(R.id.toolbar_laptop);
        lv_lap = (ListView)findViewById(R.id.listview_laptop);
        mang_laptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),mang_laptop);
        lv_lap.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lv_lap.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    insLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class Threaddata extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
