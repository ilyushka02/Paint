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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

        URL url = null;
        try {
            url = new URL(MainActivity.SERVER_URL + "/api/login");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MainActivity.NetworkTask t = new MainActivity.NetworkTask();

        try {
            JSONObject data = new JSONObject();
            data.put("login", name);
            data.put("password", password);
            HashMap<String, String> props = new HashMap<>();
            props.put("Content-Type", "application/json");
            MainActivity.Request request = new MainActivity.Request(url, "POST", props, data);

            t.execute(request);

            JSONObject result = t.get();
            Log.i(MainActivity.class.getSimpleName(), "OUTPUT Result: " + result);
            if (result != null) {
                MainActivity.authToken = result.getString("token");
                MainActivity.profileName = name;
                goUserActivity();
            } else {
                Toast.makeText(this, "ERROR login acc", Toast.LENGTH_SHORT).show();
            }
            Log.i(MainActivity.class.getSimpleName(), "OUTPUT Token: " + MainActivity.authToken);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR login acc", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR login acc", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR login acc", Toast.LENGTH_SHORT).show();
        }
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