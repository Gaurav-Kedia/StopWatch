package com.example.gaurav.stopwatch;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button start, reset;
    TextView timer;

    long starttime = 01;
    long timeinmilli = 01;
    long timeswapbuff = 01;
    long updatetime = 01;

    int t = 1;
    int mins = 0;
    int secs = 0;
    int milli = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        reset = (Button) findViewById(R.id.reset);
        timer = (TextView) findViewById(R.id.timer);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t == 1){
                    start.setText("PAUSE");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updatetimer, 0);
                    t = 0;
                }
                else {
                    start.setText("START");
                    timer.setTextColor(Color.BLUE);
                    timeswapbuff += timeinmilli;
                    handler.removeCallbacks(updatetimer);
                    t = 1;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttime = 01;
                timeinmilli = 01;
                timeswapbuff = 01;
                updatetime = 01;

                t = 1;
                mins = 0;
                secs = 0;
                milli = 0;

                start.setText("START");
                handler.removeCallbacks(updatetimer);
                timer.setText("00:00:00");
            }
        });
    }

    public Runnable updatetimer = new Runnable() {
        @Override
        public void run() {
            timeinmilli = SystemClock.uptimeMillis() - starttime;
            updatetime = timeswapbuff + timeinmilli;
            secs = (int) (updatetime/1000);
            mins = secs / 60;
            secs = secs % 60;
            milli = (int) (updatetime%1000);
            timer.setText(String.format("" + mins) + ":" + String.format("%02d", secs)+ ":" + String.format("%03d", milli));
            timer.setTextColor(Color.RED);
            handler.postDelayed(this,0);
        }
    };
}
