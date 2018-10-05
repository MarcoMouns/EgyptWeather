package com.egyptsweather.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context mContext;
    City_Details city_details;

    public CustomInfoWindowAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUp = inflater.inflate(R.layout.city_details_popup, null);
        Gson gson = new Gson();
        String info = marker.getSnippet();
        
        city_details = gson.fromJson(info, new TypeToken<City_Details>() {
        }.getType());


        TextView current_temp = (TextView) popUp.findViewById(R.id.city_current_temperature_text_popup);
        TextView city_name = (TextView) popUp.findViewById(R.id.city_name_popup);
        TextView humidity = (TextView) popUp.findViewById(R.id.city_humidity_popup);
        TextView wind_degree = (TextView) popUp.findViewById(R.id.city_wind_degree_popup);
        TextView wind_speed = (TextView) popUp.findViewById(R.id.city_wind_speed_popup);
        ImageView current_weather_icon_popup = popUp.findViewById(R.id.city_current_temperature_img_popup);
        TextView city_pressure = (TextView) popUp.findViewById(R.id.city_pressure_popup);
        TextView min_temp = (TextView) popUp.findViewById(R.id.min_temp_popup);
        TextView max_temp = (TextView) popUp.findViewById(R.id.max_temp_popup);
        TextView city_description = (TextView) popUp.findViewById(R.id.description_popup);

        String url = "http://openweathermap.org/img/w/" + city_details.getIcon() + ".png";
        Picasso.with(mContext).load(url).into(current_weather_icon_popup);
        city_name.setText(city_details.getCity_name());
        current_temp.setText(String.valueOf(city_details.getCurrent_temperature() + "°c"));
        humidity.setText(String.valueOf(city_details.getHumidity()));
        wind_degree.setText(String.valueOf(city_details.getWin_degree()));
        wind_speed.setText(String.valueOf(city_details.getWin_speed()));


        city_pressure.setText(String.valueOf((int) city_details.getPressure()));
        min_temp.setText(String.valueOf((int) city_details.getMin_temperature() + "°c"));
        max_temp.setText(String.valueOf((int) city_details.getMax_temperature() + "°c"));
        city_description.setText(String.valueOf(city_details.getWeather_description()));

        return popUp;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

}
