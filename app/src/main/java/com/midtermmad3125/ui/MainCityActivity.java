package com.midtermmad3125.ui;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.midtermmad3125.R;
import com.midtermmad3125.utils.ReadJSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainCityActivity extends AppCompatActivity
{

    private static final String TAG = "MyApp";
    ArrayList<City> cityList = new ArrayList<City>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseJson();
        City newCity = cityList.get(0);
        TextView cityName = findViewById(R.id.txtCityName);
        TextView lat = findViewById(R.id.txtLat);
        TextView lon = findViewById(R.id.txtLon);
        TextView population = findViewById(R.id.txtPopulation);
        cityName.setText(newCity.getName());
        lat.setText(newCity.getLat());
        lon.setText(newCity.getLon());
        population.setText("Population: "+newCity.getPopulation());
        Intent Intent = new Intent(MainCityActivity.this,WeatherDetailsActivity.class);
        startActivity(Intent);


    }


    public void parseJson() {
        Log.e(TAG,"Into Parse JSon");
        String jsonString = ReadJSONUtils.loadJSONFromAsset(this, "moscow_weather.json");
        if (!jsonString.isEmpty()) {

            try {
                JSONObject mJsonWeather = new JSONObject(jsonString);
                //City Data
                JSONObject mCity = mJsonWeather.getJSONObject("city");
                //Data of City
                int id  = mCity.getInt("id");
                Log.e(TAG,id +"");
                String name = mCity.getString("name");
                //Data of City Coord
                JSONObject mCoord = mCity.getJSONObject("coord");
                String lon = mCoord.getString("lon");
                String lat = mCoord.getString("lat");
                Log.e(TAG,lat +"");
                //Data of City
                String country = mCity.getString("country");
                int population  = mCity.getInt("population");
                City c = new City(id,name,lon,lat,country,population);
                cityList.add(c);
                //City Count
                int mCnt = mJsonWeather.getInt("cnt");
                //City List
                JSONArray mListArray = mJsonWeather.getJSONArray("list");
                for(int i=0;i<mListArray.length();i++) {
                    JSONObject mJsonObjectList = mListArray.getJSONObject(i);
                    int dt = mJsonObjectList.getInt("dt");
                    Log.e(TAG,dt +"dt");
                    JSONObject mTemp = mJsonObjectList.getJSONObject("temp");
                    String min = mTemp.getString("min");
                    String max = mTemp.getString("max");
                    Log.e(TAG,max +"max");
                    String night = mTemp.getString("night");
                    String eve = mTemp.getString("eve");
                    String morn = mTemp.getString("morn");
                    String pressure = mJsonObjectList.getString("pressure");
                    int humidity = mJsonObjectList.getInt("humidity");
                    JSONArray weatherArray = mJsonObjectList.getJSONArray("weather");

                    for(int j=0;j<weatherArray.length();j++) {
                        JSONObject mJsonWeatherList = weatherArray.getJSONObject(j);
                        int idWheather= mJsonWeatherList.getInt("id");
                        Log.e(TAG,idWheather +"idWheather");
                        String main = mJsonWeatherList.getString("main");
                        String description = mJsonWeatherList.getString("description");
                        String icon = mJsonWeatherList.getString("icon");
                    }
                    String speed = mJsonObjectList.getString("speed");
                    Log.e(TAG,speed +"speed");
                    int deg = mJsonObjectList.getInt("deg");
                    int clouds = mJsonObjectList.getInt("clouds");
                    Log.e(TAG,clouds +"clouds");

                    //String rain = mJsonObjectList.getString(“rain”);
                    //Log.e(TAG,rain +“clouds”);


                }


                Log.e(TAG,"jot");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

