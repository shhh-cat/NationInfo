package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.nationinfo.databinding.ActivityCountryDetailBinding;
import com.example.nationinfo.http.CountriesRequest;
import com.example.nationinfo.model.Country;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class CountryDetailActivity extends AppCompatActivity {

    private ActivityCountryDetailBinding binding;
    private Country country;
    private TextView population,area,neighbours, neighboursTitle,currency,coordinates,peopleOnArea;
    private CircularProgressIndicator neighboursLoading;
    private ShapeableImageView map,flag;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            country = bundle.getParcelable("country");

        else {
            Toast.makeText(this,"Lỗi dữ liệu",Toast.LENGTH_SHORT).show();
            backToMain();
        }

        initViews();
        initData();

        toolbar.setNavigationOnClickListener(view -> {
            backToMain();
        });


    }

    private void initViews() {
        population = binding.population;
        area = binding.area;
        neighbours = binding.neighbours;
        currency = binding.currency;
        coordinates = binding.coordinates;
        peopleOnArea = binding.peopleOnArea;

        map = binding.map;
        flag = binding.flag;

        toolbar = binding.toolbar;

        neighboursTitle = binding.neighboursTitle;
        neighboursLoading = binding.neighboursLoading;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void initData() {
        toolbar.setTitle(country.getCountryName());
        toolbar.setSubtitle(country.getContinentName());
        Picasso.get().load("https://img.geonames.org/flags/x/"+country.getCountryCode().toLowerCase()+".gif").into(flag);
        Picasso.get().load("https://img.geonames.org/img/country/250/"+country.getCountryCode()+".png").into(map);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(0);
        population.setText("Dân số: " + numberFormat.format( Double.parseDouble(country.getPopulation())) + " (Người)");
        area.setText("Diện tích: " + numberFormat.format( Double.parseDouble(country.getAreaInSqKm())) + " (km\u00B2)");
        peopleOnArea.setText("Mật độ dân số: " +numberFormat.format(
                Integer.parseInt(country.getPopulation()) / Double.parseDouble(country.getAreaInSqKm())
        ) + " (Người / km\u00B2)");
        currency.setText(country.getCurrencyCode());

        neighboursLoading.setVisibility(View.VISIBLE);
        neighbours.setVisibility(View.GONE);
        neighboursTitle.setVisibility(View.GONE);
        CountriesRequest.getNeighbours(this, String.valueOf(country.getGeonameId()), new CountriesRequest.OnCountriesCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    neighboursTitle.setText("Giáp với " + jsonObject.getInt("totalResultsCount") + " quốc gia");
                    StringBuilder nb = new StringBuilder("Không giáp quốc gia nào");
                    JSONArray jsonArray = jsonObject.getJSONArray("geonames");
                    if (jsonArray.length() > 0) {
                        nb = new StringBuilder();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            nb.append(jsonArray.getJSONObject(i).getString("name"));
                            if (i != jsonArray.length() - 1)
                                nb.append(", ");
                        }
                    }
                    neighbours.setText(nb.toString());
                    neighboursLoading.setVisibility(View.GONE);
                    neighbours.setVisibility(View.VISIBLE);
                    neighboursTitle.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    Toast.makeText(CountryDetailActivity.this, "Lấy dữ liệu tiếp giáp quốc gia không thành công",Toast.LENGTH_SHORT).show();
                    neighboursLoading.setVisibility(View.GONE);
                    neighbours.setVisibility(View.VISIBLE);
                    neighboursTitle.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });


        numberFormat.setMaximumFractionDigits(1);


        String north = numberFormat.format(country.getNorth()) + "\u00B0" + (country.getNorth() > 0 ? "B" : (country.getNorth() < 0 ? "N" : ""));
        String south = numberFormat.format(country.getSouth()) + "\u00B0" + (country.getSouth() > 0 ? "B" : (country.getSouth() < 0 ? "N" : ""));
        String west = numberFormat.format(country.getWest()) + "\u00B0" + (country.getWest() > 0 ? "Đ" : (country.getWest() < 0 ? "T" : ""));
        String east = numberFormat.format(country.getEast()) + "\u00B0" + (country.getEast() > 0 ? "Đ" : (country.getEast() < 0 ? "T" : ""));


        coordinates.setText("Kinh độ: Từ " + north + " đến " + south +"\nVĩ độ: Từ " + west + " đến " + east);
    }

    private void backToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}