package com.br.arvore;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Esta classe representa uma classe de árvore que é composta de uma variedade
 * de nós. Ele lida com todos os eventos do usuário na entrada e envia os
 * métodos apropriados, conforme necessário.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class Arvore {

    private No raiz;
    public No no;
    private ArrayList<String> temporario;
    private QuadroPrincipal qp;
    private int raio, comprimentoDeX, comprimentoDeY, coordenadaRaizX,
            coordenadaRaizY, alturaDaTela, larguraDaTela;
    private Animacao animacao;
    private Notas notas;

    public Arvore(QuadroPrincipal qp) {
        this.raiz = null;
        this.no = null;
        this.qp = qp;
        this.notas = new Notas();
        temporario = new ArrayList<String>();
    }

    /**
     * Este método é usado para verificar se a string de entrada é um número ou
     * não.
     *
     * @param entrada String
     * @return boolean
     */
    public boolean numero(String entrada) {
        try {
            Integer.parseInt(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Esse método é usado para criar e inserir um novo né em uma árvore e
     * iniciar o encadeamento da animação.
     *
     * @param dados String
     */
    public void inserir(String dados) {
        InserirNo novoNo = new InserirNo(this, dados);
        iniciarSegmentoAnimacao(novoNo);
    }

    /**
     * Este método é usado para excluir um nó da árvore e iniciar a animação da
     * linha.
     *
     * @param dados String
     */
    public void apagar(String dados) {
        ApagarNo apagarNo = new ApagarNo(this, dados);
        iniciarSegmentoAnimacao(apagarNo);
    }

    /**
     * Esse método é usado para pesquisar a árvore usando dois valores de
     * entrada e, em seguida, inicia o segmento de animação.
     *
     * @param s1 String
     * @param s2 String
     */
    public void pesquisarIntervalo(String s1, String s2) {
        PesquisarIntervalo rs = new PesquisarIntervalo(this, s1, s2);
        iniciarSegmentoAnimacao(rs);
    }

    /**
     * O método é usado para girar o nó em uma árvore Se a entrada não tiver um
     * nó, então uma rotação à direita será executada, vice-versa.
     *
     * @param no No
     */
    public void rotacionarNo(No no) {
        boolean teste = false;

        if (no.esquerda()) {
            teste = true;
        } else {
            teste = false;
        }

        No noAtual = no.irPai();

        //se o noAtual não é o pegarRaiz não então defina a entrada 'no' como a pegarRaiz e defina seu pai como nulo
        if (noAtual.noRaiz()) {
            this.raiz = no;
            no.setPai(null);
        } //senão se o atual não tiver o nó, então ligue este nó com a entrada 'nó',
        //caso contrário, ligue-o com o seu direito.
        else if (noAtual.esquerda()) {
            noAtual.irPai().conectarNo(no, "left");
        } else {
            noAtual.irPai().conectarNo(no, "right");
        }

        //Rotação a esquerda
        if (!teste) {

            if (no.irEsquerda() == null) {
                noAtual.setDireita(null);
            } else {
                noAtual.conectarNo(no.irEsquerda(), "right");
            }

            no.conectarNo(noAtual, "left");
        }

        //Rotacao a direita
        if (teste) {
            if (no.irDireita() == null) {
                noAtual.setEsquerda(null);
            } else {
                noAtual.conectarNo(no.irDireita(), "left");
            }

            no.conectarNo(noAtual, "right");
        }

        //Se houver nós presentes na árvore atual, posicione todos os nós, 
        //dando-lhes suas coordenadas x e y.
        if (this.raiz != null) {
            this.raiz.reposicioarArvore();
        }

        if (no.irEsquerda() != null) {
            no.irEsquerda().calculosGerais();
        }

        if (no.irDireita() != null) {
            no.irDireita().calculosGerais();
        }

        no.calculosGerais();
    }

    /**
     * Este método avança uma etapa de animação, retomando temporariamente o
     * segmento de animação.
     */
    public void avancarAnimacaoPassoApasso() {
        this.animacao.retomarAnimacao();
    }

    /**
     * Esse método é usado para iniciar o metodo principal na classe Animacao.
     *
     * @param anim AnimationThread
     */
    private void iniciarSegmentoAnimacao(Animacao anim) {
        this.animacao = anim;
        this.animacao.start();
        try {
            this.animacao.join();
        } catch (InterruptedException ie) {
        }
    }

    /**
     * Este método faz a árvore vazia.
     */
    public void esvaziarArvore() {
        pegarNotas().setNotas("");
        this.raiz = null;
        this.no = null;
    }

    /**
     * O método é usado para desenhar a árvore no painel de exibição de árvore.
     *
     * @param desenho Graphics
     */
    public void desenharExibicao(Graphics desenho) {
        if (this.raiz != null) {
            this.raiz.moverCadaNoDaArvore();
            this.raiz.desenharArvore(desenho);
        }
        if (this.no != null) {
            this.no.noDeDeslocamento();
            this.no.desenharNo(desenho);
        }
    }

    /**
     * Esse método calcula a largura e a altura do painel de exibição em árvore
     * e define a distância x e y entre os nós quando está sendo exibido.
     */
    public void reajustarNos() {
        int ten = 10;
        this.raio = ten + 5;
        this.comprimentoDeX = ten;
        this.comprimentoDeY = ten;

        this.larguraDaTela = this.qp.getPainelArvore().getTamanhoDimensao().width;
        this.alturaDaTela = this.qp.getPainelArvore().getTamanhoDimensao().height;

        this.coordenadaRaizX = (this.larguraDaTela / 2);
        this.coordenadaRaizY = (5 * this.raio + this.comprimentoDeY);

        reposicionarArvore();
    }

    /**
     * Metodos de acesso*
     */
    public No pegarRaiz() {
        return raiz;
    }

    public No pegarNo() {
        return no;
    }

    public int pegarRaizX() {
        return coordenadaRaizX;
    }

    public int pegarRaizY() {
        return coordenadaRaizY;
    }

    public int pegarRaio() {
        return raio;
    }

    public int pegarDistanciaX() {
        return comprimentoDeX;
    }

    public int pegarDistanciaY() {
        return comprimentoDeY;
    }

    public int pegarAlturaDaTela() {
        return alturaDaTela;
    }

    public int pegarLarguraDaTela() {
        return larguraDaTela;
    }

    public Notas pegarNotas() {
        return this.notas;
    }

    public QuadroPrincipal pegarQuadroPrincipal() {
        return this.qp;
    }

    /**
     * Métodos mutadores*
     */
    public void setDS(Notas notas) {
        this.notas = notas;
    }

    public void setNo(No no) {
        this.no = no;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    /**
     * Esse método é usado para redefinir todos os nós de volta à sua cor
     * padrão.
     */
    public void resetarCorNo() {
        resetarCorNo(raiz);
    }

    private void resetarCorNo(No n) {
        if (n == null) {
            return;
        } else {
            n.bgCor(raiz.corPreferida());
            resetarCorNo(n.irEsquerda());
            resetarCorNo(n.irDireita());
        }
    }

    public int tamanhoLista() {
        return temporario.size();
    }

    public ArrayList<String> pegarTemp() {
        return temporario;
    }

    public void matrizVazia() {
        temporario.clear();
    }

    /**
     * Conta o número de nós presentes na árvore
     *
     * @param no No
     * @return sum int
     */
    private int contarNo(No no) {
        if (no == null) {
            return 0;
        } else {
            int soma = 1;
            soma += Arvore.this.contarNo(no.irEsquerda());
            soma += Arvore.this.contarNo(no.irDireita());
            return soma;
        }
    }

    public int contarNo() {
        return Arvore.this.contarNo(raiz);
    }

    /**
     * O método a seguir calcula qual entrada é maior e a retorna.
     *
     * @param esquerda int
     * @param direita int
     * @return int
     */
    public int maximo(int esquerda, int direita) {
        if (esquerda > direita) {
            return esquerda;
        } else {
            return direita;
        }
    }

    /**
     * Este método é usado para encontrar o não com a profundidade máxima usando
     * um método recursivo.
     *
     * @param no No
     * @return int
     */
    public int profundidadeMaxima(No no) {
        if (no == null) {
            return -1;
        } else {
            int laltura = profundidadeMaxima(no.irEsquerda());
            int raltura = profundidadeMaxima(no.irDireita());

            return maximo(laltura, raltura) + 1;
        }
    }

    /**
     * O método a seguir encontra o máximo presente na árvore atual.
     *
     * @param no No
     * @return
     */
    private String procurarMaximo(No no) {
        if (no == null) {
            return "A árvore atual está vazia!";
        } else {
            String soma = "";
            if (no.irDireita() != null) {
                soma += Arvore.this.procurarMaximo(no.irDireita());
                return soma;
            } else {
                return no.irDados();
            }
        }
    }

    public String procurarMaximo() {
        return Arvore.this.procurarMaximo(raiz);
    }

    /**
     * O método a seguir encontra o mínimo presente na árvore atual.
     *
     * @param no No
     * @return
     */
    private String procurarMinimo(No no) {
        if (no == null) {
            return "A árvore atual está vazia!";
        } else {
            String soma = "";
            if (no.irEsquerda() != null) {
                soma += procurarMinimo(no.irEsquerda());
                return soma;
            } else {
                return no.irDados();
            }
        }
    }

    public String findMin() {
        return procurarMinimo(raiz);
    }

    /**
     * Este método é usado para imprimir pós-ordem.
     *
     * @param no No
     */
    public void mostrarPosOrdem(No no) {
        if (no == null) {
            return;
        }

        mostrarPosOrdem(no.irEsquerda());
        mostrarPosOrdem(no.irDireita());

        temporario.add(no.irDados());
    }

    /**
     * Este método é usado para imprimir pre-ordem.
     *
     * @param no
     */
    public void mostrarPreOrdem(No no) {
        if (no == null) {
            return;
        }

        temporario.add(no.irDados());

        mostrarPreOrdem(no.irEsquerda());
        mostrarPreOrdem(no.irDireita());
    }

    /**
     * Este método é usado para imprimir em-ordem.
     *
     * @param no No
     */
    public void mostrarEmOrdem(No no) {
        if (no == null) {
            return;
        }

        mostrarEmOrdem(no.irEsquerda());

        temporario.add(no.irDados());

        mostrarEmOrdem(no.irDireita());
    }

    /**
     * O método a seguir verifica a diferença de saldo de cada nó e executa
     * rotações simples ou duplas, se necessário.
     *
     * @param noAtual No
     */
    public void reBalanceamentoDeNo(No noAtual) {
        while (noAtual != null) {
            noAtual.bgCor(Color.PINK);
            noAtual.calculosGerais();
            pegarNotas().setNotas("Verificando o saldo no nó [" + noAtual.irDados() + "]...");
            animacao.esperarEmPausa();

            if (noAtual.diferencaBalanco() == -2) {
                pegarNotas().setNotas("Balanceamento requerido no nó [" + noAtual.irDados()
                        + "] desde que tenha uma diferença de equilíbrio de 2.");
                animacao.esperarEmPausa();

                if (noAtual.irEsquerda().diferencaBalanco() != 1) {
                    pegarNotas().setNotas("Executando a rotação direita no nó [" + noAtual.irEsquerda().irDados()
                            + "] fazendo nó [" + noAtual.irEsquerda().irPai().irDados() + "] sua subárvore direita.");
                    noAtual.bgCor(raiz.corPreferida());
                    noAtual = noAtual.irEsquerda();
                    noAtual.bgCor(Color.PINK);
                    rotacionarNo(noAtual);
                } else {
                    noAtual.bgCor(raiz.corPreferida());
                    noAtual = noAtual.irEsquerda().irDireita();
                    noAtual.bgCor(Color.PINK);
                    pegarNotas().setNotas("Executando uma rotação esquerda-direita no nó [" + noAtual.irDados() + "].");
                    animacao.esperarEmPausa();

                    pegarNotas().setNotas("Fazendo nó [" + noAtual.irPai().irDados()
                            + "] a subárvore esquerda do nó [" + noAtual.irDados() + "].");
                    rotacionarNo(noAtual);
                    animacao.esperarEmPausa();

                    pegarNotas().setNotas("Executando a rotação direita no nó [" + noAtual.irDados()
                            + "] fazendo nó [" + noAtual.irPai().irDados() + "] sua subárvore direita.");
                    rotacionarNo(noAtual);
                }

                animacao.esperarEmPausa();
            } //se a diferença de balanço deste não for 2, então uma rotação esquerda ou direita-esquerda precisa ser executada
            else if (noAtual.diferencaBalanco() == 2) {
                pegarNotas().setNotas("Balanceamento requerido no nó [" + noAtual.irDados()
                        + "] desde que tenha uma diferença de equilíbrio de 2.");
                animacao.esperarEmPausa();

                //se a diferença de saldo dos nós atuais for 1, execute uma rotação à esquerda
                if (noAtual.irDireita().diferencaBalanco() != -1) {
                    pegarNotas().setNotas("Executando rotação esquerda no nó [" + noAtual.irDireita().irDados()
                            + "] fazendo nó [" + noAtual.irDireita().irPai().irDados() + "] sua subárvore esquerda.");
                    noAtual.bgCor(raiz.corPreferida());
                    noAtual = noAtual.irDireita();
                    noAtual.bgCor(Color.PINK);
                    rotacionarNo(noAtual);
                } else //caso contrário, execute uma rotação direita-esquerda
                {
                    noAtual.bgCor(raiz.corPreferida());
                    noAtual = noAtual.irDireita().irEsquerda();
                    noAtual.bgCor(Color.PINK);
                    pegarNotas().setNotas("Executando uma rotação direita-esquerda no nó [" + noAtual.irDados() + "].");
                    animacao.esperarEmPausa();

                    pegarNotas().setNotas("Fazendo nó [" + noAtual.irPai().irDados()
                            + "] a subárvore direita do nó [" + noAtual.irDados() + "].");
                    rotacionarNo(noAtual);
                    animacao.esperarEmPausa();

                    pegarNotas().setNotas("Executando rotação esquerda no nó [" + noAtual.irDados()
                            + "] fazendo nó [" + noAtual.irPai().irDados() + "] sua subárvore esquerda.");
                    rotacionarNo(noAtual);
                }
                animacao.esperarEmPausa();
            }

            //defina a cor dos nós para a cor normal
            noAtual.bgCor(new Color(0x40, 0xFF, 0xFF));

            noAtual = noAtual.irPai();
        }
    }

    /**
     * Este método é usado para reposicionar a árvore, se ela não estiver vazia.
     */
    private void reposicionarArvore() {
        //se o pegarRaiz não for nulo, então, reequilibre a árvore.
        if (this.raiz != null) {
            this.raiz.reposicioarArvore();
        }
    }
}
