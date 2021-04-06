package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.utils.BoldTextView;
import ir.Peaky.checkit.utils.CustomEditText;
import ir.Peaky.checkit.utils.RegularTextView;
import ir.Peaky.checkit.webservice.Constants;

public class ReviewActivity extends AppCompatActivity {
    Window window;
    View view;
    RelativeLayout btn;
    int checkId;
    JSONObject jsonObject;
    CustomEditText editText1,editText2,editText3,editText4,editText5,editText6,
            editText7,editText8,editText9,editText10,editText11,editText12,editText13,
            editText14,editText15,editText16,editText17;
    BoldTextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,
            textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15
            ,textView16,textView17;
    List<String> keys=new ArrayList<String>();
    List<String> values=new ArrayList<String>();
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        statusbarColor();
        init();
        checkId=getIntent().getIntExtra("check_id",0);
        prefManager=new PrefManager(getApplicationContext());
        getItems();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.set(0,editText1.getText().toString());
                values.set(1,editText2.getText().toString());
                values.set(2,editText3.getText().toString());
                values.set(3,editText4.getText().toString());
                values.set(4,editText5.getText().toString());
                values.set(5,editText6.getText().toString());
                values.set(6,editText7.getText().toString());
                values.set(7,editText8.getText().toString());
                values.set(8,editText9.getText().toString());
                values.set(9,editText10.getText().toString());
                values.set(10,editText11.getText().toString());
                values.set(11,editText12.getText().toString());
                values.set(12,editText13.getText().toString());
                values.set(13,editText14.getText().toString());
                values.set(14,editText15.getText().toString());
                values.set(15,editText16.getText().toString());
                values.set(16,editText17.getText().toString());

                putValues();
            }
        });

    }
    public void statusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void init(){
        btn=findViewById(R.id.bottom_bar);
        editText1=findViewById(R.id.edt_value1);
        editText2=findViewById(R.id.edt_value2);
        editText3=findViewById(R.id.edt_value3);
        editText4=findViewById(R.id.edt_value4);
        editText5=findViewById(R.id.edt_value5);
        editText6=findViewById(R.id.edt_value6);
        editText7=findViewById(R.id.edt_value7);
        editText8=findViewById(R.id.edt_value8);
        editText9=findViewById(R.id.edt_value9);
        editText10=findViewById(R.id.edt_value10);
        editText11=findViewById(R.id.edt_value11);
        editText12=findViewById(R.id.edt_value12);
        editText13=findViewById(R.id.edt_value13);
        editText14=findViewById(R.id.edt_value14);
        editText15=findViewById(R.id.edt_value15);
        editText16=findViewById(R.id.edt_value16);
        editText17=findViewById(R.id.edt_value17);

        textView1=findViewById(R.id.txt_key1);
        textView2=findViewById(R.id.txt_key2);
        textView3=findViewById(R.id.txt_key3);
        textView4=findViewById(R.id.txt_key4);
        textView5=findViewById(R.id.txt_key5);
        textView6=findViewById(R.id.txt_key6);
        textView7=findViewById(R.id.txt_key7);
        textView8=findViewById(R.id.txt_key8);
        textView9=findViewById(R.id.txt_key9);
        textView10=findViewById(R.id.txt_key10);
        textView11=findViewById(R.id.txt_key11);
        textView12=findViewById(R.id.txt_key12);
        textView13=findViewById(R.id.txt_key13);
        textView14=findViewById(R.id.txt_key14);
        textView15=findViewById(R.id.txt_key15);
        textView16=findViewById(R.id.txt_key16);
        textView17=findViewById(R.id.txt_key17);



    }

    public void getItems(){
        try {

            jsonObject=new JSONObject(getIntent().getStringExtra("data1"));
            JSONArray keyJsonArray=jsonObject.getJSONArray("keys");
            JSONArray valueJsonArray=jsonObject.getJSONArray("values");
            for (int i=0;i<keyJsonArray.length();i++){
                keys.add(keyJsonArray.getString(i));
            }
            for (int i=0;i<valueJsonArray.length();i++){
                values.add(valueJsonArray.getString(i));
            }
           checkFunction(0,textView1,editText1);
           checkFunction(1,textView2,editText2);
           checkFunction(2,textView3,editText3);
           checkFunction(3,textView4,editText4);
           checkFunction(4,textView5,editText5);
           checkFunction(5,textView6,editText6);
           checkFunction(6,textView7,editText7);
           checkFunction(7,textView8,editText8);
           checkFunction(8,textView9,editText9);
           checkFunction(9,textView10,editText10);
           checkFunction(10,textView11,editText11);
           checkFunction(11,textView12,editText12);
           checkFunction(12,textView13,editText13);
           checkFunction(13,textView14,editText14);
           checkFunction(14,textView15,editText15);
           checkFunction(15,textView16,editText16);
           checkFunction(16,textView17,editText17);

           // Log.e("",keyJsonArray.toString());
        } catch (JSONException e) {
          //  e.printStackTrace();
        }
    }

    public void putValues(){
        JSONArray jsonArray=new JSONArray(keys);
        JSONArray jsonArray1=new JSONArray(values);
        Map<String,JSONArray> params=new HashMap<>();
        params.put("keys",jsonArray);
        params.put("values",jsonArray1);
        JSONObject jsonObject=new JSONObject(params);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.PUT, Constants.PUT_URL
                +checkId,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
          //      Log.e("",response.toString());
                Log.e("","");
                try {
                    JSONObject data=new JSONObject(String.valueOf(response.getJSONObject("data")));
                    JSONObject data1=new JSONObject(String.valueOf(data.getJSONObject("data")));
                    JSONObject checkResult=new JSONObject(String.valueOf(data1.getJSONObject("check_result")));
                    String analysis=checkResult.getString("analysis");
                    Intent intent=new Intent(ReviewActivity.this,AnalysisActivity.class);
                    intent.putExtra("analysis",analysis);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Log.e("", error.getMessage());
                Toast.makeText(ReviewActivity.this, "مشکلی پیش آمده لطفا مجددا تلاش کنید", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjectRequest);



    }



    public void checkFunction(int i, BoldTextView textView,CustomEditText editText){

        if (keys.size()>i) {
            if (!keys.get(i).isEmpty() || !values.get(i).isEmpty()) {
                textView.setText(keys.get(i));
                editText.setText(values.get(i));

            }
        }else {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
        }
    }

    public void setValues(int i,CustomEditText editText){



    }







}