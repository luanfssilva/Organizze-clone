package com.example.luan.organizze.activitys;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.luan.organizze.R;
import com.example.luan.organizze.model.Movimentacao;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;

    private FloatingTextButton fabSalvarReceita;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        fabSalvarReceita = findViewById(R.id.fabReceitaId);


        fabSalvarReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movimentacao = new Movimentacao();
                movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
                movimentacao.setCategoria(campoCategoria.getText().toString());
                movimentacao.setDescricao(campoDescricao.getText().toString());
                movimentacao.setData(campoData.getText().toString());
                movimentacao.setTipo("d");

                //movimentacao.salvar();
            }
        });


    }
}
