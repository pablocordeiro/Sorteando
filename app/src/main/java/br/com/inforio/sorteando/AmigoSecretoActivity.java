package br.com.inforio.sorteando;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.inforio.sorteando.adapter.SelecionarParticipanteAdapter;
import br.com.inforio.sorteando.dialog.SelecionarDialog;
import br.com.inforio.sorteando.model.Participante;


public class AmigoSecretoActivity extends AppCompatActivity {
    private Button btnSortear;
    private TextView txtSorteio;
    private TextView txtTempoLimpar;
    private Spinner spnSorteador;
    private SelecionarParticipanteAdapter selecionarParticipanteAdapter;


    private List<Participante> listaParticipantesSelecionados;
    private List<Participante> listaParticipantesNaoSorteados;
    private List<Participante> listaParticipantesSorteados;
    private List<Participante> listaParticipantesSorteador;
    private int contador;
    private int tempo;
    int contLimpar;
    private static final int QUANTIDADE_REPETICAO = 30;

    private Participante sorteador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo_secreto);


        txtTempoLimpar = (TextView) findViewById(R.id.txtTempoLimpar);
        txtSorteio = (TextView) findViewById(R.id.txtSorteio);
        btnSortear = (Button) findViewById(R.id.btnSortear);
        spnSorteador = (Spinner) findViewById(R.id.spnSorteador);
        btnSortear.setOnClickListener(onClickSortear());

        criarDialogSelecionarParticipante();
    }

    public void criarDialogSelecionarParticipante(){
        SelecionarDialog selecionarDialog = new SelecionarDialog(AmigoSecretoActivity.this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(selecionarDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        selecionarDialog.show();
        selecionarDialog.getWindow().setAttributes(lp);
    }

    public void carregarListaSorteio(List<Participante> listaParticipantes){
        listaParticipantesSelecionados = new ArrayList<Participante>();
        listaParticipantesNaoSorteados = new ArrayList<Participante>();
        listaParticipantesSorteados = new ArrayList<Participante>();
        listaParticipantesSorteador = new ArrayList<Participante>();

        for (Participante participante: listaParticipantes) {
            if (participante.getMarcado()) {
                listaParticipantesSelecionados.add(participante);
                listaParticipantesNaoSorteados.add(participante);
                listaParticipantesSorteador.add(participante);
            }
        }

        atualizarSpinner();
    }

    private void atualizarSpinner() {
        ArrayAdapter<Participante> adapter = new ArrayAdapter<Participante>(this, R.layout.adapter_spinner, listaParticipantesSorteador);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSorteador.setAdapter(adapter);
    }

    public void sortear() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                contador++;
                tempo += 5;

                int indice = new Random().nextInt(listaParticipantesSelecionados.size());
                txtSorteio.setText(listaParticipantesSelecionados.get(indice).getNome().toString());

                if (contador <= QUANTIDADE_REPETICAO)
                    sortear();
                else {
                    Participante participante = listaParticipantesNaoSorteados.get(new Random().nextInt(listaParticipantesNaoSorteados.size()));
                    txtSorteio.setText(participante.getNome().toString());
                    txtSorteio.setTextColor(getResources().getColor(R.color.Green900));
                    btnSortear.setEnabled(true);

                    boolean sorteado = false;
                    for (Participante participante1 : listaParticipantesSorteados) {
                        if (participante1.getId() == sorteador.getId()) {
                            sorteado = true;
                            break;
                        }
                    }

                    if (!sorteado)
                        listaParticipantesNaoSorteados.add(sorteador);

                    listaParticipantesNaoSorteados.remove(participante);
                    listaParticipantesSorteados.add(participante);
                    listaParticipantesSorteador.remove(sorteador);
                    contLimpar = 6;
                    cooldownLimpar();
                }
            }
        }, tempo);
    }

    public void limparResultado(){
        atualizarSpinner();
        txtSorteio.setText("");
        txtTempoLimpar.setText("");
    }
    public void cooldownLimpar(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                contLimpar--;
                txtTempoLimpar.setText("" + contLimpar);
                if (contLimpar > 0)
                    cooldownLimpar();
                else {
                    limparResultado();
                }
            }
        },1000);

    }

    private View.OnClickListener onClickSortear() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = 0;
                tempo    = 10;
                txtSorteio.setTextColor(Color.BLACK);
                btnSortear.setEnabled(false);

                sorteador = (Participante) spnSorteador.getSelectedItem();
                listaParticipantesNaoSorteados.remove(sorteador);
                sortear();
            }
        };
    }
}
