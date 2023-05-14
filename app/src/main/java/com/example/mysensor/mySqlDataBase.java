package com.example.mysensor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class mySqlDataBase {

    //props
    private SQLiteDatabase myDataBase;
    private mySqlOpenHelper sqlHelper;

    private Context ctx;
    private final String DATABASE_NAME = "ASTRES";
    private final int DATABASE_VERSION = 1;

    //Constructeur
    public mySqlDataBase(Context _ctx){
        ctx = _ctx;
        sqlHelper = new mySqlOpenHelper(ctx,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Fonction de connection
    public void connectToDataBase(){
        myDataBase = sqlHelper.getWritableDatabase();
    }

    public void dropTable(){
        myDataBase.execSQL("DROP TABLE IF EXISTS astreTable");
    }

    //Fonction CRUD
    public void insertAstre(String _nom ,int _width,int _height,int _image,int _positonIntialX,int _positonIntialY,int _habite){
        myDataBase.execSQL("INSERT INTO astreTable(nom,width,height,image,positonIntialX,positonIntialY,habite) VALUES ('"+_nom+"',"+_width+","+_height+","+_image+","+_positonIntialX+","+_positonIntialY+","+_habite+");");
    }

    public void deleteAstre(String _nom){
        myDataBase.execSQL("DELETE FROM astreTable WHERE nom='"+_nom+"';");
    }

    public ArrayList<Astre> selectAllAstre(){

        //requete sql
        Cursor myCursor = myDataBase.rawQuery("SELECT * FROM astreTable",null);

        //recuperer les index dans du curseur
        int nomIndex = myCursor.getColumnIndex("nom");
        int widthIndex = myCursor.getColumnIndex("width");
        int heightIndex = myCursor.getColumnIndex("height");
        int imageIndex = myCursor.getColumnIndex("image");
        int positonIntialXIndex = myCursor.getColumnIndex("positonIntialX");
        int positonIntialYIndex = myCursor.getColumnIndex("positonIntialY");
        int habiteIndex = myCursor.getColumnIndex("habite");


        ArrayList<Astre> myAstreArray = new ArrayList<Astre>();

        //boucler dans le curseur pour remplir une array
        if(myCursor!=null && myCursor.moveToFirst()){
            do{
                myAstreArray.add(
                                    new Astre(
                                        myCursor.getString(nomIndex),
                                        myCursor.getInt(widthIndex),
                                        myCursor.getInt(heightIndex),
                                        myCursor.getInt(imageIndex),
                                        myCursor.getInt(positonIntialXIndex),
                                        myCursor.getInt(positonIntialYIndex),
                                        myCursor.getInt(habiteIndex)
                                    )
                );
            }while(myCursor.moveToNext());
        }

        return myAstreArray;
    }




}
