package com.egyptsweather.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<City_Details> city_details_list;
    AlertDialog dialog;

    String city_name, icon, weather_description;
    int current_temperature;
    int humidity;
    double win_speed;
    double win_degree;
    double min_temperature;
    double max_temperature;
    double pressure;

    ImageView current_weather_icon_popup;
    TextView city_name_popup;
    TextView current_temperature_popup;
    TextView humidity_popup;
    TextView wind_speed_popup;
    TextView wind_degree_popup;
    TextView min_temperature_popup;
    TextView max_temperature_popup;
    TextView pressure_popup;
    TextView weather_description_popup;

    public RecyclerViewAdapter(Context context, List<City_Details> city_details_list) {
        this.context = context;
        this.city_details_list = city_details_list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_of_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {
        City_Details city_details = city_details_list.get(position);
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

        holder.city_name.setText(city_name);
        String url = "http://openweathermap.org/img/w/" + icon + ".png";
        Picasso.with(context).load(url).into(holder.current_weather_icon);
        holder.current_temperature.setText(String.valueOf(current_temperature) + "°c");
        holder.humidity.setText(String.valueOf(humidity));
        holder.wind_speed.setText(String.valueOf(win_speed));
        holder.wind_degree.setText(String.valueOf(win_speed));

        final View dialogView = getDialogView();
        current_weather_icon_popup = dialogView.findViewById(R.id.city_current_temperature_img_popup);
        city_name_popup = dialogView.findViewById(R.id.city_name_popup);
        current_temperature_popup = dialogView.findViewById(R.id.city_current_temperature_text_popup);
        humidity_popup = dialogView.findViewById(R.id.city_humidity_popup);
        wind_speed_popup = dialogView.findViewById(R.id.city_wind_speed_popup);
        wind_degree_popup = dialogView.findViewById(R.id.city_wind_degree_popup);
        weather_description_popup = dialogView.findViewById(R.id.description_popup);
        pressure_popup=dialogView.findViewById(R.id.city_pressure_popup);
        min_temperature_popup=dialogView.findViewById(R.id.min_temp_popup);
        max_temperature_popup=dialogView.findViewById(R.id.max_temp_popup);

        weather_description_popup.setText(weather_description);

        pressure_popup.setText(String.valueOf((int)pressure));
        max_temperature_popup.setText(String.valueOf((int)max_temperature)+ "°c");
        min_temperature_popup.setText(String.valueOf((int)min_temperature)+ "°c");
        Picasso.with(context).load(url).into(current_weather_icon_popup);
        city_name_popup.setText(city_name);
        current_temperature_popup.setText(String.valueOf(current_temperature) + "°c");
        humidity_popup.setText(String.valueOf(humidity));
        wind_speed_popup.setText(String.valueOf(win_speed));
        wind_degree_popup.setText(String.valueOf(win_speed));

        holder.alertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new AlertDialog.Builder(context).setView(dialogView).create();
                dialog.setCanceledOnTouchOutside(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);

            }
        });

    }


    @Override
    public int getItemCount() {
        return city_details_list.size();
    }

    private View getDialogView() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.city_details_popup, null);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView current_weather_icon;
        TextView city_name;
        TextView current_temperature;
        TextView humidity;
        TextView wind_speed;
        TextView wind_degree;


        LinearLayout alertDialog;

        public ViewHolder(View itemView) {
            super(itemView);

            current_weather_icon = itemView.findViewById(R.id.city_current_temperature_img);
            city_name = itemView.findViewById(R.id.city_name);
            current_temperature = itemView.findViewById(R.id.city_current_temperature_text);
            humidity = itemView.findViewById(R.id.city_humidity);
            wind_speed = itemView.findViewById(R.id.city_wind_speed);
            wind_degree = itemView.findViewById(R.id.city_wind_degree);
            alertDialog = itemView.findViewById(R.id.item_of_list);

        }

    }

    public void setFilter(List<City_Details> city_details_list_filter) {
        city_details_list = new ArrayList<>();
        city_details_list.addAll(city_details_list_filter);
        notifyDataSetChanged();
    }
}