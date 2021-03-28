package com.example.paint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.textfield.TextInputLayout;

public class ProfilActivity extends AppCompatActivity {

    public void goUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //Metod Login
    public void login(View view){
        EditText inputName = findViewById(R.id.textInputNameLogin);
        String name = inputName.getText().toString().trim();

        EditText inputPassword = findViewById(R.id.textInputNamePasswordLogin);
        String password = inputPassword.getText().toString().trim();

            goUserActivity();
            Toast.makeText(this, "Operation successful", Toast.LENGTH_SHORT).show();
            Log.d(ProfilActivity.class.getSimpleName(), "Name " + name + " pass " + password);
    }
    //Back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void mvRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

}