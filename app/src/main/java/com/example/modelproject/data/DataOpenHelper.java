package com.example.modelproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataOpenHelper extends SQLiteOpenHelper {

    public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(ScriptDDL.getCreateTableUsuario());
            db.execSQL(ScriptDDL.getCreateTableConfiguracao());


            db.execSQL(ScriptDDL.getCreateTableCompetencia());
            db.execSQL(ScriptDDL.getCreateTableFilial());
            db.execSQL(ScriptDDL.getCreateTableUGB());

            db.execSQL(ScriptDDL.getCreateTableColaborador());



        } catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}