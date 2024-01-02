package com.example.ass2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class cryptodet extends AppCompatActivity {
    TextView name;
    TextView sym;
    TextView price;

    //    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptodet);
        name = findViewById(R.id.name);
        sym = findViewById(R.id.symm);
        price = findViewById(R.id.pric);

//        listView = findViewById(R.id.crypto);
        String coi = getIntent().getStringExtra("coinname");
//        Log.d("Received Item", coi);
        name.setText(coi);
        getData();

    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String coi = getIntent().getStringExtra("coinname").toLowerCase();
        String idss = getIntent().getStringExtra("coinid");

        coi = "?id=" + idss;
        String url = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest";
        url = url + coi;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject leaguesArray = jsonResponse.getJSONObject("data");

                            JSONObject leagueObject = leaguesArray.getJSONObject(idss);
                            JSONObject prr = leagueObject.getJSONObject("quote");
                            JSONObject usd = prr.getJSONObject("USD");

                            double coinprice = usd.getDouble("price");
//                            Log.d("Received Item", coinprice+"");
                            String formattedValue = String.format("%.2f", coinprice);
//                            Log.d("Received Item", response);
                            price.setText(formattedValue + "");
                            String coinSymbol = leagueObject.getString("symbol");
                            sym.setText(coinSymbol);

                        } catch (JSONException e) {
                            Log.e("JSONException", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "7a67bc4a-0846-44fe-9bac-d8704b4034ec");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

}