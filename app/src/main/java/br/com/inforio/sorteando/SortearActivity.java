package br.com.inforio.sorteando;

import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.inforio.sorteando.dialog.SelecionarDialog;
import br.com.inforio.sorteando.model.Participante;


public class SortearActivity extends AppCompatActivity {
    private Button btnSortear;
    private TextView txtSorteio;

    private List<Participante> listaParticipantesSelecionados;
    private int contador;
    private int tempo;
    private static final int QUANTIDADE_REPETICAO = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortear);


        txtSorteio = (TextView) findViewById(R.id.txtSorteio);
        btnSortear = (Button) findViewById(R.id.btnSortear);
        btnSortear.setOnClickListener(onClickSortear());

        criarDialogSelecionarParticipante();
    }

    public void criarDialogSelecionarParticipante(){
        SelecionarDialog selecionarDialog = new SelecionarDialog(SortearActivity.this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(selecionarDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        selecionarDialog.show();
        selecionarDialog.getWindow().setAttributes(lp);
    }

    public void carregarListaSorteio(List<Participante> listaParticipantes){
        listaParticipantesSelecionados = new ArrayList<Participante>();

        for (Participante participante: listaParticipantes) {
            if (participante.getMarcado())
                listaParticipantesSelecionados.add(participante);
        }
    }

    public void sortear() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                contador++;
                tempo   += 5;

                int indice = new Random().nextInt(listaParticipantesSelecionados.size());
                Participante participante = listaParticipantesSelecionados.get(indice);

                txtSorteio.setText(participante.getNome().toString());

                if (contador <= QUANTIDADE_REPETICAO)
                    sortear();
                else {
                    txtSorteio.setTextColor(Color.BLUE);
                    btnSortear.setEnabled(true);
                }
            }
        }, tempo);
    }
    private View.OnClickListener onClickSortear() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = 0;
                tempo    = 10;
                txtSorteio.setTextColor(Color.BLACK);
                btnSortear.setEnabled(false);
                sortear();
            }
        };
    }
}
