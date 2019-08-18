package jide.delano.starrysky.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jide.delano.starrysky.R;
import jide.delano.starrysky.presenter.PresenterContract;

public class MainActivity extends AppCompatActivity implements WeatherDetailActivityInterface{

    private PresenterContract presenter;
    private TextView tvLocale;
    private TextView tvTemperature;
    private TextView tvStatus;
    private ImageView ivSettiing;
    private RecyclerView recyclerView;
    private ConstraintLayout clCurrentWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
    }

    @Override
    public void editCurrentWeather(String city, String temperature, String condition) {
        boolean isWarm = false;

        int warmFahrenheit = 70;
        int warmCelcius = 21;
        double temperatureDouble = Double.parseDouble(temperature.substring(0, temperature.length() - 1));
        tvLocale.setText(city);
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

    }
}
