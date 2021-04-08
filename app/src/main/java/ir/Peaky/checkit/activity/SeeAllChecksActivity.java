package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.adapter.ChecksAdapter;
import ir.Peaky.checkit.model.CheckModel;

public class SeeAllChecksActivity extends AppCompatActivity {
    JSONArray jsonArray;
    private List<CheckModel> checksList;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    AppCompatImageView iconBack;
    Window window;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_checks);
        init();
        statusbarColor();
        checksList = new ArrayList<>();
        mAdapter = new ChecksAdapter(getApplicationContext(),checksList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        getAllChecks();
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void statusbarColor(){
        if (Build.VERSION.SDK_INT>=21){
            window=this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void init(){
        recyclerView=findViewById(R.id.recycler);
        iconBack=findViewById(R.id.icon_back);
    }


    public void getAllChecks(){
        try {
            jsonArray=new JSONArray(getIntent().getStringExtra("dataArray"));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                CheckModel checkModel=new CheckModel();
                checkModel.setCheck_id(jsonObject.getInt("check_id"));
                checkModel.setCheck_name(jsonObject.getString("check_name"));
                checkModel.setCheck_result(jsonObject.getJSONObject("check_result"));
                checksList.add(checkModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

    }
}