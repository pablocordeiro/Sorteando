package br.com.inforio.sorteando;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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

import br.com.inforio.sorteando.dialog.AlertaDialog;
import br.com.inforio.sorteando.dialog.ConfirmarDialog;
import br.com.inforio.sorteando.dialog.SelecionarDialog;
import br.com.inforio.sorteando.model.Participante;


public class AmigoSecretoActivity extends AppCompatActivity {
    private Button btnSortear;
    private TextView txtSorteio;
    private TextView txtTempoLimpar;
    private Spinner spnSorteador;

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

    public boolean carregarListaSorteio(List<Participante> listaParticipantes){
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

        return !listaParticipantesSelecionados.isEmpty();
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
                btnSortear.setEnabled(false);

                int indice = new Random().nextInt(listaParticipantesSelecionados.size());
                txtSorteio.setText(listaParticipantesSelecionados.get(indice).getNome().toString());

                if (contador <= QUANTIDADE_REPETICAO)
                    sortear();
                else {
                    Participante participante = new Participante();

                    boolean sorteado = ParticipanteJaSorteado(sorteador.getId());
                    int size = listaParticipantesNaoSorteados.size();

                    if (!sorteado)
                        size += 1;

                    if ((size == 2) && (ParticipanteJaSorteado(sorteador.getId()))){
                        for (Participante participante2 : listaParticipantesNaoSorteados) {
                            if (!ParticipanteJaSorteou(participante2.getId())) {
                                participante = participante2;
                                break;
                            }
                        }
                    } else
                        participante = listaParticipantesNaoSorteados.get(new Random().nextInt(listaParticipantesNaoSorteados.size()));
                    txtSorteio.setText(participante.getNome().toString());
                    txtSorteio.setTextColor(getResources().getColor(R.color.Green900));

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
        btnSortear.setEnabled(spnSorteador.getCount() > 0);

        if (spnSorteador.getCount() <= 0) {
            final AlertaDialog ALERTA_DIALOG = new AlertaDialog(AmigoSecretoActivity.this);
            ALERTA_DIALOG.setTitle("Sorteio finalizado com sucesso!!!");
            ALERTA_DIALOG.show();
        }
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
        }, 1000);

    }

    private View.OnClickListener onClickSortear() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmarDialog CONFIRMAR_DIALOG = new ConfirmarDialog(AmigoSecretoActivity.this);
                CONFIRMAR_DIALOG.setTitle("Confirma?");
                CONFIRMAR_DIALOG.show();

                Button btnSim = (Button) CONFIRMAR_DIALOG.findViewById(R.id.btnSim);
                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONFIRMAR_DIALOG.dismiss();
                        IniciarSorteio();
                    }
                });
            }
        };
    }

    private void IniciarSorteio() {
        contador = 0;
        tempo = 10;
        txtSorteio.setTextColor(Color.BLACK);
        btnSortear.setEnabled(false);

        sorteador = (Participante) spnSorteador.getSelectedItem();
        listaParticipantesNaoSorteados.remove(sorteador);
        sortear();
    }

    private boolean ParticipanteJaSorteou(int id) {
        boolean encontrado = false;
        for (Participante participante : listaParticipantesSorteador) {
            if (participante.getId() == id){
                encontrado = true;
            }
        }
        return !encontrado;
    }

    private boolean ParticipanteJaSorteado(int id) {
        boolean sorteado = false;
        for (Participante participante : listaParticipantesSorteados) {
            if (participante.getId() == id) {
                sorteado = true;
                break;
            }
        }
        return sorteado;
    }
}
