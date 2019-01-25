package com.example.luan.organizze.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.example.luan.organizze.config.ConfiguracaoFirebase;
import com.example.luan.organizze.helper.Base64Custom;
import com.example.luan.organizze.model.Usuario;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.security.Principal;
import java.text.DecimalFormat;

public class PrincipalActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionButton fabReceita;
    private com.github.clans.fab.FloatingActionButton fabDespesa;

    private MaterialCalendarView calendarView;
    private TextView textSaudacao, textSaldoGeral;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double saldoGeral  = 0.0;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;


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

        fabReceita = findViewById(R.id.menu_receitaId);
        fabDespesa = findViewById(R.id.menu_despesaId);

        configuraCalendarView();

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

    public void recuperarResumo(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                saldoGeral  = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(saldoGeral);

                textSaudacao.setText("Olá, " + usuario.getNome());
                textSaldoGeral.setText("R$ " + resultadoFormatado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                //finish();
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

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
    }

    @Override
    protected void onStop(){
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
    }

}
