package com.example.trip_plan_budget;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FlightActivity extends AppCompatActivity {
    Button button;
    EditText t1,t2,t3,t4,t5,t6,t7;
    TextView t8;
    String url;
    String Country,Currency,Locale,Oringal,Destination,Date,Returndate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        t1=(EditText) findViewById(R.id.country);
        t2=(EditText) findViewById(R.id.currency);
        t3=(EditText) findViewById(R.id.locale);
        t4=(EditText) findViewById(R.id.oringal);
        t5=(EditText) findViewById(R.id.destination);
        t6=(EditText) findViewById(R.id.date);
        t7=(EditText) findViewById(R.id.returndate);
        t8=(TextView) findViewById(R.id.responed);
        t8.setMovementMethod(new ScrollingMovementMethod());
        button=findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Country=t1.getText().toString();
                Currency=t2.getText().toString();
                Locale=t3.getText().toString();
                Oringal=t4.getText().toString();
                Destination=t5.getText().toString();
                Date=t6.getText().toString();
                Returndate=t7.getText().toString();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/"+Country+"/"+Currency+"/"+Locale+"/"+Oringal+"/"+Destination+"/"+Date+"?inboundpartialdate="+Returndate)
                        .get()
                        .addHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "b516667e8emshc62d0a04217dcf1p1d99d6jsnf232f2210a0d")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        t8.setText("Failure !");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonData = response.body().string();
                        Log.e("RESPONSE => ", jsonData);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            JSONArray jsonArray = jsonObject.getJSONArray("Quotes");
                            Log.e("JSONArray => ", jsonArray + "");
                            String minPrice = jsonArray.getJSONObject(0).getString("MinPrice");
                            Log.e("MinPrice => ", minPrice);
                            t8.setText(minPrice);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        });

    }
}
