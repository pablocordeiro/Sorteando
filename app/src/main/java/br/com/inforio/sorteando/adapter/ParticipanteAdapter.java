package br.com.inforio.sorteando.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.inforio.sorteando.R;
import br.com.inforio.sorteando.model.Participante;

public class ParticipanteAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Participante> listaParticipantes;

    static class ParticipanteViewHolder {
        TextView txtNome;
    }

    public ParticipanteAdapter(Context context, List<Participante> listaParticipantes) {
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
        ParticipanteViewHolder participanteViewHolder;

        if (view == null){
            view = inflater.inflate(R.layout.adapter_participante, parent, false);

            participanteViewHolder = new ParticipanteViewHolder();
            participanteViewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);

            view.setTag(participanteViewHolder);
        } else {
            participanteViewHolder = (ParticipanteViewHolder) view.getTag();
        }

        participanteViewHolder.txtNome.setText(participante.getNome());

        return view;
    }
}
