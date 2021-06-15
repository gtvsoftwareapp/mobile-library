package br.agr.terraviva.mainactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.format.TextStyle;

public class Test extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = Test.this;

        
    }

    private void inicializarAcoes() {
    }
}
