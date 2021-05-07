package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Configuracao;

import java.util.ArrayList;

public class ConfiguracaoDao extends Dao {

    public static final String TABELA = "configuracao";

    public static final String ID = "id";
    public static final String URL = "url";

    public ConfiguracaoDao(Context context) {
        super(context);
    }

    public void inserirConfiguracao(ArrayList<Configuracao> configuracoes) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Configuracao pAux : configuracoes) {

                cv.put(ConfiguracaoDao.ID, pAux.getId());
                cv.put(ConfiguracaoDao.URL, pAux.getUrl());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public Configuracao obterConfiguracao() {

        Configuracao cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select id, url from " + TABELA);

            cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                cAux = new Configuracao();
                cAux.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                cAux.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }


    public void alterarConfiguracaoByID(Configuracao configuracao) {

        abrirBanco();

        ContentValues cv = new ContentValues();

        String filtro = " id = ? ";
        String[] argumentos = {String.valueOf(configuracao.getId())};

        cv.put(ConfiguracaoDao.URL, configuracao.getUrl());

        db.update(TABELA, cv, filtro, argumentos);

        fecharBanco();
    }
}
