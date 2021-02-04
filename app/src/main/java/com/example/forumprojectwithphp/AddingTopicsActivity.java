package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddingTopicsActivity extends AppCompatActivity {

    public static class TopicWriter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/addingTopic.php?" + "nick=" + strings[0] + "&id_grupy=" + strings[1] + "&tytul=" + strings[2] + "&ustawienia=" + strings[3] + "&data_zalozenia=" + strings[4];
            Log.i("url ", url_text);
            // String url_text = "http://192.168.0.3/login.php?nick=admin&haslo=admin";
            Log.i("doInBackground", "started");

            try {
                url = new URL(url_text);
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("doInBackground", "opened connection");
                InputStream in = urlConnection.getInputStream();
                Log.i("doInBackground", "got input stream");
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                Log.i("doInBackground", "starting to read data");
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("doInBackground", "finished reading data");
                urlConnection.disconnect();
                reader.close();
                in.close();
                Log.i("result = ",result);
                if (result.equals("Topic added successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Topic added successfully")) return true;
            else return false;
        }
    }

    Button CancelButton;
    Button SubmmitButton;
    EditText moder;
    EditText topic;
    EditText settings;
    String idGT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_topics);
        Intent intent = getIntent();
        idGT = intent.getStringExtra("idGT");

        CancelButton = (Button) findViewById(R.id.button12);
        SubmmitButton = (Button) findViewById(R.id.button13);
        moder = (EditText) findViewById(R.id.editText9);
        topic = (EditText) findViewById(R.id.editText7);
        settings = (EditText) findViewById(R.id.editText10);


    }
    public void CancelAddingTopic(View view){
        finish();
    }
    public void SubmmitTopic(View view){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TopicWriter topicWriter = new TopicWriter();
        String temat = topic.getText().toString();
        //temat = temat.replace(' ','-');

        try {
            if(topicWriter.execute(moder.getText().toString(),idGT,temat,settings.getText().toString(),format.format(date)).get()){
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
