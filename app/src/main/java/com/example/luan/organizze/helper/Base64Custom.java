package com.example.luan.organizze.helper;

import android.util.Base64;

/**
 * Created by @luanfssilva on 22/01/2019.
 */


public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodificarBase64(String textoCodigicado){
        return new String( Base64.decode(textoCodigicado, Base64.DEFAULT));
    }
}
