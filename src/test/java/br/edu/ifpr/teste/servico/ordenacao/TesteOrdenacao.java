package br.edu.ifpr.teste.servico.ordenacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TesteOrdenacao {

    @Test
    public void deveOrdenar() throws Exception {
        // Arrange (PREPARAR) - Roy Osherove no livro “The Art of Unit Testing”.
        // Given (DADO) - BDD (Behavior Driven Development)

        String nomeArquivo = "casoUm.txt";
        String entradaEsperada = "5, 4, 6";
        String saidaEsperada = "4, 5, 6";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ordenacao/" + nomeArquivo);

        assertNotNull(inputStream, "Arquivo casoUm.txt não encontrado em test/resources");
        // Act (AGIR)
        // When (QUANDO)
        ServicoOrdenacao servicoOrdenacao = new ServicoOrdenacao();
        ArrayList<Integer> original = servicoOrdenacao.obterDadosArquivo(inputStream);
        ArrayList<Integer> ordenado = servicoOrdenacao.ordenar(original);
        String entrada = servicoOrdenacao.prepararParaImpressao(original);
        String saida = servicoOrdenacao.prepararParaImpressao(ordenado);

        // Assert (VERIFICAR)
        // Then (ENTÃO)
        assertEquals(entradaEsperada, entrada, "Entrada não está formatada corretamente");
        assertEquals(saidaEsperada, saida, "Saída não está formatada corretamente");
    }

}