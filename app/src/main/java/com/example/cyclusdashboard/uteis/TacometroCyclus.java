package com.example.cyclusdashboard.uteis;

/** Metodos comuns à telemetria de grandeza
 *  de velocidade.
 *  NOTA: Odometria requer evento
 *  existente apenas no servidor:
 *  momento em que uma revolução
 *  conclui-se dispara incremento
 *  em variável de odometria.
 * @Author LPLA-br
 * */
public class TacometroCyclus
{
    protected int MINUTO = 60000;
    protected int PERIMETRO_CIRCULAR_PNEU;

    protected int rpm;
    protected int velocidadeKmh;

    public TacometroCyclus()
    {
        this.PERIMETRO_CIRCULAR_PNEU = 2;
        this.rpm = 0;
        this.velocidadeKmh = 0;
    }

    /** Atualiza atributo rpm  de acordo com
     * parâmetro de intervalo em millisegundos. */
    public void obterRpm( int intervalo )
    {
        if ( intervalo > 0 )
        {
            this.rpm = (this.MINUTO / intervalo);
        }
        else
        {
            this.rpm = 0;
        }
    }

    /** Usa valor de rpm e perimetro externo do pneu
     * atualizar atributo de veloocidade em KM/H */
    public void obterVelocidadeKmh()
    {
        if ( this.rpm > 0 )
        {
            this.velocidadeKmh = (((this.PERIMETRO_CIRCULAR_PNEU * this.rpm) * 60) / 1000);
        }
        else
        {
            this.velocidadeKmh = 0;
        }
    }

    /** Configura atributo PERIMETRO_CIRCULAR_PNEU para outro valor */
    public void setPERIMETRO_CIRCULAR_PNEU(int PERIMETRO_CIRCULAR_PNEU)
    {
        this.PERIMETRO_CIRCULAR_PNEU = PERIMETRO_CIRCULAR_PNEU;
    }

    public int getRpm()
    {
        return this.rpm;
    }

    public int getVelocidadeKmh() {
        return velocidadeKmh;
    }

    public int getPERIMETRO_CIRCULAR_PNEU() {
        return PERIMETRO_CIRCULAR_PNEU;
    }
}
