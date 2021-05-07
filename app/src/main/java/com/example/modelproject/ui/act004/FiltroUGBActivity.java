package com.example.modelproject.ui.act004;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.modelproject.R;
import com.example.modelproject.dao.ColaboradorDao;
import com.example.modelproject.dao.CompetenciaDao;
import com.example.modelproject.dao.FilialDao;
import com.example.modelproject.dao.UgbDao;
import com.example.modelproject.model.Colaborador;
import com.example.modelproject.model.Ugb;
import com.example.modelproject.ui.act000.ConsultaColaboradorActivity;
import com.example.modelproject.ui.act000.ConsultaFilialActivity;
import com.example.modelproject.ui.act000.ConsultaUGBActivity;
import com.example.modelproject.ui.act000.LeituraActivity;
import com.example.modelproject.ui.act003.MenuActivity;
import com.example.modelproject.util.HMAux;

import java.util.ArrayList;

public class FiltroUGBActivity extends AppCompatActivity {
    private Context context;

    public static final String DESCRICAO = "descricao";

    public static final int RESULT_FILIAL = 1;
    public static final int RESULT_LOCAL = 2;
    public static final int RESULT_GERENTE = 3;
    public static final int RESULT_COLABORADOR = 4;
    public static final int RESULT_UGB = 5;
    public static final int RESULT_LEITURA = 5;

    private int id = -1;
    private String codfil = "";
    private String compet = "";
    private String desfil = "";
    private String codloc = "";
    private String desloc = "";
    private String desger = "";
    private String codger = "";
    private String codzra = "";
    private String descoo = "";
    private String codugb = "";
    private String desugb = "";
    private String tipo = "";


    private ListView lv_filial_ugb;
    private ListView lv_colaborador_ugb;
    private ListView lv_ugb_ugb;

    private FilialDao filialDao;
    private CompetenciaDao competenciaDao;
    private UgbDao ugbDao;
    private ColaboradorDao colaboradorDao;

    String[] De = {DESCRICAO};
    int[] Para = {R.id.tv_celula_01_descricao01};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_ugb);
        incializarVaiaveis();
        inicializarAcoes();

    }

    private void incializarVaiaveis() {
        context = FiltroUGBActivity.this;

        lv_filial_ugb = findViewById(R.id.lv_filial_ugb);
        lv_colaborador_ugb = findViewById(R.id.lv_coordenador_ugb);
        lv_ugb_ugb = findViewById(R.id.lv_ugb_ugb);


        filialDao = new FilialDao(context);
        colaboradorDao = new ColaboradorDao(context);
        ugbDao = new UgbDao(context);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void inicializarAcoes() {
        ajustarTela(context);

        tipo = "I";

        carregarLista("", lv_filial_ugb);
        carregarLista("", lv_colaborador_ugb);
        carregarLista("", lv_ugb_ugb);

        //Filial
        lv_filial_ugb.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent getIntent = new Intent(context, ConsultaFilialActivity.class);
            startActivityForResult(getIntent, RESULT_FILIAL);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        });


        //UGB
        lv_ugb_ugb.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent getIntent = new Intent(context, ConsultaUGBActivity.class);
            getIntent.putExtra(UgbDao.FILIAL, codfil);
            getIntent.putExtra(UgbDao.CODLOC, codloc);
            getIntent.putExtra(UgbDao.CODGER, codger);
            getIntent.putExtra(UgbDao.CODCOO, codzra);
            startActivityForResult(getIntent, RESULT_UGB);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);


        });

        //UGB
        lv_colaborador_ugb.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent getIntent = new Intent(context, ConsultaColaboradorActivity.class);
            getIntent.putExtra(ColaboradorDao.CODZRA, codzra);
            startActivityForResult(getIntent, RESULT_COLABORADOR);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        });

    }

    private void ajustarTela(Context context) {
        getSupportActionBar().setElevation(0);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Filtro UGB");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Filial
        if (resultCode == RESULT_OK && requestCode == RESULT_FILIAL) {
            codfil = data.getStringExtra(FilialDao.FILIAL);
            desfil = data.getStringExtra(FilialDao.FAZENDA);


            if (codfil.isEmpty()) {
                codloc = "";
                desloc = "";
                codger = "";
                desger = "";
                codzra = "";
                descoo = "";
                codugb = "";
                desugb = "";
                carregarLista(descoo, lv_colaborador_ugb);
                carregarLista(desugb, lv_ugb_ugb);

            }

            carregarLista(desfil, lv_filial_ugb);


        }

        //Coordenador
        if (resultCode == RESULT_OK && requestCode == RESULT_COLABORADOR) {
            codzra = data.getStringExtra(ColaboradorDao.CODZRA);
            Colaborador auxColab = colaboradorDao.obterColaboradorByCodzra(codzra);

            String nome = "";
            if (auxColab != null) {
                nome = auxColab.getNome();
            } else {
                nome = "";
            }

            carregarLista(nome, lv_colaborador_ugb);

        }

        //UGB
        if (resultCode == RESULT_OK && requestCode == RESULT_UGB) {
            codugb = data.getStringExtra(UgbDao.CODUGB);

            Ugb auxUgb = ugbDao.obterUgbByID(codugb);
            if (auxUgb != null) {
                desugb = auxUgb.getDesugb();
            } else {
                desugb = "";
            }

            carregarLista(codugb + " " + desugb, lv_ugb_ugb);

        }

    }

    private void abrirLeitura(Context context) {
        Intent intent = new Intent(context, LeituraActivity.class);
        startActivityForResult(intent, RESULT_LEITURA);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    private void carregarLista(String conteudo, ListView listView) {
        ArrayList<HMAux> listas = new ArrayList<>();
        HMAux aux = new HMAux();
        aux.put(DESCRICAO, conteudo);
        listas.add(aux);

        listView.setAdapter(new SimpleAdapter(context, listas, R.layout.celula_01, De, Para));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharFiltroUgb(context);
                break;
            case R.id.mn_leitor_qr_code:
                abrirLeitura(context);
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        fecharFiltroUgb(context);
    }

    private void fecharFiltroUgb(Context context) {
        Intent getIntent = new Intent(context, MenuActivity.class);
        startActivity(getIntent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

}

