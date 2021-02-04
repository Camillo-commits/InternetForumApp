package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class adminActivity extends AppCompatActivity {

    FloatingActionButton plusButton;
    Button deleteUserButton;
    Button editLevelButton;
    Button editPrivillagesButton;
    boolean opened = false;
    boolean changingLvl = false;
    boolean changingPrivl = false;
    boolean deletingUser = false;

    ListView listView;

    public static class UserDeleter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/deleteUser.php?" + "id=" + strings[0] ;
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
                if (result.equals("User deleted successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("User deleted successfully")) return true;
            else return false;
        }
    }

    public static class UsersGetter extends AsyncTask<Void, Void, ArrayList<MainUserScreen.User>> {

        @Override
        protected ArrayList<MainUserScreen.User> doInBackground(Void... voids) {
            ArrayList<MainUserScreen.User> array = new ArrayList<>();
            MainUserScreen.User tmp = new MainUserScreen.User();
            String result = "";
            URL url;
            int counter = 0;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/getAllUsers.php?";
            Log.i("URL",url_text);
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
                    if (current == '_') {
                        if (counter % 7 == 0) {
                            tmp.nick = result;
                        }
                        if (counter % 7 == 1) {
                            tmp.imie = result;
                        }
                        if (counter % 7 == 2) {
                            tmp.nazwisko = result;
                        }
                        if (counter % 7 == 3) {
                            tmp.mail = result;
                        }
                        if (counter % 7 == 4) {
                            tmp.poziom = result;
                        }
                        if (counter % 7 == 5) {
                            tmp.id_uprawnien = result;
                        }
                        if (counter % 7 == 6) {
                            tmp.haslo = result;
                            array.add(tmp);
                            tmp = new MainUserScreen.User();
                        }
                        counter++;
                        result = "";
                    } else {
                        result += current;
                    }
                    data = reader.read();

                }
                Log.i("doInBackground", "finished reading data");
                urlConnection.disconnect();
                reader.close();
                in.close();
                return array;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return array;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        plusButton = (FloatingActionButton) findViewById(R.id.plusButtonAdmin);
        deleteUserButton = (Button) findViewById(R.id.button21);
        editLevelButton = (Button) findViewById(R.id.button19);
        editPrivillagesButton = (Button) findViewById(R.id.button20);
        listView = (ListView) findViewById(R.id.adminListView);

        deleteUserButton.setVisibility(View.INVISIBLE);
        editPrivillagesButton.setVisibility(View.INVISIBLE);
        editLevelButton.setVisibility(View.INVISIBLE);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLevelButton.setVisibility(View.VISIBLE);
                editPrivillagesButton.setVisibility(View.VISIBLE);
                deleteUserButton.setVisibility(View.VISIBLE);
                opened = true;
            }
        });

        ArrayList<MainUserScreen.User> list = new ArrayList<>();
        UsersGetter usersGetter = new UsersGetter();
        try {
            list = usersGetter.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> stringList = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i){
            stringList.add("Nick: " + list.get(i).nick + "\n"
                    + "Imie: " + list.get(i).imie + "\n"
                    + "Nazwisko: " + list.get(i).nazwisko + "\n"
                    + "Mail: " + list.get(i).mail + "\n"
                    + "Poziom: " + list.get(i).poziom + "\n"
                    + "ID uprawnien: " + list.get(i).id_uprawnien + "\n"
                    + "Hasło: " + list.get(i).haslo);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringList);
        final ArrayList<MainUserScreen.User> finalList = list;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(changingLvl){
                    Intent intent = new Intent(getApplicationContext(),changingLevelActivity.class);
                    intent.putExtra("nick", finalList.get(position).nick);
                    startActivity(intent);
                }
                else if(changingPrivl){
                    Intent intent = new Intent(getApplicationContext(),changingPrivillagesActivity.class);
                    intent.putExtra("nick", finalList.get(position).nick);
                    startActivity(intent);
                }
                else if(deletingUser){
                    UserDeleter userDeleter = new UserDeleter();
                    try {
                        if(userDeleter.execute(finalList.get(position).nick).get()){
                            finish();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (opened) {
                editLevelButton.setVisibility(View.INVISIBLE);
                editPrivillagesButton.setVisibility(View.INVISIBLE);
                deleteUserButton.setVisibility(View.INVISIBLE);

                opened = false;
                return true;
            } else {
                finish();
            }
        }
        return false;
    }
    public void changeLevel(View view){
        changingLvl = true;
        changingPrivl = false;
        deletingUser = false;
    }
    public void changePrivillages(View view){
        changingLvl = false;
        changingPrivl = true;
        deletingUser = false;
    }
    public void deleteUser(View view){
        changingLvl = false;
        changingPrivl = false;
        deletingUser = true;
    }

}
