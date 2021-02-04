package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class changingLevelActivity extends AppCompatActivity {

    public static class UserLevelChanger extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/editUserLevel.php?" + "id=" + strings[0] + "&level=" + strings[1];
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
                if (result.equals("User edited successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("User edited successfully")) return true;
            else return false;
        }
    }

    Intent intent;
    String nick;
    EditText poziom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_level);
        intent = getIntent();
        nick = intent.getStringExtra("nick");
        poziom = (EditText) findViewById(R.id.editText15);
    }

    public void CancelChangingLvl(View view){
        finish();
    }
    public void SubmmitChangingLvl(View view){
        UserLevelChanger changer = new UserLevelChanger();
        try {
            if(changer.execute(nick,poziom.getText().toString()).get()){
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
