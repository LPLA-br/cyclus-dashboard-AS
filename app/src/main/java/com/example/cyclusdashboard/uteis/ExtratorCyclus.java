package com.example.cyclusdashboard.uteis;

import org.json.JSONException;
import org.json.JSONObject;

public class ExtratorCyclus
{
    protected String stringJson;

    public ExtratorCyclus( String stringJson )
    {
        this.stringJson = stringJson;
    }

    /** Considera resposta do servidor como objeto
     * JSON seguido de caracteres randômicos (lixo)
     * Faz transcrição da parte da string que corresponde
     * a um objeto JSON.
     * Método potencialmente instável.
     * */
    protected String extrairDadosDoLixo()
    {
        if ( this.stringJson.isEmpty() ) return "{}";
        StringBuilder dados = new StringBuilder();
        for ( int i = 0; i < this.stringJson.length(); i++ )
        {
            if ( this.stringJson.charAt(i) == '}' )
            {
                dados.append( this.stringJson.charAt(i) );
                break;
            }
            dados.append( this.stringJson.charAt(i) );
        }
        return dados.toString();
    }

    /** Intenta extrair campo "rpm" da string
     * JSON objeto.
     * O campo "rpm" não corresponde a rpm mas
     * sim a intervalo em millisegundos que
     * a roda levou para completar uma revolução.
     * Método potencialmente instável.
     * TODO: correção na mensagem do servidor rpm -> t.
     * */
    public int extrairRpm()
    {
        String stringJsonDados = this.extrairDadosDoLixo();
        JSONObject dados;
        try {
            dados = new JSONObject( stringJsonDados );
            Object rpm = dados.get("rpm");
            if ( rpm instanceof Integer )
            {
                return (Integer) rpm;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
