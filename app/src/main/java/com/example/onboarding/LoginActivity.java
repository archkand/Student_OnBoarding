package com.example.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onboarding.Pojo.Profile;

public class LoginActivity extends AppCompatActivity {

    Profile profile = new Profile();
    String loginURL = "http://localhost:3000/login/student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        final EditText emailId = findViewById(R.id.emailId);
        final EditText studentId = findViewById(R.id.studentID);
        final Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setId(emailId.getText().toString());
                profile.setStudentID(studentId.getText().toString());
              //  Log.d("chella","Profile");
                new LoginAPI(loginURL,profile,LoginActivity.this).execute();


            }
        });
    }
}
