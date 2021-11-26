package com.example.tictoctoe.DataManage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class StoreData {

    public static void saveEmailData(String data, Context context){
            try {
                FileOutputStream fileOutputStream = context.openFileOutput("userEmailData.txt",Context.MODE_PRIVATE);
                fileOutputStream.write(data.getBytes());
                fileOutputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    public static void saveUsernameData(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("userUsernameData.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getEmailData(Context context){
        String data = "";
        try{
            FileInputStream fileInputStream = context.openFileInput("userEmailData.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    public static String getUsernameData(Context context){
        String data = "";
        try{
            FileInputStream fileInputStream = context.openFileInput("userUsernameData.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
