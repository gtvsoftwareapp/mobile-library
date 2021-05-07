package com.example.modelproject.ui.act002;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.modelproject.R;
import com.example.modelproject.dao.ConfiguracaoDao;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Usuario;
import com.example.modelproject.service.Sincronismo;
import com.example.modelproject.ui.act001.LoginActivity;
import com.example.modelproject.ui.act003.MenuActivity;
import com.example.modelproject.util.Constantes;
import com.example.modelproject.util.ToolBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MinhaContaActivity extends AppCompatActivity {

    private Context context;

    private TextInputLayout txt_senha_atual;
    private TextInputLayout txt_senha_nova;
    private TextInputLayout txt_confirmar_senha;
    private Button btn_salvar;

    private String url = "";
    private String usuario = "";
    private String senha = "";

    private ProgressDialog progressDialog;

    private ConfiguracaoDao configuracaoDao;
    private UsuarioDao usuarioDao;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = MinhaContaActivity.this;

        usuarioDao = new UsuarioDao(context);
        configuracaoDao = new ConfiguracaoDao(context);

        txt_senha_atual = findViewById(R.id.txt_senha_atual);
        txt_senha_nova = findViewById(R.id.txt_senha_nova);
        txt_confirmar_senha = findViewById(R.id.txt_confirmar_senha);

        btn_salvar = findViewById(R.id.btn_salvar_minha_conta);

    }

    private void inicializarAcoes() {
        ajustarTela(context);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos(context) && validarSenha(context)) {
                    if (ToolBox.verificarConexao(context)) {

                        progressDialog = new ProgressDialog(context);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.setIcon(R.drawable.logo_tv_folhas);
                        progressDialog.setTitle("Aguarde...");
                        progressDialog.setMessage("Gravando lançamentos...");

                        new gravarAlteracaoSenha().execute();

                    } else {
                        ToolBox.exibirMensagemConfirmacao(context, "Conexão", "Erro: Sincronização necessária!", R.drawable.ic_signal_wifi_off_black_24dp);

                    }
                }
            }
        });

        url = ToolBox.obterConfiguracao(context);

        Usuario usuari = usuarioDao.obterUsuario();
        if (usuari != null) {
            usuario = usuari.getUsuario();
            senha = usuari.getSenha();
        }
    }

    private boolean validarCampos(Context context) {
        String senhaAtual = txt_senha_atual.getEditText().getText().toString();
        String senhaNova = txt_senha_nova.getEditText().getText().toString();
        String confirmarNovaSenha = txt_confirmar_senha.getEditText().getText().toString();

        if (senhaAtual.isEmpty()) {
            ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
            return false;
        }

        if (senhaNova.isEmpty()) {
            ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
            return false;
        }

        if (confirmarNovaSenha.isEmpty()) {
            ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
            return false;
        }

        return true;
    }

    private boolean validarSenha(Context context) {

        Usuario cAux = usuarioDao.obterUsuario();
        if (cAux != null) {
            String senha = cAux.getSenha();

            if (!txt_senha_atual.getEditText().getText().toString().trim().equals(senha.trim())) {
                ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
                return false;
            }
        }

        String senhaNova = txt_senha_nova.getEditText().getText().toString();
        String senhaAtual = txt_senha_atual.getEditText().getText().toString();
        String confirmarNovaSenha = txt_confirmar_senha.getEditText().getText().toString();

        if (senhaNova.trim().equals(senhaAtual.trim())) {
            ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
            return false;
        }

        if (!senhaNova.trim().equals(confirmarNovaSenha.trim())) {
            ToolBox.exibirMensagemConfirmacao(context, "Alterar senha", "Não foi possível alterar a senha, tente novamente!", 0);
            return false;
        }

        return true;
    }

    private void gravarMinhaConta() {
        Usuario cAux = usuarioDao.obterUsuario();

        if (cAux != null) {
            String usuario = cAux.getUsuario();
            String ativo = cAux.getUsuario();
            String senha = txt_senha_nova.getEditText().getText().toString();

            cAux.setUsuario(usuario);
            cAux.setAtivo(ativo);
            cAux.setSenha(senha.trim());

            usuarioDao.alterarUsuario(cAux);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                confirmarSaida(context);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        confirmarSaida(context);
    }

    private void confirmarSaida(Context context) {

        ToolBox.exibirMensagemToolbox(context, "Confirmação de saída", "Deseja sair?", 0, true, "Sim",
                (dialog, which) -> abrirMenu(context),
                "Nao",
                null
        );
    }

    private class gravarAlteracaoSenha extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            gravarSenha();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();

                ToolBox.exibirMensagemToolbox(context, "Alterar senha", "Senha alterada com sucesso, refaça o login para aplicar as alterações!", 0, true, "Ok",
                        (dialog, which) -> abrirLogin(context),
                        "",
                        null
                );
            }
        }
    }

    private void gravarSenha() {

        String resultado;
        String mensagem = "";
        String tipomsg = "";

        String novaSenha = txt_senha_nova.getEditText().getText().toString();

        String[][] parametros = {
                {"USUARIO", usuario},
                {"SENHA", senha},
                {"NOVASENHA", novaSenha.trim()}
        };

        Sincronismo sincronismo = new Sincronismo();
        resultado = sincronismo.comunicacao(url + Constantes.WS_GRAVAR_SENHA, parametros);

        Gson gson = new Gson();

        try {

            JSONArray jsonArr = null;
            jsonArr = new JSONArray(resultado);

            for (int x = 0; x < jsonArr.length(); x++) {
                JSONObject jsonObject = jsonArr.getJSONObject(x);
                mensagem = jsonObject.getString("mensagem");
                tipomsg = jsonObject.getString("tipomsg");
            }

            if (tipomsg.contains("s")) {
                gravarMinhaConta();
            } else {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void ajustarTela(Context context) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setTitle("Alterar senha");

        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.verde));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    private void abrirLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    private void abrirMenu(Context context) {
        Intent intent = new Intent(context, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

}

