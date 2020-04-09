package com.phong.btrl_sensor_lachinh;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView imgHinh;
    SensorManager sensorManager;
    Sensor sensor;
    long lastedUpdate = System.currentTimeMillis();
    int position = 0;
    int []arrayHinh = {R.drawable.hinhmot,R.drawable.hinhhai,R.drawable.hinhba};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        imgHinh = findViewById(R.id.imgHinh);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        double squareRoot = (Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) / Math.pow(SensorManager.GRAVITY_EARTH,2);
        if (squareRoot >= 2 ){
            long actualTime = System.currentTimeMillis();
            if (actualTime - lastedUpdate <= 200)
                return;
            lastedUpdate = actualTime;
            if (sensorEvent.values[0] < 0){
                position--;
                if (position < 0)
                    position = arrayHinh.length - 1;
            }
            else {
                position++;
                if (position >= arrayHinh.length)
                    position = 0;
            }
            imgHinh.setImageResource(arrayHinh[position]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
