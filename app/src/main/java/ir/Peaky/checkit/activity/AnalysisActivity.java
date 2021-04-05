package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import ir.Peaky.checkit.MainActivity;
import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.RegularTextView;

public class AnalysisActivity extends AppCompatActivity {
    Window window;
    View view;
    RegularTextView analysisTxt;
    AppCompatImageView iconClose;
    JSONObject jsonObject;
    String analysis = "مشکلی پیش آمده";
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Bundle extras = getIntent().getExtras();

        statusbarColor();
        flag=extras.getBoolean("flag");
        if (extras != null) {
            analysis = extras.getString("analysis");
        }
        init();
        getAnalysis();
        analysisTxt.setText(analysis);
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    finish();
                }else {
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void init() {
        analysisTxt=findViewById(R.id.txt_analysis);
        iconClose=findViewById(R.id.icon_close);
    }

    public void statusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void getAnalysis(){

        if (flag) {

            try {
                jsonObject = new JSONObject(getIntent().getStringExtra("check_result"));
                analysis = jsonObject.getString("analysis");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onBackPressed() {

        if (flag){
            finish();
        }else {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

