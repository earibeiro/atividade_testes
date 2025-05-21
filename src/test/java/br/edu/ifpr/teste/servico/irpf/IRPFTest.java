package br.edu.ifpr.teste.servico.irpf;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IRPFTest {
    
    private CalculadoraIRPF calculadora;
    
    @BeforeEach
    public void setUp() {
        calculadora = new CalculadoraIRPF();
    }
    
    // Classe de Equivalência 1: Renda até R$ 1.903,98 (Isento)
    @Test
    public void testRendaIsenta() {
        BigDecimal renda = new BigDecimal("1000.00");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(new BigDecimal("0.00"), imposto, "Renda de R$ 1.000,00 deve ser isenta");
    }
    
    // Classe de Equivalência 2: Renda de R$ 1.903,99 até R$ 2.826,65 (alíquota 7,5%)
    @Test
    public void testRendaPrimeiraFaixa() {
        BigDecimal renda = new BigDecimal("2500.00");
        BigDecimal impostoEsperado = new BigDecimal("44.70");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 2.500,00 deve ser R$ 44,70");
    }
    
    // Classe de Equivalência 3: Renda de R$ 2.826,66 até R$ 3.751,05 (alíquota 15%)
    @Test
    public void testRendaSegundaFaixa() {
        BigDecimal renda = new BigDecimal("3000.00");
        BigDecimal impostoEsperado = new BigDecimal("95.20");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto, 
                   "Imposto para renda de R$ 3.000,00 deve ser R$ 95,20");
    }
    
    // Classe de Equivalência 4: Renda de R$ 3.751,06 até R$ 4.664,68 (alíquota 22,5%)
    @Test
    public void testRendaTerceiraFaixa() {
        BigDecimal renda = new BigDecimal("4000.00");
        BigDecimal impostoEsperado = new BigDecimal("263.87");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 4.000,00 deve ser R$ 263,87");
    }
    
    // Classe de Equivalência 5: Renda acima de R$ 4.664,68 (alíquota 27,5%)
    @Test
    public void testRendaQuartaFaixa() {
        BigDecimal renda = new BigDecimal("7000.00");
        BigDecimal impostoEsperado = new BigDecimal("1055.64");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 7.000,00 deve ser R$ 1.055,64");
    }
    
    // Testes de valores limites
    
    // No limite de isenção
    @Test
    public void testRendaNoLimiteIsencao() {
        BigDecimal renda = new BigDecimal("1903.98");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(new BigDecimal("0.00"), imposto, 
                   "Renda de R$ 1.903,98 deve ser isenta");
    }
    
    // No limite inferior da primeira faixa tributável
    @Test
    public void testRendaNoLimiteInferiorPrimeiraFaixa() {
        BigDecimal renda = new BigDecimal("1903.99");
        BigDecimal impostoEsperado = new BigDecimal("0.00"); // 1903.99 * 7.5% - 142.80 = 0.0
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 1.903,99 deve ser R$ 0,00");
    }
    
    // No limite superior da primeira faixa tributável
    @Test
    public void testRendaNoLimiteSuperiorPrimeiraFaixa() {
        BigDecimal renda = new BigDecimal("2826.65");
        BigDecimal impostoEsperado = new BigDecimal("69.20");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 2.826,65 deve ser R$ 69,20");
    }
    
    // No limite inferior da segunda faixa tributável
    @Test
    public void testRendaNoLimiteInferiorSegundaFaixa() {
        BigDecimal renda = new BigDecimal("2826.66");
        BigDecimal impostoEsperado = new BigDecimal("69.20");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 2.826,66 deve ser R$ 69,20");
    }
    
    // No limite superior da segunda faixa tributável
    @Test
    public void testRendaNoLimiteSuperiorSegundaFaixa() {
        BigDecimal renda = new BigDecimal("3751.05");
        BigDecimal impostoEsperado = new BigDecimal("207.86");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 3.751,05 deve ser R$ 207,86");
    }
    
    // No limite inferior da terceira faixa tributável
    @Test
    public void testRendaNoLimiteInferiorTerceiraFaixa() {
        BigDecimal renda = new BigDecimal("3751.06");
        BigDecimal impostoEsperado = new BigDecimal("207.86");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 3.751,06 deve ser R$ 207,86");
    }
    
    // No limite superior da terceira faixa tributável
    @Test
    public void testRendaNoLimiteSuperiorTerceiraFaixa() {
    BigDecimal renda = new BigDecimal("4664.68");
    BigDecimal impostoEsperado = new BigDecimal("413.42");  // Corrigido de 413.43 para 413.42
    BigDecimal imposto = calculadora.calcularIRPF(renda);
    
        assertEquals(impostoEsperado, imposto,
                    "Imposto para renda de R$ 4.664,68 deve ser R$ 413,42");
}
    
    // No limite inferior da quarta faixa tributável
    @Test
    public void testRendaNoLimiteInferiorQuartaFaixa() {
        BigDecimal renda = new BigDecimal("4664.69");
        BigDecimal impostoEsperado = new BigDecimal("413.43");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(impostoEsperado, imposto,
                   "Imposto para renda de R$ 4.664,69 deve ser R$ 413,43");
    }
    
    // Valores Inválidos
    @Test
    public void testRendaNegativa() {
        BigDecimal renda = new BigDecimal("-1000.00");
        
        // JUnit 5 usa assertThrows em vez de expected
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculadora.calcularIRPF(renda),
            "Deveria lançar exceção para renda negativa"
        );
        
        assertTrue(exception.getMessage().contains("não pode ser negativa"));
    }
    
    @Test
    public void testRendaZero() {
        BigDecimal renda = new BigDecimal("0.00");
        BigDecimal imposto = calculadora.calcularIRPF(renda);
        
        assertEquals(new BigDecimal("0.00"), imposto, 
                   "Renda de R$ 0,00 deve ser isenta");
    }
}