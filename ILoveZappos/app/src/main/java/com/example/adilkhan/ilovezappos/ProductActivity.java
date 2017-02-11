package com.example.adilkhan.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
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
    TextView t;


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
        String productId = ax.get(2);
        String originalPrice = ax.get(3);
        String styleId = ax.get(4);
        String colorId = ax.get(5);
        String price = ax.get(6);
        String percentOff = ax.get(7);
        String productUrl = ax.get(8);
        String productName = ax.get(9);

        if (thumbnailImageUrl != null) {
            Picasso.with(this).load(thumbnailImageUrl)
                    .into(im);}


            ActivityProductBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        ProductInfo user = new ProductInfo(brandName,thumbnailImageUrl,productId,originalPrice,styleId,colorId,price,percentOff,productUrl,productName);
        binding.setUser(user);
        }

    public void move(View view){
        im = (ImageView) findViewById(R.id.imageView2);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        im.startAnimation(animation1);

        t = (TextView) findViewById(R.id.textView6);
        Integer result = Integer.valueOf(t.getText().toString());
        Integer x = result + 1;
        t.setText(x.toString());
    }
}
