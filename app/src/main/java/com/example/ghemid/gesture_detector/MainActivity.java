package com.example.ghemid.gesture_detector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private EditText editTextProximityValue, editTextMaxRange, editTextAction;
    private Date date, stayDate;
    private int startTime = 0, endTime = 0, numberOfSlidesPerSecond = 0;
    private long startTimeStay, endTimeStay;
    private static boolean firstTest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextProximityValue = (EditText) findViewById(R.id.editTextProximityValue);
        editTextMaxRange = (EditText) findViewById(R.id.editTextMaxRage);
        editTextAction = (EditText) findViewById(R.id.editTextAction);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        if (mProximity == null) {
            editTextProximityValue.setText("No Proximity Sensor!");
        } else {
            editTextProximityValue.setText(mProximity.getName());
            editTextMaxRange.setText(String.valueOf(mProximity.getMaximumRange()));
            mSensorManager.registerListener(proximitySensorEventListener,
                    mProximity,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

    }


    SensorEventListener proximitySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub

            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                editTextProximityValue.setText(String.valueOf(event.values[0]));

                if (event.values[0] == 0.0) {
                    date = new Date();
                    startTime = date.getSeconds();

                }

                if (event.values[0] == 5.0) {


                    numberOfSlidesPerSecond++;

                    //Test after 500ms
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            date = new Date();
                            endTime = date.getSeconds();

                            stayDate = new Date();
                            endTimeStay = System.currentTimeMillis() % 1000;

                            if (endTime > startTime + 2) {
                                // ====== Validate action ======
                                editTextAction.setText("Validate action");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        editTextAction.setText("");
                                    }
                                }, 1500);

                            } else if (numberOfSlidesPerSecond == 2) {
                                // ====== Slice twice action =======
                                editTextAction.setText("Slide twice action");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        editTextAction.setText("");
                                    }
                                }, 1500);

                            } else {

                                // ====== Slice once action =======
                                editTextAction.setText("Slide once action");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        editTextAction.setText("");
                                    }
                                }, 1500);

                            }
                        }
                    }, 500);

                    Handler handlerNumberOfSlides = new Handler();
                    handlerNumberOfSlides.postDelayed(new Runnable() {
                        public void run() {
                            numberOfSlidesPerSecond = 0;
                        }
                    }, 1500);


                }

            }

        }


    };

}