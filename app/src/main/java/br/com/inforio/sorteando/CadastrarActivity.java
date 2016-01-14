package br.com.inforio.sorteando;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.inforio.sorteando.adapter.ParticipanteAdapter;
import br.com.inforio.sorteando.db.RepositorioParticipante;
import br.com.inforio.sorteando.dialog.CadastrarDialog;
import br.com.inforio.sorteando.model.Participante;


public class CadastrarActivity extends AppCompatActivity {
    private Button btnNovo;
    private List<Participante> listaParticipantes;
    private ListView lvParticipantes;
    private ParticipanteAdapter participanteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        btnNovo = (Button) findViewById(R.id.btnNovo);
        lvParticipantes = (ListView) findViewById(R.id.lvParticipantes);

        btnNovo.setOnClickListener(onClickNovo());
        lvParticipantes.setOnItemClickListener(onClickLvParticipantes());
        lvParticipantes.setOnItemLongClickListener(onLongClickLvParticipantes());

        atualizar();
    }

    @Override
     protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_cadastrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickNovo() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarDialogParticipante(new Participante());
            }
        };
    }

    private AdapterView.OnItemClickListener onClickLvParticipantes() {
        AdapterView.OnItemClickListener clickLvParticipantes = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participante participante = listaParticipantes.get(position);
                criarDialogParticipante(participante);
            }
        };

        return clickLvParticipantes;
    }

    private AdapterView.OnItemLongClickListener onLongClickLvParticipantes(){
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Participante participante = listaParticipantes.get(position);
                RepositorioParticipante repositorioParticipante = RepositorioParticipante.getInstancia(CadastrarActivity.this);

                repositorioParticipante.deletar(participante.getId());

                atualizar();

                return false;
            }
        };
    }

    public void atualizar() {
        listaParticipantes = RepositorioParticipante.getInstancia(CadastrarActivity.this).getParticipantes();
        participanteAdapter = new ParticipanteAdapter(this, listaParticipantes);
        lvParticipantes.setAdapter(participanteAdapter);
    }

    public void criarDialogParticipante(Participante participante){
        CadastrarDialog cadastrarDialog = new CadastrarDialog(CadastrarActivity.this, participante);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(cadastrarDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        cadastrarDialog.show();
        cadastrarDialog.getWindow().setAttributes(lp);

    }

}
