package com.example.modelproject.adapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modelproject.R;
import com.example.modelproject.util.HMAux;

import java.util.List;

public class AdapterOption extends BaseAdapter {

    public static final String ID = "id";
    public static final String IMAGEM = "imagem";
    public static final String DESCRICAO = "descricao";

    private Context context;
    private int resource;
    private List<HMAux> dados;

    private LayoutInflater mInflater;

    private long idselecionado = -1L;

    public void setIdselecionado(long idselecionado) {

        if (idselecionado != this.idselecionado) {
            this.idselecionado = idselecionado;
        } else {
            this.idselecionado = idselecionado;
        }

        notifyDataSetChanged();
    }

    public AdapterOption(Context context, int resource, List<HMAux> dados) {
        this.context = context;
        this.resource = resource;
        this.dados = dados;

        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dados.size();
    }

    @Override
    public Object getItem(int position) {
        return dados.get(position);
    }

    @Override
    public long getItemId(int position) {
        HMAux item = dados.get(position);
        return Long.parseLong(item.get(AdapterOption.ID));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
        }

        HMAux item = dados.get(position);

        LinearLayout ll_list_01 = (LinearLayout) convertView.findViewById(R.id.ll_list_01);

        ImageView iv_list_01_imagem = (ImageView) convertView.findViewById(R.id.iv_celula_02_imagem01);

        TextView tv_list_01_descricao = (TextView) convertView.findViewById(R.id.tv_celula_02_descricao01);

        iv_list_01_imagem.setImageResource(Integer.parseInt(item.get(AdapterOption.IMAGEM)));
        tv_list_01_descricao.setText(item.get(AdapterOption.DESCRICAO));

        if (getItemId(position) == idselecionado) {
            ll_list_01.setBackgroundColor(context.getColor(R.color.cinza_claro));
        } else {
            ll_list_01.setBackgroundColor(context.getColor(R.color.branco));
            //iv_list_01_imagem.setColorFilter(context.getColor(R.color.verde));
            tv_list_01_descricao.setTextColor(context.getColor(R.color.cinza));
        }

        return convertView;
    }
}
