package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EditTopicActivity extends AppCompatActivity {

    public static class TopicEditor extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/editTopic.php?" + "id=" + strings[0] + "&nick=" + strings[2] + "&tytul=" + strings[1] + "&data=" + strings[3] + "&id_ustawien=" + strings[4];
            Log.i("URL" , url_text);
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
                Log.i("Result",result);
                if (result.equals("Topic edited successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Topic edited successfully")) return true;
            else return false;
        }
    }

    EditText moder;
    EditText tytul;
    EditText id_ust;
    Switch zakoncz;

    Button CancelEditing;
    Button SubmmitEdition;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_topic);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        moder = (EditText) findViewById(R.id.editText13);
        tytul = (EditText) findViewById(R.id.editText14);
        id_ust = (EditText) findViewById(R.id.editText16);
        zakoncz = (Switch) findViewById(R.id.switch1);
        CancelEditing = (Button) findViewById(R.id.button16);
        SubmmitEdition = (Button) findViewById(R.id.button17);
    }
    public void CancelEditing(View view){
        finish();
    }
    public void SubmmitEditing(View view){
        TopicEditor topicEditor = new TopicEditor();
        try {
            if(zakoncz.isChecked()){
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (topicEditor.execute(id, moder.getText().toString(), tytul.getText().toString(), format.format(date), id_ust.getText().toString()).get()) {
                    finish();
                }
            }
            else {
                if (topicEditor.execute(id, moder.getText().toString(), tytul.getText().toString(), "NULL", id_ust.getText().toString()).get()) {
                    finish();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
