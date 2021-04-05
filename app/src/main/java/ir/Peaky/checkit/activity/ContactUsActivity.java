package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.RegularTextView;

public class ContactUsActivity extends AppCompatActivity {

    AppCompatImageView iconBack;
    Window window;
    View view;
    RegularTextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
        statusbarColor();


        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "sup.checkit@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "اپلیکیشن چک کیت");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "body");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "اپلیکیشن چک کیت"));
            }
        });
    }

    private void init() {

        iconBack=findViewById(R.id.icon_back);
        mail=findViewById(R.id.mail);

    }

    public void statusbarColor(){
        if (Build.VERSION.SDK_INT>=21){
            window=this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}