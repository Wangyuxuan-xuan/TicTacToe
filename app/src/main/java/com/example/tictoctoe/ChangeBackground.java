package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeBackground extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> cities;

    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);

        String[] originalCities = getResources().
                getStringArray(R.array.Background_array);
        cities = new ArrayList<>(Arrays.asList(originalCities));

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(adapter);

        Button buttonChange = findViewById(R.id.button_change);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeBackground.this,MainActivity.class);
                intent.putExtra("backgroundImage",R.drawable.cross_icon);
                startActivity(intent);

                //mainActivity.setBackgroundImage();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedImage = listView.getItemAtPosition(i).toString();
                //Log.d("listdebug", "clicked city was " + clickedImage);
                Intent intent = new Intent(ChangeBackground.this,MainActivity.class);
                switch (i){
                    case 0:
                        intent.putExtra("backgroundImage",R.drawable.cross_icon);
                        Log.d("listdebug", "clicked city was " + clickedImage);
                        break;
                    case 1:
                        intent.putExtra("backgroundImage",R.drawable.circle_icon);
                        Log.d("listdebug", "clicked city was " + clickedImage);
                        break;
                    case 2:
                        intent.putExtra("backgroundImage",R.drawable.game_background);
                        break;
                    case 3:
                        intent.putExtra("backgroundImage",R.drawable.main_background);
                        break;

                }


                //intent.putExtra("backgroundImage",R.drawable.cross_icon);
                startActivity(intent);

//                adapter.remove(adapter.getItem(i));
//                adapter.notifyDataSetChanged();
            }
        });

    }



}