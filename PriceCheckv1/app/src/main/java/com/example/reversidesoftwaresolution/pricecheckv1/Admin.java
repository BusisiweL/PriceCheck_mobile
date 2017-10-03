package com.example.reversidesoftwaresolution.pricecheckv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Admin extends AppCompatActivity {
    private EditText etAdminUsername,etAdminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("PRICECHECK");

        etAdminUsername=(EditText) findViewById(R.id.etAdminUsername);
        etAdminPassword=(EditText) findViewById(R.id.etAdminPassword);
    }
    public void AdminLog(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostRequest();
            }
        }).start();
}
    private void PostRequest(){
        Log.d("OKHTTP3" ,"Post function called!");

        String url ="http://10.0.2.2:8090/admin/login";
        OkHttpClient client=new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject actualData= new JSONObject();
        try{
            actualData.put("username",etAdminUsername.getText().toString());
            actualData.put("password",etAdminPassword.getText().toString());

        }catch(JSONException e){
            Log.d("OKHTTP3" ,"JSON Exception");
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,actualData.toString());
        Log.d("OKHTTP3", "Get Function Called");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            Log.d("OKHTTP3" ,response.body().string());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}