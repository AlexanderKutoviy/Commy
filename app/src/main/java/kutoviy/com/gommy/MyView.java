package kutoviy.com.gommy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

class MyView extends View {
    Paint p;
    float x = 100;
    float y = 100;
    public static ArrayList<PointF> listOfDots = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        p = new Paint();
        p.setColor(Color.RED);
    }

    protected void onDraw(Canvas canvas) {
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
                listOfDots.add(new PointF(evX, evY));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
