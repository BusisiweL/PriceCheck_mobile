package com.example.reversidesoftwaresolution.pricecheckv1;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Stock> stock;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PRICECHECK");

        stock = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListView);
        edtSearch =(EditText) findViewById(R.id.edtSearch);
        getJSON("http://10.0.2.2:8090/admin/getStock");

    }
    // SHOW DATA FROM DATABASE
    private void getJSON(final String urlWebService){
        class GetJSON extends AsyncTask<Void, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }
//       //Add To ListView
    private List<Stock> loadIntoListView(String json) throws JSONException, UnsupportedEncodingException {

        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String name = obj.getString("name");
            String description = obj.getString("description");
            String category = obj.getString("category");
            double price = Double.parseDouble(obj.getString("price"));
            String text = obj.getString("image");
            byte[] data = Base64.decode(text, Base64.DEFAULT);



            Stock s = new Stock();
            s.setImage(data);
            s.setName(name);
            s.setDescription(description);
            s.setCategory(category);
            s.setPrice(price);
            stock.add(s);
        }

        final CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.myrow, stock);
        listView.setAdapter(adapter);

        //FILTER DATA
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence z, int start, int before, int count ) {

            }

            @Override
            public void onTextChanged(CharSequence x, int start, int before, int count) {
              adapter.getFilter().filter(x);
            }
              
            @Override
            public void afterTextChanged(Editable z) {

            }
        });

        return stock;

    }



// left Bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_register) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_login){
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);

        }
        else if(id ==R.id.menu_admin){
            Intent intent=new Intent(this, Admin.class);
            startActivity(intent);

        }else if(id ==R.id.menu_add){
            Intent intent =new Intent (this, AddProduct.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
