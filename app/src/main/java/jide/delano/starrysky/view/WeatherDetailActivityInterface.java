package jide.delano.starrysky.view;

import android.widget.LinearLayout;

import java.util.List;

interface WeatherDetailActivityInterface {
    void editCurrentWeather(String city, String temperature, String condition);
    void setUpRecyclerView(List<List<LinearLayout>> hourlyTemperature, List<String> dateList);
}
