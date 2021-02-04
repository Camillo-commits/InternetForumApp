package com.example.forumprojectwithphp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TopicGroupActivity extends AppCompatActivity {

    FloatingActionButton button;
    Intent intent;
    Button button2;
    Button editTG;
    Button deleteTG;
    String nick;

    boolean opened = false;
    boolean editing = false;
    boolean deleting = false;
    boolean correct = false;
    TopicGroupGetter topicGroupGetter;
    ArrayList<TopicGroup> array;
    ListView list;

    public static class TopicGroup {
        String moderator;
        String nazwaGrupy;
        String id;

        TopicGroup() {
            id = "";
            moderator = "";
            nazwaGrupy = "";
        }
    }
        public static class TopicGroupDestroyer extends AsyncTask<String, Void, Boolean> {

            @Override
            protected Boolean doInBackground(String... strings) {
                String result = "";
                URL url;
                HttpURLConnection urlConnection = null;
                String url_text = "http://192.168.43.167/deletingEmptyTopicGroup.php?" + "id=" + strings[0] ;
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
                    if (result.equals("Topic Group deleted successfully")) return true;
                    else return false;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result.equals("Topic Group deleted successfully")) return true;
                else return false;
            }
        }

        public static class TopicGroupGetter extends AsyncTask<Void, Void, ArrayList<TopicGroup>> {

            @Override
            protected ArrayList<TopicGroup> doInBackground(Void... voids) {
                ArrayList<TopicGroup> array = new ArrayList<>();
                TopicGroup tmp = new TopicGroup();
                String result = "";
                URL url;
                int counter = 0;
                HttpURLConnection urlConnection = null;
                String url_text = "http://192.168.43.167/getTopicGroups.php?";
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
                            if (counter % 3 == 0) {
                                tmp.id = result;
                                result = "";
                            }
                            if (counter % 3 == 1) {
                                tmp.moderator = result;
                                result = "";
                            }
                            if (counter % 3 == 2) {
                                tmp.nazwaGrupy = result;
                                result = "";
                                array.add(tmp);
                                tmp = new TopicGroup();
                            }
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
                    return array;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return array;
            }


        }

    TopicGroupActivity v;
    String id_uprawnien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_group);
        intent = getIntent();
        nick = intent.getStringExtra("nick");
        button2 = (Button) findViewById(R.id.button6);
        button = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        editTG = (Button) findViewById(R.id.Button8);
        deleteTG = (Button) findViewById(R.id.button9);
        v = this;
        id_uprawnien = intent.getStringExtra("idUpr");
        topicGroupGetter = new TopicGroupGetter();
        try {
            array = topicGroupGetter.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final ArrayList <String> arrayList = new ArrayList<String>();
        for(int i = 0; i < array.size(); ++i){
            arrayList.add(array.get(i).nazwaGrupy + "\n" + "Moderator: " + array.get(i).moderator);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        list = (ListView) findViewById(R.id.TopicGroupListView);
        list.setAdapter(adapter);

        UprawnieniaActivity.PrivillagesClass privillagesClass = new UprawnieniaActivity.PrivillagesClass();
        UprawnieniaActivity.Privillages privillages = new UprawnieniaActivity.Privillages();
        try {
           privillagesClass = privillages.execute(nick).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final UprawnieniaActivity.PrivillagesClass finalPrivillagesClass = privillagesClass;
        if(finalPrivillagesClass.id.equals("3") || finalPrivillagesClass.id.equals("4") ){
            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.animate().alpha(100).setDuration(1000).start();
                button2.setVisibility(View.VISIBLE);
                opened = true;


                if(finalPrivillagesClass.id.equals("1")){
                    editTG.setVisibility(View.VISIBLE);
                    deleteTG.setVisibility(View.VISIBLE);
                }
                else if(finalPrivillagesClass.id.equals("2")){
                    editTG.setVisibility(View.VISIBLE);
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;


                if(editing){
                    if(array.get(position).moderator.equals(nick) || id_uprawnien.equals("1")) {
                        intent = new Intent(getApplicationContext(), EditTopicGroup.class);
                        intent.putExtra("id", array.get(position).id);
                        startActivity(intent);
                    }
                    else{

                        Toast.makeText(v ,"nie jesteś moderatorem",Toast.LENGTH_SHORT);
                    }
                }
                else if (deleting){
                    if(array.get(position).moderator.equals(nick) || id_uprawnien.equals("1")){
                        TopicsActivity.TopicGetter TG = new TopicsActivity.TopicGetter();
                        ArrayList<TopicsActivity.Topic> topicsID = new ArrayList<>();
                        String TGID = array.get(position).id;
                        try {
                            topicsID = TG.execute(TGID).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < topicsID.size(); ++i){
                            TopicsActivity.TopicDestroyer topicDestroyer = new TopicsActivity.TopicDestroyer();
                            try {
                                if(topicDestroyer.execute(topicsID.get(i).id_tematu).get()){
                                    TopicsActivity.TopicDestroyerStage2 topicDestroyerStage2 = new TopicsActivity.TopicDestroyerStage2();
                                    if(topicDestroyerStage2.execute(topicsID.get(i).id_tematu).get()){
                                        correct = true;
                                    }
                                    else {
                                        correct = false;
                                        return;
                                    }

                                }
                                else {
                                    correct = false;
                                    return;
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //delete TG
                        TopicGroupDestroyer topicGroupDestroyer = new TopicGroupDestroyer();
                        try {
                            if(!topicGroupDestroyer.execute(TGID).get()){
                                correct = false;
                                return;
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(correct){
                            Toast.makeText(v,"Success",Toast.LENGTH_SHORT);
                            //  Toast.makeText(getApplicationContext(),"Topic Group deleted successfully",Toast.LENGTH_SHORT);
                        }
                    }
                    else{

                        // Toast.makeText(parent.getContext(),"nie jesteś moderatorem",Toast.LENGTH_SHORT);
                    }


                }
                //getting inside
                else {
                    intent = new Intent(getApplicationContext(), TopicsActivity.class);
                    intent.putExtra("id", array.get(position).id);
                    intent.putExtra("nick",nick);
                    intent.putExtra("privillages",finalPrivillagesClass.id);
                    intent.putExtra("idUpr",id_uprawnien);
                    startActivity(intent);
                }
            }
        });


    }


    public void EditTopicGroup(View view){

        editing = true;
    }
    public void DeleteTopicGroup(View view){
        //textView.setVisibility(View.VISIBLE);
        deleting = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        switch(e.getAction()){
            case MotionEvent.ACTION_MOVE:
                button2.animate().alpha(0).setDuration(1000).start();
                button2.setVisibility(View.INVISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                button2.animate().alpha(0).setDuration(1000).start();
                break;
            case MotionEvent.ACTION_DOWN:
                button2.animate().alpha(0).setDuration(1000).start();
                button2.setVisibility(View.INVISIBLE);
                break;
            case MotionEvent.AXIS_TOUCH_MINOR:
                button2.animate().alpha(0).setDuration(1000).start();
                button2.setVisibility(View.INVISIBLE);
                break;

        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(opened){
                button2.animate().alpha(0).setDuration(1000).start();
                button2.setVisibility(View.INVISIBLE);
                opened = false;
                editTG.setVisibility(View.INVISIBLE);
                deleteTG.setVisibility(View.INVISIBLE);
                editing = false;
                deleting = false;
               // textView.setVisibility(View.INVISIBLE);
            }
            else{
                finish();
                /*intent = new Intent(getApplicationContext(),MainMenu.class);
                intent.putExtra("nick",nick);
                startActivity(intent);*/
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    public void AddTopicGroup(View view){
        intent = new Intent(getApplicationContext(),AddingTopicGroupActivity.class);
        intent.putExtra("nick",nick);
        startActivity(intent);
    }


}
