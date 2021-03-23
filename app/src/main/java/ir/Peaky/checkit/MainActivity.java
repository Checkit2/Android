package ir.Peaky.checkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import ir.Peaky.checkit.activity.NewExperimentActivity;

public class MainActivity extends AppCompatActivity {
    Window window;
    View view;
    AppCompatImageView iconImagePicker,iconMenu;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusbarColor();
        init();

        iconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.help:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.about_us:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });


        iconImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.SheetDialog);
                View sheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout,
                        findViewById(R.id.bottom_sheet));
                bottomSheetDialog.setContentView(sheetView);
                LinearLayout linCamera=sheetView.findViewById(R.id.lin_camera);
                LinearLayout linGallery=sheetView.findViewById(R.id.lin_gallery);
                linCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), NewExperimentActivity.class);
                        intent.putExtra("camera",true);
                        startActivity(intent);
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