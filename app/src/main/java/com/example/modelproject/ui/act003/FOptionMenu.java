package com.example.modelproject.ui.act003;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.modelproject.R;
import com.example.modelproject.adapter.adapter.AdapterOption;
import com.example.modelproject.dao.ColaboradorDao;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Usuario;
import com.example.modelproject.service.Sincronismo;
import com.example.modelproject.ui.act001.LoginActivity;
import com.example.modelproject.ui.act002.ConfiguracaoActivity;
import com.example.modelproject.ui.act002.MinhaContaActivity;
import com.example.modelproject.util.Constantes;
import com.example.modelproject.util.HMAux;
import com.example.modelproject.util.ToolBox;

import java.util.ArrayList;

public class FOptionMenu extends Fragment {
    private Context context;

    private ListView lv_menu;

    private AdapterOption adapterOption;

    private ProgressDialog progressDialog;

    private UsuarioDao usuarioDao;
    private ColaboradorDao colaboradorDao;

    private String usuario = "";
    private String senha = "";
    private String compet = "";

    int[] imagens = {
            R.drawable.ic_save_black_24dp,
            R.drawable.ic_synchronization_arrows,
            R.drawable.ic_wifi_connection_signal_symbol,
            R.drawable.ic_lock_black_24dp,
            R.drawable.ic_settings_black_24dp,
            R.drawable.ic_logout,
    };

    String[] descricoes = {"Gravar", "Sincronizar", "Conexão", "Alterar senha", "Configurar", "Sair"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.foption_menu, container, false);

        inicializarVariaveis(mView);
        inicializarAcoes();

