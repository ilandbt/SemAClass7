package com.bentals.class7;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private View view;
    private RelativeLayout screen;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    public float x, y;
    private float height, width;
    private float speedFactor = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (View) findViewById(R.id.view);
        screen = (RelativeLayout) findViewById(R.id.screen);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        x = view.getX();
        y = view.getY();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -= (int) event.values[0]*speedFactor;
            y += (int) event.values[1]*speedFactor;

            height = screen.getHeight();
            width = screen.getWidth();

            x = x < 0 ? 0 : x;
            x = x >= width-view.getWidth() ? width-view.getWidth() : x;


            y = y < 0 ? 0 : y;
            y = y >= height-view.getHeight() ? height-view.getHeight() : y;

            view.setX(x);
            view.setY(y);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
