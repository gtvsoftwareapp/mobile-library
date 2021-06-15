package br.agr.terraviva.mainactivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private TextView tv_versao;
    private TextInputLayout txt_usuario;
    private TextInputLayout txt_senha;

    private Button btn_entrar;

    private String usuario;
    private String senha;
    private String mensagem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = MainActivity.this;


        tv_versao = findViewById(R.id.tv_versao);
        txt_usuario = findViewById(R.id.txt_usuario);
        txt_senha = findViewById(R.id.txt_senha);
    }

    private void inicializarAcoes() {

        btn_entrar.setOnClickListener(v -> {

            usuario = txt_usuario.getEditText().getText().toString().trim();
            senha = txt_senha.getEditText().getText().toString().trim();

            Toast.makeText(context, "Há registros pendentes, verificar conexão!!", Toast.LENGTH_SHORT).show();
        });
    }
}