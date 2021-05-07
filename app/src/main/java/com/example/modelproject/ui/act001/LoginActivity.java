package com.example.modelproject.ui.act001;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.modelproject.R;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Usuario;
import com.example.modelproject.service.Sincronismo;
import com.example.modelproject.ui.act003.MenuActivity;
import com.example.modelproject.util.Constantes;
import com.example.modelproject.util.ToolBox;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private Context context;

    private TextInputLayout txt_usuario;
    private TextInputLayout txt_senha;

    private Button btn_acessar;

    private TextView tv_versao;

    private ProgressDialog progressDialog;

    private String usuario = "";
    private String senha = "";
    private String mensagem = "";
    private String url = "";

    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {

        context = LoginActivity.this;

        usuarioDao = new UsuarioDao(context);

        txt_usuario = findViewById(R.id.txt_usuario);
        txt_senha = findViewById(R.id.txt_senha);

        tv_versao = findViewById(R.id.tv_versao);

        btn_acessar = findViewById(R.id.btn_acessar);

    }

    private void inicializarAcoes() {
        ajustarTela(context);

        carregarUsuario();

        enviarAviso(context);

        btn_acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = txt_usuario.getEditText().getText().toString().trim();
                senha = txt_senha.getEditText().getText().toString().trim();

                if (validarCampos()) {
                    if (ToolBox.verificarConexao(context)) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.setIcon(R.drawable.logo_tv_folhas);
                        progressDialog.setMessage("Validando usuário...");

                        new sincronizarUsuario().execute();
                    } else {
                        if (validarLogin()) {
                            ativarUsuario();
                            abrirMenu(context);
                        } else {
                            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void carregarUsuario() {
        Usuario cAux = usuarioDao.obterUsuario();
        String usuario = "";
        String senha = "";

        if (cAux != null) {
            usuario = cAux.getUsuario();
            senha = cAux.getSenha();

            txt_usuario.getEditText().setText(usuario);
            txt_senha.getEditText().setText(senha);

            txt_usuario.getEditText().setEnabled(false);
            txt_senha.getEditText().setEnabled(false);
        }
    }

    private boolean validarCampos() {

        if (usuario.isEmpty()) {
            mensagem = "Erro: Nome é obrigatorio!";
            return false;
        }

        if (senha.isEmpty()) {
            mensagem = "Erro: Senha é obrigatoria!";
            return false;
        }

        return true;
    }

    private boolean validarLogin() {

        Usuario cAux = usuarioDao.obterUsuarioByID(usuario);

        if (cAux != null) {
            if (!usuario.trim().equalsIgnoreCase(cAux.getUsuario().trim()) || !senha.trim().equals(cAux.getSenha().trim())) {
                mensagem = "Erro: Credenciais inválidas!";
                return false;
            }
        } else {

            if (!usuario.trim().equalsIgnoreCase("admin") || !senha.trim().equals("admin")) {
                mensagem = "Erro: Credenciais inválidas!";
                return false;
            }
        }

        return true;
    }

    private void ativarUsuario() {

        Usuario cAux = new Usuario();
        cAux.setUsuario(usuario);
        cAux.setSenha(senha);
        cAux.setAtivo("S");

        usuarioDao.desativarUsuario();
        usuarioDao.alterarUsuario(cAux);

    }


    private void enviarAviso(Context context) {
        url = ToolBox.obterConfiguracao(context);

        if (url == null || url.isEmpty()) {
            mensagem = "Utilize o usuário admistrador no primeiro login!";
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
        }
    }


    private class sincronizarUsuario extends AsyncTask<Void, Void, Void> {

        private String resultado;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String[][] parametros = {
                    {"USUARIO", usuario},
                    {"SENHA", senha},
                    {"PROCESSO", Constantes.PROCESSO},
            };

            Sincronismo sincronismo = new Sincronismo();
            sincronismo.getUsuario(context, parametros);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            if (validarLogin()) {
                ativarUsuario();
                abrirMenu(context);
            } else {
                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void ajustarTela(Context context) {
        getSupportActionBar().setElevation(0);
        getSupportActionBar().hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.verde));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        tv_versao.setText("Versão: " + ToolBox.obterVersao(context));
    }

    private void abrirMenu(Context context) {
        Intent intent = new Intent(context, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }


}
