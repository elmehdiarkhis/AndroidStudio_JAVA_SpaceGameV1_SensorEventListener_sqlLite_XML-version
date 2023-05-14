package com.example.mysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //variable globale ====================
    private ImageView spaceShip;

    private ArrayList<Astre> myArrayAsrte;
    ArrayList<ImageView> myArrayImageView;

    private LinearLayout lnrGlobal;
    //======
    private SensorManager mySensorManager; //(mon intermediaire)
    private Sensor mySensor;
    //===================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //=================================Initilisation====================================
        spaceShip = new ImageView(this);
        lnrGlobal = (LinearLayout) findViewById(R.id.lnrGlobal_id);
        //====
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //=================================Initilisation====================================


        //Listner(context,sensor,reactivite)=======================================
        mySensorManager.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_GAME);
        //==============================================

        try{
            //DataBaseConnection=======
            mySqlDataBase myDataBase = new mySqlDataBase(getApplicationContext());

            myDataBase.connectToDataBase();
            //=========================

            //Insert Astres into Database===
            myDataBase.insertAstre("nostra",1000,1000,R.drawable.nostra,-900,-100,1);
            myDataBase.insertAstre("sun",1000,1000,R.drawable.sun,250,-100,0);
            myDataBase.insertAstre("moon",550,550,R.drawable.mars,-650,-100,0);
            myDataBase.insertAstre("terre",450,450,R.drawable.terre,300,-100,1);
            myDataBase.insertAstre("moon",400,400,R.drawable.moon,-350,-70,0);
            myDataBase.insertAstre("jupiter",300,300,R.drawable.jupiter,300,-100,0);




            //Select All Astres
            myArrayAsrte = myDataBase.selectAllAstre();


            //creer une Array d'imageView de tous les astre
            myArrayImageView = new ArrayList<ImageView>();
            for (Astre astre : myArrayAsrte) {
                myArrayImageView.add(new ImageView(this));
            }

            //creer une Array de params(with, height) de touts les astre
            ArrayList<LinearLayout.LayoutParams> myArrayParams= new ArrayList<LinearLayout.LayoutParams>();
            for (Astre astre : myArrayAsrte) {
                myArrayParams.add(new LinearLayout.LayoutParams(astre.getWidth(),astre.getHeight()));
            }

            //parametrer les imageView(Planet) et les ajouter dans le layout
            int counter=0;
            for (ImageView img : myArrayImageView) {
                img.setImageResource(myArrayAsrte.get(counter).getImage());
                img.setX(myArrayAsrte.get(counter).getPositonIntialX());
                img.setY(myArrayAsrte.get(counter).getPositonIntialY());
                img.setLayoutParams(myArrayParams.get(counter));
                lnrGlobal.addView(img);
                counter++;
            }

            //Create ImageView for the spaceShip et l'ajouter dans le layout===
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(300,300);
            spaceShip.setImageResource(R.drawable.shipone);
            spaceShip.setLayoutParams(parms);
            lnrGlobal.addView(spaceShip);

        }catch(Exception ex){
            Log.i("f",ex.getMessage());
        }




    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Calculer la distance du vecteur OM = Racine(X2+Y2+Z2)
        float vectorLength = (float) Math.sqrt(
                Math.pow( (float)(event.values[0]),2)
                + Math.pow( (float)(event.values[1]-9.81),2));



        //Bouger le spaceShip + bloquer le spaceShip dans le cadre_X====
        if(spaceShip.getX()>15 && spaceShip.getX()<1100) {
            spaceShip.setX( spaceShip.getX() - (event.values[0]*vectorLength)  );
        }else{
            if(spaceShip.getX()>500){
                spaceShip.setX(spaceShip.getX()-3);
            }else{
                spaceShip.setX(spaceShip.getX()+3);
            }
        }
        //===
        if(spaceShip.getY()>0 && spaceShip.getY()<2040) {
            spaceShip.setY( spaceShip.getY() - ((event.values[2]*vectorLength))   );
        }else{
            if(spaceShip.getY()>500){
                spaceShip.setY(spaceShip.getY()-3);
            }else{
                spaceShip.setY(spaceShip.getY()+3);
            }
        }
        //============================================================


        //Faire bouger les Planet dans le sens contraire du spaceShip=====
        int c = 0;
        for (ImageView img : myArrayImageView) {
                img.setX( myArrayImageView.get(c).getX() + ((event.values[0]*vectorLength/4)));
                img.setY( myArrayImageView.get(c).getY() + ((event.values[2]*vectorLength/4)));
                c++;
        }
        //============================================================


        //ajouter les position X et Y Actuel de chaque planet dans une Array====
        int counterX=0;
        ArrayList<Float> myArrayPositionActuelX = new ArrayList<Float>();
        for (Astre astre : myArrayAsrte) {
            myArrayPositionActuelX.add(myArrayImageView.get(counterX).getX());
            counterX++;
        }
        //===
        int counterY=0;
        ArrayList<Float> myArrayPositionActuelY = new ArrayList<Float>();
        for (Astre astre : myArrayAsrte) {
            myArrayPositionActuelY.add(myArrayImageView.get(counterY).getY());
            counterY++;
        }
        //==================================================================



                    //===========================!!!!!!!!!!!======================//
        //while quand on moov et on est loin de touts les planet, reset image to normale
        int counter=0;
        for (ImageView img : myArrayImageView) {
            img.setImageResource(myArrayAsrte.get(counter).getImage());
            counter++;
        }
        //change l'image de la planet to Green if on est pret d'une planet habite
        int counter_n=0;
        for (Astre astre : myArrayAsrte) {
            if(spaceShip.getX()>myArrayPositionActuelX.get(counter_n)-300 && spaceShip.getX()<myArrayPositionActuelX.get(counter_n)+300 && spaceShip.getY()>myArrayPositionActuelY.get(counter_n)-300 && spaceShip.getY()<myArrayPositionActuelY.get(counter_n)+300 && myArrayAsrte.get(counter_n).getHabite()==1){
                myArrayImageView.get(counter_n).setImageResource(R.drawable.green);
            }
            counter_n++;
        }
                      //===========================!!!!!!!!!!!======================//
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}