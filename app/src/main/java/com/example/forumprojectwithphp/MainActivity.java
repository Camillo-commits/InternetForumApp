package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button signInButton;
    TextView loginTextView;
    TextView passwordTextView;
    DatabaseConectivity databaseConectivity;

    public class DatabaseConectivity extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/login.php?nick=" + strings[0] + "&haslo=" + strings[1];
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
                while(data != -1){
                    char current = (char) data;
                    result += current;
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
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.button);
        loginTextView = findViewById(R.id.editText2);
        passwordTextView = findViewById(R.id.editText);


        Log.i("onCreate","created");
    }

    public void logIn(View view) {
        Log.i("logIn","just started ");
        String login = (String) loginTextView.getText().toString();
        String password = (String) passwordTextView.getText().toString();
        String result = null;
        try {
            Log.i("logIn","starting executing doInBackground function");
            databaseConectivity = new DatabaseConectivity();
            result = databaseConectivity.execute(login,password).get();

        } catch (ExecutionException e) {
            Log.i("logIn","Failed");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.i("logIn","Failed 2");
            e.printStackTrace();
        }

        Log.i("result ",result);
        if(result.equals("login successfull")){
            Log.i("logIn","Login successfull");
            Intent intent = new Intent(getApplicationContext(),MainMenu.class);
            intent.putExtra("nick",login);
            startActivity(intent);
        }
        else if(result.equals("login failed")){
            Log.i("logIn","login failed wrong passw or login");
            Toast.makeText(this,"Incorect login or password",Toast.LENGTH_SHORT).show();

        }
        else {
            Log.i("logIn","phpError");
            Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }


    }
    public void register(View view){
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }
}