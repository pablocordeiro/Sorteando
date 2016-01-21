package br.com.inforio.sorteando.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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


    public SelecionarDialog(Context context, boolean botaoAdicionar) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_selecionar);

        lvSelecionarParticipante = (ListView) findViewById(R.id.lvSelecionarParticipante);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(onClickConfirmar());

        atualizarListaParticipantes(botaoAdicionar);

        setCancelable(false);
    }

    public void atualizarListaParticipantes(boolean botaoAdicionar) {
        listaParticipantes = RepositorioParticipante.getInstancia(this.context).getParticipantes();
        if (botaoAdicionar) {
            Participante participante = new Participante();
            listaParticipantes.add(participante);
        }
        selecionarParticipanteAdapter = new SelecionarParticipanteAdapter(this.context, listaParticipantes);
        lvSelecionarParticipante.setAdapter(selecionarParticipanteAdapter);
    }

    private View.OnClickListener onClickConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean retorno;
                if (context instanceof SortearActivity)
                    retorno = ((SortearActivity) context).carregarListaSorteio(listaParticipantes);
                else
                    retorno = ((AmigoSecretoActivity) context).carregarListaSorteio(listaParticipantes);
                if (retorno)
                    dismiss();
                else
                    Toast.makeText(context, "Selecione pelo menos 2 participantes!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (context instanceof SortearActivity)
            ((SortearActivity) context).finish();
        else
            ((AmigoSecretoActivity) context).finish();
    }
}
