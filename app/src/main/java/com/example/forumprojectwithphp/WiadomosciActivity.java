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
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WiadomosciActivity extends AppCompatActivity {

    public class MyMessage{
        String id_wiadomosci;
        String tresc;
        String data_wpisu;
        String nick;
        String id_tematu;
        String przypiete;
        String id_odpowiedz;
        MyMessage(){
            id_wiadomosci = "";
            tresc= "";
            data_wpisu= "";
            nick= "";
            id_tematu= "";
            przypiete= "";
            id_odpowiedz= "";
        }
    }

    public static class MessageDeleter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/deleteMessage.php?" + "id=" + strings[0] ;
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
                if (result.equals("Message deleted successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Message deleted successfully")) return true;
            else return false;
        }
    }

    public static class MessagePinner extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/pinMessage.php?" + "id=" + strings[0] + "&przypiete=" + strings[1] ;
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
                if (result.equals("Pinning successfull")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Pinning successfull")) return true;
            else return false;
        }
    }


    public class MessageGetter extends AsyncTask<String, Void, ArrayList<MyMessage>> {

        @Override
        protected ArrayList<MyMessage> doInBackground(String... strings) {
            ArrayList<MyMessage> list = new ArrayList<>();
            MyMessage tmp = new MyMessage();
            String result = "";
            URL url;
            int counter = 0;
            HttpURLConnection urlConnection = null;
            String url_text = strings[0] + strings[1];
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
                            tmp.id_wiadomosci = result;
                        }
                        if (counter % 7 == 1) {
                            tmp.tresc = result;
                        }
                        if (counter % 7 == 2) {
                            tmp.data_wpisu = result;
                        }
                        if(counter % 7 == 3){
                            tmp.nick = result;
                        }
                        if(counter % 7 == 4){
                            tmp.id_tematu = result;
                        }
                        if(counter % 7 == 5){
                            tmp.przypiete = result;
                        }
                        if(counter % 7 == 6){
                            tmp.id_odpowiedz = result;
                            list.add(tmp);
                            tmp = new MyMessage();
                        }
                        result = "";
                        counter++;
                    } else {
                        result += current;
                    }
                    data = reader.read();

                }
                Log.i("doInBackground", "finished reading data");
                urlConnection.disconnect();
                reader.close();
                in.close();
                return list;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }


    }




    ListView listView;
    Button add;
    Button answer;
    Button deleteMessage;
    Button pinMessage;
    boolean answering = false;
    boolean opened = false;
    boolean pinning = false;
    boolean deleting = false;
    String nick;
    String settings;
    String moderator;
    String topicID;
    String dataZakonczenia;
    String id_uprawnien;
    ArrayList<MyMessage> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiadomosci);


        add = (Button) findViewById(R.id.WriteMessageButton);
        answer = (Button) findViewById(R.id.AnswerButton);
        deleteMessage = (Button) findViewById(R.id.UsuńWiadomość);
        deleteMessage.setVisibility(View.INVISIBLE);
        pinMessage = (Button) findViewById(R.id.PrzypnijWiadomosc);
        listView = (ListView) findViewById(R.id.wiadomosciListView);
        Intent intent = getIntent();
        topicID = intent.getStringExtra("id");
        settings = intent.getStringExtra("settings");
        nick = intent.getStringExtra("nick");
        moderator = intent.getStringExtra("moderator");
        dataZakonczenia = intent.getStringExtra("dataZ");
        id_uprawnien = intent.getStringExtra("idUpr");

        list = new ArrayList<>();

        MessageGetter messageGetter = new MessageGetter();
        try {
            list = messageGetter.execute("http://192.168.43.167/getMessages.php?id=", topicID).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainUserScreen.UserInfo userInfo = new MainUserScreen.UserInfo();
        MainUserScreen.User user = new MainUserScreen.User();
        try {
            user = userInfo.execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> stringList = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i){

            if(list.get(i).przypiete.equals("1") ){

                stringList.add("WIADOMOŚĆ PRZYPIĘTA" + "\n"
                + "użytkownik: " + list.get(i).nick + "\n"
                + "wiadomość: " + list.get(i).tresc + "\n"
                + "data: " + list.get(i).data_wpisu);
                list.remove(i);
            }

        }



        for(int i = 0; i < list.size(); ++i){

            //if answer exists
            if(!list.get(i).id_odpowiedz.equals("0")){
                ArrayList<MyMessage> arrayList = new ArrayList<>();
                try {
                   messageGetter = new MessageGetter();
                   arrayList = messageGetter.execute("http://192.168.43.167/getAnswer.php?id=",list.get(i).id_odpowiedz).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stringList.add("Odpowiedź na wiadomość użytkownika " + arrayList.get(0).nick + "\n"
                + "\" " + arrayList.get(0).tresc + " \"" + "\n\n"
                + "użytkownik: " + list.get(i).nick + "\n"
                + "wiadomość: " + list.get(i).tresc + "\n"
                + "data: " + list.get(i).data_wpisu);
            }
            else{
                stringList.add("użytkownik: " + list.get(i).nick + "\n"
                        + "wiadomość: " + list.get(i).tresc + "\n"
                        + "data: " + list.get(i).data_wpisu);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,stringList);
        listView.setAdapter(adapter);
        FloatingActionButton plusButton = (FloatingActionButton) findViewById(R.id.plusActionButtonMessage);
        if(!dataZakonczenia.equals("")){
            plusButton.setVisibility(View.INVISIBLE);
        }
        final MainUserScreen.User finalUser = user;

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);

                if(finalUser.nick.equals(moderator) || finalUser.id_uprawnien.equals("1")){
                    pinMessage.setVisibility(View.VISIBLE);
                    deleteMessage.setVisibility(View.VISIBLE);
                }

                opened = true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if(answering){
                    intent = new Intent(getApplicationContext(),WriteMessageActivity.class);
                    intent.putExtra("nick",nick);
                    intent.putExtra("id_tematu",topicID);
                    intent.putExtra("odp", list.get(position).id_wiadomosci);
                    intent.putExtra("settings",settings);
                    startActivity(intent);
                }else
                if(pinning){

                    MessagePinner messagePinner = new MessagePinner();
                    try {
                        if(messagePinner.execute(list.get(position).id_wiadomosci,"1").get()){
                            finish();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else
                if(deleting){
                    MessageDeleter messageDeleter = new MessageDeleter();
                    try {
                        for(int i = 0;i < list.size(); ++i){
                            Log.i("List",String.valueOf(i) + " " + list.get(i).id_wiadomosci);
                        }
                        Log.i("positon",String.valueOf(position));
                        if(messageDeleter.execute(list.get(position).id_wiadomosci).get()){
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
                add.setVisibility(View.INVISIBLE);
                answer.setVisibility(View.INVISIBLE);
                pinMessage.setVisibility(View.INVISIBLE);
                deleteMessage.setVisibility(View.INVISIBLE);
                opened = false;
                answering = false;
                pinning = false;
                deleting = false;

                return true;
            } else {
                finish();
            }
        }
        return false;
    }

    public void WriteMessage(View view){
        Intent intent = new Intent(getApplicationContext(),WriteMessageActivity.class);
        intent.putExtra("nick",nick);
        intent.putExtra("id_tematu", topicID);
        intent.putExtra("odp","0");
        intent.putExtra("settings",settings);
        startActivity(intent);

    }
    public void AnswerMessage(View view){
        answering = true;
        pinning = false;
        deleting = false;
    }
    public void PinMessage(View view){
        //2DO
        pinning = true;
        answering = false;
        deleting = false;
    }
    public void DeleteMessage(View view){
        //2DO
        deleting = true;
        pinning = false;
        answering = false;
    }
}