        return mView;
    }

    private void inicializarVariaveis(View mView) {
        context = getActivity();

        lv_menu = mView.findViewById(R.id.lv_foption_menu);

        usuarioDao = new UsuarioDao(context);

        colaboradorDao = new ColaboradorDao(context);
        usuarioDao = new UsuarioDao(context);

    }

    private void inicializarAcoes() {
        adapterOption = new AdapterOption(context, R.layout.f_option_celula_02, gerarOpcoes());
        lv_menu.setAdapter(adapterOption);

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterOption.setIdselecionado(l);

                Usuario cAux = usuarioDao.obterUsuario();

                switch (i) {

                    case 0://GRAVAR
                        if (ToolBox.verificarConexao(context)) {

                         /* ArrayList<HMAux> retorno = apontamentoViagemDao.obterListaApontamentosViagemPendentes();
                            if (retorno.size() > 0) {
                                new sincronizarGravacao().execute();
                            } else {
                                ToolBox.exibirMensagemConfirmacao(context, "Sincronismo", "Não há registros pendentes!", R.drawable.ic_signal_wifi_off_black_24dp);
                            }*/
                        } else {
                            ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Erro: Conexão necessária!", R.drawable.ic_signal_wifi_off_black_24dp);
                        }
                        break;

                    case 1://SINCRONIZAR
                        if (ToolBox.verificarConexao(context)) {

                            exibirProgressDialog(context, "Aguarde", "Sincronizando cadastros...");

                            new sincronizarCadastro().execute();

                        } else {
                            ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Erro: Conexão necessária!", R.drawable.ic_signal_wifi_off_black_24dp);
                        }
                        break;

                    case 2://CONEXÃO
                        if (ToolBox.verificarConexao(context)) {
                            ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Conexão estável!", 0);
                        }

                        break;

                    case 3://ALTERAR SENHA
                        if (cAux != null) {
                            if (ToolBox.verificarConexao(context)) {
                                abrirConta(context);
                            } else {
                                ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Erro: Conexão necessária!", R.drawable.ic_signal_wifi_off_black_24dp);
                            }
                        } else {
                            ToolBox.exibirMensagemConfirmacao(context, "Configuração", "Usuário sem acesso!", 0);
                        }
                        break;

                    case 4://CONFIGURAR
                        if (cAux == null) {
                            abrirConfiguracao(context);
                        } else {
                            ToolBox.exibirMensagemConfirmacao(context, "Configuração", "Usuário sem acesso!", 0);
                        }
                        break;

                    case 5://SAIR
                        abrirLogin(context);
                        break;
                }
            }
        });

        obterUsuario();
    }

    private void obterUsuario() {
        Usuario usuari = usuarioDao.obterUsuario();
        if (usuari != null) {
            usuario = ToolBox.obterUsuario(context, usuario);
            senha = ToolBox.obterSenha(context, senha);
        }
    }

    private void exibirProgressDialog(Context context, String texto, String mensagem) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logo_tv_folhas);
        progressDialog.setTitle(texto);
        progressDialog.setMessage(mensagem);
    }

    private class sincronizarCadastro extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Sincronismo sincronismo = new Sincronismo();

            buscaWSInicial(sincronismo);

            compet = ToolBox.obterCompetenciaUgb(context);

            buscaWSFinal(sincronismo);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            ToolBox.exibirMensagemConfirmacao(context, "Sincronismo", "Sincronismo realizado com sucesso!", 0);
        }

        protected void onProgressUpdate(Void... params) {
        }

    }

    private void buscaWSInicial(Sincronismo sincronismo) {
        Usuario auxUser = usuarioDao.obterUsuarioAtivo();
        if (auxUser != null) {
            usuario = ToolBox.obterUsuario(context, "");
            senha = ToolBox.obterSenha(context, "");
        }

        String[][] parametros = {
                {"WSUSER", Constantes.WS_USER},
                {"WSPSW", Constantes.WS_PSW},
                {"USUARIO", usuario},
                {"SENHA", senha},
                {"COMPET", compet},
                {"TIPO", "UGB"},
                {"FILIAL", ""},
                {"LOCUGB", ""},
                {"CODGER", ""},
                {"CODCOO", ""},
                {"CODUGB", ""}
        };

        sincronismo.getFilial(context, parametros);
        sincronismo.getCompetencia(context, parametros);

    }

    private void buscaWSFinal(Sincronismo sincronismo) {
        String[][] parametros2 = {
                {"WSUSER", Constantes.WS_USER},
                {"WSPSW", Constantes.WS_PSW},
                {"COMPET", compet},
                {"USUARIO", usuario},
                {"SENHA", senha},
                {"COMPET", compet},
                {"FILIAL", ""},
                {"TIPO", "UGB"},
                {"LOCUGB", ""},
                {"CODGER", ""},
                {"CODCOO", ""},
                {"CODUGB", ""}
        };

        sincronismo.getUgb(context, parametros2);

        buscarColaborador(context);
    }

    private void buscarColaborador(Context context) {
        Sincronismo sincronismo = new Sincronismo();

        String[][] parametros2 = {
                {"USUARIO", usuario},
                {"SENHA", senha},
                {"TIPO", "COLABORADOR"}
        };


        sincronismo.getColaborador(context, parametros2);
    }

    private ArrayList<HMAux> gerarOpcoes() {
        ArrayList<HMAux> opcoes = new ArrayList<>();

        for (int i = 0; i < imagens.length; i++) {
            HMAux opcao = new HMAux();
            opcao.put(AdapterOption.ID, String.valueOf(i + 1));
            opcao.put(AdapterOption.IMAGEM, String.valueOf(imagens[i]));
            opcao.put(AdapterOption.DESCRICAO, descricoes[i]);
            opcoes.add(opcao);
        }

        return opcoes;
    }

    private void abrirLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.right_out);
        getActivity().finish();
    }

    private void abrirConfiguracao(Context context) {
        Intent intent = new Intent(context, ConfiguracaoActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
        getActivity().finish();
    }

    private void abrirConta(Context context) {
        Intent intent = new Intent(context, MinhaContaActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
        getActivity().finish();
    }
}
