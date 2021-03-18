package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.CustomEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Window window;
    View view;
    CustomEditText edtPhoneNumber;
    RelativeLayout relButton;
    RelativeLayout relError;
    String txtPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        statusbarColor();
        init();
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPhoneNumber= String.valueOf(edtPhoneNumber.getText());
                //.makeText(LoginActivity.this, "text", Toast.LENGTH_SHORT).show();
                if (txtPhoneNumber.length()==11){
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));

                }else
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_def));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        relButton.setOnClickListener(this);





    }
    private void init(){
        edtPhoneNumber=findViewById(R.id.edt_phone_number);
        relButton=findViewById(R.id.button);
        relError=findViewById(R.id.rel_error);
    }
    public void statusbarColor(){
        if (Build.VERSION.SDK_INT>=21){
            window=this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if (!txtPhoneNumber.matches("(\\+98|0)?9\\d{9}")){
                    edtPhoneNumber.setBackground(getResources().getDrawable(R.drawable.input_error));
                    relError.setVisibility(View.VISIBLE);

            }else{
                    edtPhoneNumber.setBackground(getResources().getDrawable(R.drawable.custom_input));
                    relError.setVisibility(View.INVISIBLE);
                }
                break;


        }
    }
}