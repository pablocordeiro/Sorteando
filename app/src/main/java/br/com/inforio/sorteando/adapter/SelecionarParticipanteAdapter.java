package br.com.inforio.sorteando.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import br.com.inforio.sorteando.R;
import br.com.inforio.sorteando.dialog.CadastrarDialog;
import br.com.inforio.sorteando.dialog.SelecionarDialog;
import br.com.inforio.sorteando.model.Participante;

public class SelecionarParticipanteAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Participante> listaParticipantes;
    private Context context;

    static class SelecionarParticipanteViewHolder {
        CheckBox cbParticipante;
        LinearLayout llAdicionar;
    }

    public SelecionarParticipanteAdapter(Context context, List<Participante> listaParticipantes) {
        this.inflater           = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaParticipantes = listaParticipantes;
        this.context            = context;
    }

    public int getCount() {
        return listaParticipantes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaParticipantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Participante participante = listaParticipantes.get(position);
        SelecionarParticipanteViewHolder selecionarParticipanteViewHolder;

        if (view == null){
            view = inflater.inflate(R.layout.adapter_selecionar_participante, parent, false);

            selecionarParticipanteViewHolder = new SelecionarParticipanteViewHolder();
            selecionarParticipanteViewHolder.cbParticipante = (CheckBox) view.findViewById(R.id.cbParticipante);
            selecionarParticipanteViewHolder.llAdicionar   = (LinearLayout) view.findViewById(R.id.llAdicionar);

            view.setTag(selecionarParticipanteViewHolder);
        } else {
            selecionarParticipanteViewHolder = (SelecionarParticipanteViewHolder) view.getTag();
        }

        selecionarParticipanteViewHolder.llAdicionar.setVisibility(View.GONE);
        selecionarParticipanteViewHolder.cbParticipante.setVisibility(View.GONE);

        if (!participante.getNome().trim().equals("")){
            selecionarParticipanteViewHolder.cbParticipante.setVisibility(View.VISIBLE);
            selecionarParticipanteViewHolder.cbParticipante.setText(participante.getNome());
            selecionarParticipanteViewHolder.cbParticipante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    participante.setMarcado(isChecked);
                }
            });
        } else {
            selecionarParticipanteViewHolder.llAdicionar.setVisibility(View.VISIBLE);
            selecionarParticipanteViewHolder.llAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Participante participante1 = new Participante();
                    CadastrarDialog cadastrarDialog = new CadastrarDialog(context, participante1);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(cadastrarDialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                    cadastrarDialog.show();
                    cadastrarDialog.getWindow().setAttributes(lp);
                }
            });
        }

        return view;
    }
}
