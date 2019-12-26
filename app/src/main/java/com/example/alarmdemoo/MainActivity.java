package com.example.alarmdemoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarmdemoo.util.CallAlarm;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    TextView setTime1;
    TextView setTime2;
    TextView setTime3;
    TextView setTime4;
    TextView setTime5;
    TextView setTime6;
    Button mButton1;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Button mButton5;
    Button mButton6;
    Button mButton7;
    Button mButton8;
    Button mButton9;
    Button mButton10;
    Button mButton11;
    Button mButton12;

    String time1String = null;
    String time2String = null;
    String time3String = null;
    String time4String = null;
    String time5String = null;
    String time6String = null;
    String defalutString = "目前无设置";

    AlertDialog builder = null;
    Calendar c=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取得活动的Preferences对象
        SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
        time1String = settings.getString("TIME1", defalutString);
        time2String = settings.getString("TIME2", defalutString);
        time3String = settings.getString("TIME3", defalutString);
        time4String = settings.getString("TIME4", defalutString);
        time5String = settings.getString("TIME5", defalutString);
        time6String = settings.getString("TIME6", defalutString);

        InitButton1();
        InitButton2();
        InitButton3();
        InitButton4();
        InitButton5();
        InitButton6();
        InitButton7();
        InitButton8();
        InitButton9();
        InitButton10();
        InitButton11();
        InitButton12();

        setTime1.setText(time1String);
        setTime3.setText(time2String);
        setTime2.setText(time3String);
        setTime4.setText(time4String);
        setTime5.setText(time5String);
        setTime6.setText(time6String);
    }

    public void InitButton1()
    {
        setTime1=(TextView) findViewById(R.id.setTime1);
        mButton1=(Button)findViewById(R.id.mButton1);
        mButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );
                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime1.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME1", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }

    public void InitButton2()
    {
        mButton2=(Button) findViewById(R.id.mButton2);
        mButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime1.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }

    public void InitButton3()
    {
        setTime2=(TextView) findViewById(R.id.setTime2);
        mButton3=(Button)findViewById(R.id.mButton3);
        mButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,1, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );
                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime2.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time2Share = getPreferences(1);
                                SharedPreferences.Editor editor = time2Share.edit();
                                editor.putString("TIME2", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }

    public void InitButton4()
    {
        mButton4=(Button) findViewById(R.id.mButton4);
        mButton4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime2.setText("目前无设置");

                //SharedPreferences保存数据，并提交
                SharedPreferences time2Share = getPreferences(1);
                SharedPreferences.Editor editor = time2Share.edit();
                editor.putString("TIME2", "目前无设置");
                editor.commit();
            }
        });
    }

    public void InitButton11()
    {
        setTime6=(TextView) findViewById(R.id.setTime6);
        LayoutInflater factory = LayoutInflater.from(this);
        final View setView = factory.inflate(R.layout.timeset,null);
        final TimePicker tPicker=(TimePicker)setView
                .findViewById(R.id.tPicker);
        tPicker.setIs24HourView(true);

        final AlertDialog di=new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.clock)
                .setTitle("设置")
                .setView(setView)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                EditText ed=(EditText)setView.findViewById(R.id.mEdit);
                                int times=Integer.parseInt(ed.getText().toString())
                                        *1000;
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
                                c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this,
                                        CallAlarm.class);
                                PendingIntent sender = PendingIntent.getBroadcast(
                                        MainActivity.this,1, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.setRepeating(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),times,sender);
                                String tmpS=format(tPicker.getCurrentHour())+"："+
                                        format(tPicker.getCurrentMinute());
                                String subStr = "设置闹钟时间为"+tmpS+
                                        "开始，重复间隔为"+times/1000+"秒";
                                setTime6.setText("设置闹钟时间为"+tmpS+
                                        "开始，重复间隔为"+times/1000+"秒");

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time3Share = getPreferences(2);
                                SharedPreferences.Editor editor = time3Share.edit();
                                editor.putString("TIME6", subStr);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟为"+tmpS+
                                                "开始，重复间隔为"+times/1000+"秒",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        }).create();

        mButton11=(Button) findViewById(R.id.mButton11);
        mButton11.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
                tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
                di.show();
            }
        });
    }

    public void InitButton12()
    {
        mButton12=(Button) findViewById(R.id.mButton12);
        mButton12.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this,1, intent, 0);
                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime6.setText("目前无设置");
                //SharedPreferences保存数据，并提交
                SharedPreferences time3Share = getPreferences(2);
                SharedPreferences.Editor editor = time3Share.edit();
                editor.putString("TIME6", "目前无设置");
                editor.commit();
            }
        });
    }

    public void InitButton7()
    {
        setTime4=(TextView) findViewById(R.id.setTime4);
        mButton7=(Button)findViewById(R.id.mButton7);
        mButton7.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );
                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime4.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME4", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }

    public void InitButton8()
    {
        mButton8=(Button) findViewById(R.id.mButton8);
        mButton8.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this,1, intent, 0);
                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime4.setText("目前无设置");
                //SharedPreferences保存数据，并提交
                SharedPreferences time3Share = getPreferences(2);
                SharedPreferences.Editor editor = time3Share.edit();
                editor.putString("TIME4", "目前无设置");
                editor.commit();
            }
        });
    }

    public void InitButton9()
    {
        setTime5=(TextView) findViewById(R.id.setTime5);
        mButton9=(Button)findViewById(R.id.mButton9);
        mButton9.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );
                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime5.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME5", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }

    public void InitButton10()
    {
        mButton10=(Button) findViewById(R.id.mButton10);
        mButton10.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime5.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME5", "目前无设置");
                editor.commit();
            }
        });
    }

    public void InitButton5()
    {
        setTime3=(TextView) findViewById(R.id.setTime3);
        mButton5=(Button)findViewById(R.id.mButton5);
        mButton5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );
                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime3.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME3", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }

    public void InitButton6()
    {
        mButton6=(Button) findViewById(R.id.mButton6);
        mButton6.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                setTime3.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME3", "目前无设置");
                editor.commit();
            }
        });
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            builder = new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.clock)
                    .setTitle("温馨提示：")
                    .setMessage("您是否要退出闹钟程序!!!")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    MainActivity.this.finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    builder.dismiss();
                                }
                            }).show();
        }
        return true;
    }

    private String format(int x)
    {
        String s=""+x;
        if(s.length()==1) s="0"+s;
        return s;
    }
}
