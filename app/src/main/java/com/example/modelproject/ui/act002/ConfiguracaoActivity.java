package com.example.modelproject.ui.act002;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.modelproject.ui.act003.MenuActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com.example.modelproject.R;
import com.example.modelproject.dao.ConfiguracaoDao;
import com.example.modelproject.model.Configuracao;
import com.example.modelproject.ui.act001.LoginActivity;
import com.example.modelproject.util.ToolBox;

public class ConfiguracaoActivity extends AppCompatActivity {

    private Context context;

    private TextInputLayout txt_url;

    private Button btn_salvar;

    private String url = "";

    private ConfiguracaoDao configuracaoDao;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = ConfiguracaoActivity.this;

        txt_url = findViewById(R.id.txt_url);
        btn_salvar = findViewById(R.id.btn_salvar_configuracao);

        configuracaoDao = new ConfiguracaoDao(context);

    }

    private void inicializarAcoes() {
        ajustarTela(context);

        obterConfiguracao(context);

        btn_salvar.setOnClickListener(v -> {
            if (validarCampos(context)) {
                ToolBox.exibirMensagemToolbox(context, "Configuração", "Deseja salvar?", 0, true, "Sim",
                        (dialog, which) -> gravarConfiguracao(),
                        "Nao",
                        null
                );


            }
        });
    }

    private void obterConfiguracao(Context context) {
        url = ToolBox.obterConfiguracao(context);
        if (url != null && !url.isEmpty()) {
            txt_url.getEditText().setText(String.valueOf(url));
        }

    }

    private boolean validarCampos(Context context) {
        url = txt_url.getEditText().getText().toString();

        if (url.isEmpty()) {
            ToolBox.exibirMensagemConfirmacao(context, "Configuração", "Campos obrigatórios não preenchidos!", 0);
            return false;
        }

        return true;
    }

    private void gravarConfiguracao() {
        Configuracao cAux = configuracaoDao.obterConfiguracao();

        //Inserir
        if (cAux == null) {

            ArrayList<Configuracao> urls = new ArrayList<>();

            int id = 1;
            String url = txt_url.getEditText().getText().toString().trim();

            Configuracao a1 = new Configuracao(id, url);

            urls.add(a1);

            configuracaoDao.inserirConfiguracao(urls);

        }

        //Alterar
        if (cAux != null) {

            int id = cAux.getId();
            String url = txt_url.getEditText().getText().toString().trim();

            cAux.setId(id);
            cAux.setUrl(url);

            configuracaoDao.alterarConfiguracaoByID(cAux);

        }

        ToolBox.exibirMensagemToolbox(context, "Configurações", "Configurações salvas com sucesso, refaça o login para aplicar as alterações!", 0, true, "Ok",
                (dialog, which) -> abrirLogin(context),
                "",
                null
        );
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

    private void ajustarTela(Context context) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setTitle("Configurações");
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.verde));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

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

