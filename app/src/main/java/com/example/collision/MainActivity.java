package com.example.collision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    // Initialisations variables
    SensorManager sensorManager;
    Sensor Accelerometre;
    ImageView carre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération par id
        carre = findViewById(R.id.carre);

        // Initialisation sensorManager pour gérer les capteurs
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Affectation du capteur "ACCELEROMER" à la variable Accelerometre
        Accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Méthode appelée lorsque les données capteurs sont MAJ
     * Affecte le résultat dans deux textView
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Récupère les valeurs de gavité x et y
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];

        // Récupère les nouvelles positions
        float newX = carre.getX() - x;
        float newY = carre.getY() + y;


        // Récupération des délimitations de l'écran
        int largeurEcran = Resources.getSystem().getDisplayMetrics().widthPixels;
        int hauteurEcran = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Test pour que le carré ne sorte pas de l'écran
        // Tests pour la largeur
        if (newX < 0) {
            newX = 0;
        } else if (newX > largeurEcran - carre.getWidth()) {
            newX = largeurEcran - carre.getWidth();
        }

        // Tests pour la hauteur
        if (newY < 0) {
            newY = 0;
        } else if (newY > hauteurEcran - carre.getHeight()) {
            newY = hauteurEcran - carre.getHeight();
        }

        // Positionne le carré avec les nouvelles positions
        carre.setX(newX);
        carre.setY(newY);

    }

    /**
     * Méthode non utilisé (implémentée de base)
     * @param sensor
     * @param i
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /**
     * Enregistre l'événement du capteur
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Enregistre un sensorEventListener pour mettre a jour les valeurs x et y
        sensorManager.registerListener(this, Accelerometre, SensorManager.SENSOR_DELAY_UI);
    }
}