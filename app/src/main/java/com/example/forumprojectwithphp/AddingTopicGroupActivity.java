package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.concurrent.ExecutionException;

public class AddingTopicGroupActivity extends AppCompatActivity {

    EditText moderator;
    EditText title;
    Button cancelButton;
    Button submmitButton;

    public static class TopicGroupWritter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/addingTopicGroup.php?" + "nick=" + strings[0] + "&tytul=" + strings[1];
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
                if (result.equals("Topic Group added successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Topic Group added successfully")) return true;
            else return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_topic_group);
        moderator = (EditText) findViewById(R.id.editText12);
        title = (EditText) findViewById(R.id.editText11);
        cancelButton = (Button) findViewById(R.id.button14);
        submmitButton = (Button) findViewById(R.id.button15);

    }

    public void SubmmitTopicGroup(View view){
        TopicGroupWritter topicGroupWritter = new TopicGroupWritter();
        try {
            if(topicGroupWritter.execute(moderator.getText().toString(),title.getText().toString()).get()){
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void CancelAddingTG(View view){
        finish();
    }

}
