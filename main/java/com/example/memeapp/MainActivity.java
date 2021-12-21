package com.example.memeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

     Button nex,sha;
    String url=null;
    String u=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadmeme();
        nex=findViewById(R.id.nextbtn);
        sha=findViewById(R.id.sharebtn);
        //progressBar=findViewById(R.id.progress);

        nex.setOnClickListener(view -> {
            next();
        });
        sha.setOnClickListener(view -> {
            share();
        });


    }
    public void loadmeme(){
      //  progressBar.setVisibility(View.VISIBLE);
        // setProgressBarVisibility(true);
         url=" https://meme-api.herokuapp.com/gimme";
        //RequestQueue queue = Volley.newRequestQueue(this);
        ImageView memeview=findViewById(R.id.memeview);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        (Response.Listener<JSONObject>) response -> {
                            try {
                                 u=response.getString("url");
                                Glide.with(this).load(u).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                       // Toast.makeText(MainActivity.this, "Can't load meme", Toast.LENGTH_SHORT).show();
                                       // progressBar.setVisibility(View.GONE);
//                                        setProgressBarVisibility(false);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                        setProgressBarVisibility(false);
                                        return false;
                                    }
                                }).into(memeview);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

// Add the request to the RequestQueue.
       // queue.add(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void share(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check this awesome meme"+u);
        Intent chooser=Intent.createChooser(intent,"Share Using....");
        startActivity(chooser);




    }
     public void next(){
        loadmeme();
     }
}