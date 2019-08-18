package jide.delano.starrysky.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResult {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherDataList> hourlyWeatherData = null;
    @SerializedName("city")
    @Expose
    private jide.delano.starrysky.model.City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<WeatherDataList> getList() {
        return hourlyWeatherData;
    }

    public void setList(java.util.List<WeatherDataList> hourlyWeatherData) {
        this.hourlyWeatherData = hourlyWeatherData;
    }

    public jide.delano.starrysky.model.City getCity() {
        return city;
    }

    public void setCity(jide.delano.starrysky.model.City city) {
        this.city = city;
    }

}
