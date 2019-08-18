package jide.delano.starrysky.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jide.delano.starrysky.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    LinearLayout llWeather;
    TextView tvDate;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        llWeather = itemView.findViewById(R.id.ll_weather);
        tvDate = itemView.findViewById(R.id.tv_date);
    }
}
