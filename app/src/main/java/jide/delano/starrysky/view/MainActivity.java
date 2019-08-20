package jide.delano.starrysky.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jide.delano.starrysky.R;
import jide.delano.starrysky.presenter.Presenter;
import jide.delano.starrysky.presenter.PresenterContract;

public class MainActivity extends AppCompatActivity implements WeatherDetailActivityInterface{

    private PresenterContract presenter;
    private TextView tvCity;
    private TextView tvTemperature;
    private TextView tvStatus;
    private ImageView ivSettiing;
    private RecyclerView recyclerView;
    private ConstraintLayout clCurrentWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        Intent intent = getIntent();
        setUpViews();
        presenter = new Presenter(this);

        presenter.initRetrofit(intent.getStringExtra(UserSelectionUtil.ZIP),
                intent.getStringExtra(UserSelectionUtil.UNIT));
    }


    private void setUpViews() {
        tvCity = findViewById(R.id.tv_locale);
        tvTemperature = findViewById(R.id.tv_current_temp);
        tvStatus = findViewById(R.id.tv_weather_cond);
        ivSettiing = findViewById(R.id.iv_settings);
        recyclerView = findViewById(R.id.recycler_view);
        clCurrentWeather = findViewById(R.id.cl_current_weather);

        ivSettiing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void editCurrentWeather(String city, String temperature, String condition) {
        boolean isWarm = false;

        int warmFahrenheit = 70;
        int warmCelcius = 21;
        double temperatureDouble = Double.parseDouble(temperature.substring(0, temperature.length() - 1));
        tvCity.setText(city);
        tvTemperature.setText(temperature);
        tvStatus.setText(condition);

        if (UserSelectionUtil.INPUT_UNIT.equals(getResources().getString(R.string.fahrenheit))) {
            if (temperatureDouble > warmFahrenheit) {
                isWarm = true;
            }
        } else if (UserSelectionUtil.INPUT_UNIT.equals(getResources().getString(R.string.celsius))) {
            if (temperatureDouble > warmCelcius) {
                isWarm = true;
            }
        }

        if (isWarm) clCurrentWeather.setBackgroundColor(getResources().getColor(R.color.colorWarm));
        else clCurrentWeather.setBackgroundColor(getResources().getColor(R.color.colorCold));

    }

    @Override
    public void setUpRecyclerView(List<List<LinearLayout>> hourlyTemperature, List<String> dateList) {
        CustomAdapter customAdapter = new CustomAdapter(hourlyTemperature, dateList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
