package com.example.modelproject.service;

import android.content.Context;
import android.os.StrictMode;

import com.example.modelproject.dao.ColaboradorDao;
import com.example.modelproject.dao.CompetenciaDao;
import com.example.modelproject.dao.FilialDao;
import com.example.modelproject.dao.UgbDao;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Colaborador;
import com.example.modelproject.model.Competencia;
import com.example.modelproject.model.Filial;
import com.example.modelproject.model.Ugb;
import com.example.modelproject.model.Usuario;
import com.example.modelproject.util.Constantes;
import com.example.modelproject.util.ToolBox;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Sincronismo {

    private UsuarioDao usuarioDao;
    private ColaboradorDao colaboradorDao;
    private CompetenciaDao competenciaDao;
    private FilialDao filialDao;
    private UgbDao ugbDao;


    private String url = "";

    public void getUsuario(Context context, String[][] parametros) {

        url = ToolBox.obterConfiguracao(context);

        try {
            usuarioDao = new UsuarioDao(context);
            ArrayList<Usuario> usuarios = new ArrayList<>();

            Gson gson = new Gson();

            String resultado = comunicacao(url + Constantes.WS_USUARIO, parametros);

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                String codigo = jsonObject.getString("codigo");
                String usuario = jsonObject.getString("usuario");
                String nome = jsonObject.getString("nome");
                String senha = jsonObject.getString("senha");
                String email = jsonObject.getString("email");
                String codpro = jsonObject.getString("codpro");
                String codsub = jsonObject.getString("codsub");
                String ativo = "N";

                Usuario u1 = new Usuario(codigo, usuario, nome, senha, email, codpro, codsub, ativo);
                usuarios.add(u1);
            }

            usuarioDao.inserirUsuario(usuarios);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getColaborador(Context context, String[][] parametros) {

        url = ToolBox.obterConfiguracao(context);

        try {
            colaboradorDao = new ColaboradorDao(context);
            ArrayList<Colaborador> colaboradores = new ArrayList<>();

            Gson gson = new Gson();

            String resultado = comunicacao(url + Constantes.WS_COLABORADOR, parametros);

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                String codzra = jsonObject.getString("codigo");
                String nome = jsonObject.getString("nome");
                String codugb = jsonObject.getString("ugb");

                Colaborador u1 = new Colaborador(codzra, nome, codugb);
                colaboradores.add(u1);
            }

            colaboradorDao.inserirColaborador(colaboradores);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //COMPETENCIA
    public void getCompetencia(Context context, String[][] parametros) {

        url = ToolBox.obterConfiguracao(context);

        try {
            competenciaDao = new CompetenciaDao(context);
            ArrayList<Competencia> competencias = new ArrayList<>();

            String resultado = comunicacao(url + Constantes.WS_COMPETENCIA, parametros);

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                String compet = jsonObject.getString("COMPET");
                String nivel = jsonObject.getString("NIVEL");


                Competencia comp = new Competencia(compet, nivel);
                competencias.add(comp);
            }

            competenciaDao.inserirCompetencia(competencias);
            //caso de erro
        } catch (JSONException e) {
            ArrayList<Competencia> competencias = new ArrayList<>();

            Competencia comp = new Competencia("", "");
            competencias.add(comp);
            competenciaDao.inserirCompetencia(competencias);
            e.printStackTrace();
        }

    }

    //FILIAL
    public void getFilial(Context context, String[][] parametros) {

        url = ToolBox.obterConfiguracao(context);

        try {
            filialDao = new FilialDao(context);
            ArrayList<Filial> filiais = new ArrayList<>();

            String resultado = comunicacao(url + Constantes.WS_FILIAL, parametros);

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                String fazenda = jsonObject.getString("FAZENDA");
                String estado = jsonObject.getString("ESTADO");
                String codloc = jsonObject.getString("CODLOC");
                String desloc = jsonObject.getString("DESLOC");
                String filial = jsonObject.getString("FILIAL");
                String regiao = jsonObject.getString("REGIAO");

                Filial fil = new Filial(filial, desloc);
                filiais.add(fil);
            }

            filialDao.inserirFilial(filiais);
            //caso de erro
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //UGB
    public void getUgb(Context context, String[][] parametros) {

        url = ToolBox.obterConfiguracao(context);

        try {
            ugbDao = new UgbDao(context);
            ArrayList<Ugb> ugbs = new ArrayList<>();

            String resultado = comunicacao(url + Constantes.WS_UGB, parametros);

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                String filial = jsonObject.getString("FILIAL");
                String codloc = jsonObject.getString("CODLOC");
                String codger = jsonObject.getString("CODGER");
                String codcoo = jsonObject.getString("CODCOO");
                String codugb = jsonObject.getString("CODUGB");
                String desugb = jsonObject.getString("DESUGB");
                Integer notasm = jsonObject.getInt("NOTASM");
                Integer nota5s = jsonObject.getInt("NOTA5S");
                Integer avlmat = jsonObject.getInt("AVLMAT");

                Ugb ugb = new Ugb(filial, codloc, codger, codcoo, codugb, desugb, notasm, nota5s, avlmat);
                ugbs.add(ugb);
            }

            ugbDao.inserirUgb(ugbs);
            //caso de erro
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public static String comunicacao(String urlEnd, String[][] parametros) {
        StringBuilder sb = new StringBuilder();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        URL url;
        HttpURLConnection conn;

        try {

            url = new URL(urlEnd);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "close");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            os,
                            "UTF-8"
                    )
            );

            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < parametros.length; i++) {
                jsonObject.put(parametros[i][0], parametros[i][1]);
            }

            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();

            sb.append(readStream(conn.getInputStream()));

        } catch (Exception e) {
            sb.append("Error: " + e.toString());
        } finally {

        }

        return sb.toString();
    }

    private static String readStream(InputStream inputStream) {
        Reader reader = null;
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            inputStream,
                            "UTF-8"
                    )
            );

            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            reader.close();

        } catch (Exception e) {

        }

        return writer.toString();
    }
}
