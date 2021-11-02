package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    TextView textViewPlayerName1;
    TextView textViewPlayerName2;
    ImageView square1,square2,square3,square4,square5,square6,square7,square8,square9;


    private int recordPlayerMove[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private int isSquareOccupiedArray[] = {0,0,0,0,0,0,0,0,0,0};
    private int playerTern = 0;
    private ArrayList<int[]> successArrayCombination = new ArrayList<int[]>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String playerName1 = intent.getStringExtra("playerName1");
        String playerName2 = intent.getStringExtra("playerName2");

        Log.d("playerNames",playerName1+"  "+playerName2);
//--------------------------------find views---------------------------------------------
        textViewPlayerName1 = findViewById(R.id.textViewPlayerName1);
        textViewPlayerName2 = findViewById(R.id.textViewPlayerName2);
        square1 = findViewById(R.id.square1);
        square2 = findViewById(R.id.square2);
        square3 = findViewById(R.id.square3);
        square4 = findViewById(R.id.square4);
        square5 = findViewById(R.id.square5);
        square6 = findViewById(R.id.square6);
        square7 = findViewById(R.id.square7);
        square8 = findViewById(R.id.square8);
        square9 = findViewById(R.id.square9);


        addCombinations(successArrayCombination);
        setTextPlayerName(playerName1,playerName2);
    }

    public void setTextPlayerName(String playerName1,String playerName2){
        textViewPlayerName1.setText(playerName1);
        textViewPlayerName2.setText(playerName2);
    }


    public boolean isSquareOccupied(int squareNumber){
        if(isSquareOccupiedArray[squareNumber] == 0){
            return false;
        }else return true;
    }

    public void setSquareOccupied(int squareNumber){
        isSquareOccupiedArray[squareNumber] = 1;
    }

    public void setPlayerAction(ImageView imageView,int squareNumber){
        if (!isSquareOccupied(squareNumber)){
            if (getPlayerTern() == 0){
                imageView.setImageResource(R.drawable.circle_icon);
                setSquareOccupied(squareNumber);
                recordPlayerMove[squareNumber] = 0;
                if (isSuccess()){
                        Toast.makeText(this, textViewPlayerName1.getText().toString()+" is the winner!", Toast.LENGTH_SHORT).show();
                        Intent winnerIntent= new Intent(this,WinnerPage.class);
                        String winner1 = textViewPlayerName1.getText().toString();
                        winnerIntent.putExtra("winnerName",winner1);
                    Log.d("textView",textViewPlayerName2.getText().toString());
                        startActivity(winnerIntent);

                }
                setPlayerTern(1);
            }else {
                imageView.setImageResource(R.drawable.cross_icon);
                setSquareOccupied(squareNumber);
                recordPlayerMove[squareNumber] = 1;
                if (isSuccess()){
                    Toast.makeText(this, textViewPlayerName2.getText().toString()+" is the winner!", Toast.LENGTH_SHORT).show();
                    Intent winnerIntent= new Intent(this,WinnerPage.class);
                    String winner2 = textViewPlayerName2.getText().toString();
                    winnerIntent.putExtra("winnerName",winner2);
                    Log.d("textView",textViewPlayerName2.getText().toString());
                    startActivity(winnerIntent);
                }
                setPlayerTern(0);
            }
        }else {
            Toast.makeText(this, "occupied", Toast.LENGTH_SHORT).show();
        }

        Log.d("recordPlayerMove", Arrays.toString(recordPlayerMove));
    }

    public boolean isSuccess(){

        for (int i = 0; i < successArrayCombination.size(); i++) {
            int[] currentCombination = successArrayCombination.get(i);
//            Log.d("isSuccess", Arrays.toString(currentCombination));
//            Log.d("isSuccess", String.valueOf(recordPlayerMove[currentCombination[0]]));
//            Log.d("isSuccess", String.valueOf(recordPlayerMove[currentCombination[1]]));
//            Log.d("isSuccess", String.valueOf(recordPlayerMove[currentCombination[2]]));

            if(recordPlayerMove[currentCombination[0]] == getPlayerTern()&&
                    recordPlayerMove[currentCombination[1]] == getPlayerTern() &&
                    recordPlayerMove[currentCombination[2]] == getPlayerTern()){
                return true;
            }else {
                Log.d("isSuccess","non-success");
            }
        }
        return false;
    }

    public void addCombinations(ArrayList<int[]> successArrayCombination){
        successArrayCombination.add(new int[] {7,8,9});
        successArrayCombination.add(new int[] {4,5,6});
        successArrayCombination.add(new int[] {1,2,3});
        successArrayCombination.add(new int[] {1,4,7});
        successArrayCombination.add(new int[] {2,5,8});
        successArrayCombination.add(new int[] {3,6,9});
        successArrayCombination.add(new int[] {1,5,9});
        successArrayCombination.add(new int[] {3,5,7});

        recordPlayerMove = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        isSquareOccupiedArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTern = 0;
    }

    public void restartGame(){
        square1.setImageResource(R.drawable.square_blue);
        square2.setImageResource(R.drawable.square_blue);
        square3.setImageResource(R.drawable.square_blue);
        square4.setImageResource(R.drawable.square_blue);
        square5.setImageResource(R.drawable.square_blue);
        square6.setImageResource(R.drawable.square_blue);
        square7.setImageResource(R.drawable.square_blue);
        square8.setImageResource(R.drawable.square_blue);
        square9.setImageResource(R.drawable.square_blue);


    }

    public void setOnClickSquare1(View view){
        setPlayerAction(square1,1);
    }

    public void setOnClickSquare2(View view){
        setPlayerAction(square2,2);
    }

    public void setOnClickSquare3(View view){
        setPlayerAction(square3,3);
    }

    public void setOnClickSquare4(View view){
        setPlayerAction(square4,4);
    }

    public void setOnClickSquare5(View view){
        setPlayerAction(square5,5);
    }

    public void setOnClickSquare6(View view){
        setPlayerAction(square6,6);
    }

    public void setOnClickSquare7(View view){
        setPlayerAction(square7,7);
    }

    public void setOnClickSquare8(View view){
        setPlayerAction(square8,8);
    }

    public void setOnClickSquare9(View view){
        setPlayerAction(square9,9);
    }


    public int getPlayerTern() {
        return playerTern;
    }

    public void setPlayerTern(int playerTern) {
        this.playerTern = playerTern;
    }
}