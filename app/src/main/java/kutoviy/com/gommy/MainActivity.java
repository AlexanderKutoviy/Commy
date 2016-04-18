package kutoviy.com.gommy;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import kutoviy.com.gommy.ga.TSP_GA;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setText("Tap to put cities");
        linLayout.addView(tv);
        Button btnBuild = new Button(this);
        btnBuild.setText("Button");
        linLayout.addView(btnBuild);
        linLayout.addView(new MyView(this));
        setContentView(linLayout);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionsServiceIntent = new Intent(getApplicationContext(), TSP_GA.class);
                startService(transactionsServiceIntent);
            }
        };

    }

    class MyView extends View {
        Paint p;
        float x = 100;
        float y = 100;
        List<Point> listOfDots = new LinkedList<>();

        public MyView(Context context) {
            super(context);
            p = new Paint();
            p.setColor(Color.RED);
        }

        protected void onDraw(Canvas canvas) {
            Button buildButton = new Button(getContext());
            for (int i = 0; i < listOfDots.size(); i++) {
                canvas.drawCircle(listOfDots.get(i).x, listOfDots.get(i).y, 5, p);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float evX = event.getX();
            float evY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = evX;
                    y = evY;
                    listOfDots.add(new Point(evX, evY));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
    }

    class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x=>" + x + "y=>" + y;
        }
    }
}

