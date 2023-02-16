package com.example.wallpaperapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wallpaperapp.API.Client;
import com.example.wallpaperapp.API.Service;
import com.example.wallpaperapp.Adapters.Adapter;
import com.example.wallpaperapp.R;
import com.example.wallpaperapp.model.Image;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Image> images;
    private RecyclerView recyclerView;
    Adapter adapter;
    CardView mnatue, mbus, mcar, mtrain, mtrending;
    EditText editText;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerview);
        mnatue = findViewById(R.id.nature);
        mcar = findViewById(R.id.car);
        mbus = findViewById(R.id.bus);
        mtrain = findViewById(R.id.Train);
        mtrending = findViewById(R.id.trending);
        editText = findViewById(R.id.editext);
        search = findViewById(R.id.search);

        images = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        getsearchimage("");
        mnatue.setOnClickListener(view -> {
            String query = "nature";
            getsearchimage(query);
        });

//        mnatue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String query = "nature";
//                getsearchimage(query);
//            }
//        });
        mcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "car";
                getsearchimage(query);
            }
        });
        mbus.setOnClickListener(view -> {
            String query = "bus";
            getsearchimage(query);
        });
        mtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "train";
                getsearchimage(query);
            }
        });
        mtrending.setOnClickListener(view -> getsearchimage("trending"));

        search.setOnClickListener(view -> {
            String query = editText.getText().toString().trim().toLowerCase();
            if (query.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
            } else {
                getsearchimage(query);
            }
        });
    }

    private void getsearchimage(String query) {
        String BASE_URL = "https://pixabay.com/";
        String url = "api/?key=31726340-d78ce32d5c986a578f71407f2&q="+query+"&image_type=photo";
        try {
            Service apiService = Client.retrofitCon(BASE_URL).create(Service.class);
            String Authorization = "Bearer " + "";
            Call<String> call = apiService.getRequestWithUrlWithoutAuth(url);
//            Call<String> call = apiService.getRequestWithUrlWithAuth(Authorization, url);

            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> Response) {
                    images.clear();
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject;
                    if (Response.isSuccessful()) {
                        String responseBody = String.valueOf(Response.body());
               //Json Body
                        jsonObject = parser.parse(responseBody).getAsJsonObject();
                 //Json array
                        JsonElement yourJson = jsonObject.get("hits");
                        Type listType = new TypeToken<List<Image>>() {
                        }.getType();
                        images = new Gson().fromJson(yourJson, listType);
                        int Size = images.size();
                        if (Size > 0) {
                            LoadRecyclerView();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Invoices found!", Toast.LENGTH_SHORT).show();
                        }
//                        sweetAlertDialog.dismiss();

                    } else {
                        try {
                            jsonObject = parser.parse(Response.errorBody().string()).getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();
//                            sweetAlertDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Fetching images failed, Request Timed out!", Toast.LENGTH_SHORT).show();
//
                        } catch (Exception e) {
//                            sweetAlertDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Fetching images failed, Request Timed out!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    sweetAlertDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Fetching images failed, Request Timed out!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
//            sweetAlertDialog.dismiss();
        }

    }

    private void LoadRecyclerView() {
        adapter = new Adapter(getApplicationContext(), images);
        recyclerView.setAdapter(adapter);

    }
//
//    private void getsearchimage(String query) {
//        String url = "api/?key=31726340-d78ce32d5c986a578f71407f2&q=yellow+flowers&image_type=photo";
//        ApiUtilities.getApiInterface().getImages( url).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                 modelclasslist.clear();;
//                 if(response.isSuccessful()){
//                     String responseBody = String.valueOf(response.body());
//                     String responseBody2 = response.body().toString();
//
////                     modelclasslist.addAll(response.body().getPhotos());
//                     adapter.notifyDataSetChanged();
//                 }
//                 else{
//                     Toast.makeText(getApplicationContext(), "Not able to get", Toast.LENGTH_SHORT).show();
//                 }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                int k =0;
//            }
//        });
//    }


}