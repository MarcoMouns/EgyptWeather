package com.egyptsweather.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.egyptsweather.Adapter.CustomInfoWindowAdapter;
import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Bitmap.Config.ARGB_8888;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private int current = 42;
    ArrayList<City_Details> arrayList_City_Details;
    Context context;
    GoogleMap googleMap;
    MapView mapView;
    View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View views, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public String toString() {
        return "Map";
    }


    @Override
    public void onMapReady(GoogleMap googleMaps) {
        Gson gson = new Gson();

        SharedPreferences prefs = getContext().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        if (prefs.contains("map")) {

            String list = prefs.getString("map", null);
            arrayList_City_Details = gson.fromJson(list, new TypeToken<List<City_Details>>() {
            }.getType());
            for (int i = 0; i < arrayList_City_Details.size(); i++) {
                City_Details city_details = arrayList_City_Details.get(i);
                city_name = city_details.getCity_name();
                icon = city_details.getIcon();
                weather_description = city_details.getWeather_description();
                current_temperature = city_details.getCurrent_temperature();
                humidity = city_details.getHumidity();
                win_speed = city_details.getWin_speed();
                win_degree = city_details.getWin_degree();
                min_temperature = city_details.getMin_temperature();
                max_temperature = city_details.getMax_temperature();
                pressure = city_details.getPressure();
                lat = city_details.getLat();
                lon = city_details.getLon();
                MapsInitializer.initialize(getContext());
                googleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap = googleMaps;

                String text = String.valueOf(current_temperature);
                try {
                    Bitmap bitmap;
                    String info = gson.toJson(city_details);
                    bitmap = makeBitmap(getContext(), text, icon);
                    googleMaps.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(city_name).snippet(info).
                            icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                    googleMaps.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            prefs.edit().remove("map").apply();
        }

    }

    public Bitmap makeBitmap(Context context, String text, String icon) throws ExecutionException, InterruptedException {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;

        Bitmap bitmap = Ion.with(context)
                .load("http://openweathermap.org/img/w/" + icon + ".png").asBitmap().get();
        bitmap = bitmap.copy(ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED); // Text color
        paint.setTextSize(14 * scale); // Text size
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE); // Text shadow
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        int x = bitmap.getWidth() - bounds.width() - 10; // 10 for padding from right
        int y = bounds.height();
        canvas.drawText(text, x, y, paint);


        return bitmap;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
