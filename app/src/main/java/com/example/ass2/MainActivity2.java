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


public class MainActivity2 extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    private long id = 0;
    private ArrayList<String> idd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.crypto);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity2.this, cryptodet.class);
                intent.putExtra("coinname", (String) parent.getItemAtPosition(position));
                String stringValue = String.valueOf(id);
                intent.putExtra("coinid", idd.get(position));
                startActivity(intent);
            }
        });

    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=30";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            JSONArray Arr = jsonRes.getJSONArray("data");

                            ArrayList<String> coinNames = new ArrayList<>();

                            for (int i = 0; i < Arr.length(); i++) {
                                JSONObject coinObj = Arr.getJSONObject(i);
                                String coinName = coinObj.getString("name");
                                id = coinObj.getLong("id");
//                                Log.d("Received Item", id+"");
                                coinNames.add(coinName);
                                idd.add(id + "");
                            }

                            adapter = new ArrayAdapter<>(MainActivity2.this, R.layout.row, coinNames);
                            listView.setAdapter(adapter);

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