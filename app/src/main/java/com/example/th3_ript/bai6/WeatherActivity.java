package com.example.th3_ript.bai6;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th3_ript.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnSearch;
    private TextView tvCityName, tvTemperature, tvHumidity, tvDescription, tvError;
    private ProgressBar progressBar;
    private View layoutResult;

    // Sử dụng Open-Meteo API - HOÀN TOÀN MIỄN PHÍ, không cần API key
    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_URL = "https://api.open-meteo.com/v1/forecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etCity = findViewById(R.id.etCity);
        btnSearch = findViewById(R.id.btnSearch);
        tvCityName = findViewById(R.id.tvCityName);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvDescription = findViewById(R.id.tvDescription);
        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progressBar);
        layoutResult = findViewById(R.id.layoutResult);

        btnSearch.setOnClickListener(v -> searchWeather());
    }

    private void searchWeather() {
        String city = etCity.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            etCity.setError("Vui lòng nhập tên thành phố");
            return;
        }

        showLoading();
        // Bước 1: Tìm tọa độ của thành phố
        getCoordinates(city);
    }

    private void getCoordinates(String city) {
        String encodedCity = android.net.Uri.encode(city);
        String url = GEOCODING_URL + "?name=" + encodedCity + "&count=1&language=en&format=json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    hideLoading();
                    showError("Lỗi kết nối: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body() != null ? response.body().string() : "";

                if (!response.isSuccessful()) {
                    runOnUiThread(() -> {
                        hideLoading();
                        showError("Không tìm thấy thành phố");
                    });
                    return;
                }

                try {
                    JSONObject json = new JSONObject(responseData);

                    if (!json.has("results") || json.getJSONArray("results").length() == 0) {
                        runOnUiThread(() -> {
                            hideLoading();
                            showError("Không tìm thấy thành phố. Hãy thử: Tokyo, London, Hanoi");
                        });
                        return;
                    }

                    JSONArray results = json.getJSONArray("results");
                    JSONObject location = results.getJSONObject(0);

                    String cityName = location.getString("name");
                    double latitude = location.getDouble("latitude");
                    double longitude = location.getDouble("longitude");

                    // Bước 2: Lấy thông tin thời tiết
                    fetchWeatherData(cityName, latitude, longitude);

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        hideLoading();
                        showError("Lỗi xử lý dữ liệu: " + e.getMessage());
                    });
                }
            }
        });
    }

    private void fetchWeatherData(String cityName, double lat, double lon) {
        String url = WEATHER_URL + "?latitude=" + lat + "&longitude=" + lon
                + "&current=temperature_2m,relative_humidity_2m,weather_code&timezone=auto";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    hideLoading();
                    showError("Lỗi lấy thông tin thời tiết: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body() != null ? response.body().string() : "";

                if (!response.isSuccessful()) {
                    runOnUiThread(() -> {
                        hideLoading();
                        showError("Lỗi lấy dữ liệu thời tiết");
                    });
                    return;
                }

                try {
                    JSONObject json = new JSONObject(responseData);
                    JSONObject current = json.getJSONObject("current");

                    double temp = current.getDouble("temperature_2m");
                    int humidity = current.getInt("relative_humidity_2m");
                    int weatherCode = current.getInt("weather_code");

                    String description = getWeatherDescription(weatherCode);

                    WeatherData weatherData = new WeatherData(cityName, temp, humidity, description, "");

                    runOnUiThread(() -> {
                        hideLoading();
                        showWeatherData(weatherData);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        hideLoading();
                        showError("Lỗi xử lý dữ liệu thời tiết: " + e.getMessage());
                    });
                }
            }
        });
    }

    private String getWeatherDescription(int code) {
        if (code == 0) return "Trời quang đãng";
        if (code <= 3) return "Có mây";
        if (code <= 49) return "Có sương mù";
        if (code <= 69) return "Có mưa";
        if (code <= 79) return "Có tuyết";
        if (code <= 84) return "Có mưa rào";
        if (code <= 99) return "Có giông bão";
        return "Không rõ";
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        layoutResult.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        btnSearch.setEnabled(false);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnSearch.setEnabled(true);
    }

    private void showWeatherData(WeatherData data) {
        layoutResult.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        tvCityName.setText(data.getCityName());
        tvTemperature.setText(String.format("%.1f°C", data.getTemperature()));
        tvHumidity.setText("Độ ẩm: " + data.getHumidity() + "%");
        tvDescription.setText(data.getDescription());
    }

    private void showError(String message) {
        layoutResult.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

