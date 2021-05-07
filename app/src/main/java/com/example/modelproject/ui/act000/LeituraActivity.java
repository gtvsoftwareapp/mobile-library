package com.example.modelproject.ui.act000;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.modelproject.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class LeituraActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private Context context;

    private BarcodeReader barcodeReader;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);

        inicializarVariaveis();
    }

    private void inicializarVariaveis() {

        context = LeituraActivity.this;

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.preview);

        ajustarTela(context);
    }

    @Override
    public void onScanned(Barcode barcode) {
        fecharLeitura(barcode.displayValue);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        fecharLeitura(sparseArray.toString());
    }

    @Override
    public void onScanError(String errorMessage) {
        Toast.makeText(context, "Houve um erro ao scanear: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    public void onCameraPermissionDenied(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharLeitura("");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        fecharLeitura("");
    }

    private void ajustarTela(Context context) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setTitle("Leitura");
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.verde));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void fecharLeitura(String codigo) {
        Intent intent = new Intent();
        intent.putExtra("CODIGO", codigo);
        setResult(RESULT_OK, intent);
        finish();
    }
}