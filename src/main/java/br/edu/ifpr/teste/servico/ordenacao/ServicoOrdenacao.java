package br.edu.ifpr.teste.servico.ordenacao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ServicoOrdenacao {

    public ArrayList<Integer> obterDadosArquivo(InputStream inputStream) throws Exception {

        ArrayList<Integer> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String primeiraLinha = reader.readLine();
            int quantidadeDeNumeros = Integer.parseInt(primeiraLinha);

            for (int i = 0; i < quantidadeDeNumeros; i++) {
                String linha = reader.readLine();
                int numero = Integer.parseInt(linha);
                lista.add(numero);
            }
        }
        return lista;
    }

    public ArrayList<Integer> ordenar(ArrayList<Integer> original) {
        ArrayList<Integer> ordenado = new ArrayList<>(original);
        Collections.sort(ordenado);

        return ordenado;
    }

    public String prepararParaImpressao(ArrayList<Integer> ordenado) {
        String saida = ordenado.get(0).toString();
        for (int i = 1; i < ordenado.size(); i++) {
            saida += ", " + ordenado.get(i);
        }

        return saida;
    }
}