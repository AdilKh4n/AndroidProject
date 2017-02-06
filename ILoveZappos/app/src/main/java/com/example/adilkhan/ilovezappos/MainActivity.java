package com.example.adilkhan.ilovezappos;

import android.content.Intent;
//import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adilkhan.ilovezappos.NetworkConnectivity;

import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText searchquery;
    ArrayList<String> ProductDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchquery = (EditText) findViewById(R.id.editText);
    }

    public void searchqueryfire(View v)
    {
        String item = searchquery.getText().toString();
       // if(item == null)
        Log.d("adil",item);
       // String inputStreamAsString = "https://api.zappos.com/Search?term="+item+"&key=b743e26728e16b81da139182bb2094357c31d331";
       // JSONObject json = new JSONObject(inputStreamAsString);

        if(NetworkConnectivity.checkInternetConnection(MainActivity.this))
        {
            new GetItems().execute(item);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Internet connection not avaliable!",Toast.LENGTH_SHORT).show();
        }

    }

    private class GetItems extends AsyncTask<String, Void, ArrayList<String>> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      //  Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        Log.d("adil",params[0]);
        String url = "https://api.zappos.com/Search?term="+params[0]+"&key=b743e26728e16b81da139182bb2094357c31d331";
        String jsonStr = sh.makeServiceCall(url);

        String brandName = null;
        String thumbnail = null;
        String productID = null;
        String originalPrice = null;
        String styleId =null;
        String colorId =null;
        String price = null;
        String percentOff = null;
        String productUrl =null;
        String productName = null;

        ProductDetails = new ArrayList<String>();

        Log.e("adil", "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String check = jsonObj.getString("currentResultCount");
                Log.d("adil",check);
                if(check.equals("0"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json error: ",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                // Getting JSON Array node
                else {
                    JSONArray contacts = jsonObj.getJSONArray("results");


                    JSONObject c = contacts.getJSONObject(0);
                    brandName = c.getString("brandName");
                    Log.d("adil", brandName);
                    thumbnail = c.getString("thumbnailImageUrl");
                    Log.d("adil", thumbnail);
                    productID = c.getString("productId");
                    originalPrice = c.getString("originalPrice");
                    styleId = c.getString("styleId");
                    colorId = c.getString("colorId");
                    price = c.getString("price");
                    percentOff = c.getString("percentOff");
                    productUrl = c.getString("productUrl");
                    productName = c.getString("productName");


                    // ProductInfo p = new ProductInfo(brandName,thumbnail,productID,originalPrice,styleId,colorId,price,percentOff,productUrl,productName);
                    // ProductDetails.add()
                    ProductDetails.add(brandName);
                    ProductDetails.add(thumbnail);
                    ProductDetails.add(productID);
                    ProductDetails.add(originalPrice);
                    ProductDetails.add(styleId);
                    ProductDetails.add(colorId);
                    ProductDetails.add(price);
                    ProductDetails.add(percentOff);
                    ProductDetails.add(productUrl);
                    ProductDetails.add(productName);

                }
            } catch (final JSONException e) {
                Log.e("adil", "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else {
            Log.e("adil", "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    return ProductDetails;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {

        if(!result.isEmpty()) {
            Intent i = new Intent(MainActivity.this, ProductActivity.class);
            i.putExtra("message", result);
            i.putStringArrayListExtra("Info", result);
            startActivity(i);
        }

    }
}
}
