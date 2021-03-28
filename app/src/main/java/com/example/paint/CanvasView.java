package com.example.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

public class CanvasView extends View {
    private Bitmap image;

    public CanvasView(Context context){
        super(context);
    }


    public Canvas getCanvas() {
        return new Canvas(image);
    }

    protected void onClear(){
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);
    }

    @Override
    protected  void  onMeasure(int withMeasureSpec, int heightMeasureSpec){
        super.onMeasure(withMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected  void onLayout(boolean change, int left, int top, int right, int bottom){
        super.onLayout(change, left, top, right, bottom);
        Bitmap.Config config = Bitmap.Config.RGB_565;
        image = Bitmap.createBitmap(getWidth(), getHeight(), config);
        onClear();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(CanvasView.class.getSimpleName(), "Draw");
        Paint paint = new Paint();
        canvas.drawBitmap(image, 0 , 0, paint);
    }



}
