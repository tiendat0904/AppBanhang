package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.until.CheckConnection;
import com.example.cuahangthietbionline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {

    EditText editTextTenkhachhang,editTextSodienthoai,editTextEmail;
    Button btnxacnhan,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            EventButton();
        }else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = editTextTenkhachhang.getText().toString().trim();
                final String sdt = editTextSodienthoai.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                if(ten.length()>0 && sdt.length()>0 && email.length()>0)
                {
                    RequestQueue requestQueue  = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("MadonHang",madonhang);
                            if(Integer.parseInt(madonhang)>0)
                            {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request =  new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("aaaaaaaa",response);
                                        if(response.equals("1"))
                                        {
                                            MainActivity.array_giohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(Thongtinkhachhang.this,MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }
                                        else{
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng bị lỗi ");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i =0;i<MainActivity.array_giohang.size();i++)
                                        {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.array_giohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.array_giohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.array_giohang.get(i).getGisp());
                                                jsonObject.put("soluongsanpham",MainActivity.array_giohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap  = new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        editTextTenkhachhang = findViewById(R.id.edit_tenkh);
        editTextSodienthoai = findViewById(R.id.edit_sdt);
        editTextEmail = findViewById(R.id.edit_email);
        btnxacnhan = findViewById(R.id.btn_xacnhan);
        btntrove = findViewById(R.id.btn_trove);
    }
}
