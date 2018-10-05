package com.egyptsweather.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.egyptsweather.Fragment.ListFragment;
import com.egyptsweather.Fragment.MapFragment;
import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SplashScreen_Activity extends AppCompatActivity {

    City_Details city_details;
    ArrayList<City_Details> arrayList_City_Details = new ArrayList<City_Details>();
    String city_name, icon, weather_description;
    int current_temperature;
    int humidity;
    double win_speed;
    double win_degree;
    double min_temperature;
    double max_temperature;
    double pressure;
    double lon;
    double lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        ImageView imageView = (ImageView) findViewById(R.id.splashIcon);
        Animation animS = AnimationUtils.loadAnimation(this, R.anim.scale_splashscreen_anim);
        imageView.setAnimation(animS);
        Handler handler = new Handler();

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
//        dialog.show();

        String url = "http://api.openweathermap.org/data/2.5/group?id=360630,360686,360716,347634,350370,352354,353219,354502,355795,358619,359173,359280,359796,360502,360995,361055,361478,361546,361661,7521348&units=metric&appid=fb070f9b813f245ff36a6cea3bec8bfd";

        MyStringRequestDone myStringRequestDone = new MyStringRequestDone(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DecimalFormat df = new DecimalFormat("#.#");

                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject array = jsonArray.getJSONObject(i);
                        city_name = array.getString("name");
                        JSONObject objj = array.getJSONObject("coord");
                        lon = objj.getDouble("lon");
                        lat = objj.getDouble("lat");

                        JSONObject main = array.getJSONObject("main");
                        current_temperature = main.getInt("temp");
                        pressure = main.getDouble("pressure");
                        humidity = main.getInt("humidity");
                        min_temperature = main.getDouble("temp_min");
                        max_temperature = main.getDouble("temp_max");

                        JSONObject wind = array.getJSONObject("wind");
                        win_speed = Double.valueOf(df.format(wind.getDouble("speed")));
                        win_degree = Double.valueOf(df.format(wind.getDouble("deg")));


                        JSONArray weather = array.getJSONArray("weather");
                        JSONObject weather_object = weather.getJSONObject(0);
                        weather_description = weather_object.getString("description");
                        icon = weather_object.getString("icon");

                        city_details = new City_Details(city_name, icon, weather_description, current_temperature, humidity,
                                win_speed, win_degree, min_temperature, max_temperature, pressure, lon, lat);
                        arrayList_City_Details.add(city_details);
                    }


                    String jsonList = new Gson().toJson(arrayList_City_Details);
                    SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

                    prefs.edit().putString("list", jsonList).apply();
                    prefs.edit().putString("map", jsonList).apply();

                    dialog.dismiss();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(SplashScreen_Activity.this, "catch", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(SplashScreen_Activity.this, "Please check internet connection...!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(myStringRequestDone);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen_Activity.this, MainActivity.class));
                finish();
            }
        }, 5000);


    }

    class MyStringRequestDone extends StringRequest {
        public MyStringRequestDone(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

    }

}
