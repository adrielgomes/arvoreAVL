package com.br.arvore;

import java.awt.Color;

/**
 * A classe a seguir é usada para pesquisar na árvore O usuário primeiro fornece
 * os intervalos a serem pesquisados ​​no painel da janela principal Esses dois
 * valores são usados ​​para comparar com a árvore atual e realça os nós em
 * verde se esse nó estiver dentro do intervalo atual.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class PesquisarIntervalo extends Animacao {

    private Arvore arvore;
    private String s1, s2;
    private No no;

    /**
     * Construtor*
     */
    public PesquisarIntervalo(Arvore arvore, String s1, String s2) {
        super(arvore.pegarQuadroPrincipal());
        this.arvore = arvore;
        this.s1 = s1;
        this.s2 = s2;
        this.arvore.matrizVazia();

        this.no = new No("", arvore);
        arvore.setNo(this.no);
        this.no.bgCor(no.buscarCor());
    }

    /**
     * Esse método de execução é chamado quando o encadeamento é iniciado a
     * partir da classe de árvore Ele então reproduz a animação passando pela
     * árvore atual e destacando os nós no intervalo.
     */
    public void run() {
        pesquisarIntervalo(arvore.pegarRaiz(), s1, s2);
        this.no.definirCor(no.corInvisivel(), no.corInvisivel());
        this.no.descer();

        if (arvore.tamanhoLista() == 0) {
            arvore.pegarNotas().setNotas("Pesquisa de intervalo concluída.\n                           "
                    + "Não há nós que estejam dentro do intervalo");
        } else {
            arvore.pegarNotas().setNotas("Pesquisa de intervalo concluída.\n                           "
                    + "Os nós no intervalo são nós " + imprimirIntervaloDePesquisa() + ".\n");
        }
    }

    /**
     * Esse método é usado para pesquisar a árvore atual primeiro, verificando
     * sua raiz e, em seguida, verificando seu filho esquerdo e direito de forma
     * recursiva.
     *
     * @param no No
     * @param valor1 String
     * @param valor2 String
     */
    private void pesquisarIntervalo(No no, String valor1, String valor2) {
        int noInt = 0, valor1Int = 0, valor2Int = 0;

        //se o nó atual estiver vazio, saia
        if (no == null) {
            return;
        }

        //definir a cor dos nós de localização para laranja
        this.no.bgCor(no.buscarCor());
        this.no.setDados(no.irDados());
        this.no.avancarPara(no);
        arvore.pegarNotas().setNotas("Verificando se o nó [" + no.irDados()
                + "] encontra-se entre [" + valor1 + "] e [" + valor2 + "]...");
        esperarEmPausa();

        //Se o nó pesquisado não estiver dentro do intervalo, altere sua cor para cinza
        no.bgCor(Color.GRAY);

        /**
         * VERIFICANDO SE O NO ENCONTRA-SE NO INTERVALO*
         */
        /**
         * A instrução if a seguir verifica se o nó atual é um número, de forma
         * que possa ser comparado como um número, caso contrário, ele será
         * comparado como strings.
         */
        if (arvore.numero(no.irDados()) && arvore.numero(valor1) && arvore.numero(valor2)) {
            noInt = Integer.parseInt(no.irDados());
            valor1Int = Integer.parseInt(valor1);
            valor2Int = Integer.parseInt(valor2);

            if (valor1Int <= noInt && valor2Int >= noInt) {
                verificaNo(this.no, no, this.arvore);
            }
        } else {
            if (arvore.numero(no.irDados()) && arvore.numero(valor1)) {
                noInt = Integer.parseInt(no.irDados());
                valor1Int = Integer.parseInt(valor1);

                if (valor1Int <= noInt && valor2.compareTo(no.irDados()) >= 0) {
                    verificaNo(this.no, no, this.arvore);
                }
            } else if (arvore.numero(no.irDados()) && arvore.numero(valor2)) {
                noInt = Integer.parseInt(no.irDados());
                valor2Int = Integer.parseInt(valor2);

                if (valor1.compareTo(no.irDados()) <= 0 && valor2Int >= noInt) {
                    verificaNo(this.no, no, this.arvore);
                }
            } else {
                if (valor1.compareTo(no.irDados()) <= 0 && valor2.compareTo(no.irDados()) >= 0) {
                    verificaNo(this.no, no, this.arvore);
                }
            }
        }

        //Verifique se tanto o valor dos nós atuais quanto a primeira string de
        //entrada podem ser convertidos em números, caso contrário, compará-los como strings.
        if (arvore.numero(no.irDados()) && arvore.numero(valor1)) {
            noInt = Integer.parseInt(no.irDados());
            valor1Int = Integer.parseInt(valor1);

            if (valor1Int < noInt) {
                pesquisarIntervalo(no.irEsquerda(), valor1, valor2);
            }
        } else {
            if (valor1.compareTo(no.irDados()) < 0) {
                pesquisarIntervalo(no.irEsquerda(), valor1, valor2);
            }
        }

        //Verifique se tanto o valor dos nós atuais quanto a segunda string de entrada podem ser 
        //convertidos em números, caso contrário, compará-los como strings.
        if (arvore.numero(no.irDados()) && arvore.numero(valor2)) {
            noInt = Integer.parseInt(no.irDados());
            valor2Int = Integer.parseInt(valor2);

            if (valor2Int > noInt) {
                pesquisarIntervalo(no.irDireita(), valor1, valor2);
            }
        } else {
            if (valor2.compareTo(no.irDados()) > 0) {
                pesquisarIntervalo(no.irDireita(), valor1, valor2);
            }
        }
    }

    /**
     * O método a seguir destaca o nó de entrada para a cor verde e o adiciona
     * ao array temporário.
     *
     * @param no1 No
     * @param no2 No
     * @param arvore Arvore
     */
    public void verificaNo(No no1, No no2, Arvore arvore) {
        no1.setDados(no2.irDados());	//define o valor dos nós de localização para o nó atual
        no1.bgCor(no.corEncontrada());
        no2.bgCor(no.corEncontrada());
        arvore.pegarNotas().setNotas("Node [" + no2.irDados() + "] lies within the range.");
        arvore.pegarTemp().add(no2.irDados());
        esperarEmPausa();
    }

    /**
     * Esse método imprime todos os nós que estão dentro do intervalo e os
     * retorna como uma string.
     *
     * @return text String
     */
    public String imprimirIntervaloDePesquisa() {
        String texto = "";
        for (int i = 0; i < arvore.tamanhoLista(); i++) {
            texto += "[" + arvore.pegarTemp().get(i) + "] ";
        }

        return texto;
    }

    /**
     * Metodos de acesso*
     */
    public String getPalavra1() {
        return s1;
    }

    public String getPalavra2() {
        return s2;
    }

}
