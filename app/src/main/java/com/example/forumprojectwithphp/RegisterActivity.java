package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    public static class UserAdder extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/addUser.php?" + "nick=" + strings[0] + "&imie=" + strings[1] + "&nazwisko=" + strings[2] + "&mail=" + strings[3] + "&poziom=poczatkujacy" + "&id_uprawnien=4" + "&haslo="+ strings[4];
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
                if (result.equals("User added successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("User added successfully")) return true;
            else return false;
        }
    }

    EditText nick;
    EditText imie;
    EditText nazwisko;
    EditText mail;
    EditText haslo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nick = (EditText) findViewById(R.id.editText18);
        imie = (EditText) findViewById(R.id.editText19);
        nazwisko = (EditText) findViewById(R.id.editText20);
        mail = (EditText) findViewById(R.id.editText21);
        haslo = (EditText) findViewById(R.id.editText22);
    }

    public void SubmmitAccount(View view){
        UserAdder userAdder = new UserAdder();
        try {
            if(userAdder.execute(nick.getText().toString(),imie.getText().toString(),nazwisko.getText().toString(),mail.getText().toString(),haslo.getText().toString()).get()){
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void CancelAccount(View view){
        finish();
    }
}
