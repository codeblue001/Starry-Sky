package jide.delano.starrysky.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jide.delano.starrysky.R;

class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<List<LinearLayout>> weatherRowList;
    private List<String> dataSet;

    CustomAdapter(List<List<LinearLayout>> weatherRowList, List<String> dataSet) {
        this.weatherRowList = weatherRowList;
        this.dataSet = dataSet;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LinearLayout cardView = (LinearLayout) layoutInflater.inflate(R.layout.daily_weather_layout, null);
        if (cardView.getParent() != null) {
            ((ViewGroup) cardView.getParent()).removeView(cardView);
        }
        return new CustomViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull jide.delano.starrysky.view.CustomViewHolder holder, int position) {
        List<LinearLayout> currentDate = weatherRowList.get(position);
        String date = dataSet.get(position);
        holder.tvDate.setText(date);
        for (LinearLayout row : currentDate) {
            if (row.getParent() != null) {
                ((ViewGroup) row.getParent()).removeView(row);
            }
            holder.llWeather.addView(row);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}

