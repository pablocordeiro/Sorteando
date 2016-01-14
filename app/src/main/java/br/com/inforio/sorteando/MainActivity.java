package br.com.inforio.sorteando;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button btnIncluir;
    private Button btnSortear;
    private Button btnAmigoSecreto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIncluir = (Button) findViewById(R.id.btnIncluir);
        btnSortear = (Button) findViewById(R.id.btnSortear);
        btnAmigoSecreto = (Button) findViewById(R.id.btnAmigoSecreto);

        btnIncluir.setOnClickListener(onClickIncluir());
        btnSortear.setOnClickListener(onClickSortear());
        btnAmigoSecreto.setOnClickListener(onClickAmigoSecreto());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private View.OnClickListener onClickIncluir() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivityForResult(intent, 0);
            }
        };
    }

    private View.OnClickListener onClickSortear() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SortearActivity.class);
                startActivityForResult(intent, 0);
            }
        };
    }

    private View.OnClickListener onClickAmigoSecreto() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AmigoSecretoActivity.class);
                startActivityForResult(intent, 0);
            }
        };
    }
}
