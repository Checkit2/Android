package ir.Peaky.checkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.Peaky.checkit.activity.AboutUsActivity;
import ir.Peaky.checkit.activity.ContactUsActivity;
import ir.Peaky.checkit.activity.NewExperimentActivity;
import ir.Peaky.checkit.activity.SplashActivity;
import ir.Peaky.checkit.adapter.ChecksAdapter;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.model.CheckModel;
import ir.Peaky.checkit.utils.ApplicationManager;
import ir.Peaky.checkit.utils.RegularTextView;
import ir.Peaky.checkit.webservice.Constants;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;
    Window window;
    View view;
    RelativeLayout relGotIt,relBottom;
    AppCompatImageView iconImagePicker,iconMenu;
    RegularTextView txtVersionName;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    PrefManager prefManager;
    RecyclerView recyclerView;
    private BottomSheetDialog bottomSheetDialog;
    private List<CheckModel> checksList;
    RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusbarColor();
        init();
        prefManager=new PrefManager(getApplicationContext());
        checksList = new ArrayList<>();
        mAdapter = new ChecksAdapter(getApplicationContext(),checksList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        getChecks();





        iconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        txtVersionName.setText(ApplicationManager.getVersionName(MainActivity.this));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.help:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.about_us:
                        Intent intent=new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contact_us:
                        Intent intent1=new Intent(getApplicationContext(), ContactUsActivity.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });


        iconImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.flash_dialog);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.shape_dialog));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                relGotIt = dialog.findViewById(R.id.rel_try);
                relGotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent=new Intent(getApplicationContext(), NewExperimentActivity.class);
                        intent.putExtra("camera",true);
                        startActivity(intent);
                    }
                });






                bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.SheetDialog);
                View sheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout,
                        findViewById(R.id.bottom_sheet));
                bottomSheetDialog.setContentView(sheetView);
                LinearLayout linCamera=sheetView.findViewById(R.id.lin_camera);
                LinearLayout linGallery=sheetView.findViewById(R.id.lin_gallery);
                linCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dexter.withContext(MainActivity.this).withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                // check if all permissions are granted
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {

                                    // do you work now
                                    dialog.show();


                                }

                                // check for permanent denial of any permission
                                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                    // permission is denied permenantly, navigate user to app settings
                                    Toast.makeText(MainActivity.this, "برای گرفتن عکس نیاز به دسترسی دوربین است در غیر این صورت عکس را از گالری انتخاب کنید", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();

                            }
                        }).onSameThread().check();

                    }
                });
                linGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), NewExperimentActivity.class);
                        intent.putExtra("gallery",true);
                        startActivity(intent);
                    }
                });

                bottomSheetDialog.show();

            }
        });

    }
    public void init(){
        iconImagePicker=findViewById(R.id.icon_camera);
        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);
        iconMenu=findViewById(R.id.icon_menu);
        txtVersionName=findViewById(R.id.txt_version_name);
        recyclerView=findViewById(R.id.recycler);
        relBottom=findViewById(R.id.rel_bottom);
    }



    public void statusbarColor(){
        if (Build.VERSION.SDK_INT>=21){
            window=this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    public void getChecks(){




        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Constants.GET_CHECKS_URL
                +prefManager.getUserId(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("","");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    try {
                        JSONArray dataJsonArray=response.getJSONArray("data");
                        if (dataJsonArray.length()>0){
                            relBottom.setVisibility(View.VISIBLE);
                        }else
                            relBottom.setVisibility(View.GONE);
                        Log.e("", String.valueOf(dataJsonArray.length()));
                      //  if (dataJsonArray.length()>0){
                            for (int i=0;i<=3;i++){
                                JSONObject jsonObject=dataJsonArray.getJSONObject(i);

                                CheckModel checkModel=new CheckModel();
                                checkModel.setCheck_id(jsonObject.getInt("check_id"));
                                checkModel.setCheck_name(jsonObject.getString("check_name"));
                                checkModel.setCheck_result(jsonObject.getJSONObject("check_result"));
                                checksList.add(checkModel);
                            }

                      //  }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("","");
            }
        });
            Volley.newRequestQueue(this).add(jsonObjectRequest);


    }


}