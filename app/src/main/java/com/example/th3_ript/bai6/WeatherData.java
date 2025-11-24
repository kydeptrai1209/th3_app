package com.example.th3_ript.bai6;

public class WeatherData {
    private String cityName;
    private double temperature;
    private int humidity;
    private String description;
    private String icon;

    public WeatherData(String cityName, double temperature, int humidity, String description, String icon) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.icon = icon;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}

