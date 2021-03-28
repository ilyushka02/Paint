package com.example.paint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public  void mvMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logoutAccount(View view) {
        URL url = null;
        try {
            url = new URL(MainActivity.SERVER_URL + "/api/logout/" + MainActivity.profileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MainActivity.NetworkTask t = new MainActivity.NetworkTask();

        try {
            JSONObject data = new JSONObject();
            HashMap<String, String> props = new HashMap<>();
            props.put("Content-Type", "application/json");
            props.put("token", MainActivity.authToken);
            MainActivity.Request request = new MainActivity.Request(url, "GET", props, data);

            t.execute(request);

            JSONObject result = t.get();
            Log.i(MainActivity.class.getSimpleName(), "OUTPUT Result: " + result);
            if (result != null) {
                MainActivity.authToken = "";
                MainActivity.profileName = "";
                mvMainActivity();
            } else {
                Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAccount(View view) {
        URL url = null;

        try {
            url = new URL(MainActivity.SERVER_URL + "/api/logout/" + MainActivity.profileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MainActivity.NetworkTask t = new MainActivity.NetworkTask();

        try {
            JSONObject data = new JSONObject();
            HashMap<String, String> props = new HashMap<>();
            props.put("Content-Type", "application/json");
            props.put("token", MainActivity.authToken);
            MainActivity.Request request = new MainActivity.Request(url, "GET", props, data);

            t.execute(request);

            JSONObject result = t.get();
            Log.i(MainActivity.class.getSimpleName(), "OUTPUT Result: " + result);
            if (result != null) {
                MainActivity.authToken = "";
                MainActivity.profileName = "";
            } else {
                Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        }

        try {
            url = new URL(MainActivity.SERVER_URL + "/api/deleteaccount/" + MainActivity.profileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            JSONObject data = new JSONObject();
            HashMap<String, String> props = new HashMap<>();
            props.put("Content-Type", "application/json");
            props.put("token", MainActivity.authToken);
            MainActivity.Request request = new MainActivity.Request(url, "POST", props, data);

            t.execute(request);

            JSONObject result = t.get();
            Log.i(MainActivity.class.getSimpleName(), "OUTPUT Result: " + result);
            MainActivity.authToken = "";
            MainActivity.profileName = "";
            mvMainActivity();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
        }
    }
}