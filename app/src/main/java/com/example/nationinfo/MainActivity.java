package com.example.nationinfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.nationinfo.adapter.CountriesListAdapter;
import com.example.nationinfo.databinding.ActivityMainBinding;
import com.example.nationinfo.http.CountriesRequest;
import com.example.nationinfo.model.Country;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;



    private AlertDialog loadingDialog;
    private TextView noData;
    private List<Country> countries;
    private RecyclerView nationRecyclerView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // SỬ DỤNG VIEW BINDING TIỆN HƠN findViewById()
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initViews();
        initData();


        loadingDialog.show();

        nationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nationRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        CountriesRequest.getAll(this, new CountriesRequest.OnCountriesCallback() {
            @Override
            public void onSuccess(String response) {
                try {

                    JSONArray array = new JSONObject(response).getJSONArray("geonames");
                    for (int i = 0; i < array.length(); i++) {
                        countries.add(new Country(array.getJSONObject(i)));
                    }
                    updateStateNoData();
                    updateRecyclerView();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Lỗi phân tích dữ liệu",Toast.LENGTH_SHORT).show();
                    updateStateNoData();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(VolleyError volleyError) {
                String errorDetail = "";
                if (volleyError.networkResponse != null)
                    errorDetail = new String(volleyError.networkResponse.data, StandardCharsets.UTF_8);
                else
                    errorDetail = volleyError.getMessage();
                Toast.makeText(MainActivity.this,"Lấy dữ liệu các quốc gia thất bại\n Chi tiết: "+ errorDetail,Toast.LENGTH_LONG).show();
                updateStateNoData();
                loadingDialog.dismiss();

            }
        });

    }

    private void updateRecyclerView() {
        CountriesListAdapter adapter = new CountriesListAdapter(countries, this, new CountriesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Country country) {
                Intent i = new Intent(MainActivity.this, CountryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("country",country);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        nationRecyclerView.setAdapter(adapter);
    }

    /**
     *
     * Khởi tạo các view
     */
    private void initViews() {
        noData = binding.noData;
        nationRecyclerView = binding.nationRecyclerView;
    }


    /**
     *
     * Khởi tạo giá trị các thuộc tính cần thiết
     */
    private void initData() {
        countries = new ArrayList<>();

        // Tạo 1 dialog hiển thị xoay mòng mòng lúc chờ load dữ liệu :))
        loadingDialog = new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                .setTitle("Đang tải")
                .setCancelable(false)
                .setView(View.inflate(this,R.layout.loading,null)).create();
    }

    /**
     *
     * ẨN HIỆN DÒNG THÔNG BÁO "Không có dữ liệu"
     */
    private void updateStateNoData() {
        noData.setVisibility(countries.isEmpty() ?  View.VISIBLE: View.GONE);
    }
}