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

import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button registerBtn;
    TextView warningMsg;
    Button getData;
    SharedPreferences sharedPreferences;
    TextView loginText;
    TextView registerText;
    Button loginBtn;
    TextView switchToLogin;
    TextView switchToRegister;

    ArrayList<Users> arrayListUsers = new ArrayList<Users>();
    MainActivity mainActivity = new MainActivity();
    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://friendschat-15843-default-rtdb.europe-west1.firebasedatabase.app/");

    final String USER_ID_KEY = "userID";
    final String LOGIN_STATUS_KEY = "login_status";
    private final String sharedPrefFileName = "com.example.tictoctoe.StoredBackgroundPref";
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        editTextEmail = findViewById(R.id.editText_Email);
        editTextPassword = findViewById(R.id.editText_Password);

        warningMsg = findViewById(R.id.textView_warringMessage);
        loginText = findViewById(R.id.textView_logIn_RegisterPage);
        registerText = findViewById(R.id.textView_Register);
        loginBtn = findViewById(R.id.button_login);
        registerBtn = findViewById(R.id.button_register);
        switchToLogin = findViewById(R.id.textView_switchToLogin);
        switchToRegister = findViewById(R.id.textView_switchToRegister);

        progressDialog = new ProgressDialog(this);
        getData = findViewById(R.id.button_getData);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");

        sharedPreferences = getSharedPreferences(sharedPrefFileName,MODE_PRIVATE);
        userID = sharedPreferences.getInt(USER_ID_KEY,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userID);
        editor.apply();
    }


    public void setLoginBtnOnClick(View view){
        progressDialog.show();

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            progressDialog.dismiss();
            warningMsg.setText(getString(R.string.warning));
        }else {

            databaseReference.addValueEventListener(new ValueEventListener() {
                Users user0;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    for(DataSnapshot userSnapshot : snapshot.getChildren()){

                        user0 = userSnapshot.getValue(Users.class);
                        arrayListUsers.add(user0);
                    }
                    Log.d("user_data",user0.email+"   "+user0.password);
                    Log.d("user_data", arrayListUsers.size()+"");
                   // Log.d("user_data", arrayListUsers.get(0).email+ arrayListUsers.get(1).email+ arrayListUsers.get(2).email);


                    for (int i = 0; i < arrayListUsers.size(); i++) {
                        if(email.equals(arrayListUsers.get(i).email)){
                            if(password.equals(arrayListUsers.get(i).password)){
                                Toast.makeText(RegisterPage.this, "Login Success !", Toast.LENGTH_SHORT).show();
                                mainActivity.setLoginStatus(true);
                                Intent intent = new Intent(RegisterPage.this,MainActivity.class);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(LOGIN_STATUS_KEY,true);
                                editor.apply();

                                //intent.putExtra("loginStatus",true);
                                startActivity(intent);
                            }else Toast.makeText(RegisterPage.this, "Email or Password is wrong !", Toast.LENGTH_SHORT).show();
                        }//else {Toast.makeText(RegisterPage.this, "No such Email !", Toast.LENGTH_SHORT).show();}
                    }
                    arrayListUsers.clear();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void  setRegisterBtnOnClick(View view){

        progressDialog.show();

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        if (email.isEmpty() || password.isEmpty()){
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

                        Toast.makeText(RegisterPage.this, "Register success !", Toast.LENGTH_SHORT).show();
                        //mainActivity.setLoginStatus(true);

                        Intent intent = new Intent(RegisterPage.this, MainActivity.class);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(LOGIN_STATUS_KEY,true);
                        editor.apply();

                        //intent.putExtra("loginStatus",true);
                        intent.putExtra("email",email);
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
                    arrayListUsers.add(user0);
                    //Toast.makeText(RegisterPage.this, user0.email+"   "+user0.password, Toast.LENGTH_SHORT).show();


                    //Log.d("user_data",arrayList.get(0).email+"   "+arrayList.get(0).password);
                }
                Log.d("user_data",user0.email+"   "+user0.password);
                Log.d("user_data", arrayListUsers.size()+"");
                Log.d("user_data", arrayListUsers.get(0).email+ arrayListUsers.get(1).email+ arrayListUsers.get(2).email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void setSwitchToRegisterOnClick(View view){
        loginText.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        switchToRegister.setVisibility(View.INVISIBLE);
        registerText.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.VISIBLE);
        switchToLogin.setVisibility(View.VISIBLE);
    }

    public void setSwitchToLoginOnClick(View view){
        loginText.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
        switchToRegister.setVisibility(View.VISIBLE);
        registerText.setVisibility(View.INVISIBLE);
        registerBtn.setVisibility(View.INVISIBLE);
        switchToLogin.setVisibility(View.INVISIBLE);
    }

}