package com.example.cyclusdashboard.uteis;

import java.util.Vector;

public class ExtratorCyclus
{
    protected String stringJson;

    public ExtratorCyclus( String stringJson )
    {
        this.stringJson = stringJson;
    }

    /** Considera resposta do servidor como objeto
     * JSON seguido de caracteres randômicos (lixo).
     * Faz transcrição da parte da string que corresponde
     * a um objeto JSON simples.
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

    /** Método extrator de dados de objeto simples de uma
     * dimensão. recebe campo (trata posicionalmente)
     * e retorna string possivelmente convertível do dado;
     * Método possivelmente instável.
     * TODO: correção na mensagem do servidor rpm -> t.
     * */
    public String extrairDadosDeStringJson( int campo )
    {
        String stringJsonDados = this.extrairDadosDoLixo();
        Vector<String> vetorString = new Vector<String>(8);
        StringBuilder parteVetor = new StringBuilder();
        boolean transcricao = false;

        for ( int i = 0; i < stringJsonDados.length(); i++ )
        {
            if ( stringJsonDados.charAt(i) == ':' )
            {
                transcricao = true;
                parteVetor = new StringBuilder();
            }
            else if ( stringJsonDados.charAt(i) == ',' )
            {
                transcricao = false;
                vetorString.add( parteVetor.toString() );
                parteVetor = new StringBuilder();
            }
            else if ( stringJsonDados.charAt(i) == '}' )
            {
                vetorString.add( parteVetor.toString() );
                break;
            }
            else
            {
                if ( transcricao )
                {
                    parteVetor.append( stringJsonDados.charAt(i) );
                }
            }
        }
        return vetorString.get( campo );
    }

    public void setStringJson(String stringJson) {
        this.stringJson = stringJson;
    }
}
