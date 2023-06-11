package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    TextView textView11;
    TextView textView12;
    TextView textView13;
    TextView textView14;
    TextView textView15;
    EditText editText1;
    EditText editText2;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    Button button;
    String info="";
    String realTime;
    String realTime2;
    String realTime3;
    int x;
    int x2;
    int x3;
    String hr;
    String hr2;
    String hr3;
    String longi;
    String latit;
    int c=0;
    DownloadFilesTask downloadFilesTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.id_city1);
        textView2 = findViewById(R.id.id_city2);
        textView3 = findViewById(R.id.id_city3);
        textView4 = findViewById(R.id.id_temp1);
        textView5 = findViewById(R.id.id_temp2);
        textView6 = findViewById(R.id.id_temp3);
        textView7 = findViewById(R.id.id_time1);
        textView8 = findViewById(R.id.id_time2);
        textView9 = findViewById(R.id.id_time3);
        textView10 = findViewById(R.id.id_date1);
        textView11 = findViewById(R.id.id_date2);
        textView12 = findViewById(R.id.id_date3);
        textView13 = findViewById(R.id.id_weath1);
        textView14 = findViewById(R.id.id_weath2);
        textView15 = findViewById(R.id.id_weath3);
        editText1 = findViewById(R.id.id_lat);
        editText2 = findViewById(R.id.id_long);
        imageView1 = findViewById(R.id.id_imageView);
        imageView2 = findViewById(R.id.id_imageView2);
        imageView3 = findViewById(R.id.id_imageView3);
        button = findViewById(R.id.id_button);


        downloadFilesTask = new DownloadFilesTask();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                latit = editText1.getText().toString();
                longi = editText2.getText().toString();
                if (c>0) {
                    downloadFilesTask.cancel(true);
                    downloadFilesTask = new DownloadFilesTask();
                }
                downloadFilesTask.execute();
                c++;

            }
        });
    }
    private class DownloadFilesTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... urls) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/find?lat="+latit+"&lon="+longi+"&cnt=3&appid=36152020e32e516d32b7065e3f2a51f7");
                Log.d("TAG7", url.toString());
                URLConnection urlConnection = url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                 info = bufferedReader.readLine();

            } catch (Exception e) {
                e.printStackTrace();

            }
            //String s = content.toString();
            Log.d("TAG_INFO5", info);

            return null;


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aLong) {
            JSONObject filler = null;
            JSONObject cityOne = new JSONObject();
            JSONObject cityTwo = new JSONObject();
            JSONObject cityThree = new JSONObject();

            try {
                filler = new JSONObject(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                cityOne = filler.getJSONArray("list").getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Log.d("TAG_TE1", cityOne.getString("name"));
                textView1.setText(cityOne.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                cityTwo = filler.getJSONArray("list").getJSONObject(1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Log.d("TAG_TE2", cityTwo.getString("name"));
                textView2.setText(cityTwo.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                cityThree = filler.getJSONArray("list").getJSONObject(2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Log.d("TAG_TE3", cityThree.getString("name"));
                textView3.setText(cityThree.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Double tempOne = (int) (((cityOne.getJSONObject("main").getDouble("temp")-273.15)*(9.0/5.0)+32.0)*10)/10.0;
                Log.d("TAG_TEMP1", tempOne.toString()+"°F");
                textView4.setText(tempOne.toString()+"°F");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Double tempTwo = (int) (((cityTwo.getJSONObject("main").getDouble("temp")-273.15)*(9.0/5.0)+32.0)*10)/10.0;
                Log.d("TAG_TEMP2", tempTwo.toString()+"°F");
                textView5.setText(tempTwo.toString()+"°F");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Double tempThree = (int) (((cityThree.getJSONObject("main").getDouble("temp")-273.15)*(9.0/5.0)+32.0)*10)/10.0;
                Log.d("TAG_TEMP3", tempThree.toString()+"°F");
                textView6.setText(tempThree.toString()+"°F");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            long changeTime = 0;
            try {
                changeTime = cityOne.getLong("dt");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Date date = new Date(changeTime*1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            String formattedDate = dateFormat.format(date);

            SimpleDateFormat calenderDate = new SimpleDateFormat("MM/dd/yyyy");
            String calenderDateText1 = calenderDate.format(date);
            SimpleDateFormat zone = new SimpleDateFormat("z");
            String side = zone.format(date);
            SimpleDateFormat hour = new SimpleDateFormat("HH");
             hr = hour.format(date);
            SimpleDateFormat minute = new SimpleDateFormat("mm");
            String min = minute.format(date);
            if(Integer.parseInt(hr)<12)
            {
                realTime = hr+":"+min+"am "+side;
            }
            else if(Integer.parseInt(hr)==12)
            {
                realTime = "12:"+min+side;
            }
            else
            {
                x = Integer.parseInt(hr) - 12;
                hr = x+":";
                realTime = hr+min+"pm "+side;

            }
            Log.d("TAG_DA", calenderDateText1);
            Log.d("TAG_CH", realTime);
            textView7.setText(realTime);
            textView10.setText(calenderDateText1);

            long changeTime2 = 0;
            try {
                changeTime2 = cityTwo.getLong("dt");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Date date2 = new Date(changeTime2*1000);
            String calenderDateText2 = calenderDate.format(date2);
            SimpleDateFormat zone2 = new SimpleDateFormat("z");
            String side2 = zone2.format(date);
            SimpleDateFormat hour2 = new SimpleDateFormat("HH");
            hr2 = hour2.format(date);
            SimpleDateFormat minute2 = new SimpleDateFormat("mm");
            String min2 = minute2.format(date);
            if(Integer.parseInt(hr2)<12)
            {
                realTime2 = hr2+":"+min2+"am "+side2;
            }
            else if(Integer.parseInt(hr2)==12)
            {
                realTime2 = "12:"+min2+side2;
            }
            else
            {
                x2 = Integer.parseInt(hr2) - 12;
                hr2 = x2+":";
                realTime2 = hr2+min2+"pm "+side2;

            }
            Log.d("TAG_DA", calenderDateText2);
            Log.d("TAG_CH", realTime2);
            textView8.setText(realTime2);
            textView11.setText(calenderDateText2);

            long changeTime3 = 0;
            try {
                changeTime3 = cityThree.getLong("dt");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Date date3 = new Date(changeTime3*1000);
            String calenderDateText3 = calenderDate.format(date3);
            SimpleDateFormat zone3 = new SimpleDateFormat("z");
            String side3 = zone3.format(date);
            SimpleDateFormat hour3 = new SimpleDateFormat("HH");
            hr3 = hour3.format(date);
            SimpleDateFormat minute3 = new SimpleDateFormat("mm");
            String min3 = minute3.format(date);
            if(Integer.parseInt(hr3)<12)
            {
                realTime3 = hr3+":"+min3+"am "+side3;
            }
            else if(Integer.parseInt(hr3)==12)
            {
                realTime3 = "12:"+min3+side3;
            }
            else
            {
                x3 = Integer.parseInt(hr3) - 12;
                hr3 = x3+":";
                realTime3 = hr3+min3+"pm "+side3;

            }
            Log.d("TAG_DA", calenderDateText3);
            Log.d("TAG_CH", realTime3);
            textView9.setText(realTime3);
            textView12.setText(calenderDateText3);

            JSONObject weather1 = null;
            try {
                weather1 = cityOne.getJSONArray("weather").getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String outside1 = weather1.getString("description");
                Log.d("TAG_WE", outside1);
                textView13.setText(outside1);
                if(outside1.equals("clear sky"))
                {
                    imageView1.setImageResource(R.drawable.clear);
                }
                if(outside1.equals("thunderstorm"))
                {
                    imageView1.setImageResource(R.drawable.thunderstorm);
                }
                if(outside1.equals("overcast clouds"))
                {
                    imageView1.setImageResource(R.drawable.cloudy);
                }
                if(outside1.equals("scattered clouds"))
                {
                    imageView1.setImageResource(R.drawable.cloudy);
                }
                if(outside1.equals("few clouds"))
                {
                    imageView1.setImageResource(R.drawable.cloudy);
                }
                if(outside1.equals("rain"))
                {
                    imageView1.setImageResource(R.drawable.rain);
                }
                if(outside1.equals("drizzle"))
                {
                    imageView1.setImageResource(R.drawable.drizzle);
                }
                if(outside1.equals("snow"))
                {
                    imageView1.setImageResource(R.drawable.snow);
                }
                if(outside1.equals("light snow"))
                {
                    imageView1.setImageResource(R.drawable.snow);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            JSONObject weather2 = null;
            try {
                weather2 = cityTwo.getJSONArray("weather").getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String outside2 = weather2.getString("description");
                Log.d("TAG_WE2", outside2);
                textView14.setText(outside2);
                if(outside2.equals("clear sky"))
                {
                    imageView2.setImageResource(R.drawable.clear);
                }
                if(outside2.equals("thunderstorm"))
                {
                    imageView2.setImageResource(R.drawable.thunderstorm);
                }
                if(outside2.equals("overcast clouds"))
                {
                    imageView2.setImageResource(R.drawable.cloudy);
                }
                if(outside2.equals("scattered clouds"))
                {
                    imageView2.setImageResource(R.drawable.cloudy);
                }
                if(outside2.equals("few clouds"))
                {
                    imageView2.setImageResource(R.drawable.cloudy);
                }
                if(outside2.equals("rain"))
                {
                    imageView2.setImageResource(R.drawable.rain);
                }
                if(outside2.equals("drizzle"))
                {
                    imageView2.setImageResource(R.drawable.drizzle);
                }
                if(outside2.equals("snow"))
                {
                    imageView2.setImageResource(R.drawable.snow);
                }
                if(outside2.equals("light snow"))
                {
                    imageView2.setImageResource(R.drawable.snow);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject weather3 = null;
            try {
                weather3 = cityThree.getJSONArray("weather").getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String outside3 = weather3.getString("description");
                Log.d("TAG_WE3", outside3);
                textView15.setText(outside3);
                if(outside3.equals("clear sky"))
                {
                    imageView3.setImageResource(R.drawable.clear);
                }
                if(outside3.equals("thunderstorm"))
                {
                    imageView3.setImageResource(R.drawable.thunderstorm);
                }
                if(outside3.equals("overcast clouds"))
                {
                    imageView3.setImageResource(R.drawable.cloudy);
                }
                if(outside3.equals("scattered clouds"))
                {
                    imageView3.setImageResource(R.drawable.cloudy);
                }
                if(outside3.equals("few clouds"))
                {
                    imageView3.setImageResource(R.drawable.cloudy);
                }
                if(outside3.equals("rain"))
                {
                    imageView3.setImageResource(R.drawable.rain);
                }
                if(outside3.equals("drizzle"))
                {
                    imageView3.setImageResource(R.drawable.drizzle);
                }
                if(outside3.equals("snow"))
                {
                    imageView3.setImageResource(R.drawable.snow);
                }
                if(outside3.equals("light snow"))
                {
                    imageView3.setImageResource(R.drawable.snow);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

}