package com.example.forumprojectwithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ShowPasswordActivity extends AppCompatActivity {

    EditText text;
    EditText confirmText;
    Button button;
    boolean visible = false;

    TextView textView;

    public class ChangePassword extends AsyncTask<String,Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            String url_text = "http://192.168.43.167/changePassword.php?nick=" + strings[0] + "&haslo=" + strings[1];
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
                if(result.equals("Password changed")){
                    return true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return false;
        }
    }

    public void showHide(View view){
       // Log.i("Password",password);
        if(visible){
            textView.setVisibility(View.INVISIBLE);
            button.setText("SHOW");
            visible = !visible;
        }
        else{
            textView.setVisibility(View.VISIBLE);
            button.setText("HIDE");
            visible = !visible;
        }
    }
    boolean tmp = false;
    public void changePassword(View view){
        text.setVisibility(View.VISIBLE);
        confirmText.setVisibility(View.VISIBLE);

        String new_password = text.getText().toString();
        String new_password_confirmation = confirmText.getText().toString();
        if(tmp) {
            if (new_password.equals(new_password_confirmation)) {
                Log.i("New password", new_password);
                //place change the password method
                ChangePassword changePassword = new ChangePassword();
                boolean worked = false;
                try {
                     worked = changePassword.execute(intent.getStringExtra("nick"),new_password).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(worked){
                    Toast.makeText(this,"Password changed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Password failed to change",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "hasła się różnią!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            tmp = true;
        }
    }
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);
        intent = getIntent();
        String password =  intent.getStringExtra("Password");
       // System.out.println(password);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(password.toString());
        confirmText =(EditText) findViewById(R.id.editText5);
        text = (EditText) findViewById(R.id.editText6);
        button = (Button) findViewById(R.id.button2);
        text.setVisibility(View.INVISIBLE);

    }
}
