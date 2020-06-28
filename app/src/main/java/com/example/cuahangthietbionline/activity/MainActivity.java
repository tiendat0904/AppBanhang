package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.LoaispAdapter;
import com.example.cuahangthietbionline.adapter.SanphamAdapter;
import com.example.cuahangthietbionline.model.Giohang;
import com.example.cuahangthietbionline.model.Loaisp;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.until.CheckConnection;
import com.example.cuahangthietbionline.until.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recycleView_mhc;
    NavigationView navigationView;
    ListView listView_mhc;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    ArrayList<Sanpham> mangsp;
    SanphamAdapter sanphamAdapter;
    LoaispAdapter loaispAdapter ;
    int id=0;
    String tenloaisp="";
    String imgloaisp="";
    public static  ArrayList<Giohang> array_giohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFliper();
            Getdulieu_loaisp();
            Getdulieu_sanphammoinhat();
            CatchOnItemListview();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra lai ket noi");
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

    private void CatchOnItemListview() {
        listView_mhc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent =new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent =new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent =new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent =new Intent(MainActivity.this,LienheActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent =new Intent(MainActivity.this,ThongtinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"ban hay kiem tra ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieu_sanphammoinhat() {
         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String Tensp = "";
                    Integer Giassp=0;
                    String IMGsp= "";
                    String motasp="";
                    int ID_sanpham = 0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject  = response.getJSONObject(i);
                            ID =jsonObject.getInt("id");
                            Tensp= jsonObject.getString("tensp");
                            Giassp = jsonObject.getInt("giasp");
                            IMGsp = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("motasp");
                            ID_sanpham= jsonObject.getInt("idsanpham");
                            mangsp.add(new Sanpham(ID,Tensp,Giassp,IMGsp,motasp,ID_sanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieu_loaisp() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisp");
                            imgloaisp=jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,imgloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Liên Hệ","https://png.pngtree.com/png-clipart/20190619/original/pngtree-vector-connected-to-cloud-icon-png-image_3997327.jpg"));
                    mangloaisp.add(4,new Loaisp(0,"Thông Tin","https://cdn.pixabay.com/photo/2013/11/21/07/30/icon-214446_960_720.jpg"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFliper() {
        ArrayList<String> mangquangcao =new ArrayList<>();
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.0-9/87261739_1464273280403033_1638399284619509760_n.jpg?_nc_cat=104&_nc_sid=85a577&_nc_ohc=VrxcJ-FXZtQAX9mgWrr&_nc_ht=scontent.fhan2-4.fna&oh=99d59e011706ee14208e2aaa16cbaee7&oe=5EFB1BF8");
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.0-9/51608107_1160379044125793_1266266533361876992_n.jpg?_nc_cat=100&_nc_sid=7aed08&_nc_ohc=WQQ6YcQCSjwAX9uuQEr&_nc_ht=scontent.fhan2-4.fna&oh=8747b09d4b9119fed0a4c64d6c52735b&oe=5EB85668");
        mangquangcao.add("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/p960x960/41413832_1060032857493746_8796991933093249024_o.jpg?_nc_cat=111&_nc_sid=7aed08&_nc_ohc=AFGuj-9EZ04AX-deYU6&_nc_ht=scontent.fhan2-3.fna&_nc_tp=6&oh=4c419fc6f40048eca06aa29f7d43fbaf&oe=5EF92257");
        mangquangcao.add("https://scontent.fhan2-3.fna.fbcdn.net/v/t31.0-8/p720x720/28514625_925036617660038_3192132735666834543_o.jpg?_nc_cat=111&_nc_sid=7aed08&_nc_ohc=S2mx0BsJG2kAX_-vu41&_nc_ht=scontent.fhan2-3.fna&_nc_tp=6&oh=8a4255d0d62b5098488c0c9aa12041fc&oe=5EB6FAB8");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView= new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar=(Toolbar)findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewlipper);
        recycleView_mhc=(RecyclerView)findViewById(R.id.recyclerview);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        listView_mhc=(ListView)findViewById(R.id.listview_mhc);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mangloaisp= new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chủ","https://image.flaticon.com/icons/png/512/69/69524.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView_mhc.setAdapter(loaispAdapter);
        mangsp = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsp);
        recycleView_mhc.setHasFixedSize(true); //toi uu kich thuoc
        recycleView_mhc.setLayoutManager(new GridLayoutManager(getApplicationContext(),2)); //chia lam 2 cot
        recycleView_mhc.setAdapter(sanphamAdapter);
        if(array_giohang != null){

        }else{
            array_giohang = new ArrayList<>();
        }
    }
}
