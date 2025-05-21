package br.edu.ifpr.teste.servico.calendario;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import paranavai.calendario.Calendario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

/**
 * Testes para o programa Cal usando particionamento de equivalência
 */
public class TesteCalendario {
    
    private Calendario calendario;
    private Calendar dataAtual;
    
    @BeforeEach
    public void setUp() {
        calendario = new Calendario();
        dataAtual = Calendar.getInstance();
    }
    
    @Test
    public void imprimeJaneiro2025() throws IOException {
        // Arrange (PREPARAR)
        Path path = Paths.get("src\\test\\resources\\calendario", "janeiro2025.txt");
        String saidaEsperada = Files.readString(path);
        saidaEsperada = saidaEsperada.replace("\r\n", "\n");

        // Act (AGIR)
        String janeiro2025 = calendario.getCalendario("1", "2025");

        // Assert (VERIFICAR)
        assertEquals(saidaEsperada, janeiro2025);
    }
    
    // 1. Sem parâmetros - exibe o mês atual
    @Test
    public void testSemParametros() {
        String resultado = calendario.getCalendario();
        int mesAtual = dataAtual.get(Calendar.MONTH) + 1; // Calendar.MONTH começa em 0
        int anoAtual = dataAtual.get(Calendar.YEAR);
        
        assertTrue(resultado.contains("" + mesAtual) && resultado.contains("" + anoAtual),
                  "Calendário sem parâmetros deve mostrar o mês atual");
    }
    
    // 2. Um parâmetro (ano) - casos válidos
    @Test
    public void testAnoValido() {
        String resultado = calendario.getCalendario("2023");
        assertTrue(resultado.contains("2023"), "Calendário deve mostrar o ano 2023");
        assertTrue(resultado.contains("Janeiro"), "Calendário deve mostrar Janeiro");
        assertTrue(resultado.contains("Dezembro"), "Calendário deve mostrar Dezembro");
    }
    
    @Test
    public void testAnoLimiteInferior() {
        String resultado = calendario.getCalendario("1");
        assertTrue(resultado.contains("1"), "Calendário deve mostrar o ano 1");
    }
    
    @Test
    public void testAnoLimiteSuperior() {
        String resultado = calendario.getCalendario("9999");
        assertTrue(resultado.contains("9999"), "Calendário deve mostrar o ano 9999");
    }
    
    // 2. Um parâmetro (ano) - casos inválidos
    @Test
    public void testAnoAbaixoDoLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("0");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testAnoAcimaDoLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("10000");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testAnoNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("-5");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testAnoNaoNumerico() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("abc");
        });
        assertNotNull(exception);
    }
    
    // 3. Dois parâmetros (mês, ano) - casos válidos
    @Test
    public void testMesAnoValidos() {
        String resultado = calendario.getCalendario("6", "2023");
        assertTrue(resultado.contains("Junho") && resultado.contains("2023"),
                 "Calendário deve mostrar Junho de 2023");
    }
    
    @Test
    public void testMesLimiteInferior() {
        String resultado = calendario.getCalendario("1", "2023");
        assertTrue(resultado.contains("Janeiro") && resultado.contains("2023"),
                 "Calendário deve mostrar Janeiro de 2023");
    }
    
    @Test
    public void testMesLimiteSuperior() {
        String resultado = calendario.getCalendario("12", "2023");
        assertTrue(resultado.contains("Dezembro") && resultado.contains("2023"),
                 "Calendário deve mostrar Dezembro de 2023");
    }
    
    // 3. Dois parâmetros (mês, ano) - casos inválidos
    @Test
    public void testMesAbaixoDoLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("0", "2023");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testMesAcimaDoLimite() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("13", "2023");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testMesNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("-3", "2023");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testMesNaoNumerico() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("abc", "2023");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testMesValidoAnoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("6", "0");
        });
        assertNotNull(exception);
    }
    
    // 4. Mais de dois parâmetros - ignora parâmetros extras
    @Test
    public void testParametrosExtras() {
        String resultadoComDois = calendario.getCalendario("6", "2023");
        String resultadoComTres = calendario.getCalendario("6", "2023", "extra");
        
        assertEquals(resultadoComDois, resultadoComTres, "Parâmetros extras devem ser ignorados");
    }
    
    @Test
    public void testMultiplosParametrosExtras() {
        String resultadoComDois = calendario.getCalendario("6", "2023");
        String resultadoComVarios = calendario.getCalendario("6", "2023", "10", "20");
        
        assertEquals(resultadoComDois, resultadoComVarios, "Múltiplos parâmetros extras devem ser ignorados");
    }
    
    // 5. Testes para a reforma do calendário Gregoriano
    @Test
    public void testReformaGregorianaSeptember1752() {
        String resultado = calendario.getCalendario("9", "1752");
        
        // Verifica se o calendário não contém os dias eliminados
        for (int dia = 3; dia <= 13; dia++) {
            assertFalse(resultado.contains(" " + dia + " "),
                      "O calendário não deve conter o dia " + dia + " de setembro de 1752");
        }
        
        assertTrue(resultado.contains(" 2 "), "O calendário deve conter o dia 2 de setembro");
        assertTrue(resultado.contains(" 14 "), "O calendário deve conter o dia 14 de setembro");
    }
    
    @Test
    public void testMesAposReforma() {
        String resultado = calendario.getCalendario("10", "1752");
        // Verifica se outubro de 1752 tem estrutura normal
        assertTrue(resultado.contains("Outubro") && resultado.contains("1752"),
                 "O calendário deve mostrar Outubro de 1752");
    }
    
    @Test
    public void testMesAntesReforma() {
        String resultado = calendario.getCalendario("8", "1752");
        // Verifica se agosto de 1752 tem estrutura normal
        assertTrue(resultado.contains("Agosto") && resultado.contains("1752"),
                 "O calendário deve mostrar Agosto de 1752");
    }
    
    @Test
    public void testAnoComReforma() {
        String resultado = calendario.getCalendario("1752");
        // Verifica se contém setembro de 1752 e se o ano tem todos os meses
        assertTrue(resultado.contains("1752"), "O calendário deve conter o ano 1752");
        assertTrue(resultado.contains("Setembro"), "O calendário deve conter setembro");
    }
    
    // 6. Casos especiais de interpretação de valores
    @Test
    public void testAnoComApenasDigitos() {
        String resultado = calendario.getCalendario("25");
        assertTrue(resultado.contains("25"), "Calendário deve mostrar o ano 25, não 2025");
        assertFalse(resultado.contains("2025"), "Calendário não deve mostrar o ano 2025");
    }
    
    @Test
    public void testMesComZeroEsquerda() {
        String resultadoComZero = calendario.getCalendario("06", "2023");
        String resultadoSemZero = calendario.getCalendario("6", "2023");
        
        assertEquals(resultadoSemZero, resultadoComZero, 
                   "Mês com zero à esquerda deve ser interpretado corretamente");
    }
    
    @Test
    public void testAnoComZerosEsquerda() {
        String resultadoComZeros = calendario.getCalendario("6", "02023");
        String resultadoSemZeros = calendario.getCalendario("6", "2023");
        
        assertEquals(resultadoSemZeros, resultadoComZeros,
                   "Ano com zeros à esquerda deve ser interpretado corretamente");
    }
    
    // 7. Combinação de entradas inválidas
    @Test
    public void testMesEAnoInvalidos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("0", "0");
        });
        assertNotNull(exception);
    }
    
    @Test
    public void testMesEAnoNaoNumericos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calendario.getCalendario("abc", "xyz");
        });
        assertNotNull(exception);
    }
}