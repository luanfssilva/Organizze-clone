package com.example.luan.organizze.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.example.luan.organizze.config.ConfiguracaoFirebase;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.security.Principal;

public class PrincipalActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionButton fabReceita;
    private com.github.clans.fab.FloatingActionButton fabDespesa;

    private MaterialCalendarView calendarView;
    private TextView textSaudacao, textSaldoGeral;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze");
        setSupportActionBar(toolbar);

        textSaldoGeral = findViewById(R.id.textSaldoGeralId);
        textSaudacao = findViewById(R.id.textSaudacaoId);
        calendarView = findViewById(R.id.calendarViewId);
        configuraCalendarView();

        fabReceita = findViewById(R.id.menu_receitaId);
        fabDespesa = findViewById(R.id.menu_despesaId);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSairId :
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void configuraCalendarView(){

        CharSequence meses[] = {"Janeiro","Fevereiro","Março","Abril",
        "Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        calendarView.setTitleMonths(meses);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });

    }

}
