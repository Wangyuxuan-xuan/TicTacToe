package com.example.tictoctoe.DataManage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class RegisterPage extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonRegister;
    TextView warningMsg;


    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://friendschat-15843-default-rtdb.europe-west1.firebasedatabase.app/");

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
                        databaseReference.child("users").child(email).setValue(username);
                        databaseReference.child("users").child(email).setValue(password);

                        StoreData.saveEmailData(email,RegisterPage.this);
                        StoreData.saveUsernameData(username,RegisterPage.this);

                        Toast.makeText(RegisterPage.this, "Success!", Toast.LENGTH_SHORT).show();

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
}