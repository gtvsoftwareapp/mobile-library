package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Ugb;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;

public class UgbDao extends Dao {

    public static final String TABELA = "ugbs";
    public static final String FILIAL = "filial";
    public static final String CODLOC = "codloc";
    public static final String CODGER = "codger";
    public static final String CODCOO = "codcoo";
    public static final String CODUGB = "codugb";
    public static final String DESUGB = "desugb";
    public static final String NOTASM = "notasm";
    public static final String NOTA5S = "nota5s";
    public static final String AVLMAT = "avlmat";


    public UgbDao(Context context) {
        super(context);
    }

    public void inserirUgb(ArrayList<Ugb> ugbs) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Ugb pAux : ugbs) {
                cv.put(UgbDao.FILIAL, pAux.getFilial());
                cv.put(UgbDao.CODLOC, pAux.getCodloc());
                cv.put(UgbDao.CODGER, pAux.getCodger());
                cv.put(UgbDao.CODCOO, pAux.getCodcoo());
                cv.put(UgbDao.CODUGB, pAux.getCodugb());
                cv.put(UgbDao.DESUGB, pAux.getDesugb());
                cv.put(UgbDao.NOTASM, pAux.getNotasm());
                cv.put(UgbDao.NOTA5S, pAux.getNota5s());
                cv.put(UgbDao.AVLMAT, pAux.getAvlmat());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public ArrayList<HMAux> obterListaUgb() {

        ArrayList<HMAux> ugbs = new ArrayList<>();

        abrirBanco();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select * from " + TABELA + " order by codugb ");

            Cursor cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                HMAux pAux = new HMAux();

                pAux.put(UgbDao.FILIAL, cursor.getString(cursor.getColumnIndex(UgbDao.FILIAL)));
                pAux.put(UgbDao.CODLOC, cursor.getString(cursor.getColumnIndex(UgbDao.CODLOC)));
                pAux.put(UgbDao.CODGER, cursor.getString(cursor.getColumnIndex(UgbDao.CODGER)));
                pAux.put(UgbDao.CODCOO, cursor.getString(cursor.getColumnIndex(UgbDao.CODCOO)));
                pAux.put(UgbDao.CODUGB, cursor.getString(cursor.getColumnIndex(UgbDao.CODUGB)));
                pAux.put(UgbDao.DESUGB, cursor.getString(cursor.getColumnIndex(UgbDao.DESUGB)));
                pAux.put(UgbDao.NOTASM, cursor.getString(cursor.getColumnIndex(UgbDao.NOTASM)));
                pAux.put(UgbDao.NOTA5S, cursor.getString(cursor.getColumnIndex(UgbDao.NOTA5S)));
                pAux.put(UgbDao.AVLMAT, cursor.getString(cursor.getColumnIndex(UgbDao.AVLMAT)));

                ugbs.add(pAux);
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return ugbs;
    }

    public Ugb obterUgbByID(String codugb) {

        Ugb cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            String[] argumentos = {String.valueOf(codugb)};

            StringBuilder sb = new StringBuilder();

            sb.append("select * from " + TABELA + " where codugb = ?");

            cursor = db.rawQuery(sb.toString(), argumentos);

            while (cursor.moveToNext()) {
                cAux = new Ugb();
                cAux.setFilial(cursor.getString(cursor.getColumnIndex(FILIAL)));
                cAux.setCodloc(cursor.getString(cursor.getColumnIndex(CODLOC)));
                cAux.setCodger(cursor.getString(cursor.getColumnIndex(CODGER)));
                cAux.setCodcoo(cursor.getString(cursor.getColumnIndex(CODCOO)));
                cAux.setCodugb(cursor.getString(cursor.getColumnIndex(CODUGB)));
                cAux.setDesugb(cursor.getString(cursor.getColumnIndex(DESUGB)));
                cAux.setNotasm(cursor.getInt(cursor.getColumnIndex(NOTASM)));
                cAux.setNota5s(cursor.getInt(cursor.getColumnIndex(NOTA5S)));
                cAux.setAvlmat(cursor.getInt(cursor.getColumnIndex(AVLMAT)));


            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

}
