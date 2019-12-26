package com.example.alarmdemoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmAlertActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final MediaPlayer mediaPlayer01 ;

        mediaPlayer01 = MediaPlayer.create(getBaseContext(), R.raw.ring1);
        Runnable rmp = new Runnable() {
            public void run() {
                mediaPlayer01.start();
            }
        };
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm_alert);
        new AlertDialog.Builder(AlarmAlertActivity.this)
                .setIcon(R.drawable.clock)
                .setTitle("闹钟响了!!")
                .setMessage("快完成自己的任务吧!!!")
                .setPositiveButton("关掉它",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                AlarmAlertActivity.this.finish();
                                mediaPlayer01.stop();
                            }
                        })
                .show();
    }
}
