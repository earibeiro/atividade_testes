package br.edu.ifpr.teste.servico.irpf;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe responsável por calcular o Imposto de Renda Pessoa Física (IRPF)
 * baseado na renda mensal.
 */
public class CalculadoraIRPF {
    
    // Constantes para as faixas de renda
    private static final BigDecimal LIMITE_FAIXA1 = new BigDecimal("1903.98");
    private static final BigDecimal LIMITE_FAIXA2 = new BigDecimal("2826.65");
    private static final BigDecimal LIMITE_FAIXA3 = new BigDecimal("3751.05");
    private static final BigDecimal LIMITE_FAIXA4 = new BigDecimal("4664.68");
    
    // Constantes para as alíquotas
    private static final BigDecimal ALIQUOTA_FAIXA1 = new BigDecimal("0.075"); // 7.5%
    private static final BigDecimal ALIQUOTA_FAIXA2 = new BigDecimal("0.15");  // 15%
    private static final BigDecimal ALIQUOTA_FAIXA3 = new BigDecimal("0.225"); // 22.5%
    private static final BigDecimal ALIQUOTA_FAIXA4 = new BigDecimal("0.275"); // 27.5%
    
    // Constantes para as deduções
    private static final BigDecimal DEDUCAO_FAIXA1 = new BigDecimal("142.80");
    private static final BigDecimal DEDUCAO_FAIXA2 = new BigDecimal("354.80");
    private static final BigDecimal DEDUCAO_FAIXA3 = new BigDecimal("636.13");
    private static final BigDecimal DEDUCAO_FAIXA4 = new BigDecimal("869.36");
    
    /**
     * Calcula o IRPF com base na renda mensal
     * @param rendaMensal A renda mensal em reais
     * @return O valor do imposto a ser pago
     * @throws IllegalArgumentException se a renda for negativa
     */
    public BigDecimal calcularIRPF(BigDecimal rendaMensal) {
        // Validação da entrada
        if (rendaMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A renda não pode ser negativa");
        }
        
        // Cálculo do imposto conforme a faixa de renda
        BigDecimal imposto;
        
        if (rendaMensal.compareTo(LIMITE_FAIXA1) <= 0) {
            // Faixa isenta
            imposto = BigDecimal.ZERO;
        } else if (rendaMensal.compareTo(LIMITE_FAIXA2) <= 0) {
            // Primeira faixa: 7.5%
            imposto = rendaMensal.multiply(ALIQUOTA_FAIXA1).subtract(DEDUCAO_FAIXA1);
        } else if (rendaMensal.compareTo(LIMITE_FAIXA3) <= 0) {
            // Segunda faixa: 15%
            imposto = rendaMensal.multiply(ALIQUOTA_FAIXA2).subtract(DEDUCAO_FAIXA2);
        } else if (rendaMensal.compareTo(LIMITE_FAIXA4) <= 0) {
            // Terceira faixa: 22.5%
            imposto = rendaMensal.multiply(ALIQUOTA_FAIXA3).subtract(DEDUCAO_FAIXA3);
        } else {
            // Quarta faixa: 27.5%
            imposto = rendaMensal.multiply(ALIQUOTA_FAIXA4).subtract(DEDUCAO_FAIXA4);
        }
        
        // Arredonda para 2 casas decimais e verifica valor mínimo
        imposto = imposto.setScale(2, RoundingMode.HALF_UP);
        
        // Se o imposto calculado for negativo, retorna zero
        if (imposto.compareTo(BigDecimal.ZERO) < 0) {
            imposto = BigDecimal.ZERO;
        }
        
        return imposto;
    }
}