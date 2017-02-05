package com.example.adilkhan.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adilkhan.ilovezappos.databinding.ActivityProductBinding;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.bitmap;

/**
 * Created by adilkhan on 2/2/17.
 */

public class ProductActivity extends AppCompatActivity {

    ImageView im=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> ax = new ArrayList<String>();
        ax = bundle.getStringArrayList("Info");
        System.out.print(ax.toString());

       im = (ImageView) findViewById(R.id.imageView2);


        String brandName= ax.get(0);
        String thumbnailImageUrl = ax.get(1);
        Log.d("adil1",thumbnailImageUrl);
        String productId = ax.get(2);
        String originalPrice = ax.get(3);
        String styleId = ax.get(4);
        String colorId = ax.get(5);
        String price = ax.get(6);
        String percentOff = ax.get(7);
        String productUrl = ax.get(8);
        String productName = ax.get(9);
       // Picasso.with(this).load(thumbnailImageUrl).into(im);

        if (thumbnailImageUrl != null) {
            Picasso.with(this).load(thumbnailImageUrl)
                    .into(im);}


            ActivityProductBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        ProductInfo user = new ProductInfo(brandName,thumbnailImageUrl,productId,originalPrice,styleId,colorId,price,percentOff,productUrl,productName);
        binding.setUser(user);
        /*new GetXMLTask()
                .execute(thumbnailImageUrl);
*/

//        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.myFAB);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"CLicked here",Toast.LENGTH_SHORT).show();
//
//                Animation animation1 =
//                        AnimationUtils.loadAnimation(getApplicationContext(), R.animator.move);
//                im.startAnimation(animation1);
//            }
//        });


    }

    public void move(View view){
        im = (ImageView) findViewById(R.id.imageView2);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        im.startAnimation(animation1);
    }
/*
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {

            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            im.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Log.d("adil","here");
            return stream;
        }
    }*/
}
