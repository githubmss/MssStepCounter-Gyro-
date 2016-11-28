package com.mss.stepcounter;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class CounterActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView mCount;
    boolean activityRunning;
    TextView txtName;
    ImageButton imgIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mCount = (TextView) findViewById(R.id.count);
        imgIcon = (ImageButton) findViewById(R.id.ibtn_back);
        imgIcon.setImageResource(R.drawable.mss);
        txtName = (TextView) findViewById(R.id.txt_edit);
        txtName.setText(getResources().getString(R.string.app_name));
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            mSensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            mCount.setText(getResources().getString(R.string.device_cmpt));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            mCount.setText(String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
