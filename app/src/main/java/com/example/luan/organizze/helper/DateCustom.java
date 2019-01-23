package com.example.luan.organizze.helper;

import java.text.SimpleDateFormat;

/**
 * Created by @luanfssilva on 22/01/2019.
 */


public class DateCustom {

    public static String dataAtual(){
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    public static String mesAnoDataEscolhida(String data){

        String retornoData[] = data.split("/");

        String mesAno = retornoData[1] + retornoData[2];
        return mesAno;
    }
}
