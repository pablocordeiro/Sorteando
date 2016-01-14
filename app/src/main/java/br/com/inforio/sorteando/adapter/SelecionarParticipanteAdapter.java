package br.com.inforio.sorteando.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import br.com.inforio.sorteando.R;
import br.com.inforio.sorteando.model.Participante;

public class SelecionarParticipanteAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Participante> listaParticipantes;

    static class SelecionarParticipanteViewHolder {
        CheckBox cbParticipante;
    }

    public SelecionarParticipanteAdapter(Context context, List<Participante> listaParticipantes) {
        this.inflater           = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaParticipantes = listaParticipantes;
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

            view.setTag(selecionarParticipanteViewHolder);
        } else {
            selecionarParticipanteViewHolder = (SelecionarParticipanteViewHolder) view.getTag();
        }

        selecionarParticipanteViewHolder.cbParticipante.setText(participante.getNome());
        selecionarParticipanteViewHolder.cbParticipante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                participante.setMarcado(isChecked);
            }
        });

        return view;
    }
}
