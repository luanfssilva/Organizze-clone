package com.example.luan.organizze.activitys;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luan.organizze.R;
import com.example.luan.organizze.config.ConfiguracaoFirebase;
import com.example.luan.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNomeId);
        campoEmail = findViewById(R.id.editEmailId);
        campoSenha = findViewById(R.id.editSenhaId);
        btnCadastrar = findViewById(R.id.btnCadastrarId);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textNome = campoNome.getText().toString();
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();

                //Validacao se os campos foram preenchidos
                if(!textNome.isEmpty()){ //.isEmpty retorna se esta vazio
                   if(!textEmail.isEmpty()){
                       if(!textSenha.isEmpty()){

                           usuario = new Usuario();
                           usuario.setNome(textNome);
                           usuario.setEmail(textEmail);
                           usuario.setSenha(textSenha);

                           cadastrarUsuario();

                       }else{
                           Toast.makeText(CadastroActivity.this,
                                   "Campo SENHA vazio!",
                                   Toast.LENGTH_LONG).show();
                       }
                   }else{
                       Toast.makeText(CadastroActivity.this,
                               "Campo EMAIL vazio!",
                               Toast.LENGTH_LONG).show();
                   }
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Campo NOME vazio!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    } //end onCreate

    public  void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
            usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //Verifica se deu certo o cadastro
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar usuário!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(CadastroActivity.this,
                            "Erro ao cadastrar usuário!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
