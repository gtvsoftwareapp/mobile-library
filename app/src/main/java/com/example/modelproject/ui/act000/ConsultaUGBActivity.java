package com.example.modelproject.ui.act000;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modelproject.R;
import com.example.modelproject.dao.UgbDao;
import com.example.modelproject.ui.act004.FiltroUGBActivity;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;

public class ConsultaUGBActivity extends AppCompatActivity {

    private Context context;

    private EditText et_ugb_consulta;
    private ListView lv_ugb_consulta;

    private SimpleAdapter adapter;

    private UgbDao ugbDao;

    String[] De = {UgbDao.CODUGB, UgbDao.DESUGB,};
    int[] Para = {R.id.tv_celula_02_descricao01, R.id.tv_celula_02_descricao02};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        inicializarVariaveis();
        inicializarAcoes();

    }

    private void inicializarVariaveis() {

        context = ConsultaUGBActivity.this;

        ugbDao = new UgbDao(context);

        et_ugb_consulta = findViewById(R.id.et_consulta);
        lv_ugb_consulta = findViewById(R.id.lv_consulta);

        SimpleAdapter adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_ugb_consulta.setAdapter(adapter);

    }

    private void inicializarAcoes() {
        ajustarTela();

        et_ugb_consulta.addTextChangedListener(new TextWatcher() {
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

        lv_ugb_consulta.setOnItemClickListener((adapterView, view, i, l) -> {
            HMAux retorno = (HMAux) adapterView.getItemAtPosition(i);
            String desugb = retorno.get(UgbDao.DESUGB);
            String codugb = retorno.get(UgbDao.CODUGB);

            Intent getIntent = new Intent();
            getIntent.putExtra(UgbDao.CODUGB, codugb);
            getIntent.putExtra(UgbDao.DESUGB, desugb);
            setResult(FiltroUGBActivity.RESULT_OK, getIntent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        });

    }

    private void atualizarLista() {
        adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_02, De, Para);
        lv_ugb_consulta.setAdapter(adapter);
    }

    private void ajustarTela() {
        getSupportActionBar().setElevation(0);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("UGB");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private ArrayList<HMAux> carregarLista() {
        ArrayList<HMAux> retorno = ugbDao.obterListaUgb();
        ArrayList<HMAux> ugbs = new ArrayList<>();

        String texto = et_ugb_consulta.getText().toString().toLowerCase();

        for (int i = 0; i < retorno.size(); i++) {
            HMAux item = (HMAux) retorno.get(i);

            String codugb = item.get(UgbDao.CODUGB);
            String desugb = item.get(UgbDao.DESUGB).toLowerCase();

            if (codugb.contains(texto) || desugb.contains(texto)) {
                if (item.get(UgbDao.CODCOO).contains(getIntent().getStringExtra(UgbDao.CODCOO)) && item.get(UgbDao.CODGER).contains(getIntent().getStringExtra(UgbDao.CODGER)) && item.get(UgbDao.CODLOC).contains(getIntent().getStringExtra(UgbDao.CODLOC)) && item.get(UgbDao.FILIAL).contains(getIntent().getStringExtra(UgbDao.FILIAL))) {
                    HMAux mAux = new HMAux();
                    mAux.put(UgbDao.CODUGB, item.get(UgbDao.CODUGB));
                    mAux.put(UgbDao.DESUGB, item.get(UgbDao.DESUGB));
                    ugbs.add(mAux);
                }
            }
        }

        return ugbs;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharConsultaUgb();
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        fecharConsultaUgb();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void fecharConsultaUgb() {
        Intent getIntent = new Intent();
        getIntent.putExtra(UgbDao.DESUGB, "");
        getIntent.putExtra(UgbDao.CODUGB, "");
        setResult(FiltroUGBActivity.RESULT_OK, getIntent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
