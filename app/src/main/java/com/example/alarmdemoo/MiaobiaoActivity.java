package com.example.alarmdemoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiaobiaoActivity extends AppCompatActivity implements Runnable
{
    private long time = 0;

    private long startTime;

    private Handler handler;


    private TextView timeView;


    private ListView listView;


    private Button startButton;


    private Button pauseButton;


    private Button markButton;


    private Button resetButton;


    private List<Long> marks;


    private int state = 0;

    private  static int STATE_RUNNING = 1;
    private  static int STATE_STOP = 0;
    private  static int STATE_PAUSE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaobiao);
        //读取环境信息（偏好 ）
        readEnvironment();
        Toast.makeText(this, "环境已读取", Toast.LENGTH_LONG).show();

        //开始按钮
        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onStartClick(view);
            }
        });

        //暂停按钮
        pauseButton = (Button)findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                onPauseClick(view);
            }
        });

        //分记按钮
        markButton = (Button) findViewById(R.id.mark);
        markButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                onMarkClick(view);
            }
        });

        //重置按钮
        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                onResetClick(view);
            }
        });

        timeView = (TextView) findViewById(R.id.timeView);
        listView = (ListView) findViewById(R.id.ListView01);

        //创建 handler
        handler = new Handler();

        // 设置各按钮
        setButtons();

        //设置时间显示
        if (state == STATE_STOP){
            timeView.setText(" Running!!!");
        } else if(state == STATE_PAUSE){
            timeView.setText(getFormatTime(time));
        }

        //显示列表
        refreshMarkList();
    }

    /**
     * 根据状态设置按钮显示于不显示
     */
    private void setButtons() {
        switch (state) {
            case 1://如果正在运行
                startButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                markButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.GONE);
                resetButton.setEnabled(false);
                break;
            case 2://如果暂停中
                startButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                markButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
                resetButton.setEnabled(true);
                break;
            case 0://如果停止中
                startButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                markButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
                resetButton.setEnabled(false);
                break;
            default:
                break;
        }
    }

    /**
     * 读取环境
     */
    @SuppressWarnings("unchecked")
    private void readEnvironment() {
        SharedPreferences sharedPreferences = getSharedPreferences("environment", MODE_PRIVATE);
        state = sharedPreferences.getInt("state", STATE_STOP);
        startTime = sharedPreferences.getLong("startTime", 0);
        time = sharedPreferences.getLong("time", 0);

        marks = new ArrayList<Long>();
        SharedPreferences sharedPreferencesMarks = getSharedPreferences("marks", MODE_PRIVATE);
        Map<String, Long> mapMarks = (Map<String, Long>) sharedPreferencesMarks.getAll();
        for (int i = 0; i < mapMarks.size(); i++){
            Long mark = mapMarks.get("" + i);
            marks.add(mark);
        }

    }
    /**
     * 保存环境
     */
    private void saveEnvironment() {
        SharedPreferences sharedPreferences = getSharedPreferences("environment", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("state", state);
        editor.putLong("time", time);
        editor.putLong("startTime", startTime);
        editor.commit();

        //保存分记数据
        SharedPreferences sharedPreferencesMarks = getSharedPreferences("marks", MODE_PRIVATE);
        SharedPreferences.Editor editorMarks = sharedPreferencesMarks.edit();
        editorMarks.clear();
        for(Long mark : marks){
            editorMarks.putLong(""  + marks.indexOf(mark), mark.longValue());
        }
        editorMarks.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (state == STATE_RUNNING){//如果正在运行
            handler.removeCallbacks(this);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存环境
        saveEnvironment();
        Toast.makeText(this, "当前环境已保存", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (state == STATE_RUNNING){//如果正在运行
            handler.post(this);
        }
    }

    /**
     * 点击重置
     * @param view
     */
    protected void onResetClick(View view) {
        //设置状态为停止
        state = STATE_STOP;

        //不再刷新
        if (state == STATE_RUNNING){//如果正在运行
            handler.removeCallbacks(this);
        }

        //初始化分记数组
        marks = new ArrayList<Long>();
        refreshMarkList();

        //设置时间显示
        time = 0;
        timeView.setText(getFormatTime(time));

        // 设置各按钮
        setButtons();
    }

    /**
     * 点击暂停
     * @param view
     */
    protected void onPauseClick(View view) {
        //不再刷新
        if (state == STATE_RUNNING){//如果正在运行
            handler.removeCallbacks(this);
        }

        //设置状态为暂停
        state = STATE_PAUSE;

        // 设置各按钮
        setButtons();
    }

    /**
     * 点击开始
     * @param view
     */
    protected void onStartClick(View view) {
        startTime = new Date().getTime() - time;

        handler.post(this);

        //初始化分记数组
        marks = new ArrayList<Long>();

        //设置状态为正在运行
        state = STATE_RUNNING;

        // 设置各按钮
        setButtons();
    }

    /**
     * 点击分记
     *
     * @param view
     */
    protected void onMarkClick(View view) {

        if(time == 0){
            return;
        }
        // 添加分记数据, 最近的加在最前面
        marks.add(0, time);

        //刷新列表
        refreshMarkList();
    }

    /**
     * 刷新列表
     */
    private void refreshMarkList() {
        //显示
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
        int no = marks.size();//序号
        for(long mark : marks){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mark", getFormatTime(mark));
            map.put("no", this.getString(R.string.mark) + no);
            no--;
            data.add(map);

        }
        String[] from = new String[]{"no","mark"};
        int[] to = new int[]{R.id.no, R.id.time};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        listView.setAdapter(simpleAdapter);
    }

    public void run() {
        handler.postDelayed(this, 50);
        time = new Date().getTime() - startTime;
        timeView.setText(getFormatTime(time));
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time
     * 			时间 毫秒
     * @return 分：秒：毫秒
     */
    private String getFormatTime(long time) {
        long millisecond = time % 1000;
        long second = (time / 1000) % 60;
        long minute = time / 1000 / 60;

        //秒以下的只显示一位
        String strMillisecond = "" + (millisecond / 100);
        //秒显示两位
        String strSecond = ("00" + second).substring(("00" + second).length() - 2);
        //分显示两位
        String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);

        return strMinute + ":" + strSecond + ":" + strMillisecond;
    }

}