package br.com.inforio.sorteando.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import br.com.inforio.sorteando.AmigoSecretoActivity;
import br.com.inforio.sorteando.R;

public class AlertaDialog extends Dialog {
    private Context context;
    private Button btnOk;
    private TextView txtMensagem;

    public AlertaDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alerta);

        btnOk = (Button) findViewById(R.id.btnOk);
        txtMensagem = (TextView) findViewById(R.id.txtMensagem);
        btnOk.setOnClickListener(onClickOk());

        setCancelable(false);
    }

    private View.OnClickListener onClickOk() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AmigoSecretoActivity) context).finish();
                dismiss();
            }
        };
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        txtMensagem.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        txtMensagem.setText(context.getResources().getString(titleId));
    }
}
