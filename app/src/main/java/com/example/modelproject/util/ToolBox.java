package com.example.modelproject.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;

import com.example.modelproject.R;
import com.example.modelproject.dao.ColaboradorDao;
import com.example.modelproject.dao.CompetenciaDao;
import com.example.modelproject.dao.ConfiguracaoDao;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Colaborador;
import com.example.modelproject.model.Competencia;
import com.example.modelproject.model.Configuracao;
import com.example.modelproject.model.Usuario;

import java.text.Normalizer;
import java.util.regex.Pattern;


public class ToolBox {

    public static String obterVersao(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String versionName = pInfo.versionName;//Version Name
        int versionCode = pInfo.versionCode;//Version Code

        return versionName;
    }

    public static boolean verificarSincronizacao(Context context) {
        ColaboradorDao colaboradorDao = new ColaboradorDao(context);
        Colaborador cAux = colaboradorDao.obterColaborador();

        if (cAux == null) {
            ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Erro: Sincronização necessária!", R.drawable.ic_signal_wifi_off_black_24dp);
            return false;
        }
        return true;
    }


    public static boolean verificarConexao(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }

        return true;
    }

    public static void exibirMensagemConfirmacao(Context context, String titulo, String mensagem, int icon) {
        exibirMensagemToolbox(context, titulo, mensagem, icon, false, "Ok", null, "", null);
    }

    public static void exibirMensagemToolbox(Context context, String titulo, String mensagem, int icon, boolean tipo, String textoPositivo, DialogInterface.OnClickListener ActionPositivo, String textoNegativo, DialogInterface.OnClickListener ActionNegativo) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);

        if (icon > 0) {
            alerta.setIcon(icon);
        } else {
            alerta.setIcon(R.drawable.logo_tv_folhas);
        }

        alerta.setCancelable(false);

        alerta.setPositiveButton(textoPositivo, ActionPositivo);

        if (tipo) {
            alerta.setNegativeButton(textoNegativo, ActionNegativo);
        }

        alerta.show();
    }


    public static Boolean verificarAcesso(Context context, String processo, String subprocesso) {
        UsuarioDao usuarioDao = new UsuarioDao(context);
        Usuario cAux = usuarioDao.obterAcessoUsuario(processo, subprocesso);

        if (cAux == null) {
            return false;
        }

        return true;
    }

    public static String obterConfiguracao(Context context) {
        String config = "";

        ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(context);
        Configuracao cConf = configuracaoDao.obterConfiguracao();
        if (cConf != null) {
            config = cConf.getUrl();
        }

        return config;
    }

    public static String obterCompetenciaUgb(Context context) {
        String compet = "";
        CompetenciaDao competenciaDao = new CompetenciaDao(context);
        Competencia cCompUgb = competenciaDao.obterCompetencia();
        if (cCompUgb != null) {
            compet = cCompUgb.getCompet();
        }
        return compet;
    }


    public static String obterUsuario(Context context, String usuario) {
        Usuario auxUsr;

        UsuarioDao usuarioDao = new UsuarioDao(context);
        if (usuario.isEmpty()) {
            auxUsr = usuarioDao.obterUsuarioAtivo();
            if (auxUsr != null) {
                usuario = auxUsr.getUsuario();
            } else {
                usuario = "";
            }
        } else {
            auxUsr = usuarioDao.obterUsuarioByID(usuario);
            if (auxUsr != null) {
                usuario = auxUsr.getUsuario();
            } else {
                usuario = "";
            }

        }


        return usuario;
    }

    public static String obterSenha(Context context, String usuario) {
        Usuario auxUser;
        UsuarioDao usuarioDao = new UsuarioDao(context);

        if (usuario.isEmpty()) {
            auxUser = usuarioDao.obterUsuarioAtivo();
            if (auxUser != null) {
                usuario = auxUser.getSenha();

            }
        } else {
            auxUser = usuarioDao.obterUsuarioByID(usuario);
            if (auxUser != null) {
                usuario = auxUser.getSenha();
            } else {
                usuario = "";
            }
        }

        return usuario;
    }


    public static String tirarAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }


    public static ProgressDialog exibirProgressDialog(Context context, ProgressDialog progressDialog, int icon, String titulo, String mensagem, String controle) {
        if (controle.equals("A")) {//Abertura
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            if (icon == 0) {
                progressDialog.setIcon(R.drawable.logo_tv_folhas);
            } else {
                progressDialog.setIcon(icon);
            }


            progressDialog.setTitle(titulo);
            progressDialog.setMessage(mensagem);
            progressDialog.show();

        } else {//Fechar
            ToolBox.exibirMessagem(context, titulo, mensagem, R.drawable.logo_tv_folhas);
            progressDialog.dismiss();
        }

        return progressDialog;
    }

    public static void exibirMessagem(Context context, String titulo, String mensagem, int icon) {
        exibirMessagem(context, titulo, mensagem, icon, false, "Ok", null, "", null);
    }

    public static void exibirMessagem(Context context, String titulo, String mensagem, int icon, boolean tipo, String textoPositivo, DialogInterface.OnClickListener ActionPositivo, String textoNegativo, DialogInterface.OnClickListener ActionNegativo) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);

        if (icon > 0) {
            alerta.setIcon(icon);
        } else {
            alerta.setIcon(R.mipmap.ic_launcher);
        }

        alerta.setCancelable(false);

        alerta.setPositiveButton(textoPositivo, ActionPositivo);

        if (tipo) {
            alerta.setNegativeButton(textoNegativo, ActionNegativo);
        }

        alerta.show();
    }

}
