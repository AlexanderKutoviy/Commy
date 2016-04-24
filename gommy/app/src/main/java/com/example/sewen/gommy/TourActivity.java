package com.example.sewen.gommy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sewen.gommy.ga.TSP_GA;

import java.util.ArrayList;

public class TourActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final MyView mw = new MyView(this);
        Intent intent = getIntent();
        mw.listOfDots = intent.getParcelableArrayListExtra("tour");
        super.onCreate(savedInstanceState);
       setContentView(mw);
    }

    class MyView extends View {
        public ArrayList<PointF> listOfDots = new ArrayList<>();
        Paint p;
        float x = 100;
        float y = 100;

        public MyView(Context context) {
            super(context);
            p = new Paint();
            p.setColor(Color.BLUE);
        }

        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < listOfDots.size()-1; i++) {
                canvas.drawCircle(listOfDots.get(i).x, listOfDots.get(i).y, 10, p);
                if (listOfDots.get(i + 1) == null) {
                    canvas.drawLine(listOfDots.get(i).x, listOfDots.get(i).y, listOfDots.get(0).x, listOfDots.get(0).y, p);
                    break;
                } else  {
                    canvas.drawLine(listOfDots.get(i).x, listOfDots.get(i).y, listOfDots.get(i + 1).x, listOfDots.get(i + 1).y, p);
                }
            }
            canvas.drawCircle(listOfDots.get(listOfDots.size()-1).x, listOfDots.get(listOfDots.size()-1).y, 10, p);
            canvas.drawLine(listOfDots.get(listOfDots.size()-1).x, listOfDots.get(listOfDots.size()-1).y, listOfDots.get(0).x, listOfDots.get(0).y, p);
            listOfDots = null;
        }
    }
}
