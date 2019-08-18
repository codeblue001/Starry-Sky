package jide.delano.starrysky.presenter;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jide.delano.starrysky.model.ApiInterface;
import jide.delano.starrysky.model.WeatherResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract{

    private final String TAG = this.getClass().getSimpleName();
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
