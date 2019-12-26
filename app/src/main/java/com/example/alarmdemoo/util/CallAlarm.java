package com.example.alarmdemoo.util;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;

import com.example.alarmdemoo.AlarmAlertActivity;

public class CallAlarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, AlarmAlertActivity.class);

        Bundle bundleRet = new Bundle();
        bundleRet.putString("STR_CALLER", "");
        i.putExtras(bundleRet);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
