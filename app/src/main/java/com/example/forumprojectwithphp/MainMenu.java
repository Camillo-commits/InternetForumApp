package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class MainMenu extends AppCompatActivity {


    public void userData(View view){
        Intent intent = new Intent(getApplicationContext(),MainUserScreen.class);
        intent.putExtra("nick",nick);
        startActivity(intent);
    }

    public void grupyTematyczne(View view){
        Intent intent = new Intent(getApplicationContext(),TopicGroupActivity.class);
        intent.putExtra("nick",nick);
        intent.putExtra("idUpr",user.id_uprawnien);
        startActivity(intent);
    }

    public void logOut(View view){
        finish();
    }
    public void Administracja(View view){
        Intent intent = new Intent(getApplicationContext(),adminActivity.class);
        startActivity(intent);
    }

    Intent intent;
    String nick;
    Button adminButton;
    MainUserScreen.User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        intent = getIntent();
        nick = intent.getStringExtra("nick");
        MainUserScreen.UserInfo userInfo = new MainUserScreen.UserInfo();
        adminButton = (Button) findViewById(R.id.button18);
        adminButton.setVisibility(View.INVISIBLE);
        user = new MainUserScreen.User();
        try {
            user = userInfo.execute(nick).get();
            Log.i("id upr", user.id_uprawnien);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("id upr", user.id_uprawnien);
        if(user.id_uprawnien.equals("1")){
            adminButton.setVisibility(View.VISIBLE);
        }

    }
}
