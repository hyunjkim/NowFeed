package com.example.nowfeed;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nowfeed.network.InstagramAPIs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.nowfeed.model.Weather;
import com.example.nowfeed.model.WeatherRespond;
import com.example.nowfeed.network.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ViewGroup.OnClickListener{

 List<Object> mCardsData= new ArrayList<>();
    Button refresh;
    private static final String TAG= "MainActivity";
    private static final String API_KEY = "62f136aaf813f7d74fabcdfdb0fcb3ba";
    private static final String LOCATION="NEWYORK,USA";

RecyclerView recyclerView;
    public static Drawable mWeatherIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InstagramAPIs igAPI = new InstagramAPIs(this);
        igAPI.getUserID();


        mCardsData.add("Mila's Notes");

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
        final WeatherApi weatherApi=retrofit.create(WeatherApi.class);
        Call<WeatherRespond> call= weatherApi.fetchWeather(LOCATION,API_KEY);
        call.enqueue(new Callback<WeatherRespond>() {
            @Override
            public void onResponse(Call<WeatherRespond> call, Response<WeatherRespond> response) {
                if(response.isSuccessful()){

                    WeatherRespond weatherRespond = response.body();
                    List<Weather> weather=   weatherRespond.getWeather();

                    Log.d(TAG,weather.get(0).getDescription());
                    Log.d(TAG,weather.get(0).getMain());
                    Log.d(TAG,weather.get(0).getIcon());
                    mCardsData.add(weatherRespond);
                    //mCardsData.add(new Instagram());
                    initializeRecView();
//                    URL imageURL= null;
//                    try {
//                        imageURL = new URL("http://openweathermap.org/img/w/"+weather.get(0).getIcon()+".png");
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        InputStream content=(InputStream)imageURL.getContent();
//                        mWeatherIcon= Drawable.createFromStream(content,"src");
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                }

            }

            @Override
            public void onFailure(Call<WeatherRespond> call, Throwable t) {

            }
        });



    }

    public void initializeRecView(){
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardAdapter(mCardsData));

    }

    public void getWeatherPicture(String icon) throws MalformedURLException {

        URL imageURL= new URL("http://openweathermap.org/img/w/");

        try {
            InputStream content=(InputStream)imageURL.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View view) {


    }
}
