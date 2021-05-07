package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Competencia;

import java.util.ArrayList;


public class CompetenciaDao extends Dao {

    public static final String TABELA = "competencias";
    public static final String COMPET = "compet";
    public static final String NIVCOM = "nivcom";

    public CompetenciaDao(Context context) {
        super(context);
    }

    public void inserirCompetencia(ArrayList<Competencia> competencias) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Competencia pAux : competencias) {
                cv.put(CompetenciaDao.COMPET, pAux.getCompet());
                cv.put(CompetenciaDao.NIVCOM, pAux.getNivcom());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public Competencia obterCompetencia() {

        Competencia cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select * from " + TABELA);

            cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                cAux = new Competencia();
                cAux.setCompet(cursor.getString(cursor.getColumnIndex(COMPET)));
                cAux.setNivcom(cursor.getString(cursor.getColumnIndex(NIVCOM)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

}


