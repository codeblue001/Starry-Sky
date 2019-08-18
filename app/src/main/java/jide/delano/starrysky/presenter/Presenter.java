package jide.delano.starrysky.presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jide.delano.starrysky.model.ApiInterface;
import jide.delano.starrysky.model.WeatherDataList;
import jide.delano.starrysky.model.WeatherResult;
import jide.delano.starrysky.view.MainActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract{


    private Context context;
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    public Presenter(Context context) {
        this.context = context;
    }

    @Override
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

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
