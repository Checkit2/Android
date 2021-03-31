package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ir.Peaky.checkit.MainActivity;
import ir.Peaky.checkit.R;
import ir.Peaky.checkit.adapter.IntroViewPagerAdapter;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.model.IntroItem;
import ir.Peaky.checkit.utils.BoldTextView;

public class IntroActivity extends AppCompatActivity {
    Window window;
    View view;
    ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    RelativeLayout btnNext;
    RelativeLayout btnContinue;
    BoldTextView txtNext;
    int position = 0;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        statusbarColor();
        init();
        prefManager=new PrefManager(getApplicationContext());
        List<IntroItem> mList = new ArrayList<>();
        mList.add(new IntroItem(getResources().getString(R.string.intro_title1),
                getResources().getString(R.string.intro_description1), R.drawable.intro_img1));

        mList.add(new IntroItem(getResources().getString(R.string.intro_title2),
                getResources().getString(R.string.intro_description2), R.drawable.intro_img2));

        mList.add(new IntroItem(getResources().getString(R.string.intro_title3),
                getResources().getString(R.string.intro_description3), R.drawable.intro_img3));

        introViewPagerAdapter = new IntroViewPagerAdapter(IntroActivity.this, mList);
        screenPager.setAdapter(introViewPagerAdapter);
        tabIndicator.setupWithViewPager(screenPager);
        tabIndicator.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);

                    if (position == mList.size()-1) {
                        //  txtNext.setText("ادامه");
                        btnNext.setVisibility(View.GONE);
                        btnContinue.setVisibility(View.VISIBLE);
                       btnContinue.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               prefManager.setFirstTimeLaunch(true);
                               if (!prefManager.isLogin()){
                                   Intent intent=new Intent(IntroActivity.this, LoginActivity.class);
                                   startActivity(intent);
                                   finish();
                               }
                           }
                       });
                    }
                } else if (position <= 2) {
                    //      txtNext.setText("بعدی");
                       btnNext.setVisibility(View.VISIBLE);
                      btnContinue.setVisibility(View.GONE);
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() -1) {
                    //   txtNext.setText("ادامه");
                    btnNext.setVisibility(View.GONE);
                    btnContinue.setVisibility(View.VISIBLE);
                    btnContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            prefManager.setFirstTimeLaunch(true);
                            if (!prefManager.isLogin()){
                                Intent intent=new Intent(IntroActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        }
                    });
                } else {
                    //     txtNext.setText("بعدی");
                    btnNext.setVisibility(View.VISIBLE);
                    btnContinue.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void init() {
        screenPager = findViewById(R.id.viewpager);
        tabIndicator = findViewById(R.id.tab_layout);
        btnNext = findViewById(R.id.btn_go);
        txtNext = findViewById(R.id.txt_next);
        btnContinue = findViewById(R.id.btn_start);
    }

    public void statusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.white));
            view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}