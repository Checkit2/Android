package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import ir.Peaky.checkit.MainActivity;
import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.RegularTextView;

public class AnalysisActivity extends AppCompatActivity {
    Window window;
    View view;
    RegularTextView analysisTxt;
    AppCompatImageView iconClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Bundle extras = getIntent().getExtras();
        String analysis = "مشکلی پیش آمده";
        statusbarColor();
        if (extras != null) {
            analysis = extras.getString("analysis");
        }
        init();
        analysisTxt.setText(analysis);
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}

