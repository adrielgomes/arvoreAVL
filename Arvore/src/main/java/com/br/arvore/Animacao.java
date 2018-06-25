package com.br.arvore;

/**
 * Esta classe é usada para verificar se o usuário selecionou o botão de pausa
 * ou não Se o botão de pausa tiver sido ativado, o encadeamento irá dormir
 * temporariamente até que o botão de reprodução ou o próximo botão seja
 * pressionado.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class Animacao extends Thread {

    public QuadroPrincipal pq;
    boolean hold = false;

    /**
     * @param qp
     */
    public Animacao(QuadroPrincipal qp) {
        this.pq = qp;
    }

    /**
     * Este método é usado para suspender o encadeamento
     */
    public void esperarEmPausa() {
        if (this.pq.pausa()) {
            this.hold = true;
            synchronized (this) {
                try {
                    while (this.hold) {
                        wait();
                    }
                } catch (InterruptedException localInterruptedException) {
                }
            }
        }
    }

    /**
     * Este método é usado para retomar o encadeamento
     */
    public void retomarAnimacao() {
        synchronized (this) {
            this.hold = false;
            notifyAll();
        }
    }
}
