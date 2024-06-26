package com.example.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView editTextText;
    Button button;
    TextView show;
    String url;

    class getWeather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
       @Override
       protected void onPostExecute(String result){
           super.onPostExecute(result);
           try{
               JSONObject jsonObject = new JSONObject(result);
               String weatherInfo = jsonObject.getString("main");
               weatherInfo = weatherInfo.replace("temp","Temperature");
               weatherInfo = weatherInfo.replace("feels_like","Feels Like");
               weatherInfo = weatherInfo.replace("temp_max","Temperature Max");
               weatherInfo = weatherInfo.replace("temp_min","Temperature Min");
               weatherInfo = weatherInfo.replace("pressure","Pressure");
               weatherInfo = weatherInfo.replace("humidity","Humidity");
               weatherInfo = weatherInfo.replace("{","");
               weatherInfo = weatherInfo.replace("}","");
               weatherInfo = weatherInfo.replace(",","\n");
               weatherInfo = weatherInfo.replace(":"," : ");
               show.setText(weatherInfo);
           }catch(Exception e){
               e.printStackTrace();
           }
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
        show = findViewById(R.id.textView2);

        final String[] temp={""};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                String city = editTextText.getText().toString();

                try{
                    if (city!=null){
                        url ="https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=a48e676ad71137b6cf39ca831907c788";
                    }else{
                        Toast.makeText(MainActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                    }
                    getWeather task= new getWeather();
                    temp[0] = task.execute(url).get();
                }catch(ExecutionException e){
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                if(temp[0] == null){
                    show.setText("Cannot able to find Weather");


                }

            }
        });

    }
}