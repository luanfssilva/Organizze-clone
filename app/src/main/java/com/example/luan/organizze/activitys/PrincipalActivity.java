package com.example.luan.organizze.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.github.clans.fab.FloatingActionMenu;

import java.security.Principal;

public class PrincipalActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionButton fabReceita;
    private com.github.clans.fab.FloatingActionButton fabDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabReceita = findViewById(R.id.menu_receitaId);
        fabDespesa = findViewById(R.id.menu_depesaId);

        //Adicionar Receita
        fabReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ReceitasActivity.class));
            }
        });

        //Adicionar Despesa
        fabDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, DespesasActivity.class));

            }
        });
    }

}
