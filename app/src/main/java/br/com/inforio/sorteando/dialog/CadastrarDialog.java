package br.com.inforio.sorteando.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.inforio.sorteando.CadastrarActivity;
import br.com.inforio.sorteando.R;
import br.com.inforio.sorteando.db.RepositorioParticipante;
import br.com.inforio.sorteando.model.Participante;

public class CadastrarDialog extends Dialog {
    private Context context;
    private Button btnSalvar;
    private TextView edtNome;
    private Participante participante;

    public CadastrarDialog(Context context, Participante participante) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cadastrar);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        edtNome   = (TextView) findViewById(R.id.edtNome);

        this.participante = participante;

        edtNome.setText(participante.getNome().toString());

        btnSalvar.setOnClickListener(onClickSalvar());

        setCancelable(true);
    }

    private View.OnClickListener onClickSalvar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepositorioParticipante repositorioParticipante = RepositorioParticipante.getInstancia(context);

                if (testarCampos()) {
                    participante.setNome(edtNome.getText().toString());

                    if (participante.getId() == 0) {
                        repositorioParticipante.inserir(participante);
                    } else {
                        repositorioParticipante.alterar(participante);
                    }

                    ((CadastrarActivity) context).atualizar();
                    dismiss();
                }
            }
        };
    }

    private boolean testarCampos(){
        String mens;
        mens = "";
        if (edtNome.getText().toString().trim().equals("")){
            mens = mens + "\nNome deve ser preenchido.";
        }

        if (!(mens.equals(""))) {
            mens = "Campo(s) obrigatórios não preenchidos: " + "\n" + mens;
            Toast.makeText(context, mens, Toast.LENGTH_LONG).show();
        }
        return (mens.equals(""));

    }
}
