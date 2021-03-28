package com.example.paint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    public void goUsActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    //Create Acc
    public void createAccount(View view) {
        EditText inputName = findViewById(R.id.textInputNameReg);
        String name = inputName.getText().toString().trim();

        EditText inputPassword = findViewById(R.id.textInputPasswordReg);
        String password = inputPassword.getText().toString().trim();

        EditText inputPasswordSecond = findViewById(R.id.textInputPasswordAgainReg);
        String passwordSecond = inputPasswordSecond.getText().toString().trim();

        goUsActivity();
        if(password.equals(passwordSecond)){
            String createAccount = " USERNAME " + name + "  PASSWORD " +  password + " PASSWORDSECOND "+ passwordSecond;
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            Log.d(RegistrationActivity.class.getSimpleName(), createAccount);
        } else {
            Toast.makeText(this, "ERROR: Пароли не совпадают. Попробуйте еще раз", Toast.LENGTH_SHORT).show();
        }

    }


}
