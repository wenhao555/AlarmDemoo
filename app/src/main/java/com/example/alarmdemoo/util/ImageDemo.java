package com.example.alarmdemoo.util;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.alarmdemoo.MainActivity;
import com.example.alarmdemoo.MiaobiaoActivity;
import com.example.alarmdemoo.R;

public class ImageDemo extends TabActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("TAB1")
                .setIndicator("闹钟", getResources().getDrawable(R.drawable.clock))
                .setContent(new Intent(this, MainActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("TAB2")
                .setIndicator("秒表", getResources().getDrawable(R.drawable.clock1))
                .setContent(new Intent(this, MiaobiaoActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("TAB3")
                .setIndicator("日历", getResources().getDrawable(R.drawable.calendar))
                .setContent(new Intent(this, BaseCalendar.class)));
		
		/*tabHost.addTab(tabHost.newTabSpec("TAB4")
				.setIndicator("���±�",getResources().getDrawable(R.drawable.jsb))
				.setContent(new Intent(this,activitydiary.class)));
		
		tabHost.addTab(tabHost.newTabSpec("TAB5")
				.setIndicator("����ʱ��",getResources().getDrawable(R.drawable.clock3))
				.setContent(new Intent(this,MiaobiaoActivity.class)));*/
    }

}
