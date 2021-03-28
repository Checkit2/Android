package ir.Peaky.checkit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.utils.CustomEditText;

public class NewExperimentActivity extends AppCompatActivity {
    Window window;
    View view;
    AppCompatSpinner spinner;
    AppCompatImageView imageScan;
    CustomEditText edtAge;
    String age = "";
    RelativeLayout btnScan;
    private final int CODE_IMG_GALLERY = 1;
    private final String SAMPLE_CROP_IMG_NAME = "sampleCropImg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_experiment);
        statusbarColor();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        init();
        setSpinner();


        if (bundle != null) {
            if (bundle.getBoolean("gallery")) {
                startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"), CODE_IMG_GALLERY);


            } else if (bundle.getBoolean("camera")) {

            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


        edtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                age = edtAge.getText().toString();
                if (!age.isEmpty()) {
                    btnScan.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
                } else
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
        imageScan = findViewById(R.id.img_pic);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                startCrop(imageUri);
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri imageUriResultCrop = UCrop.getOutput(data);

            if (imageUriResultCrop != null) {
                imageScan.setImageURI(imageUriResultCrop);
            }
        }
    }

    private void startCrop(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROP_IMG_NAME;
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
       // uCrop.withAspectRatio(1, 1);
         uCrop.withAspectRatio(3,4);
        // uCrop.useSourceImageAspectRatio();
        // uCrop.withAspectRatio(16,9);
        uCrop.withMaxResultSize(800, 800);
        uCrop.withOptions(getCropOption());
        uCrop.start(NewExperimentActivity.this);
    }

    private UCrop.Options getCropOption() {
        UCrop.Options options = new UCrop.Options();

        //Compress type
        options.setCompressionQuality(70);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        //Ui
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        //Colors
        options.setStatusBarColor(getResources().getColor(R.color.white));
        options.setToolbarColor(getResources().getColor(R.color.white));
        options.setToolbarTitle("برش عکس");
        return options;
    }
}