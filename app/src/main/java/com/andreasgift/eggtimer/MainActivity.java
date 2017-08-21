package com.andreasgift.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/*Created by Andrea Liu
*Singapore, September 2017
*/

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private int maxTimer = 600;
    private TextView timerTV;
    private Button goButton;
    //status 0 = pause/stop 1=ticking 2=beeping
    private int status = 0;
    private int currentTimeLeft = 30;
    private CountDownTimer timer;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timerTV = (TextView) findViewById(R.id.timer_textview);
        goButton = (Button) findViewById(R.id.go_button);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.teapot);

        seekBar.setMax(maxTimer);
        seekBar.setProgress(currentTimeLeft);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*
    *Method when user click the button
    *Button will react play when the status is onPause/stop,
    * pause when the timer is ticking
    * stop mediaPlayer when the timer is beeping
    */
    public void goButton(View view) {
        if (status == 0) {

            goButton.setText("STOP");

            timer = new CountDownTimer(currentTimeLeft * 1000, 1000) {
                @Override
                public void onTick(long milisecBeforeFinish) {
                    updateProgress((int) milisecBeforeFinish / 1000);
                }

                @Override
                public void onFinish() {
                    mp.start();
                    updateProgress(0);
                    status = 2;
                }
            };
            timer.start();
            status = 1;
        }

        else if (status == 1) {
            timer.cancel();
            goButton.setText("GO!");
            updateProgress(currentTimeLeft);
            status = 0;
        }

        else {
            mp.stop();
            goButton.setText("GO!");
            status = 0;
        }
    }

    //Method to update the Timer TextView and data
    private void updateProgress(int timeInSec) {
        currentTimeLeft = timeInSec;

        int min = timeInSec / 60;
        int sec = timeInSec - min * 60;

        timerTV.setText(String.format("%02d", min) +
                ":" +
                String.format("%02d", sec));

        seekBar.setProgress(timeInSec);
    }
}
