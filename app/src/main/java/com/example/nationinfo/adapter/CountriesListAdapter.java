package com.example.nationinfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationinfo.R;
import com.example.nationinfo.model.Country;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.ViewHolder> {

    private List<Country> countries;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    public CountriesListAdapter(List<Country> countries, Context context, final OnItemClickListener listener) {
        this.countries = countries;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.countries_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.country_name.setText(country.getCountryName());
        holder.bind(country,listener);

        Picasso.get().load("https://img.geonames.org/flags/x/"+ country.getCountryCode().toLowerCase() + ".gif").into(holder.country_flag, new Callback() {
            @Override
            public void onSuccess() {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Exception e) {
                holder.country_name.setText("Không thể load lá cờ của nước: " + country.getCountryName() +
                        "\n Chi tiết:" + e.getMessage() + "\n URL: " + "https://img.geonames.org/flags/x/"+ country.getCountryCode() + ".gif");
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView country_name;
        private ShapeableImageView country_flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country_name = itemView.findViewById(R.id.country_name);
            country_flag = itemView.findViewById(R.id.country_flag);
        }

        public void bind(final Country item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
