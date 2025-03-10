package ir.Peaky.checkit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.config.PrefManager;
import ir.Peaky.checkit.utils.CustomEditText;
import ir.Peaky.checkit.webservice.Constants;
import ir.Peaky.checkit.webservice.VolleyMultipartRequest;

import static androidx.core.content.FileProvider.getUriForFile;

public class NewExperimentActivity extends AppCompatActivity {
    Window window;
    View view;
    AppCompatSpinner spinner;
    AppCompatImageView imageScan, closeIcon;
    CustomEditText edtAge,edtTitle;
    String age = "",checkTitle=null;
    RelativeLayout btnScan,relVis;
    private final int CODE_IMG_GALLERY = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    private final String SAMPLE_CROP_IMG_NAME = "androidimg";
    public static String fileName;
    private int IMAGE_COMPRESSION = 100;
    Uri imageUriResultCrop;
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    String imageUrl="";
    Bitmap bitmap;
    PrefManager prefManager;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_experiment);
        statusbarColor();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        init();
        setSpinner();
        prefManager=new PrefManager(getApplicationContext());
        relVis.setVisibility(View.GONE);


        if (bundle != null) {
            if (bundle.getBoolean("gallery")) {
                startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"), CODE_IMG_GALLERY);


            } else if (bundle.getBoolean("camera")) {
                takeCameraImage();
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
                checkTitle=edtTitle.getText().toString();
                if (!age.isEmpty()) {
                    relVis.setVisibility(View.VISIBLE);
                    Toast.makeText(NewExperimentActivity.this, "لطفا کمی صبرکنید", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(NewExperimentActivity.this, imageUriResultCrop.toString(), Toast.LENGTH_SHORT).show();
                      uploadBitmap(bitmap);

                }
            }
        });


        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void init() {
        spinner = findViewById(R.id.spinner);
        edtAge = findViewById(R.id.edt_age);
        btnScan = findViewById(R.id.btn_scan);
        imageScan = findViewById(R.id.img_pic);
        closeIcon = findViewById(R.id.icon_close);
        edtTitle=findViewById(R.id.edt_title);
        relVis=findViewById(R.id.rel_vis);
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
             imageUri = data.getData();
            if (imageUri != null) {
                // if need crop image from gallery use startCrop function
                startCrop(imageUri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    imageScan.setImageBitmap(bitmap);

                } catch (IOException e) {
                //    e.printStackTrace();
                }

            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            imageUriResultCrop = UCrop.getOutput(data);

            if (imageUriResultCrop != null) {
                imageScan.setImageURI(imageUriResultCrop);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriResultCrop);

                } catch (IOException e) {
              //      e.printStackTrace();
                }

            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            cropImage(getCacheImagePath(fileName));
        } else
            finish();
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);

        options.withAspectRatio(3, 4);
        options.withMaxResultSize(1100, 1100);

        options.setFreeStyleCropEnabled(true);
        options.setToolbarTitle("ویرایش عکس");


        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(1100, 1100);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }


    private void startCrop(@NonNull Uri uri) {

        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        fileName = SAMPLE_CROP_IMG_NAME + timeStamp;
        fileName += ".jpg";
      /*  UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), fileName)));
        // uCrop.withAspectRatio(1, 1);
        uCrop.withAspectRatio(3, 4);
        // uCrop.useSourceImageAspectRatio();
        // uCrop.withAspectRatio(16,9);
        uCrop.withMaxResultSize(1100, 1100);
        uCrop.withOptions(getCropOption());
        uCrop.start(NewExperimentActivity.this);*/
    }

    private UCrop.Options getCropOption() {
        UCrop.Options options = new UCrop.Options();

        //Compress type
        options.setCompressionQuality(IMAGE_COMPRESSION);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        //Ui
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        //Colors
        options.setStatusBarColor(getResources().getColor(R.color.white));
        options.setToolbarColor(getResources().getColor(R.color.white));
        options.setToolbarTitle("ویرایش عکس");
        return options;
    }

    private void takeCameraImage() {
        fileName = System.currentTimeMillis() + ".jpg";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(NewExperimentActivity.this, getPackageName() + ".provider", image);
    }

    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {



        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.UPLOAD_IMAGE_URL +
                prefManager.getUserId(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            imageUrl=obj.getString("data");
                            int t=obj.getInt("code");
                            Intent intent1=new Intent(NewExperimentActivity.this,LoadingActivity.class);
                            intent1.putExtra("imageUrl",imageUrl);
                            intent1.putExtra("checkName",checkTitle);
                            startActivity(intent1);
                        } catch (JSONException e) {

                     //       e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewExperimentActivity.this, "لطفا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();

            }
        }) {

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("file", new DataPart(fileName, getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        relVis.setVisibility(View.GONE);
    }
}