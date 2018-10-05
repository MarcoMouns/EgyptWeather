package com.egyptsweather.Model;

public class City_Details {

    String city_name = "";
    String icon = "";
    String weather_description = "";

    int current_temperature = 0;
    int humidity = 0;
    double win_speed = 0;
    double win_degree = 0;
    double min_temperature = 0;
    double max_temperature = 0;
    double pressure = 0;
    double lon = 0;
    double lat = 0;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public int getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(int current_temperature) {
        this.current_temperature = current_temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(double win_speed) {
        this.win_speed = win_speed;
    }

    public double getWin_degree() {
        return win_degree;
    }

    public void setWin_degree(double win_degree) {
        this.win_degree = win_degree;
    }

    public double getMin_temperature() {
        return min_temperature;
    }

    public void setMin_temperature(double min_temperature) {
        this.min_temperature = min_temperature;
    }

    public double getMax_temperature() {
        return max_temperature;
    }

    public void setMax_temperature(double max_temperature) {
        this.max_temperature = max_temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public City_Details(String city_name, String icon, String weather_description, int current_temperature, int humidity, double win_speed, double win_degree, double min_temperature, double max_temperature, double pressure, double lon, double lat) {
        this.city_name = city_name;
        this.icon = icon;
        this.weather_description = weather_description;
        this.current_temperature = current_temperature;
        this.humidity = humidity;
        this.win_speed = win_speed;
        this.win_degree = win_degree;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
        this.pressure = pressure;
        this.lon = lon;
        this.lat = lat;
    }
}
