package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Filial;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;


public class FilialDao extends Dao {
    public static final String TABELA = "filiais";
    public static final String FILIAL = "filial";
    public static final String FAZENDA = "fazenda";


    public FilialDao(Context context) {
        super(context);
    }

    public void inserirFilial(ArrayList<Filial> filiais) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Filial pAux : filiais) {
                cv.put(FilialDao.FILIAL, pAux.getFilial());
                cv.put(FilialDao.FAZENDA, pAux.getFazenda());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public ArrayList<HMAux> obterListaFilial() {

        ArrayList<HMAux> filiais = new ArrayList<>();

        abrirBanco();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select * from " + TABELA + " order by fazenda");

            Cursor cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                HMAux pAux = new HMAux();

                pAux.put(FILIAL, cursor.getString(cursor.getColumnIndex(FilialDao.FILIAL)));
                pAux.put(FAZENDA, cursor.getString(cursor.getColumnIndex(FilialDao.FAZENDA)));

                filiais.add(pAux);
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return filiais;
    }


}