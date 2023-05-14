package com.example.mysensor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class mySqlOpenHelper extends SQLiteOpenHelper {

    public mySqlOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS astreTable(id integer primary key autoincrement,nom text,width integer,height integer,image integer,positonIntialX integer,positonIntialY integer,habite integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

    }
}
