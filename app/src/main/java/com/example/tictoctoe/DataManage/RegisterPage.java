package com.example.tictoctoe.DataManage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictoctoe.MainActivity;
import com.example.tictoctoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Arrays;

import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {
    ArrayList<Users> arrayList = new ArrayList<Users>();
    EditText editTextEmail;
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonRegister;
    TextView warningMsg;
    Button getData;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://friendschat-15843-default-rtdb.europe-west1.firebasedatabase.app/");

    final String USER_ID_KEY = "userID";
    private final String sharedPrefFileName = "com.example.tictoctoe.StoredBackgroundPref";
    int userID = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        editTextEmail = findViewById(R.id.editText_Email);
        editTextUsername = findViewById(R.id.editText_Username);
        editTextPassword = findViewById(R.id.editText_Password);
        buttonRegister = findViewById(R.id.button_register);
        warningMsg = findViewById(R.id.textView_warringMessage);

        progressDialog = new ProgressDialog(this);
        getData = findViewById(R.id.button_getData);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");

//        //check if user already logged in
//        if(!StoreData.getEmailData(this).isEmpty()){
//            Intent intent = new Intent(RegisterPage.this,MainActivity.class);
//            intent.putExtra("email",StoreData.getEmailData(this));
//            intent.putExtra("username",StoreData.getUsernameData(this));
//            intent.putExtra("password","");
//            startActivity(intent);
//            finish();
//        }

        sharedPreferences = getSharedPreferences(sharedPrefFileName,MODE_PRIVATE);

    }




    public void  setRegisterBtnOnClick(View view){

        progressDialog.show();

        String email = editTextEmail.getText().toString();
        String username = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        if (email.isEmpty() || username.isEmpty() || password.isEmpty()){
            progressDialog.dismiss();
            warningMsg.setText(getString(R.string.warning));
        }else {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    progressDialog.dismiss();

                    if(snapshot.child("users").hasChild(email)){
                        Toast.makeText(RegisterPage.this, "Email already registered!", Toast.LENGTH_SHORT).show();

                    }else {
                        //databaseReference.child("users").child(email).setValue(username);
                        Users user = new Users(email,password);

                        userID = sharedPreferences.getInt(USER_ID_KEY,0);
                        databaseReference.child("users"+String.valueOf(userID)).setValue(user);
                        userID++;



//                        String emailGet =  snapshot.child("users").child(email).getKey();
//                        String passwd = "";
                        //String passwd = snapshot.child("users").getChildren();
//                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                            passwd = (String) childSnapshot.child(email).getValue();
//                        }
//
//                        StoreData.saveEmailData(email,RegisterPage.this);
//                        StoreData.saveUsernameData(username,RegisterPage.this);

                        Toast.makeText(RegisterPage.this, "success !", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("username",username);
                        intent.putExtra("password",password);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });


        }


    }



    public void setonGETdateclicl(View view){

        databaseReference.addValueEventListener(new ValueEventListener() {
            Users user0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot userSnapshot : snapshot.getChildren()){

                    user0 = userSnapshot.getValue(Users.class);
                    arrayList.add(user0);
                    //Toast.makeText(RegisterPage.this, user0.email+"   "+user0.password, Toast.LENGTH_SHORT).show();


                    //Log.d("user_data",arrayList.get(0).email+"   "+arrayList.get(0).password);
                }
                Log.d("user_data",user0.email+"   "+user0.password);
                Log.d("user_data",arrayList.size()+"");
                Log.d("user_data",arrayList.get(0).email+arrayList.get(1).email+arrayList.get(2).email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userID);
        editor.apply();
    }

}