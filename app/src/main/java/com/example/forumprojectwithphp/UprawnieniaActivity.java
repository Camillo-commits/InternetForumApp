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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UprawnieniaActivity extends AppCompatActivity {

    public static class PrivillagesClass{
        String id;
        String opis;

        PrivillagesClass(){
           id = "";
           opis = "";
        }
    }

    public static class Privillages extends AsyncTask<String,Void,PrivillagesClass>{

        @Override
        protected PrivillagesClass doInBackground(String... strings) {
            PrivillagesClass result = new PrivillagesClass();
            String text = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/userPrivilages.php?nick=" + strings[0];
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
                boolean next = false;
                while(data != -1){
                    char current = (char) data;
                    if(current == '-'){
                            result.id = text;
                            text = "";
                            next = true;
                    }
                    else {
                        text += current;
                    }
                    if(next){
                        result.opis += current;
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
        setContentView(R.layout.activity_uprawnienia);

        ListView list = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        String nick = intent.getStringExtra("nick");
        PrivillagesClass privillagesClass = new PrivillagesClass();
        Privillages privillages = new Privillages();

        try {
            privillagesClass = privillages.execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add("id uprawnien: " + privillagesClass.id);
        arrayList.add("Definicja: " + privillagesClass.opis);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        list.setAdapter(arrayAdapter);
    }
}
