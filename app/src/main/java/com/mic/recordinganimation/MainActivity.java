package com.mic.recordinganimation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static com.mic.recordinganimation.RecordView.MODEL_PLAY;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    private RecordView mRecorfView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int db = (int) (Math.random()*100);
            mRecorfView.setVolume(db);
        }
    };
    private int nowModel = RecordView.MODEL_RECORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnTouchListener(this);
        mRecorfView = (RecordView) findViewById(R.id.recordView);
        mRecorfView.setCountdownTime(9);
        mRecorfView.setModel(RecordView.MODEL_RECORD);
        findViewById(R.id.button2).setOnClickListener(this);
    }
    private TimerTask timeTask;
    private Timer timeTimer = new Timer(true);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mRecorfView.start();
            timeTimer.schedule(timeTask = new TimerTask() {
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }, 20, 20);
            mRecorfView.setOnCountDownListener(new RecordView.OnCountDownListener() {
                @Override
                public void onCountDown() {
                    Toast.makeText(MainActivity.this,"计时结束啦~~",Toast.LENGTH_SHORT).show();
                }
            });
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            mRecorfView.cancel();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(nowModel == MODEL_PLAY){
            mRecorfView.setModel(RecordView.MODEL_RECORD);
            nowModel = RecordView.MODEL_RECORD;
        }else{
            mRecorfView.setModel(RecordView.MODEL_PLAY);
            nowModel = RecordView.MODEL_PLAY;
        }
    }
}
