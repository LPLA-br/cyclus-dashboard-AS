package com.example.cyclusdashboard;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cyclusdashboard.uteis.ConversorCyclus;
import com.example.cyclusdashboard.uteis.ExtratorCyclus;
import com.example.cyclusdashboard.uteis.TacometroCyclus;


/**
 * Example local unit test, which will execute on the development machine (host).
 * TESTES UNITÁRIOS E DE INTEGRAÇÃO ENTRE CLASSES CRÍTICAS DO PROJETO CYCLUS.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UteisTests {

    @Test
    public void ConversorCyclusConvercao() {
        byte[] dadosFake = {0x7B ,0x69, 0x3A, 0x34,0x35,0x32,0x2C,0x63,0x3A,0x38,0x39,0x2C ,0x73 ,0x3A ,0x32 ,0x7D, 0x6B };
        String retornoEsperado = "{i:452,c:89,s:2}k";
        ConversorCyclus c = new ConversorCyclus( dadosFake );
        assertEquals( c.converterByteArrayParaString(), retornoEsperado );
    }

    @Test
    public void ExtratorCyclusObterDadoJsonSequencialmente() {
        byte[] dadosFake = {0x7B ,0x69, 0x3A, 0x34,0x35,0x32,0x2C,0x63,0x3A,0x38,0x39,0x2C ,0x73 ,0x3A ,0x32 ,0x7D, 0x6B };
        ConversorCyclus c = new ConversorCyclus( dadosFake );
        ExtratorCyclus x = new ExtratorCyclus(c.converterByteArrayParaString());
        String ret = x.extrairDadosDeStringJson(0);
        assertEquals( ret, "452" );
    }

    @Test
    public void TacometroCyclusRPM() {
        TacometroCyclus t = new TacometroCyclus();
        t.setPERIMETRO_CIRCULAR_PNEU(2);
        t.obterRpm( 1000 );
        assertEquals(t.getRpm(), 60);
    }

    @Test
    public void TacometroCyclusRPM_nulo() {
        TacometroCyclus t = new TacometroCyclus();
        t.setPERIMETRO_CIRCULAR_PNEU(2);
        t.obterRpm( 0 );
        assertEquals(t.getRpm(), 0);
    }

    @Test
    public void TacometroCyclusVel() {
        TacometroCyclus t = new TacometroCyclus();
        t.setPERIMETRO_CIRCULAR_PNEU(2);
        t.obterRpm( 1000 );
        t.obterVelocidadeKmh();
        assertEquals( t.getVelocidadeKmh() , 7);
    }

    @Test
    public void TacometroCyclusVel_nula() {
        TacometroCyclus t = new TacometroCyclus();
        t.setPERIMETRO_CIRCULAR_PNEU(2);
        t.obterRpm( 0 );
        t.obterVelocidadeKmh();
        assertEquals( t.getVelocidadeKmh() , 0);
    }

}