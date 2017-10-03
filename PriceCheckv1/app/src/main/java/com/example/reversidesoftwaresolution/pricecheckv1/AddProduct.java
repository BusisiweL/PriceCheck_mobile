package com.example.reversidesoftwaresolution.pricecheckv1;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {
    private EditText etProName, etProDescription, etProCategory, etProPrice;


    

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private Button btnUpload;
    final int REQUEST_CODE_GALLERY = 999;
    private ImageView image1V;
    private Uri uri;
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        imgDecodableString = null;
        image1V = (ImageView) findViewById(R.id.image1V);
        btnUpload = (Button)findViewById(R.id.button7);

    }

    public void click(View v){


        ActivityCompat.requestPermissions(
                AddProduct.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode ==RESULT_LOAD_IMG  && resultCode == RESULT_OK && null != data) {

                uri = data.getData();
                Uri selectedImage = data.getData();
                System.out.print("selected img " + selectedImage.getPath());

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                InputStream inputStream = getContentResolver().openInputStream(uri);

                image1V = (ImageView) findViewById(R.id.image1V);
                etProName = (EditText) findViewById(R.id.etProName);
                etProDescription = (EditText) findViewById(R.id.etProDescription);
                etProCategory = (EditText) findViewById(R.id.etProCategory);
                etProPrice = (EditText) findViewById(R.id.etProPrice);

                System.out.println("pre..." + filePathColumn[0]);


                System.out.println(filePathColumn[0] + " post.....");
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                imgDecodableString = cursor.getString(columnIndex);

                image1V.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                System.out.println("IMG DECODABLE..." + imgDecodableString);
                cursor.close();

                Toast.makeText(this, "Correct!!", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            System.out.print("Message : ");
            Toast.makeText(this, "Something went wrong " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }
    //Upload To Database
    public void cli(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PostRequest();
            }
        }).start();
    }
    // Back Button
    public void BtnBack (View view)
    {
        Intent intent = new Intent(AddProduct.this, MainActivity.class);
        startActivity(intent);
    }

    private void PostRequest() {
        Log.d("OKHTTP3" ,"Post function called!");

        String data = "";
        String name= "";
        OkHttpClient client=new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json");
        JSONObject actualData =new JSONObject();

        try{
            Log.d("OKHTTP3" ,"inside function called!  "+imgDecodableString);
            byte[] imgData = imageViewToByte();


            Stock stock = new Stock(etProName.getText().toString(),
            etProDescription.getText().toString(),
            imgData,
            etProCategory.getText().toString(),
            Double.parseDouble(etProPrice.getText().toString()));



            String url ="http://10.0.2.2:8090/image/"+stock.toString();
            System.out.println(url);

            RequestBody requestBody = RequestBody.create(JSON,imgData);


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

        }catch(Exception e){
            Log.d("OKHTTP3" ,"JSON Exception");
            e.printStackTrace();

        }
    }
    // Convert string to byte
    public byte[] imageViewToByte() throws Exception{

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}


