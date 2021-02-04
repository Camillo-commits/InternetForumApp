package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainUserScreen extends AppCompatActivity {

    ListView listView;

    public static class User{

        public User(){
            nick = "";
            imie = "";
            nazwisko = "";
            mail = "";
            poziom = "";
            haslo = "";
            id_uprawnien = "";
        }

        public String nick;
        public String imie;
        public String nazwisko;
        public String mail;
        public String poziom;
        public String id_uprawnien;
        public String haslo;
    }

    public static class UserInfo extends AsyncTask<String,Void,User> {

        @Override
        protected User doInBackground(String... strings) {
            User result = new User();
            String text = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/userData.php?nick=" + strings[0];
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
                int i = 0;
                while(data != -1){
                    char current = (char) data;

                    if(current == '_'){

                        switch(i){
                            case 0:
                                result.nick = text;
                                Log.i("nick" ,result.nick);
                                break;
                            case 1:
                                result.imie = text;
                                Log.i("imie", result.imie);
                                break;
                            case 2:
                                result.nazwisko = text;
                                break;
                            case 3:
                                result.mail = text;
                                break;
                            case 4:
                                result.poziom = text;
                                break;
                            case 5:
                              result.id_uprawnien = text;
                                break;

                        }

                        text = "";
                        ++i;

                    }
                    else {
                        text += current;
                        if(i == 6 ){
                            result.haslo += current;
                        }
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
        setContentView(R.layout.activity_main_user_screen);
        final Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.MainUserScreenListView);
        ArrayList<String> array = new ArrayList<String>();
        UserInfo userInfo = new UserInfo();
        User result = new User();
        try {
            result = userInfo.execute(intent.getStringExtra("nick")).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("adding data","");
            final String tmp = result.haslo;
            array.add("informacje o uzytkowniku");
            array.add("nick: " + result.nick);
            array.add("imie: " + result.imie);
            array.add("nazwisko: " + result.nazwisko);
            array.add("mail: " + result.mail);
            array.add("poziom: " + result.poziom);
            array.add("uprawnienia: " + result.id_uprawnien);
            array.add("haslo");



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,array);
        listView.setAdapter(adapter);

        final User finalResult = result;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 5:
                        intent = new Intent(getApplicationContext(),AvailableLevelsActivity.class);
                        intent.putExtra("nick", finalResult.nick);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(),UprawnieniaActivity.class);
                        intent.putExtra("nick",finalResult.nick);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(),ShowPasswordActivity.class);
                        intent.putExtra("Password",tmp);
                        intent.putExtra("nick",finalResult.nick);

                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
