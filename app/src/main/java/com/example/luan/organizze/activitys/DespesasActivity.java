package com.example.luan.organizze.activitys;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.example.luan.organizze.helper.DateCustom;
import com.example.luan.organizze.model.Movimentacao;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private FloatingTextButton fabSalvarDespesa;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.editDespesaReaisId);
        campoData = findViewById(R.id.editDespesaDataId);
        campoCategoria = findViewById(R.id.editDespesaCategoriaId);
        campoDescricao = findViewById(R.id.editDespesaDescricaoId);
        fabSalvarDespesa = findViewById(R.id.fabDespesaId);

        //Preenche o campo data com a date atual
        campoData.setText(DateCustom.dataAtual());

        fabSalvarDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movimentacao = new Movimentacao();
                movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
                movimentacao.setCategoria(campoCategoria.getText().toString());
                movimentacao.setDescricao(campoDescricao.getText().toString());
                movimentacao.setData(campoData.getText().toString());
                movimentacao.setTipo("d");

                movimentacao.salvar(campoData.getText().toString());

                Toast.makeText(getApplicationContext(),"Despesa Salva",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


}
