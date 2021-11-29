package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeBackground extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);

        String[] originalCities = getResources().
                getStringArray(R.array.Background_array);
        backgrounds = new ArrayList<>(Arrays.asList(originalCities));

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, backgrounds);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ChangeBackground.this,MainActivity.class);
                switch (i){
                    case 0:
                        intent.putExtra("backgroundImage",R.drawable.wallpaper_1);
                        break;
                    case 1:
                        intent.putExtra("backgroundImage",R.drawable.wallpaper_2);
                        break;
                    case 2:
                        intent.putExtra("backgroundImage",R.drawable.wallpaper_3);
                        break;
                    case 3:
                        intent.putExtra("backgroundImage",R.drawable.main_background);
                        break;
                    case 4:
                        intent.putExtra("backgroundImage",R.drawable.game_background);
                        break;
                    case 5:
                        intent.putExtra("backgroundImage",R.drawable.wallpaper_wild);
                        break;
                    case 6:
                        intent.putExtra("backgroundImage",R.drawable.wallpaper_battlefield1);
                        break;
                }
                startActivity(intent);
            }
        });

    }



}