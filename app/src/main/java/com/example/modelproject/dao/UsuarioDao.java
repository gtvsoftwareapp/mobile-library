package com.example.modelproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.modelproject.data.Dao;
import com.example.modelproject.model.Usuario;

import java.util.ArrayList;

public class UsuarioDao extends Dao {

    public static final String TABELA = "usuario";

    public static final String CODIGO = "codigo";
    public static final String USUARIO = "usuario";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String EMAIL = "email";
    public static final String CODPRO = "codpro";
    public static final String CODSUB = "codsub";
    public static final String ATIVO = "ativo";

    public UsuarioDao(Context context) {
        super(context);
    }

    public void inserirUsuario(ArrayList<Usuario> usuarios) {

        abrirBanco();

        db.beginTransaction();

        try {

            db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Usuario pAux : usuarios) {

                cv.put(CODIGO, pAux.getCodigo());
                cv.put(USUARIO, pAux.getUsuario());
                cv.put(NOME, pAux.getNome());
                cv.put(SENHA, pAux.getSenha());
                cv.put(EMAIL, pAux.getEmail());
                cv.put(CODPRO, pAux.getCodpro());
                cv.put(CODSUB, pAux.getCodsub());
                cv.put(ATIVO, pAux.getAtivo());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public Usuario obterUsuario() {

        Usuario cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select codigo, usuario, nome, senha, email, codpro, codsub, ativo from " + TABELA);
            sb.append(" where ativo = 'S' ");

            cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                cAux = new Usuario();
                cAux.setCodigo(cursor.getString(cursor.getColumnIndex(CODIGO)));
                cAux.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cAux.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
                cAux.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                cAux.setCodpro(cursor.getString(cursor.getColumnIndex(CODPRO)));
                cAux.setCodsub(cursor.getString(cursor.getColumnIndex(CODSUB)));
                cAux.setAtivo(cursor.getString(cursor.getColumnIndex(ATIVO)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

    public Usuario obterUsuarioByID(String usuario) {

        Usuario cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            String[] argumentos = {usuario};
            StringBuilder sb = new StringBuilder();

            sb.append("select codigo, usuario, nome, senha, email, codpro, codsub, ativo from " + TABELA);
            sb.append(" where usuario = ? ");

            cursor = db.rawQuery(sb.toString(), argumentos);

            while (cursor.moveToNext()) {
                cAux = new Usuario();
                cAux.setCodigo(cursor.getString(cursor.getColumnIndex(CODIGO)));
                cAux.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cAux.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
                cAux.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                cAux.setCodpro(cursor.getString(cursor.getColumnIndex(CODPRO)));
                cAux.setCodsub(cursor.getString(cursor.getColumnIndex(CODSUB)));
                cAux.setAtivo(cursor.getString(cursor.getColumnIndex(ATIVO)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

    public Usuario obterAcessoUsuario(String codpro, String subpro) {

        Usuario cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            String[] argumentos = {codpro, subpro};
            StringBuilder sb = new StringBuilder();

            sb.append("select codigo, usuario, nome, senha, email, codpro, codsub, ativo from " + TABELA);
            sb.append(" where ativo = 'S' ");
            sb.append(" and codpro = ? and codsub = ? ");

            cursor = db.rawQuery(sb.toString(), argumentos);

            while (cursor.moveToNext()) {
                cAux = new Usuario();
                cAux.setCodigo(cursor.getString(cursor.getColumnIndex(CODIGO)));
                cAux.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cAux.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
                cAux.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                cAux.setCodpro(cursor.getString(cursor.getColumnIndex(CODPRO)));
                cAux.setCodsub(cursor.getString(cursor.getColumnIndex(CODSUB)));
                cAux.setAtivo(cursor.getString(cursor.getColumnIndex(ATIVO)));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return cAux;
    }

    public Usuario obterUsuarioAtivo() {

        Usuario cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {


            StringBuilder sb = new StringBuilder();

            sb.append("select codigo, usuario, nome, senha, email, codpro, codsub, ativo from " + TABELA);
            sb.append(" where ativo = 'S' ");

            cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                cAux = new Usuario();

                cAux.setCodigo(cursor.getString(cursor.getColumnIndex(UsuarioDao.CODIGO)));
                cAux.setUsuario(cursor.getString(cursor.getColumnIndex(UsuarioDao.USUARIO)));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDao.NOME)));
                cAux.setSenha(cursor.getString(cursor.getColumnIndex(UsuarioDao.SENHA)));
                cAux.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDao.EMAIL)));
                cAux.setCodpro(cursor.getString(cursor.getColumnIndex(UsuarioDao.CODPRO)));
                cAux.setCodsub(cursor.getString(cursor.getColumnIndex(UsuarioDao.CODSUB)));
                cAux.setCodigo(cursor.getString(cursor.getColumnIndex(UsuarioDao.CODIGO)));
                cAux.setAtivo(cursor.getString(cursor.getColumnIndex(UsuarioDao.ATIVO)));
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return cAux;
    }


    public void desativarUsuario() {

        abrirBanco();

        ContentValues cv = new ContentValues();

        cv.put(UsuarioDao.ATIVO, "N");
        db.update(TABELA, cv, "", null);

        fecharBanco();
    }

    public void alterarUsuario(Usuario usuario) {

        abrirBanco();

        ContentValues cv = new ContentValues();

        String filtro = " usuario = ? ";
        String[] argumentos = {usuario.getUsuario()};

        cv.put(UsuarioDao.ATIVO, usuario.getAtivo());
        cv.put(UsuarioDao.SENHA, usuario.getSenha());

        db.update(TABELA, cv, filtro, argumentos);

        fecharBanco();
    }

}
