package com.example.tapneatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://tapn-eat-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText UserID = findViewById(R.id.userID);
        final EditText passwordUser = findViewById(R.id.userPwd);
        final Button LoginBtn = findViewById(R.id.loginBtn);
        final TextView GoRegisterNow = findViewById(R.id.registerNowBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String UseridTxt = UserID.getText().toString();
                final String passwordTxt = passwordUser.getText().toString();

                if (UseridTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your ID or password",
                            Toast.LENGTH_SHORT).show();

                }
                else{
                    databaseReference.child("userID").addListenerForSingleValueEvent
                            (new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if user has been register in the system oor exist in database

                            if (snapshot.hasChild(UseridTxt)){
                                //this is ID have exist in database
                                //check if password match to ID in database
                                final String getPwd = snapshot.child(UseridTxt).child("Password").
                                        getValue(String.class);
                                if(getPwd.equals(passwordTxt)){
                                    Toast.makeText(Login.this, "Successfully Logged In",
                                            Toast.LENGTH_SHORT).show();

                                    // intent to go main page / main activity
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Password!!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Wrong userID!!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        GoRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go register account
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}