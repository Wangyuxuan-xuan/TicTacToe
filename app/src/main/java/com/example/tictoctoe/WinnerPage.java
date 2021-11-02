package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinnerPage extends AppCompatActivity {

    TextView winnerText;
    Button restartButton;
    Button exitButton;
    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_page);

        Intent winnerIntent = getIntent();   //not new intent but get intent!
        String winnerName = winnerIntent.getStringExtra("winnerName");
        Log.d("winnerName",winnerName);
        winnerText = findViewById(R.id.textViewWinnerText);
        restartButton = findViewById(R.id.restartButton);
        exitButton = findViewById(R.id.leaveGameButton);

        winnerText.setText(getString(R.string.winnerMessage,winnerName));

    }

    public void setRestartOnClick(View view){
        mainActivity.restartGame();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void setExitOnClick(View view){
        System.exit(0);
    }
}