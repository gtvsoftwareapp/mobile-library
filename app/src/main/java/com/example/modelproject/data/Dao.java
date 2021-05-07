package com.example.modelproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.modelproject.util.Constantes;

public class Dao {

    private Context context;
    protected SQLiteDatabase db;

    public Dao(Context context) {
        this.context = context;
    }

    public void abrirBanco() {
        DataOpenHelper vHelper = new DataOpenHelper(context, Constantes.BANCO, null, Constantes.VERSAO);
        this.db = vHelper.getWritableDatabase();
    }

    public void fecharBanco() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

}
