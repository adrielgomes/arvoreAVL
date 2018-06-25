package com.br.arvore;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta é a classe principal que é usada para iniciar e executar o programa GUI.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 *
 */
public class InciarPrograma {

    public static void main(String[] args) {
        new InciarPrograma();
    }

    /**
     * Constructor - O método a seguir cria um quadro de boas-vindas de
     * inicialização antes de o usuário prosseguir para o painel principal.
     */
    public InciarPrograma() {

        JFrame startFrame = new JFrame("Seja Bem Vindos");
        JButton iniciar = new JButton("Iniciar Árvore AVL");
        iniciar.setPreferredSize(new Dimension(10, 35));
        JPanel painelBoasVindas = new JPanel(new GridLayout(5, 1));

        painelBoasVindas.add(new JLabel("    Adriel - Ângela - Bruno - Elton - Éricka"));
        painelBoasVindas.add(new JLabel("  Click em 'Iniciar' para iniciar o programa"));
        painelBoasVindas.add(iniciar);
        //painelBoasVindas.add(Box.createVerticalStrut(6));
        painelBoasVindas.add(new JLabel("Instituto Federal de Roraima - TADS 2017.1"));

        startFrame.setLayout(new FlowLayout());
        startFrame.add(painelBoasVindas);
        startFrame.setSize(450, 195);
        startFrame.setLocation(400, 300);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);

        iniciar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                QuadroPrincipal cg = new QuadroPrincipal();
                cg.setVisible(true);
            }
        });
    }
}
