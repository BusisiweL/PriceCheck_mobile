package com.example.reversidesoftwaresolution.pricecheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

public class Register extends AppCompatActivity {


    private EditText etName, etEmail, etUsername, etPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("PRICECHECK");

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword)

        ;


    }


    public void UserReg(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostRequest();
            }
        }).start();
        Toast.makeText(this, "User Successfully Registered!!", Toast.LENGTH_LONG).show();
    }

    public void BtnBack (View view)
    {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }

    private void PostRequest() {
        Log.d("OKHTTP3" ,"Post function called!");

        String url ="http://10.0.2.2:8090/customers/create";
      OkHttpClient client=new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject actualData =new JSONObject();
        try{
            actualData.put("name", etName.getText().toString());
            actualData.put("email", etEmail.getText().toString());
            actualData.put("username", etUsername.getText().toString());
            actualData.put("password", etPassword.getText().toString());

        }catch(JSONException e){
        Log.d("OKHTTP3" ,"JSON Exception");
            e.printStackTrace();

        }
        RequestBody body = RequestBody.create(JSON,actualData.toString());
        Log.d("OKHTTP3", "Get function called");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("OKHTTP3" ,response.body().string());
        }catch (IOException e) {
            e.printStackTrace();
        }

        }

    }
