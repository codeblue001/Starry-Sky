package jide.delano.starrysky.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jide.delano.starrysky.R;
import jide.delano.starrysky.model.ApiInterface;
import jide.delano.starrysky.model.WeatherDataList;
import jide.delano.starrysky.model.WeatherResult;
import jide.delano.starrysky.view.MainActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract {
    private Context context;
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    public Presenter(Context context) {
        this.context = context;
    }

    public void initRetrofit(String zipCode, String unit) {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        Observable<WeatherResult> observable = apiInterface.getWeatherData(zipCode, unit, "edfb020ad373d635619c62ad3fc202b2");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WeatherResult weatherData) {
                        List<WeatherDataList> dataSet = weatherData.getList();

                        String city = weatherData.getCity().getName();
                        String country = weatherData.getCity().getCountry();
                        String temperature = dataSet.get(0).getWeatherMain().getTemp().toString() + "°";
                        String status = dataSet.get(0).getWeather().get(0).getMain();

                        ((MainActivity) context).editCurrentWeather(city + ", " + country, temperature, status);



                        List<List<WeatherDataList>> dailyWeather = separateListByDate(dataSet);
                        List<List<LinearLayout>> hourlyTemperature = new ArrayList<>();
                        List<String> dateList = new ArrayList<>();
                        int count = 0;

                        for (int i = 0; i < dailyWeather.size(); i++) {
                            hourlyTemperature.add(setUpHourlyWeather(dailyWeather.get(i)));
                            if (count == 0) dateList.add("Today");
                            else if (count == 1) dateList.add("Tomorrow");
                            else dateList.add(dailyWeather.get(i).get(0).getDtTxt().split("\\s+")[0]);
                            count += 1;
                        }

                        ((MainActivity) context).setUpRecyclerView(hourlyTemperature, dateList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    public Observable<WeatherResult> getWeatherData(String zip, String unit, String apiKey) {
        return apiInterface.getWeatherData(zip, unit, apiKey);
    }

    private List<LinearLayout> setUpHourlyWeather(List<WeatherDataList> day) {
        LayoutInflater layoutInflater = ((MainActivity) context).getLayoutInflater();
        LinearLayout llWeather = layoutInflater.inflate(R.layout.daily_weather_layout, null).findViewById(R.id.ll_weather);

        List<LinearLayout> weatherRowList = new ArrayList<LinearLayout>();
        String time, iconLink, temperature;

        String iconLinkMain = "http://openweathermap.org/img/w/";
        WeatherDataList today;
        int index;
        LinearLayout currentRow = null;

        for (int i = 0; i < day.size(); i++) {
            index = i % 4;
            if (index == 0) {
                weatherRowList.add((LinearLayout) layoutInflater.inflate(R.layout.item_layout, null));
                currentRow = weatherRowList.get(weatherRowList.size() - 1);
            }
            today = day.get(i);
            time = today.getDtTxt().split("\\s+")[1];
            iconLink = iconLinkMain + today.getWeather().get(0).getIcon() + ".png";
            temperature = today.getWeatherMain().getTemp().toString() + "°";
            fillWeatherEntry(currentRow, index, time.substring(0, time.length() - 3), iconLink, temperature);
        }


        int numEntryLastRow = (day.size() % 4);
        if (numEntryLastRow == 0) {
            return weatherRowList;
        }

        LinearLayout lastRow = weatherRowList.get(weatherRowList.size() - 1);
        for (int i = numEntryLastRow; i < 4; i++) {
            if (i == 1) {
                lastRow.findViewById(R.id.tv_time1).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.icon1).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.tv_temperature1).setVisibility(View.INVISIBLE);
            }
            if (i == 2) {
                lastRow.findViewById(R.id.tv_time2).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.icon2).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.tv_temperature2).setVisibility(View.INVISIBLE);
            }
            if (i == 3) {
                lastRow.findViewById(R.id.tv_time3).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.icon3).setVisibility(View.INVISIBLE);
                lastRow.findViewById(R.id.tv_temperature3).setVisibility(View.INVISIBLE);
            }
        }
        return weatherRowList;
    }


    private List<List<WeatherDataList>> separateListByDate(List<WeatherDataList> dataSet) {
        List<List<WeatherDataList>> dailyWeather = new ArrayList<>();
        String currentDate = "";
        int currentDateIndex = -1;

        for (int i = 0; i < dataSet.size(); i++) {
            if (!dataSet.get(i).getDtTxt().split("\\s+")[0].equals(currentDate)) {
                currentDate = dataSet.get(i).getDtTxt().split("\\s+")[0];
                currentDateIndex += 1;
                dailyWeather.add(new ArrayList<WeatherDataList>());
            }
            dailyWeather.get(currentDateIndex).add(dataSet.get(i));
        }

        return dailyWeather;
    }


    private void fillWeatherEntry(LinearLayout currentRow, int index, String time, String iconLink, String temperature) {

        if (index == 0){
            ((TextView) currentRow.findViewById(R.id.tv_time0)).setText(time);
            Picasso.get().load(iconLink).into((ImageView) currentRow.findViewById(R.id.icon0));
            ((TextView) currentRow.findViewById(R.id.tv_temperature0)).setText(temperature);
        }
        if (index == 1){
            ((TextView) currentRow.findViewById(R.id.tv_time1)).setText(time);
            Picasso.get().load(iconLink).into((ImageView) currentRow.findViewById(R.id.icon1));
            ((TextView) currentRow.findViewById(R.id.tv_temperature1)).setText(temperature);
        }
        if (index == 2){
            ((TextView) currentRow.findViewById(R.id.tv_time2)).setText(time);
            Picasso.get().load(iconLink).into((ImageView) currentRow.findViewById(R.id.icon2));
            ((TextView) currentRow.findViewById(R.id.tv_temperature2)).setText(temperature);
        }
        if (index == 3){
            ((TextView) currentRow.findViewById(R.id.tv_time3)).setText(time);
            Picasso.get().load(iconLink).into((ImageView) currentRow.findViewById(R.id.icon3));
            ((TextView) currentRow.findViewById(R.id.tv_temperature3)).setText(temperature);
        }
    }
}