package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AvailableLevelsActivity extends AppCompatActivity {

    ListView listView;


    public class AvailableLevelsActivityConnection extends AsyncTask<String,Void, ArrayList<String>>{

            @Override
            protected ArrayList<String> doInBackground(String... strings) {
                ArrayList<String> result = new ArrayList<>();
                String tmp = "";
                URL url;
                HttpURLConnection urlConnection = null;
                String url_text = "http://192.168.43.167/availableLevels.php?nick=" + strings[0];
                // String url_text = "http://192.168.0.3/login.php?nick=admin&haslo=admin";
                Log.i("doInBackground","started");

                try {
                    url = new URL(url_text);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    Log.i("doInBackground","opened connection");
                    InputStream in = urlConnection.getInputStream();
                    Log.i("doInBackground","got input stream");
                    InputStreamReader reader = new InputStreamReader(in);
                    int data = reader.read();
                    Log.i("doInBackground","starting to read data");
                    while(data != -1){
                        char current = (char) data;
                        if(current == '_'){
                            result.add(tmp);
                            tmp = "";
                        }
                        else {
                            tmp += current;
                        }


                        data = reader.read();
                    }
                    Log.i("doInBackground","finished reading data");
                    urlConnection.disconnect();
                    reader.close();
                    in.close();
                    return  result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return result;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_levels);
        Log.i("Available levels","started");

        listView = (ListView) findViewById(R.id.Levels);
        Intent intent = getIntent();
        Log.i("Available levels","listview");
        ArrayList<String> array = new ArrayList<>();

        AvailableLevelsActivityConnection activity_available_levels = new AvailableLevelsActivityConnection();
        try {
            array = activity_available_levels.execute(intent.getStringExtra("nick")).get();
            array.add(0,"Available levels");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        listView.setAdapter(adapter);

    }
}
