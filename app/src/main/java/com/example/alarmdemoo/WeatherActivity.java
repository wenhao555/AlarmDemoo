package com.example.alarmdemoo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alarmdemoo.util.HttpUtil;
import com.example.alarmdemoo.util.Weather2;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity
{

    public SwipeRefreshLayout swipeRefresh;

    private ScrollView weatherLayout;

    private Button nav_button;

    private TextView titleCity;


    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //其他

        bingPicImg = findViewById(R.id.bing_pic_img);
        weatherLayout = findViewById(R.id.weather_layout);
        nav_button = findViewById(R.id.nav_button);
        titleCity = findViewById(R.id.title_city);
        weatherLayout = findViewById(R.id.weather_layout);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        context = this;
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        nav_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示选择城市");
                final EditText editText = new EditText(context);
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        titleCity.setText(editText.getText().toString());
                        getWeather(editText.getText().toString());
                    }
                })
                        .show();
            }
        });
        getWeather("北京");
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getWeather(titleCity.getText().toString());
            }
        });

    }

    List<Weather2.Result.Future> futures = new ArrayList<>();

    public void getWeather(String districtCode)
    {
        final String weatherUrl = "http://v.juhe.cn/weather/index?format=2&cityname=" + districtCode + "&key=a83b99ba53e7dcdd98cbfbd00e9434d9";

        OkHttpUtils.post()
                .url(weatherUrl)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {

                    }

                    @Override
                    public void onResponse(String response, int id)
                    {
                        JsonObject resultJson = new JsonParser().parse(response).getAsJsonObject();
                        JsonObject result = resultJson.get("result").getAsJsonObject();
                        futures = new Gson().fromJson(result.getAsJsonArray("future"), new TypeToken<ArrayList<Weather2.Result.Future>>()
                        {
                        }.getType());
                    }
                });
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String responseText = response.body().string();
                final Weather2 weather = new Gson().fromJson(responseText, Weather2.class);
                if (weather.getResultcode().equals("200"))
                {
                    Weather2.Result.Future future = new Weather2.Result.Future();
                    //这用province中就有了[]中的所有数据，下面遍历就可以了
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (weather != null)
                            {
//                                mWeatherId = weather.getResult().getToday().getCity();
                                showWeatherInfo(weather);
                            } else
                            {
                                Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                            }
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather2 weather)
    {
        String cityName = weather.getResult().getToday().getCity();
        String degree = weather.getResult().getSk().getTemp() + "℃";
        String weatherInfo = weather.getResult().getToday().getWeather();
        titleCity.setText(cityName);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Weather2.Result.Future future : futures)
        {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText =  view.findViewById(R.id.date_text);
            TextView infoText =  view.findViewById(R.id.info_text);
            TextView maxText =  view.findViewById(R.id.max_text);
            dateText.setText(future.getWeek());
            infoText.setText(future.getWeather());
            maxText.setText(future.getTemperature());
            forecastLayout.addView(view);
        }
        String comfort = "穿衣建议：" + weather.getResult().getToday().getDressing_advice();
        String carWash = "洗车指数：" + weather.getResult().getToday().getWash_index();
        String sport = "旅游建议：" + weather.getResult().getToday().getTravel_index();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic()
    {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
