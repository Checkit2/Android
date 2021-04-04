package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.Peaky.checkit.R;

public class ReviewActivity extends AppCompatActivity {
    Window window;
    View view;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        statusbarColor();
        getItems();

    }
    public void statusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    public void getItems(){
        try {
            jsonObject=new JSONObject(getIntent().getStringExtra("data"));
            JSONArray keyJsonArray=jsonObject.getJSONArray("keys");
            JSONArray valueJsonArray=jsonObject.getJSONArray("values");
            Log.e("",keyJsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }









}