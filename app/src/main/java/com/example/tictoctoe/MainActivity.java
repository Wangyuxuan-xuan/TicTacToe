package com.example.tictoctoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictoctoe.fragments.ChangeBgFragment;
import com.example.tictoctoe.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout myNavDrawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ConstraintLayout mainPageBackground;

    TextView textViewPlayerName1;
    TextView textViewPlayerName2;
    Button changeBgBtn;
    Button signOutBtn;
    ImageView square1,square2,square3,square4,square5,square6,square7,square8,square9;
    ImageView icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon9;

    final int DEFAULT_VALUE = 0;
    final String STORED_BG_KEY = "backgroundImageID";
    final String LOGIN_STATUS_KEY = "login_status";
    int storedBackground;
    SharedPreferences sharedPreferences;
    private final String sharedPrefFileName = "com.example.tictoctoe.StoredBackgroundPref";

    private int recordPlayerMove[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private int isSquareOccupiedArray[] = {-1,0,0,0,0,0,0,0,0,0};
    private int playerTern = 0;
    private ArrayList<int[]> successArrayCombination = new ArrayList<int[]>();

    private Boolean loginStatus = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//--------------------------------navigation drawer---------------------------------------------
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        myNavDrawer = findViewById(R.id.my_nav_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, myNavDrawer, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        myNavDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       //NavigationView navigationView = myNavDrawer;
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//       fragmentTransaction.replace(R.id.fragment_Container,
//                new ProfileFragment()).commit();
//        navigationView.setCheckedItem(R.id.nav_profile);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        fragmentTransaction.replace(R.id.fragment_Container, profileFragment);
        fragmentTransaction.commit();


//--------------------------------Get intent---------------------------------------------
        Intent intent = getIntent();
        String playerName1 = intent.getStringExtra("playerName1");
        String playerName2 = intent.getStringExtra("playerName2");

        Log.d("playerNames",playerName1+"  "+playerName2);



//--------------------------------find views---------------------------------------------
        textViewPlayerName1 = findViewById(R.id.textViewPlayerName1);
        textViewPlayerName2 = findViewById(R.id.textViewPlayerName2);
        changeBgBtn = findViewById(R.id.button_changeBg);
        signOutBtn = findViewById(R.id.button_signOut);
        mainPageBackground = (ConstraintLayout)findViewById(R.id.constraintLayout_background);

        square1 = findViewById(R.id.square1);
        square2 = findViewById(R.id.square2);
        square3 = findViewById(R.id.square3);
        square4 = findViewById(R.id.square4);
        square5 = findViewById(R.id.square5);
        square6 = findViewById(R.id.square6);
        square7 = findViewById(R.id.square7);
        square8 = findViewById(R.id.square8);
        square9 = findViewById(R.id.square9);

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);
        icon6 = findViewById(R.id.icon6);
        icon7 = findViewById(R.id.icon7);
        icon8 = findViewById(R.id.icon8);
        icon9 = findViewById(R.id.icon9);
//--------------------------------Shared Pref---------------------------------------------
        sharedPreferences = getSharedPreferences(sharedPrefFileName,MODE_PRIVATE);
        storedBackground = sharedPreferences.getInt(STORED_BG_KEY,0);

        if (storedBackground == DEFAULT_VALUE){
            setBackgroundImage(R.drawable.main_background);
        }else {
            setBackgroundImage(storedBackground);
        }
        //get Intent from change background
        Intent intent1 = getIntent();
        int bgImg = intent1.getIntExtra("backgroundImage",DEFAULT_VALUE);
        if(bgImg != DEFAULT_VALUE){
            setBackgroundImage(bgImg);
            storedBackground = bgImg;
        }
//--------------------------------Manage Login Status---------------------------------------------

        loginStatus = sharedPreferences.getBoolean(LOGIN_STATUS_KEY,false);
        if (loginStatus){
            changeBgBtn.setVisibility(View.VISIBLE);
            signOutBtn.setVisibility(View.VISIBLE);
        }else {
            changeBgBtn.setVisibility(View.INVISIBLE);
            signOutBtn.setVisibility(View.INVISIBLE);
        }

//--------------------------------Logic Part---------------------------------------------
        restartGame();

        addCombinations(successArrayCombination);
        setTextPlayerName(playerName1,playerName2);


        if(savedInstanceState != null){
            recordPlayerMove = savedInstanceState.getIntArray("recordPlayerMove");
            isSquareOccupiedArray = savedInstanceState.getIntArray("isSquareOccupiedArray");

            icon1.setVisibility(savedInstanceState.getInt("icon1Visibility"));
            icon2.setVisibility(savedInstanceState.getInt("icon2Visibility"));
            icon3.setVisibility(savedInstanceState.getInt("icon3Visibility"));
            icon4.setVisibility(savedInstanceState.getInt("icon4Visibility"));
            icon5.setVisibility(savedInstanceState.getInt("icon5Visibility"));
            icon6.setVisibility(savedInstanceState.getInt("icon6Visibility"));
            icon7.setVisibility(savedInstanceState.getInt("icon7Visibility"));
            icon8.setVisibility(savedInstanceState.getInt("icon8Visibility"));
            icon9.setVisibility(savedInstanceState.getInt("icon9Visibility"));

            ImageView imageViews[] ={null,icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon9};
            for (int i = 1 ; i < recordPlayerMove.length; i++) {
                if(recordPlayerMove[i] == 0){
                    imageViews[i].setImageResource(R.drawable.circle_icon);
                }
                if(recordPlayerMove[i] == 1){
                    imageViews[i].setImageResource(R.drawable.cross_icon);
                }
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STORED_BG_KEY, storedBackground);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putIntArray("recordPlayerMove",recordPlayerMove);
        outState.putIntArray("isSquareOccupiedArray",isSquareOccupiedArray);

        outState.putInt("icon1Visibility",icon1.getVisibility());
        outState.putInt("icon2Visibility",icon2.getVisibility());
        outState.putInt("icon3Visibility",icon3.getVisibility());
        outState.putInt("icon4Visibility",icon4.getVisibility());
        outState.putInt("icon5Visibility",icon5.getVisibility());
        outState.putInt("icon6Visibility",icon6.getVisibility());
        outState.putInt("icon7Visibility",icon7.getVisibility());
        outState.putInt("icon8Visibility",icon8.getVisibility());
        outState.putInt("icon9Visibility",icon9.getVisibility());

        //outState.putInt("iconImageResource",R.);
    }



    public void setSignOutBtnOnClick(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_STATUS_KEY, false);
        editor.apply();

        changeBgBtn.setVisibility(View.INVISIBLE);
        signOutBtn.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "Now you signed out", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "item clicked", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new ProfileFragment()).commit();
                Toast.makeText(this, "profile clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_changeBg:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new ChangeBgFragment()).commit();
                Toast.makeText(this, "ChangeBg clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        myNavDrawer.closeDrawer(GravityCompat.START);
        return true;

    }


    /**
     * override the onOptionsItemSelected()
     *  function to implement
     *  the item click listener callback
     *  to open and close the navigation
     *   drawer when the icon is clicked
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOnChangeBgBtnClick(View view){
        Intent intent = new Intent(this,ChangeBackground.class);
        startActivity(intent);
    }

    public void setBackgroundImage(int bgImg){
        mainPageBackground.setBackgroundResource(bgImg);
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


    public void setPlayerAction(ImageView icon,int squareNumber){


        if (!isSquareOccupied(squareNumber)){
            if (getPlayerTern() == 0){


                icon.setImageResource(R.drawable.circle_icon);
                icon.setVisibility(View.VISIBLE);
                icon.setScaleX(0.8f);
                icon.setScaleY(0.8f);

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
            }
            else {


                icon.setImageResource(R.drawable.cross_icon);
                icon.setVisibility(View.VISIBLE);
                icon.setScaleX(0.8f);
                icon.setScaleY(0.8f);

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

        Log.d("check Draw",String.valueOf(isDraw()));
        if(isDraw()){

            Toast.makeText(this, "It is a Draw !", Toast.LENGTH_SHORT).show();
            Intent winnerIntent= new Intent(this,WinnerPage.class);
            winnerIntent.putExtra("isDraw", true);
            startActivity(winnerIntent);
        }
        Log.d("isSquareOccupiedArray", Arrays.toString(isSquareOccupiedArray));
        ///Log.d("recordPlayerMove", Arrays.toString(recordPlayerMove));
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

    public boolean isDraw(){
        for (int i : isSquareOccupiedArray) {
            if (i == 0) {
                return false;
            }
        }
        return true;
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

        isSquareOccupiedArray = new int[]{-1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTern = 0;
    }

    public void restartGame(){

        icon1.setVisibility(View.INVISIBLE);
        icon2.setVisibility(View.INVISIBLE);
        icon3.setVisibility(View.INVISIBLE);
        icon4.setVisibility(View.INVISIBLE);
        icon5.setVisibility(View.INVISIBLE);
        icon6.setVisibility(View.INVISIBLE);
        icon7.setVisibility(View.INVISIBLE);
        icon8.setVisibility(View.INVISIBLE);
        icon9.setVisibility(View.INVISIBLE);
    }

    public void setOnClickSquare1(View view){
        setPlayerAction(icon1,1);
    }

    public void setOnClickSquare2(View view){
        setPlayerAction(icon2,2);
    }

    public void setOnClickSquare3(View view){
        setPlayerAction(icon3,3);
    }

    public void setOnClickSquare4(View view){
        setPlayerAction(icon4,4);
    }

    public void setOnClickSquare5(View view){
        setPlayerAction(icon5,5);
    }

    public void setOnClickSquare6(View view){
        setPlayerAction(icon6,6);
    }

    public void setOnClickSquare7(View view){
        setPlayerAction(icon7,7);
    }

    public void setOnClickSquare8(View view){
        setPlayerAction(icon8,8);
    }

    public void setOnClickSquare9(View view){
        setPlayerAction(icon9,9);
    }


    public int getPlayerTern() {
        return playerTern;
    }

    public void setPlayerTern(int playerTern) {
        this.playerTern = playerTern;
    }


    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
}