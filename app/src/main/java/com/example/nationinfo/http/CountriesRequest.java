package com.example.nationinfo.http;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;



public class CountriesRequest {

    public interface OnCountriesCallback {
        public void onSuccess(String response);
        public void onError(VolleyError volleyError);
    }

    public static void getAll(Context context, final OnCountriesCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(

                Request.Method.GET, //METHOD : GET

                "http://api.geonames.org/countryInfoJSON?lang=vi&username=laotienboi2804", // URL

                response -> callback.onSuccess(response),

                error -> callback.onError(error)
        ) {
            @Override
            public Map<String, String> getHeaders() {

                // Yêu cầu phản hồi bằng json
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept","application/json");


                return headers;
            }
        };

        requestQueue.add(mStringRequest);
    }

    public static void getNeighbours(Context context, String geonameId ,final OnCountriesCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(

                Request.Method.GET, //METHOD : GET

                "http://api.geonames.org/neighboursJSON?username=laotienboi2804&lang=vi&geonameId=" + geonameId, // URL

                response -> callback.onSuccess(response),

                error -> callback.onError(error)
        ) {
            @Override
            public Map<String, String> getHeaders() {

                // Yêu cầu phản hồi bằng json
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept","application/json");


                return headers;
            }
        };

        requestQueue.add(mStringRequest);
    }
}
