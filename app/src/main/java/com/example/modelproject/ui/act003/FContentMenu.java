package com.example.modelproject.ui.act003;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.modelproject.R;
import com.example.modelproject.dao.UsuarioDao;
import com.example.modelproject.model.Usuario;
import com.example.modelproject.ui.act004.FiltroUGBActivity;
import com.example.modelproject.util.Constantes;
import com.example.modelproject.util.ToolBox;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class FContentMenu extends Fragment {

    private Context context;

    private String usuario = "";
    private String senha = "";

    GridLayout gridLayout;

    private UsuarioDao usuarioDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fcontent_menu, container, false);

        inicializarVariaveis(mView);
        inicializarAcoes();

        setSingleEvent(gridLayout);

        return mView;
    }

    private void inicializarVariaveis(View mView) {
        context = getActivity();

        gridLayout = mView.findViewById(R.id.grid_menu);

        usuarioDao = new UsuarioDao(context);

    }

    private void inicializarAcoes() {
        buscaDadosUsuario();
    }

    private boolean requisitarPermissoes(Activity context) {//pedido de permissoes para leitura, gravação e acesso a camera
        final int[] permissionsAcess = {-1};

        Dexter.withActivity(context)
                .withPermissions(
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {//ok
                            permissionsAcess[0] = 1;
                        } else {//DON'T OK
                            permissionsAcess[0] = 0;
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> {
                })
                .onSameThread()
                .check();

        if (permissionsAcess[0] == 1) {//OK
            return true;
        } else {//DON'T OK
            return false;
        }
    }

    private void buscaDadosUsuario() {
        Usuario usuari = usuarioDao.obterUsuario();
        if (usuari != null) {
            usuario = usuari.getUsuario();
            senha = usuari.getSenha();
        }
    }

    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(view -> {
                if (finalI == 0) {//APTO. VIAGENS

                    if (requisitarPermissoes((Activity) context)) {//Verifica permissões

                        if (ToolBox.verificarAcesso(context, Constantes.PROCESSO, Constantes.SUB_PROCESSO_CONTROLE_ACESSO)) {//Verificação Usuario x Processo

                            if (ToolBox.verificarConexao(context)) {//Verificação conexão

                                if (ToolBox.verificarSincronizacao(context)) {//Verificação Sincronização
                                    abrirFiltroUGB(context);

                                }
                            }

                        } else {
                            ToolBox.exibirMensagemConfirmacao(context, "Controle Acesso", "Usuário sem acesso!", 0);
                        }
                    }

                }
            });
        }
    }

    private void abrirFiltroUGB(Context context) {
        Intent getIntent = new Intent(context, FiltroUGBActivity.class);
        startActivity(getIntent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
        getActivity().finish();
    }


    private class syncBuscaAlteracaoUGB extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ToolBox.exibirProgressDialog(context, progressDialog, 0, "Aguarde...", "Sincronizando registros...", "A");

        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }

        protected void onProgressUpdate(Void... params) {
        }

    }


}
