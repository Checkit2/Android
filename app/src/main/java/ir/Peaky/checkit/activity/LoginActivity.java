package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.Peaky.checkit.MainActivity;
import ir.Peaky.checkit.R;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.utils.CustomEditText;
import ir.Peaky.checkit.utils.RegularTextView;
import ir.Peaky.checkit.webservice.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Window window;
    View view;
    CustomEditText edtPhoneNumber;
    RelativeLayout relButton;
    RelativeLayout relError;
    String txtPhoneNumber = "";
    AppCompatCheckBox checkBox;
    PrefManager prefManager;
    RegularTextView txtRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        statusbarColor();
        init();
        prefManager = new PrefManager(getApplicationContext());
        checkBox.setChecked(false);
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPhoneNumber = String.valueOf(edtPhoneNumber.getText());
                if ((txtPhoneNumber.length() == 11) && (checkBox.isChecked())) {
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));

                } else
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_def));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        relButton.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        txtRules.setOnClickListener(this);


    }

    private void init() {
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        relButton = findViewById(R.id.button);
        relError = findViewById(R.id.rel_error);
        checkBox = findViewById(R.id.checkbox);
        txtRules=findViewById(R.id.rules);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if ((txtPhoneNumber == null) || !txtPhoneNumber.matches("(\\+98|0)?9\\d{9}")) {
                    edtPhoneNumber.setBackground(getResources().getDrawable(R.drawable.input_error));
                    relError.setVisibility(View.VISIBLE);
                    break;

                } else if (!checkBox.isChecked()) {
                    Toast.makeText(this, "لطفا قوانین و مقررات خوانده و قبول نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    edtPhoneNumber.setBackground(getResources().getDrawable(R.drawable.custom_input));
                    relError.setVisibility(View.INVISIBLE);
                    register();
                }
                break;
            case R.id.checkbox:
                if (checkBox.isChecked() && ((txtPhoneNumber.length() == 11))) {
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));

                } else
                    relButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_def));
            case R.id.rules:
                String url = Constants.BASE_URL+"privacy";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
        }


    }

    public void register() {
        prefManager.setPhoneNumber(txtPhoneNumber);
        Map<String,String> params=new HashMap<>();
        params.put("phone",prefManager.getPhoneNumber());
        JSONObject parameters=new JSONObject(params);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Constants.REGISTER_URL,
                parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code")==201 || response.getInt("code")==200){
                        prefManager.setLogin(true);
                        JSONObject dataJsonObject=new JSONObject(String.valueOf(response.getJSONObject("data")));
                        prefManager.setUserId(dataJsonObject.getInt("user_id"));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                        Toast.makeText(LoginActivity.this, "مشکلی پیش آمده لطفا بعدا تلاش نمایید!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                 //   e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "مشکلی پیش آمده لطفا بعدا تلاش نمایید!", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}
