package br.com.inforio.sorteando.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import br.com.inforio.sorteando.R;

public class ConfirmarDialog extends Dialog {
    private Context context;
    private Button btnSim;
    private Button btnNao;
    private TextView txtTitulo;

    public ConfirmarDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirmar);

        btnSim = (Button) findViewById(R.id.btnSim);
        btnNao = (Button) findViewById(R.id.btnNao);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);

        btnNao = (Button) findViewById(R.id.btnNao);
        btnNao.setOnClickListener(onClickNao());

        setCancelable(false);
    }

    private View.OnClickListener onClickNao() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        txtTitulo.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        txtTitulo.setText(context.getResources().getString(titleId));
    }
}
