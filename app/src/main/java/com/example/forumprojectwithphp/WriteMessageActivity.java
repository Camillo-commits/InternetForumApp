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
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class WriteMessageActivity extends AppCompatActivity {

    String nick;
    String topicID;
    String id_wiadomosci;
    String settings;

    public static class MessageWriter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/writingMessage.php?" + "nick=" + strings[0] + "&id_tematu=" + strings[1] + "&tresc=" + strings[2] + "&przypiete=" + strings[3] + "&id_odpowiedz=" + strings[4] + "&data=" + strings[5];
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
                if (result.equals("Message added successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Message added successfully")) return true;
            else return false;
        }
    }
/*
    public static class MessageToConfirmWriter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/addingToConfirmMessage.php?" + "id_wiadomosci=" + strings[0];
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
                if (result.equals("Message added successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Message added successfully")) return true;
            else return false;
        }
    }*/

    EditText text;
    Button cancel;
   // Boolean toConfirm = false;
    Button submmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        Intent intent = getIntent();
        nick = intent.getStringExtra("nick");
        topicID = intent.getStringExtra("id_tematu");
        id_wiadomosci = intent.getStringExtra("odp");
        settings = intent.getStringExtra("settings");
        text = (EditText) findViewById(R.id.editText8);
        cancel = (Button) findViewById(R.id.button8);
        submmit = (Button) findViewById(R.id.button11);
        MainUserScreen.UserInfo info = new MainUserScreen.UserInfo();
        MainUserScreen.User user = new MainUserScreen.User();
        try {
            user = info.execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      /*  if(settings.equals(1)){

        }else
        if(settings.equals(2)){
            if(user.poziom.equals("poczatkujacy")){
                toConfirm = true;
            }
            else toConfirm = false;
        }else
        if(settings.equals(3)){
            if(user.poziom.equals("poczatkujacy") || user.poziom.equals("aktywny")){
                toConfirm = true;
            }
            else toConfirm = false;
        }else
        if(settings.equals(4)){
            if(user.poziom.equals("poczatkujacy") || user.poziom.equals("aktywny") || user.poziom.equals("doswiadczony")){
                toConfirm = true;
            }
            else toConfirm = false;
        }
        if(toConfirm){
            text.setHint("Your level is to low for this topic \n your answer needs to be aproved. \n Type your message here");
        }*/
    }

    public void SubmmitMessage(View view ){
        MessageWriter messageWriter = new MessageWriter();
        String txt = text.getText().toString();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if(messageWriter.execute(nick,topicID,txt,"0",id_wiadomosci,format.format(date)).get()){
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void CancelMessage(View view){
        finish();
    }
}
