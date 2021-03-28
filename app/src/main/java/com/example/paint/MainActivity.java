package com.example.paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements View.OnTouchListener,View.OnClickListener{
    public static String SERVER_URL = "http://192.168.43.24:3000";
    public static String authToken = "";
    public static String profileName = "";
    private String name = "Новый рисунок";

    private  CanvasView canvasView;
    private Path drawPath = new Path();
    private Paint drawPaint = new Paint();
    private Paint paint = new Paint();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        return true;
    }

    private void  openSet(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openProfil() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                canvasView.onClear();
                break;
            case R.id.save:
                save();
                return true;
            case R.id.MySettings:
                openSet();
                break;
            case R.id.myProfil:
                openProfil();
            //default:
            //return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = new CanvasView(this);
        FrameLayout layout = findViewById(R.id.canvas_frame);
        layout.addView(canvasView);

        Button buttonRed = findViewById(R.id.button_color_red);
        buttonRed.setOnClickListener(this);
        Button buttonGreen = findViewById(R.id.button_color_gren);
        buttonGreen.setOnClickListener(this);
        Button buttonBlue = findViewById(R.id.button_color_blue);
        buttonBlue.setOnClickListener(this);
        Button buttonOrange = findViewById(R.id.button_color_orange);
        buttonOrange.setOnClickListener(this);
        Button buttonBlack = findViewById(R.id.button_color_black);
        buttonBlack.setOnClickListener(this);
        ImageButton buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(this);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_color_red:
                drawPaint.setColor(Color.RED);
                break;
            case R.id.button_color_gren:
                drawPaint.setColor(Color.GREEN);
                break;
            case R.id.button_color_blue:
                drawPaint.setColor(Color.BLUE);
                break;
            case R.id.button_color_orange:
                drawPaint.setColor(Color.YELLOW);
                break;
            case R.id.button_color_black:
                drawPaint.setColor(Color.BLACK);
                break;
            case R.id.button_clear:
                drawPaint.setColor(Color.WHITE);
                break;
        }

        Log.d(MainActivity.class.getSimpleName() ,"click click click <3");

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Canvas canvas = canvasView.getCanvas();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo( event.getX(), event.getY());
                break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(event.getX(), event.getY());
                    break;
                    case MotionEvent.ACTION_UP:
                        canvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                    break;
            default:
                return false;
        }
        canvasView.invalidate();
        return true;
    }
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    public void save(){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Сохранение изображения");
        saveDialog.setMessage("Хотите сохранить изображение в гелерею?");
        saveDialog.setPositiveButton("Да", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                canvasView.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), canvasView.getDrawingCache(),
                        UUID.randomUUID().toString()+".png", "drawing");
                if(imgSaved!=null){
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Изображение успешно сохранено", Toast.LENGTH_SHORT);
                    savedToast.show();
                }
                else{
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Упс! Похоже, что возникла ошибка. Попробуйте выдать права приложению.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                canvasView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    //---------------------------------------------------------------------------------------------
    static class Request {
        public URL url;
        public String method;
        public HashMap<String, String> props;
        public JSONObject data;

        public Request(URL url, String method, HashMap<String, String> props, JSONObject data) {
            this.url = url;
            this.method = method;
            this.props = props;
            this.data = data;
        }
    }

    static class NetworkTask extends AsyncTask<Request, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Request[] objects) {
            return connectAndSend(objects[0]);
        }
    }

    private static JSONObject connectAndSend(Request request) {
        try {
            HttpURLConnection con = (HttpURLConnection) request.url.openConnection();
            con.setRequestMethod(request.method);
            for (Map.Entry<String, String> entry : request.props.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if (request.data.length() > 0) {
                OutputStream os = con.getOutputStream();
                os.write(request.data.toString().getBytes());
            }
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();
            if (result.startsWith("[")) {
                result = "{ \"uploads\": " + result + "}";
            }
            return new JSONObject(result);
        } catch (IOException | JSONException e) {
           Log.d(MainActivity.class.getSimpleName(), "ERROR check mee ");
            e.printStackTrace();
            return null;
        }
    }

}
