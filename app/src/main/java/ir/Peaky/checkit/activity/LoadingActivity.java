package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
import ir.Peaky.checkit.utils.BoldTextView;
import ir.Peaky.checkit.webservice.Constants;

public class LoadingActivity extends AppCompatActivity {
    Window window;
    View view;
    String imageUrl,checkTitle=null;
    BoldTextView cancelTxt;
    PrefManager prefManager;
    Dialog dialog;
    RelativeLayout relYes,relNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        statusbarColor();
        init();
        prefManager=new PrefManager(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

            imageUrl=bundle.getString("imageUrl");
            if (bundle.getString("checkName")==null || bundle.getString("checkName")==""
            || bundle.getString("checkName").isEmpty()) {
                checkTitle = null;
            }else
                checkTitle=bundle.getString("checkName");

        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        newCheck();






    }

    private void init() {
        cancelTxt=findViewById(R.id.cancel);
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

                try {
                    JSONObject dataJsonObject=response.getJSONObject("data");
       //             Log.e("",dataJsonObject.toString());
                    Intent intent=new Intent(getApplicationContext(),ReviewActivity.class);
                    intent.putExtra("data1",dataJsonObject.toString());
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
         //           Log.e("",e.getMessage());
          //          e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
   //             Log.e("",error.getMessage());

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

    }

    @Override
    public void onBackPressed() {

        showDialog();
    }

    private void showDialog() {
        dialog = new Dialog(LoadingActivity.this);
        dialog.setContentView(R.layout.cancel_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.shape_dialog));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        relYes=dialog.findViewById(R.id.rel_yes);
        relNo=dialog.findViewById(R.id.rel_no);
        relYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        relNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}