package com.example.cyclusdashboard.uteis;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

/** Faz tarefas de conversao especificas
 *  da aplicacao Cyclus.
 * @author LPLA-br
 * */
public class ConversorCyclus
{
    protected byte[] dados;

    public ConversorCyclus(byte[] dados)
    {
        this.dados = dados;
    }

    public String converterByteArrayParaString()
    {
        if ( this.dados.length == 0 ) return "{}";

        StringBuilder dados = new StringBuilder("");
        for ( int i = 0; i < this.dados.length; i++ )
        {
            dados.append((char) this.dados[i]);
        }
        return dados.toString();
    }


};
