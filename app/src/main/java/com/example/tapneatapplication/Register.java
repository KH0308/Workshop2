package com.example.tapneatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://tapn-eat-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText firstname = findViewById(R.id.firstName);
        final EditText lastname = findViewById(R.id.lastName);
        final EditText email = findViewById(R.id.userEmail);
        final EditText IDUser = findViewById(R.id.userID);
        final EditText passwordUser = findViewById(R.id.userPwd);
        final EditText ComPasswordUser = findViewById(R.id.ComUserPwd);

        final Button regisButton = findViewById(R.id.regBtn);
        final TextView loginNowButton = findViewById(R.id.loginNowBtn);

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstnameTxt = firstname.getText().toString();
                final String lastNameTxt = lastname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String IDUserTxt = IDUser.getText().toString();
                final String pwdUserTxt = passwordUser.getText().toString();
                final String comPwdUserTxt = ComPasswordUser.getText().toString();

                if (firstnameTxt.isEmpty() || lastNameTxt.isEmpty() || emailTxt.isEmpty() ||
                        IDUserTxt.isEmpty() || pwdUserTxt.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!pwdUserTxt.equals(comPwdUserTxt)){
                    Toast.makeText(Register.this, "Password doesn't match with top",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check matric has been used register before

                            if (snapshot.hasChild(IDUserTxt)){
                                Toast.makeText(Register.this, "ID has been registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("userID").child(IDUserTxt).child("First Name").
                                        setValue(firstnameTxt);
                                databaseReference.child("userID").child(IDUserTxt).child("Last Name").
                                        setValue(lastNameTxt);
                                databaseReference.child("userID").child(IDUserTxt).child("User Email").
                                        setValue(emailTxt);
                                databaseReference.child("userID").child(IDUserTxt).child("Password").
                                        setValue(pwdUserTxt);
                                databaseReference.child("userID").child(IDUserTxt).child("Confirm Password").
                                        setValue(comPwdUserTxt);

                                Toast.makeText(Register.this, "User has been successfully register.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        loginNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}