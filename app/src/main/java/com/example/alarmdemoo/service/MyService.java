package com.example.alarmdemoo.service;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.alarmdemoo.db.NotePadDB;

public class MyService extends Service
{//后台服务
    private NotePadDB notePadDB;
    private SQLiteDatabase database;

    public MyService()
    {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        notePadDB = new NotePadDB(this);
        database = notePadDB.getWritableDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        deleteByPos(intent.getStringExtra("pos"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return new Binder();
    }

    private void deleteByPos(String pos)
    {
        database.delete(NotePadDB.NAME, "_id=" + pos, null);
    }
}
