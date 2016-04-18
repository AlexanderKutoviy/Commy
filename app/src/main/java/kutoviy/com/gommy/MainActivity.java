package kutoviy.com.gommy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import kutoviy.com.gommy.ga.TSP_GA;

public class MainActivity extends Activity {

    public final MyView mw = new MyView(this);

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
        linLayout.addView(mw);
        setContentView(linLayout);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), TSP_GA.class).putExtra("points", MyView.listOfDots));
            }
        };

    }
}

