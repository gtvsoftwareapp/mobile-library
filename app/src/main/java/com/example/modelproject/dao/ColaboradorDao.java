package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Colaborador;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;

public class ColaboradorDao extends Dao {

    public static final String TABELA = "colaboradores";

    public static final String CODZRA = "codzra";
    public static final String NOME = "nome";
    public static final String CODUGB = "codugb";

    public ColaboradorDao(Context context) {
        super(context);
    }

    public void inserirColaborador(ArrayList<Colaborador> colaboradores) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Colaborador pAux : colaboradores) {

                cv.put(ColaboradorDao.CODZRA, pAux.getCodzra());
                cv.put(ColaboradorDao.NOME, pAux.getNome());
                cv.put(ColaboradorDao.CODUGB, pAux.getCodugb());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public Colaborador obterColaborador() {

        Colaborador cAux = null;

        abrirBanco();

        Cursor cursor = null;


        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select codzra, nome, codugb from " + TABELA);

            cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                cAux = new Colaborador();
                cAux.setCodzra((cursor.getString(cursor.getColumnIndex(CODZRA))));
                cAux.setNome((cursor.getString(cursor.getColumnIndex(NOME))));
                cAux.setCodugb(cursor.getString(cursor.getColumnIndex(CODUGB)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

    public Colaborador obterColaboradorByCodzra(String codzra) {

        Colaborador cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {
            String[] argumentos = {String.valueOf(codzra)};

            StringBuilder sb = new StringBuilder();

            sb.append("select codzra, nome, codugb from " + TABELA
                    + " where codzra = ?");

            cursor = db.rawQuery(sb.toString(), argumentos);

            while (cursor.moveToNext()) {
                cAux = new Colaborador();
                cAux.setCodzra(cursor.getString(cursor.getColumnIndex(CODZRA)));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cAux.setCodugb(cursor.getString(cursor.getColumnIndex(CODUGB)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

    public ArrayList<HMAux> obterListaColaborador() {

        ArrayList<HMAux> colaboradores = new ArrayList<>();

        abrirBanco();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select codzra, nome, codugb from " + TABELA + " order by nome");

            Cursor cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                HMAux pAux = new HMAux();

                pAux.put(ColaboradorDao.CODZRA, cursor.getString(cursor.getColumnIndex(ColaboradorDao.CODZRA)));
                pAux.put(ColaboradorDao.CODUGB, cursor.getString(cursor.getColumnIndex(ColaboradorDao.CODUGB)));
                pAux.put(ColaboradorDao.NOME, cursor.getString(cursor.getColumnIndex(ColaboradorDao.NOME)));


                colaboradores.add(pAux);
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return colaboradores;
    }

}
