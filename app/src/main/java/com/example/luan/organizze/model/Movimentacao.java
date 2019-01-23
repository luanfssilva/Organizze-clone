package com.example.luan.organizze.model;

import android.util.Base64;

import com.example.luan.organizze.config.ConfiguracaoFirebase;
import com.example.luan.organizze.helper.Base64Custom;
import com.example.luan.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by @luanfssilva on 23/01/2019.
 */


public class Movimentacao {

    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private double valor = 00.00;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida){

        FirebaseAuth  autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String mesAno = DateCustom.mesAnoDataEscolhida( dataEscolhida );
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDataBase();
        firebase.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
