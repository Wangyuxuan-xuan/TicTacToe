package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String playerName1 = intent.getStringExtra("playerName1");
        String playerName2 = intent.getStringExtra("playerName2");

        Log.d("playerNames",playerName1+"  "+playerName2);
    }
}