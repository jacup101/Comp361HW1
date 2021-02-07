package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //Author Joshua Pulido
    private AsyncHttpClient http = new AsyncHttpClient();
    private static final String url= "http://madlibz.herokuapp.com/api/random";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivity(View v) {

        //API Calls
        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonText = new String(responseBody);
                Log.d("json_grab",jsonText);
                //Create Intent, start new activity, pass required data
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                intent.putExtra(getString(R.string.input_intent_data),jsonText);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("no_connection","Failed to connect to server");
                Toast toast = Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT);
                toast.show();
            }

        });

    }
}