package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.webservice.Constants;

public class LoadingActivity extends AppCompatActivity {
    Window window;
    View view;
    String imageUrl,checkTitle=null;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        statusbarColor();
        prefManager=new PrefManager(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            imageUrl=bundle.getString("imageUrl");
            checkTitle=bundle.getString("checkName");
        }

    }
    public void statusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void newCheck(){
        Map<String,String> params=new HashMap<>();
        params.put("user", String.valueOf(prefManager.getUserId()));
        params.put("check_name",checkTitle);
        params.put("image_url",imageUrl);
        JSONObject parameters=new JSONObject(params);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Constants.NEW_CHECK_URL,
                parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

}