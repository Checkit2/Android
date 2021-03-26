package ir.Peaky.checkit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.CustomEditText;

public class NewExperimentActivity extends AppCompatActivity {
    Window window;
    View view;
    AppCompatSpinner spinner;
    CustomEditText edtAge;
    String age = "";
    RelativeLayout btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_experiment);
        statusbarColor();
        init();
        setSpinner();
        edtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                age = edtAge.getText().toString();
                if (!age.isEmpty()) {
                    btnScan.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
                }else
                    btnScan.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_def));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void init() {
        spinner = findViewById(R.id.spinner);
        edtAge = findViewById(R.id.edt_age);
        btnScan = findViewById(R.id.btn_scan);
    }


    public void setSpinner() {
        String value[] = {"آزمایش ادرار"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(value));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout_style, arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
}