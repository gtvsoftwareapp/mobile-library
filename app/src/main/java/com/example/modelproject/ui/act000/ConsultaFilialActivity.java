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

import com.example.modelproject.R;
import com.example.modelproject.dao.FilialDao;
import com.example.modelproject.ui.act004.FiltroUGBActivity;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;


public class ConsultaFilialActivity extends AppCompatActivity {

    private Context context;

    private EditText et_consulta;
    private ListView lv_consulta;

    private SimpleAdapter adapter;

    private FilialDao filialDao;

    String[] De = {FilialDao.FAZENDA};
    int[] Para = {R.id.tv_celula_02_descricao01};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {

        context = ConsultaFilialActivity.this;

        filialDao = new FilialDao(context);

        et_consulta = findViewById(R.id.et_consulta);
        lv_consulta = findViewById(R.id.lv_consulta);

        SimpleAdapter adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_consulta.setAdapter(adapter);


    }

    private void inicializarAcoes() {
        ajustarTela();

        et_consulta.addTextChangedListener(new TextWatcher() {
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

        lv_consulta.setOnItemClickListener((adapterView, view, i, l) -> {
            HMAux retorno = (HMAux) adapterView.getItemAtPosition(i);
            String filial = retorno.get(FilialDao.FILIAL);
            String fazenda = retorno.get(FilialDao.FAZENDA);

            Intent getIntent = new Intent();
            getIntent.putExtra(FilialDao.FILIAL, filial);
            getIntent.putExtra(FilialDao.FAZENDA, fazenda);
            setResult(FiltroUGBActivity.RESULT_OK, getIntent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        });

    }

    private void atualizarLista() {
        adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_consulta.setAdapter(adapter);
    }

    private void ajustarTela() {
        getSupportActionBar().setElevation(0);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Filial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private ArrayList<HMAux> carregarLista() {

        ArrayList<HMAux> retorno = filialDao.obterListaFilial();
        ArrayList<HMAux> filiais = new ArrayList<>();

        String texto = et_consulta.getText().toString().toLowerCase();
        for (int i = 0; i < retorno.size(); i++) {
            HMAux item = (HMAux) retorno.get(i);

            String filial = item.get(FilialDao.FILIAL);
            String fazenda = item.get(FilialDao.FAZENDA).toLowerCase();

            if (filial.contains(texto) || fazenda.contains(texto)) {
                HMAux mAux = new HMAux();
                mAux.put(FilialDao.FAZENDA, item.get(FilialDao.FAZENDA));
                mAux.put(FilialDao.FILIAL, item.get(FilialDao.FILIAL));
                filiais.add(mAux);
            }
        }

        return filiais;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharConsultaUgb();
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
        return true;


    }

    @Override
    public void onBackPressed() {
        fecharConsultaUgb();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    private void fecharConsultaUgb() {
        Intent getIntent = new Intent();
        getIntent.putExtra(FilialDao.FAZENDA, "");
        getIntent.putExtra(FilialDao.FILIAL, "");
        setResult(FiltroUGBActivity.RESULT_OK, getIntent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

}
