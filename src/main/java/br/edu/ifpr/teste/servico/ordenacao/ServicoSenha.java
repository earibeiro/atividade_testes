package br.edu.ifpr.teste.servico.ordenacao;

import br.edu.ifpr.teste.excecao.SenhaException;
import br.edu.ifpr.teste.utils.MensagemUtil;

public class ServicoSenha {

    public void validar(String senha) throws SenhaException {
        // TODO Auto-generated method stub
        if(!senha.matches(".*[!@#$%^&*].*"))
            throw new SenhaException(MensagemUtil.ERRO_SENHA_SEM_CARACTERE_ESPECIAL);
    }

}