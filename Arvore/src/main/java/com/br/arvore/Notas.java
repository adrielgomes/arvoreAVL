package com.br.arvore;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Esta classe é usada para exibir todas as etapas necessárias ao animar a
 * árvore do AVL usando o painel de texto do java.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
@SuppressWarnings("serial")
public class Notas extends JTextPane {

    /**
     * Contrutor
     */
    public Notas() {
        this.setFont(new Font("Helvetica", Font.PLAIN, 16));
        this.setForeground(Color.RED);
        this.setPreferredSize(new Dimension(800, 50));
        SimpleAttributeSet novoAtributo = new SimpleAttributeSet();
        StyleConstants.setAlignment(novoAtributo, StyleConstants.ALIGN_CENTER);
        this.setCharacterAttributes(novoAtributo, true);
        this.setEditable(false);
        apagarNotas();
    }

    /**
     * Esse método é usado para definir o texto no campo de anotações.
     */
    public void setNotas(String nota) {
        this.setText("\n   Comentários:    " + nota);
    }

    /**
     * Esse método é usado para limpar o campo de anotações.
     */
    public void apagarNotas() {
        this.setText("\n   Comentários:");
    }

}
