package com.example.luan.organizze.activitys;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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
        campoEmail = findViewById(R.id.editCadastroEmailId);
        campoSenha = findViewById(R.id.editCadastroSenhaId);
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

                    finish();
                }
                else{
                    //Tratamento de exception, caso o usuario faça alguma merda
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Este e-mail já está em uso";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                           excecao, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
