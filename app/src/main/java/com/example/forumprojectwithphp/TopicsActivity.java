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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TopicsActivity extends AppCompatActivity {

    ListView listView;
    public static class Topic{
        String id_tematu;
        String id_moderatora;
        String tytul;
        String id_grupy;
        String data_zakonczenia;
        String data_zalozenia;
        String id_ustawien;
        Topic(){
            id_tematu = "";
            id_moderatora = "";
            tytul = "";
            id_grupy = "";
            data_zakonczenia = "";
            data_zalozenia = "";
            id_ustawien = "";
        }
    }

    public static class TopicDestroyerStage2 extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/deletingEmptyTopic.php?" + "id=" + strings[0] ;
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
                if (result.equals("Topic deleted successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Topic deleted successfully")) return true;
            else return false;
        }
    }


    public static class TopicDestroyer extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/deletingAllMessagesFromTopic.php?" + "id=" + strings[0] ;
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
                if (result.equals("Messages deleted successfully")) return true;
                else return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("Messages deleted successfully")) return true;
            else return false;
        }
    }

    public static class TopicGetter extends AsyncTask<String,Void, ArrayList<Topic>> {

        @Override
        protected ArrayList<Topic> doInBackground(String... strings) {
            String result = "";
            Topic tmp = new Topic();
            ArrayList<Topic> list = new ArrayList<>();
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/getTopics.php?id=" + strings[0];
            // String url_text = "http://192.168.0.3/login.php?nick=admin&haslo=admin";
            Log.i("doInBackground","started");
            int counter = 0;
            try {
                url = new URL(url_text);
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("doInBackground","opened connection");
                InputStream in = urlConnection.getInputStream();
                Log.i("doInBackground","got input stream");
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                Log.i("doInBackground","starting to read data");
                while(data != -1){
                    char current = (char    ) data;
                    if(current == '_'){
                        if(counter % 7 == 0){
                            tmp.id_tematu = result;
                            result = "";
                        }
                        if(counter % 7 == 1){
                            tmp.id_moderatora = result;
                            result = "";
                        }
                        if(counter % 7 == 2){
                            tmp.tytul = result;
                            result = "";
                        }
                        if(counter % 7 == 3){
                            tmp.id_grupy = result;
                            result = "";
                        }
                        if(counter % 7 == 4){
                            tmp.data_zalozenia = result;
                            result = "";
                        }
                        if(counter % 7 == 5){
                            tmp.data_zakonczenia = result;
                            result = "";
                        }
                        if(counter % 7 == 6){
                            tmp.id_ustawien = result;
                            result = "";
                            list.add(tmp);
                            tmp = new Topic();
                        }
                        counter ++;
                    }
                    else{
                        result += current;
                    }

                    data = reader.read();
                }
                Log.i("doInBackground","finished reading data");
                urlConnection.disconnect();
                reader.close();
                in.close();
                return  list;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    Button add;
    Button edit;
    Button del;
    boolean adding = false;
    boolean editing = false;
    boolean deleting = false;
    boolean opened = false;
    String id;
    String id_uprawnien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        id_uprawnien = intent.getStringExtra("idUpr");
        final String privillages = intent.getStringExtra("privillages");
        final String nick = intent.getStringExtra("nick");
        listView = (ListView) findViewById(R.id.TopicsListView);
        ArrayList<Topic> list = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        TopicGetter topicGetter = new TopicGetter();
        try {
            list = topicGetter.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < list.size(); ++i){
            Log.i("inside",list.get(i).tytul);
            stringList.add(list.get(i).tytul + "\n"
                    + "moderator: " + list.get(i).id_moderatora + "\n"
                    + "data założenia: " + list.get(i).data_zalozenia + "\n"
                    + "data zakończenia: " + list.get(i).data_zakonczenia + "\n"
                    + "dostępność: " + list.get(i).id_ustawien
            );
        }
        topicGetter  = null;
        MainUserScreen.User user = new MainUserScreen.User();
        MainUserScreen.UserInfo info = new MainUserScreen.UserInfo();
        try {
            user = info.execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,stringList);
        listView.setAdapter(adapter);
        final ArrayList<Topic> finalList = list;
        final MainUserScreen.User finalUser = user;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(editing){
                    if(finalList.get(position).id_moderatora.equals(nick) || id_uprawnien.equals("1")){
                        String toEdit = finalList.get(position).id_tematu;
                        Intent intent1 = new Intent(getApplicationContext(),EditTopicActivity.class);
                        intent1.putExtra("id",toEdit);
                        startActivity(intent1);
                    }

                }
                else
                if(deleting) {
                    if (finalList.get(position).id_moderatora.equals(nick) || id_uprawnien.equals("1")) {
                        String toDelete = finalList.get(position).id_tematu;
                        TopicDestroyer topicDestroyer = new TopicDestroyer();
                        try {
                            if (topicDestroyer.execute(toDelete).get()) {
                                TopicDestroyerStage2 topicDestroyerStage2 = new TopicDestroyerStage2();
                                if (topicDestroyerStage2.execute(toDelete).get()) {
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
                else {
                  //  Log.i("id_ustawien",finalList.get(position).id_ustawien );
                  //  Log.i("poziom",finalUser.poziom );
                    if(finalList.get(position).id_ustawien.equals("Dla wszystkich") || (finalList.get(position).id_ustawien.equals("Dla aktywnych") && (finalUser.poziom.equals("aktywny") || finalUser.poziom.equals("doswiadczony") || finalUser.poziom.equals("weteran"))) ||
                            (finalList.get(position).id_ustawien.equals("Dla doświadczonych") && (finalUser.poziom.equals("doswiadczony") || finalUser.poziom.equals("weteran"))) || (finalList.get(position).id_ustawien.equals("Dla weteranów") &&  finalUser.poziom.equals("weteran"))){
                        Intent intent1 = new Intent(getApplicationContext(), WiadomosciActivity.class);
                        intent1.putExtra("id", finalList.get(position).id_tematu);
                        intent1.putExtra("settings",finalList.get(position).id_ustawien);
                        intent1.putExtra("nick",nick);
                        intent1.putExtra("moderator",finalList.get(position).id_moderatora);
                        intent1.putExtra("dataZ",finalList.get(position).data_zakonczenia);
                        intent1.putExtra("idUpr",id_uprawnien);
                        startActivity(intent1);
                    }

                }
            }
        });

        FloatingActionButton plusButton = (FloatingActionButton) findViewById(R.id.plusActionButton);
        add = (Button) findViewById(R.id.addTopicButton);
        edit = (Button) findViewById(R.id.editTopicButton);
        del = (Button) findViewById(R.id.deleteTopicButton);
        if(privillages.equals("4")){
            plusButton.setVisibility(View.INVISIBLE);
        }
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(privillages.equals("1") || privillages.equals("2")|| privillages.equals("3")){
                    add.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    del.setVisibility(View.VISIBLE);
                    opened = true;
                }
            }
        });


    }
    public void AddTopic(View view){

            Intent intent1 = new Intent(getApplicationContext(),AddingTopicsActivity.class);
            intent1.putExtra("idGT",id);
            startActivity(intent1);

    }
    public void EditTopic(View view){
        adding = false;
        editing = true;
        deleting = false;
    }
    public void DeleteTopic(View view){
        adding = false;
        editing = false;
        deleting = true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (opened) {
                opened = false;
                add.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                del.setVisibility(View.INVISIBLE);
                adding = false;
                editing = false;
                deleting = false;
                return true;
            } else {
                finish();
            }
        }
        return false;
    }
}
