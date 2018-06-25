package com.br.arvore;

import java.awt.*;

/**
 * Essa classe é usada para representar um nó em uma classe Arvore. Ele calcula
 * a altura e o tamanho do nó e envia a diferença de balanceamento de volta ao
 * método de balanceamento na classe árvore, quando necessário. Essa classe
 * também desenha a forma e os dados do nó dentro de sua forma. Também define a
 * cor do nó a ser exibido ao usuário.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class No implements Comparable<No> {

    private No esquerda = null, direita = null, pai = null;
    private String dados;
    private int somaAltura = 1, tamanho = 1, altura = 1;
    private int numeroDeEtapas, tamanhoEsquerda, tamnhoDireita, x_coordenada, y_coordenada, irParaX, irParaY;
    private Color fgcor, bgcor;
    private Arvore arvore;
    private int[] p = new int[6];
    private Font valorFonte = new Font("Courier New", Font.BOLD, 15);

    public No(String dados, Arvore arvore, int xPos, int yPos) {
        this.arvore = arvore;
        this.dados = dados;

        this.irParaX = xPos;
        this.x_coordenada = irParaX;
        this.irParaY = yPos;
        this.y_coordenada = irParaY;

        definirCor(corPreta(), corPreferida());

        this.numeroDeEtapas = 0;
    }

    public No(String dados, Arvore arvore) {
        this(dados, arvore, arvore.pegarLarguraDaTela() / 2, -20);
    }

    public No(No no) {
        this(no.dados, no.arvore, no.x_coordenada, no.y_coordenada);
    }

    /**
     * Métodos mutadores Esse método é usado para que, toda vez que for chamado,
     * ele possa ser usado para definir uma variável.
     */
    public void setEsquerda(No e) {
        this.esquerda = e;
    }

    public void setDireita(No d) {
        this.direita = d;
    }

    public void setAltura(int a) {
        this.altura = a;
    }

    public void setDados(String d) {
        this.dados = d;
    }

    public void setPai(No p) {
        this.pai = p;
    }

    public void fgCor(Color cor) {
        this.fgcor = cor;
    }

    public void bgCor(Color cor) {
        this.bgcor = cor;
    }

    public void setArray(int valor, int indice) {
        p[indice] = valor;
    }

    /**
     * O método a seguir move o nó para acima do nó de entrada.
     */
    public void avancarParaNo(No no) {
        advanceTo(no.irParaX, no.irParaY - 30 - 15);
    }

    /**
     * Esse método faz com que o primeiro nó seja inserido em uma nova árvore
     * para ir para sua posição pegarRaiz.
     */
    public void avancarParaRaiz() {
        advanceTo(this.arvore.pegarRaizX(), this.arvore.pegarRaizY());
    }

    /**
     * Esse método faz com que o novo nó fique acima do nó pegarRaiz antes de
     * ser inserido na árvore.
     */
    public void avancarParaCimaDaRaiz() {
        advanceTo(this.arvore.pegarRaizX(), this.arvore.pegarRaizY() - 30 - this.arvore.pegarDistanciaY());
    }

    /**
     * Este método move um nó para o lado direito de um nó que está sendo
     * pesquisado.
     */
    public void avancarParaDireita() {
        advanceTo(this.arvore.pegarLarguraDaTela() + 15, this.arvore.pegarAlturaDaTela() + 45);
    }

    /**
     * Este método move um nó para o lado esquerdo de um nó que está sendo
     * pesquisado.
     */
    public void avancarParaEsquerda() {
        advanceTo(-15, this.arvore.pegarAlturaDaTela() + 45);
    }

    /**
     * O método a seguir é usado para descartar o nó abaixo da tela, se estiver
     * sendo excluído ou se o nó não tiver sido encontrado.
     */
    public void descer() {
        advanceTo(this.irParaX, this.arvore.pegarAlturaDaTela() + 45);
    }

    /**
     * Métodos de acesso
     */
    public No irEsquerda() {
        return this.esquerda;
    }

    public No irDireita() {
        return this.direita;
    }

    public int irAltura() {
        return this.altura;
    }

    public String irDados() {
        return this.dados;
    }

    public No irPai() {
        return this.pai;
    }

    public int irParaX() {
        return x_coordenada;
    }

    public int irParaY() {
        return y_coordenada;
    }

    public boolean noRaiz() {
        return this.pai == null;
    }

    public boolean esquerda() {
        return this.pai.esquerda == this;
    }

    public int irArray(int indice) {
        return p[indice];
    }

    //acessar todas as cores necessárias para os nós
    public Color corPreferida() {
        return new Color(0x40, 0xFF, 0xFF);
    }

    public Color inserirCor() {
        return Color.yellow.brighter();
    }

    public Color deletarCor() {
        return Color.red;
    }

    public Color buscarCor() {
        return Color.orange;
    }

    public Color corEncontrada() {
        return Color.green;
    }

    public Color corInvisivel() {
        return Color.white;
    }

    public Color corPreta() {
        return Color.black;
    }

    /**
     * Esse método usa um nó como um parâmetro e o utiliza para comparar o valor
     * da sequência com outro nó.
     *
     * @param no No
     */
    @Override
    public int compareTo(No no) {
        if (this.irDados().equals(no.irDados())) {
            return 0;
        }

        if (this.irDados().compareTo(no.irDados()) < 0) {
            return -1;
        }

        if (this.irDados().compareTo(no.irDados()) > 0) {
            return 1;
        }

        return 0;
    }

    //Define a cor do nó
    public void definirCor(Color cor1, Color cor2) {
        this.fgcor = cor1;
        this.bgcor = cor2;
    }

    /**
     * O método a seguir é usado para conectar um nó ao lado esquerdo ou o
     * tamanho da direita do nó
     */
    public void conectarNo(No no, String posicao) {
        if (posicao.equals("right")) {
            this.direita = no;
        } else {
            this.esquerda = no;
        }

        if (no != null) {
            no.pai = this;
        }
    }

    /**
     * Este método a seguir calcula a altura e o tamanho do nó.
     */
    public void calculosGerais() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 0;
        }

        if (this.direita != null) {
            setArray(this.direita.altura, 3);
            setArray(this.direita.somaAltura, 5);
            setArray(this.direita.tamanho, 1);
        }

        if (this.esquerda != null) {
            setArray(this.esquerda.altura, 2);
            setArray(this.esquerda.somaAltura, 4);
            setArray(this.esquerda.tamanho, 0);
        }

        calculos();

    }

    /**
     * Este método é usado para retornar a diferença de saldo do nó.
     *
     * @return result int
     */
    public int diferencaBalanco() {

        int resultado, x1, x2;

        if (this.esquerda == null) {
            x1 = 0;
        } else {
            x1 = this.esquerda.altura;
        }

        if (this.direita == null) {
            x2 = 0;
        } else {
            x2 = this.direita.altura;
        }

        resultado = x2 - x1;

        return resultado;
    }

    /**
     * Esse método é usado para desenhar a árvore desenhando as linhas que unem
     * os nós na árvore.
     *
     * @param desenho Graphics
     */
    public void desenharArvore(Graphics desenho) {
        // torna as linhas, a forma e o valor do nó em um formato suave
        ((Graphics2D) desenho).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //define a cor da linha de conexão
        desenho.setColor(corPreta());

        if (this.esquerda != null) {
            //isto desenha as conexões de linha entre 2 nós
            desenho.drawLine(this.x_coordenada, this.y_coordenada, this.esquerda.x_coordenada, this.esquerda.y_coordenada);
            this.esquerda.desenharArvore(desenho);
        }

        if (this.direita != null) {
            //isto desenha as conexões de linha entre 2 nós
            desenho.drawLine(this.x_coordenada, this.y_coordenada, this.direita.x_coordenada, this.direita.y_coordenada);
            this.direita.desenharArvore(desenho);
        }

        desenharNo(desenho);
    }

    /**
     * Esse método é usado para desenhar o nó como uma forma de círculo e o
     * valor do nó dentro da forma.
     *
     * @param desenho Graphics
     */
    public void desenharNo(Graphics desenho) {
        //desenha a forma do nó
        desenho.setColor(this.bgcor);
        desenho.fillOval(this.x_coordenada - 15, this.y_coordenada - 15, 30, 30);
        desenho.setColor(this.fgcor);
        desenho.drawOval(this.x_coordenada - 15, this.y_coordenada - 15, 30, 30);

        //desenha o valor dentro do nó
        desenho.setFont(valorFonte);
        desenho.setColor(this.fgcor);
        FontMetrics metricas = desenho.getFontMetrics(valorFonte);
        desenho.drawString(this.dados, this.x_coordenada - metricas.stringWidth("" + this.dados) / 2,
                this.y_coordenada - metricas.getHeight() / 2 + metricas.getAscent());
    }

    /**
     * Esse método é usado para mover os nós da árvore em um nó por vez. Ele
     * verifica se o filho da direita / esquerda do nó é nulo ou não. Se não for
     * nulo, verifique o próximo nó direita / esquerda.
     */
    public void moverCadaNoDaArvore() {
        if (this.direita != null) {
            this.direita.moverCadaNoDaArvore();
        }

        if (this.esquerda != null) {
            this.esquerda.moverCadaNoDaArvore();
        }

        noDeDeslocamento();
    }

    /**
     * Este método define os cálculos para a altura do nó, seu tamanho e a
     * altura total.
     */
    private void calculos() {
        this.altura = (arvore.maximo(irArray(2), irArray(3))) + 1;
        this.tamanho = 1 + irArray(0) + irArray(1);
        this.somaAltura = this.tamanho + irArray(4) + irArray(5);

        diferencaBalanco();
    }

    /**
     * Este método é usado para mover o novo nó de sua posição inicial da parte
     * superior da tela para sua nova posição na árvore.
     */
    public void noDeDeslocamento() {
        if (this.numeroDeEtapas > 0) {
            this.x_coordenada = ((this.irParaX - this.x_coordenada) / this.numeroDeEtapas) + this.x_coordenada;
            this.y_coordenada = ((this.irParaY - this.y_coordenada) / this.numeroDeEtapas) + this.y_coordenada;
            this.numeroDeEtapas--;
        }
    }

    /**
     * Este método é usado para definir os nós esquerdo e tamanho da direita.
     */
    public void calculaAnguloEntreNos() {

        if (this.esquerda != null) {
            this.tamanhoEsquerda = this.esquerda.tamanhoEsquerda + this.esquerda.tamnhoDireita;
        } else {
            this.tamanhoEsquerda = this.arvore.pegarDistanciaX() + this.arvore.pegarRaio() + 5;
        }

        if (this.direita != null) {
            this.tamnhoDireita = this.direita.tamnhoDireita + this.direita.tamanhoEsquerda;
        } else {
            this.tamnhoDireita = this.arvore.pegarRaio() + this.arvore.pegarDistanciaX() + 5;
        }
    }

    /**
     * Esse método é usado para percorrer a árvore até que cada nó seja
     * posicionado.
     */
    public void reposicioarArvore() {
        if (this.esquerda != null) {
            this.esquerda.reposicioarArvore();
        }

        if (this.direita != null) {
            this.direita.reposicioarArvore();
        }

        calculaAnguloEntreNos();
        posicaoDoNo();
    }

    /**
     * Este método é usado para definir o nó para sua posição correta.
     */
    private void posicaoDoNo() {
        if (this.pai == null) {
            avancarParaRaiz();
        }

        if (this.direita != null) {
            this.direita.advanceTo(this.irParaX + this.direita.tamanhoEsquerda, this.irParaY + 40);
            this.direita.posicaoDoNo();
        }

        if (this.esquerda != null) {
            this.esquerda.advanceTo(this.irParaX - this.esquerda.tamnhoDireita, this.irParaY + 40);
            this.esquerda.posicaoDoNo();
        }
    }

    /**
     * O método a seguir move um nó definindo suas coordenadas x e y.
     *
     * @param x int
     * @param y int
     */
    public void advanceTo(int x, int y) {
        this.irParaX = x;
        this.irParaY = y + 15;

        //verifica em qual passo a contagem está.
        this.numeroDeEtapas = this.arvore.pegarQuadroPrincipal().getPassos();
    }

    /**
     * O método a seguir se move das coordenadas x e y de um nó para as
     * coordenadas x e y definidas.
     *
     * @param no No
     */
    public void avancarPara(No no) {
        advanceTo(no.irParaX, no.irParaY - 15);
    }
}
