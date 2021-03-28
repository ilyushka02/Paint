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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

            URL url = null;
            try {
                url = new URL(MainActivity.SERVER_URL + "/api/signup");
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
                    MainActivity.profileName = result.getString("login");
                } else {
                    Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
                    Log.d(RegistrationActivity.class.getSimpleName(), "ERROR check me");
                    return;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
                return;
            }


            try {
                url = new URL(MainActivity.SERVER_URL + "/api/login");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            t = new MainActivity.NetworkTask();
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
                    goUsActivity();
                } else {
                    Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
                }
                Log.d(MainActivity.class.getSimpleName(), "OUTPUT Token: " + MainActivity.authToken);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR create acc", Toast.LENGTH_SHORT).show();
            }

            String createAccount = " USERNAME " + name + "  PASSWORD " +  password + " PASSWORDSECOND "+ passwordSecond;
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            Log.d(RegistrationActivity.class.getSimpleName(), createAccount);
        } else {
            Toast.makeText(this, "ERROR: Пароли не совпадают. Попробуйте еще раз", Toast.LENGTH_SHORT).show();
        }

    }


}
