package com.example.practicacanvas;


import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Pelota p1;
    DisplayMetrics metrics = new DisplayMetrics();
    private MediaPlayer sonidoGol;

    private int ancho, alto;
    private float y_ultima = 0, x_ultima = 0;
    private boolean reseteo = false;
    private int contadorGoles = 0;

    private SensorManager sensorManager;
    private Sensor acelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ancho = metrics.widthPixels;
        alto = metrics.heightPixels;
        p1 = new Pelota(this);

        p1.setAncho(ancho);
        p1.setAlto(alto);
        sonidoGol = MediaPlayer.create(this, R.raw.gol);

        setContentView(p1);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (reseteo) {
                x_ultima = 0;
                y_ultima = 0;
                reseteo = false;
                contadorGoles++;
                sonidoGol.start();
            }

            x_ultima -= Math.round(event.values[0]);
            p1.X = String.format("%.2f", x_ultima);

            if (x_ultima < (p1.getTamanio() + p1.getBorde())) {
                x_ultima = p1.getTamanio() + p1.getBorde();
            } else if (x_ultima > (p1.getAncho() - (p1.getTamanio() + p1.getBorde()))) {
                x_ultima = p1.getAncho() - (p1.getTamanio() + p1.getBorde());
            }
            
            p1.setEjeX(x_ultima);

            y_ultima += Math.round(event.values[1]);
            p1.Y = String.format("%.2f", y_ultima);

            if (y_ultima < (p1.getTamanio() + p1.getBorde())) {
                y_ultima = p1.getTamanio() + p1.getBorde();
            } else if (y_ultima > (p1.getAlto() - p1.getTamanio()) - p1.getBorde() * 6 - 2) {
                if (p1.getEjeX() - 40 >= p1.getAncho() / 2 - 130 && p1.getEjeX() + 40 <= p1.getAncho() / 2 + 130) {
                    y_ultima = p1.getAlto() - p1.getTamanio() - p1.getBorde() * 2 - 2 / 2;
                    reseteo = true;
                } else {
                    y_ultima = p1.getAlto() - p1.getTamanio() - p1.getBorde() * 6 - 2;
                }
            }

            
            p1.setEjeY(y_ultima);
            p1.setEjeZ(event.values[2]);
            p1.Z = String.format("%.2f", p1.getEjeZ());
            p1.setContador(contadorGoles);
            setContentView(p1);
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometerSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}