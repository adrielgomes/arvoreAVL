package com.br.arvore;

/**
 * Essa classe é executa sempre que uma operação é executada, como inserção,
 * exclusão e pesquisa de intervalo.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class MetodoPrincipal extends Thread {

    private String dados, fim;
    private int id;
    private QuadroPrincipal qp;

    /**
     * Construtor
     *
     * @param qp QuadroPrincipal
     * @param id int
     * @param dados String
     * @param fim String
     */
    public MetodoPrincipal(QuadroPrincipal qp, int id, String dados, String fim) {
        this.dados = dados;
        this.fim = fim;
        this.qp = qp;
        this.id = id;
    }

    /**
     * Esse método de execução é chamado sempre que uma operação é executada.
     */
    public void run() {
        qp.opercacoesDePerformace(id, dados, fim);
    }

    /**
     * Metodos de acesso
     */
    public String getNumeros() {
        return dados;
    }

    public int getID() {
        return id;
    }
}
