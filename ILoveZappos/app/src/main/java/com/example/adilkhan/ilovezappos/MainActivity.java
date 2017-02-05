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
        new GetItems().execute(item);

    }

    private class GetItems extends AsyncTask<String, Void, ArrayList<String>> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
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

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("results");


                    JSONObject c = contacts.getJSONObject(0);
                    brandName = c.getString("brandName");
                    Log.d("adil", brandName);
                    thumbnail = c.getString("thumbnailImageUrl");
                    Log.d("adil",thumbnail);
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
                   /* // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);

                    // adding contact to contact list
                    contactList.add(contact);*/

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
        //super.onPostExecute(result);
       /* ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                R.layout.list_item, new String[]{ "email","mobile"},
                new int[]{R.id.email, R.id.mobile});
        lv.setAdapter(adapter);
    */
        //Log.d("adil",result);

        Intent i = new Intent(MainActivity.this, ProductActivity.class);
        i.putExtra("message", result);
        i.putStringArrayListExtra("Info",result);
// Starts TargetActivity
        startActivity(i);

    }
}
}
