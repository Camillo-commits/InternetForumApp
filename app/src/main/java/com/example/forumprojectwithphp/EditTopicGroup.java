
package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class EditTopicGroup extends AppCompatActivity {

    public static class TopicGroupEditor extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/editTopicGroup.php?" + "id=" + strings[0] + "&nazwa=" + strings[1] + "&moder=" + strings[2];
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
                if (result.equals("Edit successful")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Edit successful")) return true;
            else return false;
        }
    }

    Intent intent;
    String id;
    EditText nazwa;
    EditText moderator;

    public void ApplyChanges(View view){
        TopicGroupEditor topicGroupEditor = new TopicGroupEditor();
        try {
            if(topicGroupEditor.execute(id,nazwa.getText().toString(),moderator.getText().toString()).get()){
                Toast.makeText(this,"Editing successfull",Toast.LENGTH_SHORT);
                Log.i("Change TopicGroup","Successfull");
            }
            else{
                Toast.makeText(this,"Editing failed",Toast.LENGTH_SHORT);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_topic_group);
        intent = getIntent();
        id = intent.getStringExtra("id");
        nazwa = (EditText) findViewById(R.id.editText3);
        moderator = (EditText) findViewById(R.id.editText4);
    }
}
