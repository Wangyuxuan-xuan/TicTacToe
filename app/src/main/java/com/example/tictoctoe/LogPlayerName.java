package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogPlayerName extends AppCompatActivity {

    EditText playerName1EditText;
    EditText playerName2EditText;
    Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_player_name);

        playerName1EditText = findViewById(R.id.playerName1);
        playerName2EditText = findViewById(R.id.playerName2);
        startGameButton = findViewById(R.id.startGameButton);
    }

    public void setOnStartGameButtonClick(View view){

        String playerName1 = playerName1EditText.getText().toString();
        String playerName2 = playerName2EditText.getText().toString();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("playerName1",playerName1);
        intent.putExtra("playerName2",playerName2);
        startActivity(intent);
    }

}