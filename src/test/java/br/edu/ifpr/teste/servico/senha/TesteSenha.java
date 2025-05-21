package br.edu.ifpr.teste.servico.senha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.teste.excecao.SenhaException;
import br.edu.ifpr.teste.servico.ordenacao.ServicoSenha;
import br.edu.ifpr.teste.utils.MensagemUtil;

public class TesteSenha {

    @Test
    public void deveLancarExcecaoQuandoNaoTemCaractereEspecial() {
        // Arrange (PREPARAR) - Roy Osherove no livro “The Art of Unit Testing”.
        // Given (DADO) - BDD (Behavior Driven Development)

        ServicoSenha servicoSenha = new ServicoSenha();
        String senhaInvalida = "Senha123";

        // Act (AGIR)
        // When (QUANDO)

        Exception excecao = assertThrows(SenhaException.class, () -> {
            servicoSenha.validar(senhaInvalida);
        });

        // Assert (VERIFICAR)
        // Then (ENTÃO)
        assertEquals(MensagemUtil.ERRO_SENHA_SEM_CARACTERE_ESPECIAL, excecao.getMessage(),
                MensagemUtil.ERRO_SENHA_SEM_CARACTERE_ESPECIAL);
    }

}