package com.br.arvore;

import java.awt.*;
import javax.swing.*;

/**
 * Essa classe é usada para desenhar os nós e suas conexões no painel central do
 * quadro Ele implementa o executável e esse encadeamento é iniciado após o
 * quadro principal ser inicializado.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
@SuppressWarnings("serial")
public class PainelDaArvore extends JPanel implements Runnable {

    /**
     * Instance Variable*
     */
    private boolean nome = false, linhas = false;
    private Arvore arvore = null;
    private QuadroPrincipal qp;
    private Thread fio = null;
    private Dimension tamanhoDimensao, tamanhoAtual;
    private Image imagem;
    private Graphics desenho;

    /**
     * Construtor*
     */
    public PainelDaArvore() {

    }

    /**
     * Este método inicializa a árvore e o objeto de quadroPrincipal.
     *
     * @param arvore Arvore
     * @param qp Mainframe
     */
    public void setObjetos(Arvore arvore, QuadroPrincipal qp) {
        this.arvore = arvore;
        this.qp = qp;
    }

    /**
     * Define o nome e as linhas de profundidade usando os valores das variáveis
     * ​​booleanas.
     *
     * @param nome boolean
     * @param linhas boolean
     */
    public void isso(boolean nome, boolean linhas) {
        this.nome = nome;
        this.linhas = linhas;
    }

    /**
     * O método começa a executar o encadeamento nessa classe.
     */
    public void iniciarSegmentoDesenho() {
        if (this.fio == null) {
            this.fio = new Thread(this);
            this.fio.start();
        }
    }

    /**
     * O método a seguir pinta o desenho no painel, primeiro verificando o
     * tamanho da tela, desenhando as linhas de profundidade e / ou nomes
     * necessários e finalmente desenhando os nós.
     */
    public void paintComponent(Graphics desenho) {
        tamanhoAtual = getSize();

        //Essa condição verifica o tamanho da tela e ajusta o desenho da árvore de acordo. 
        if ((this.imagem == null) || (tamanhoAtual.width != this.tamanhoDimensao.width)
                || (tamanhoAtual.height != this.tamanhoDimensao.height)) {
            this.imagem = createImage(tamanhoAtual.width, tamanhoAtual.height);
            this.desenho = this.imagem.getGraphics();
            this.tamanhoDimensao = tamanhoAtual;
            this.arvore.reajustarNos();
        }

        this.desenho.setColor(Color.WHITE);
        this.desenho.fillRect(0, 0, getWidth(), getHeight());

        if (this.arvore != null) {
            if (linhas) {
                desenharLinhaTracejada(this.desenho);
            }

            if (nome) {
                desenharCordaDeProfundidade(this.desenho);
            }

            this.arvore.desenharExibicao(this.desenho);
        }
        desenho.drawImage(this.imagem, 0, 0, null);
    }

    /**
     * O método a seguir usa um objeto gráfico como um parâmetro e desenha as
     * linhas no painel.
     *
     * @param desenho
     */
    public void desenharLinhaTracejada(Graphics desenho) {

        Graphics2D linha = (Graphics2D) imagem.getGraphics();

        Stroke tracejada = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{1, 2}, 0);
        linha.setStroke(tracejada);

        final int x1 = 0;
        final int x2 = this.getWidth();
        final int numeroLinhas = 10;
        int y = 100;

        //desenha as linhas tracejadas
        for (int i = 0; i < numeroLinhas; i++) {
            linha.drawLine(x1, y, x2, y); // (x1,y1) (x2,y2)
            y += 55;
        }

        //se livra da cópia
        linha.dispose();
    }

    /**
     * Este método é usado para identificar a profundidade de uma linha.
     *
     * @param desenho
     */
    public void desenharCordaDeProfundidade(Graphics desenho) {

        Graphics2D corda = (Graphics2D) imagem.getGraphics();

        final int numeroLinhas = 10;
        int y = 100;
        int x = this.getWidth();
        corda.setFont(new Font("Courier New", Font.PLAIN, 11));

        for (int i = 0; i < numeroLinhas; i++) {
            corda.drawString("depth " + i, x - 50, y - 5);
            y += 55;
        }
        corda.dispose();
    }

    /**
     * Este método é executado continuamente quando iniciado e redesenha a
     * árvore e permite que a velocidade da animação seja alterada.
     */
    public void run() {
        for (;;) {
            repaint();	//re-desenhe a árvore no painel

            try {
                int valorPausa = qp.getVelocidadeDeslizante().getValue();
                //pause o encadeamento de acordo com o valor do JSlider
                Thread.sleep(valorPausa);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Metodos de acesso*
     */
    public Dimension getTamanhoDimensao() {
        return tamanhoDimensao;
    }

    public Dimension getTamanhoAtual() {
        return tamanhoAtual;
    }
}
