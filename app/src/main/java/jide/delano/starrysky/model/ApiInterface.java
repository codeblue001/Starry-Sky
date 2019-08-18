package jide.delano.starrysky.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("data/2.5/forecast")
    Observable<WeatherResult> getWeatherData(
            @Query("zip") String zip,
            @Query("units") String unit,
            @Query("appid") String apiKey);
}

