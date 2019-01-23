package com.example.luan.organizze.activitys;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.example.luan.organizze.config.ConfiguracaoFirebase;
import com.example.luan.organizze.helper.Base64Custom;
import com.example.luan.organizze.helper.DateCustom;
import com.example.luan.organizze.model.Movimentacao;
import com.example.luan.organizze.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private FloatingTextButton fabSalvarReceita;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoValor = findViewById(R.id.editReceitaReaisId);
        campoData = findViewById(R.id.editReceitaDataId);
        campoCategoria = findViewById(R.id.editReceitaCategoriaId);
        campoDescricao = findViewById(R.id.editReceitaDescricaoId);
        fabSalvarReceita = findViewById(R.id.fabReceitaId);

        //Preenche o campo data com a date atual
        campoData.setText(DateCustom.dataAtual());

        recuperarReceitaTotal();

        fabSalvarReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCamposReceita()){
                    movimentacao = new Movimentacao();
                    Double novaReceita = Double.parseDouble(campoValor.getText().toString());
                    movimentacao.setValor(novaReceita);
                    movimentacao.setCategoria(campoCategoria.getText().toString());
                    movimentacao.setDescricao(campoDescricao.getText().toString());
                    movimentacao.setData(campoData.getText().toString());
                    movimentacao.setTipo("r");

                    Double receitaAtualizada = receitaTotal + novaReceita;
                    atualizarReceita(receitaAtualizada);
                    movimentacao.salvar(campoData.getText().toString());

                    Toast.makeText(getApplicationContext(),"Receita Salva",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }//end onCreate

    public Boolean validarCamposReceita(){

        String textValor = campoValor.getText().toString();
        String textData = campoData.getText().toString();
        String textCategoria = campoCategoria.getText().toString();
        String textDescricao = campoDescricao.getText().toString();

        //Validacao se os campos foram preenchidos
        if(!textValor.isEmpty()){//.isEmpty retorna se esta vazio
            if(!textData.isEmpty()){
                if(!textCategoria.isEmpty()){
                    if(!textDescricao.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(ReceitasActivity.this,
                                "Descrição não foi preenchida!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(ReceitasActivity.this,
                            "Categoria não foi preenchida!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(ReceitasActivity.this,
                        "Data não foi preenchida!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(ReceitasActivity.this,
                    "Valor não foi preenchido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }//end validaCamposReceita

    public void recuperarReceitaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios")
                .child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void atualizarReceita(Double receita){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios")
                .child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);
    }


}
