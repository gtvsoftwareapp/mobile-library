package com.example.modelproject.ui.act000;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.modelproject.R;
import com.example.modelproject.dao.ColaboradorDao;

import com.example.modelproject.ui.act004.FiltroUGBActivity;
import com.example.modelproject.util.HMAux;

public class ConsultaColaboradorActivity extends AppCompatActivity {
    private Context context;

    private EditText et_colaborador_consulta;
    private ListView lv_colaborador_consulta;

    SimpleAdapter adapter;

    private ColaboradorDao colaboradorDao;

    String[] De = {ColaboradorDao.CODZRA, ColaboradorDao.NOME};
    int[] Para = {R.id.tv_celula_02_descricao01, R.id.tv_celula_02_descricao02};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        inicializarVariaveis();
        inicializarAcoes();

    }

    private void inicializarVariaveis() {
        context = ConsultaColaboradorActivity.this;

        colaboradorDao = new ColaboradorDao(context);

        et_colaborador_consulta = findViewById(R.id.et_consulta);
        lv_colaborador_consulta = findViewById(R.id.lv_consulta);

        SimpleAdapter adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_colaborador_consulta.setAdapter(adapter);

    }

    private void inicializarAcoes() {
        ajustarTela();

        et_colaborador_consulta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                atualizarLista();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lv_colaborador_consulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HMAux retorno = (HMAux) adapterView.getItemAtPosition(i);
                String codcol = retorno.get(ColaboradorDao.CODZRA);
                String nome = retorno.get(ColaboradorDao.NOME);

                Intent getIntent = new Intent();
                getIntent.putExtra(ColaboradorDao.CODZRA, codcol);
                getIntent.putExtra(ColaboradorDao.NOME, nome);
                setResult(FiltroUGBActivity.RESULT_OK, getIntent);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

    }

    private void atualizarLista() {
        adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_colaborador_consulta.setAdapter(adapter);
    }

    private void ajustarTela() {
        getSupportActionBar().setElevation(0);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Colaborador");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private ArrayList<HMAux> carregarLista() {

        colaboradorDao = new ColaboradorDao(getBaseContext());
        ArrayList<HMAux> retorno = colaboradorDao.obterListaColaborador();

        ArrayList<HMAux> colaboradores = new ArrayList<>();

        String texto = et_colaborador_consulta.getText().toString().toLowerCase();
        for (int i = 0; i < retorno.size(); i++) {
            HMAux item = (HMAux) retorno.get(i);

            String descricao = item.get(ColaboradorDao.NOME).toLowerCase();
            String codigo = item.get(ColaboradorDao.CODZRA);

            if (descricao.contains(texto) || (codigo.contains(texto))) {
                HMAux colaborador = new HMAux();
                colaborador.put(ColaboradorDao.CODZRA, item.get(ColaboradorDao.CODZRA));
                colaborador.put(ColaboradorDao.NOME, item.get(ColaboradorDao.NOME));
                colaboradores.add(colaborador);
            }
        }


        return colaboradores;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharConsultaColaborador();
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

        return true;
    }

    @Override
    public void onBackPressed() {
        fecharConsultaColaborador();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    private void fecharConsultaColaborador() {
        Intent getIntent = new Intent();
        getIntent.putExtra(ColaboradorDao.CODZRA, "");
        getIntent.putExtra(ColaboradorDao.NOME, "");
        setResult(FiltroUGBActivity.RESULT_OK, getIntent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
