package com.example.alarmdemoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alarmdemoo.adapter.MyAdapter;
import com.example.alarmdemoo.db.NotePadDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JishibenActivity extends AppCompatActivity implements View.OnClickListener
{
    private ListView listView;
    private FloatingActionButton button;
    private MyAdapter adapter;
    private Intent intent;
    private NotePadDB notePadDB;
    private SQLiteDatabase database;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jishiben);
        listView = findViewById(R.id.list_view);
        button = findViewById(R.id.fab);
        button.setOnClickListener(this);
        notePadDB = new NotePadDB(this);
        database = notePadDB.getReadableDatabase();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        queryAll();
    }

    @Override
    public void onClick(View v)
    {
        intent = new Intent(JishibenActivity.this, ActionActivity.class);
        intent.putExtra("action", 1);
        startActivity(intent);
    }


    public void queryAll()
    {
        Cursor cursor = database.query(NotePadDB.NAME, null, null, null, null, null, null);
        adapter = new MyAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(JishibenActivity.this, LookNoteActivity.class);
                intent.putExtra("i", position);
                Cursor cursor = database.query(NotePadDB.NAME, null, null, null, null, null, "_id");
                cursor.moveToPosition(position);
                intent.putExtra(NotePadDB.ID, cursor.getString(cursor.getColumnIndex(NotePadDB.ID)));
                intent.putExtra(NotePadDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotePadDB.CONTENT)));
                intent.putExtra(NotePadDB.TIME, cursor.getString(cursor.getColumnIndex(NotePadDB.TIME)));
                startActivity(intent);
            }
        });
    }
}
