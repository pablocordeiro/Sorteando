package br.com.inforio.sorteando.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.inforio.sorteando.AmigoSecretoActivity;
import br.com.inforio.sorteando.R;
import br.com.inforio.sorteando.SortearActivity;
import br.com.inforio.sorteando.adapter.SelecionarParticipanteAdapter;
import br.com.inforio.sorteando.db.RepositorioParticipante;
import br.com.inforio.sorteando.model.Participante;

public class SelecionarDialog extends Dialog {
    private Context context;
    private ListView lvSelecionarParticipante;
    private List<Participante> listaParticipantes;
    private SelecionarParticipanteAdapter selecionarParticipanteAdapter;
    private Button btnConfirmar;


    public SelecionarDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_selecionar);

        lvSelecionarParticipante = (ListView) findViewById(R.id.lvSelecionarParticipante);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(onClickConfirmar());

        listaParticipantes = RepositorioParticipante.getInstancia(this.context).getParticipantes();
        selecionarParticipanteAdapter = new SelecionarParticipanteAdapter(this.context, listaParticipantes);
        lvSelecionarParticipante.setAdapter(selecionarParticipanteAdapter);

        setCancelable(true);
    }

    private View.OnClickListener onClickConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof SortearActivity)
                    ((SortearActivity) context).carregarListaSorteio(listaParticipantes);
                else
                    ((AmigoSecretoActivity) context).carregarListaSorteio(listaParticipantes);
                dismiss();
            }
        };
    }

}
