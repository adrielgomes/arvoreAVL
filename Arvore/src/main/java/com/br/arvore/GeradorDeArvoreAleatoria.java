package com.br.arvore;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta classe é usada para gerar uma árvore aleatória no painel de exibição
 * principal O usuário pode escolher se escolhe como entrada - palavras /
 * números positivos ou números negativos no painel da árvore.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 *
 */
@SuppressWarnings("serial")
public class GeradorDeArvoreAleatoria extends JFrame {

    private boolean numeroPositivo = false;
    private boolean numeroNegativo = false;
    private boolean palavras = false;
    private JTextField tamanhoCampo;
    private JButton geracao;
    private QuadroPrincipal qp;

    /**
     * Construtor Cria um quadro para exibir o gerador.
     *
     * @param qp QuadroPrincipal
     */
    public GeradorDeArvoreAleatoria(QuadroPrincipal qp) {
        setSize(600, 150);
        setLocation(350, 300);
        setTitle("Gerador de Árvore Aleatória");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 150));
        this.qp = qp;

        iniciar();

        this.setVisible(true);
    }

    /**
     * O método a seguir inicializa todas as caixas de seleção e campo de
     * entrada de tamanho para o usuário selecionar e gerar.
     */
    public void iniciar() {
        JPanel painelPrincipal = new JPanel(new GridLayout(3, 1));

        final JCheckBox numPosi = new JCheckBox("Número Positivo");
        final JCheckBox numNega = new JCheckBox("Numero Negativo");
        final JCheckBox words = new JCheckBox("Palavras");
        tamanhoCampo = new JTextField(2);
        geracao = new JButton("Gerar");

        //escutando todas as caixas de seleção e definindo-as como verdadeiras,
        //se elas forem verificadas, caso contrário, serão falsas.
        numPosi.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    numeroPositivo = true;
                } else {
                    numeroPositivo = false;
                }
            }
        });

        numNega.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    numeroNegativo = true;
                } else {
                    numeroNegativo = false;
                }
            }
        });

        words.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    palavras = true;
                } else {
                    palavras = false;
                }
            }
        });

        geracao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == geracao) {
                    try {
                        /**
                         * se o botão gerar for pressionado, verifique se o
                         * usuário selecionou pelo menos uma entrada, caso
                         * contrário, jogue um erro.
                         */
                        if (numeroPositivo == false && numeroNegativo == false && palavras == false) {
                            JOptionPane.showMessageDialog(null, "Por favor, selecione pelo menos uma entrada",
                                    "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String sizeS = tamanhoCampo.getText();
                            int tamanhoI = Integer.parseInt(sizeS);	//converte em inteiro

                            if (tamanhoI <= 0) {
                                //se o tamanho for menor ou igual a 0, em seguida, lançar um erro
                                JOptionPane.showMessageDialog(null, "Por favor insira um tamanho tamanho válido.",
                                        "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                                tamanhoCampo.requestFocus();
                            } else if (tamanhoI > 25) {
                                //se o tamanho for maior que 30, avise o usuário que todos os nós podem não estar totalmente exibidos no painel da árvore.
                                int reply = JOptionPane.showConfirmDialog(null,
                                        "O tamanho que você inseriu pode não exibir todos os nós na tela.\n"
                                        + "Por favor, insira um tamanho entre 1 e 25, caso contrário, pressione Não para prosseguir com seu tamanho atual.",
                                        "Mensagem de aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                                if (reply == JOptionPane.NO_OPTION) {
                                    qp.gerarArvoreAleatorio(numeroPositivo, numeroNegativo, palavras, tamanhoI);
                                    setVisible(false);
                                }

                                if (reply == JOptionPane.YES_OPTION) {
                                    tamanhoCampo.requestFocus();
                                }
                            } else {
                                //gerar arvore avl
                                qp.gerarArvoreAleatorio(numeroPositivo, numeroNegativo, palavras, tamanhoI);
                                setVisible(false); //esconde esse quadro atual
                            }

                        }
                    } catch (NumberFormatException ie) {
                        //se o usuário nao digitar um número no campo de tamanho, em seguida, lançar um erro

                        JOptionPane.showMessageDialog(null, "Por favor insira um número válido para o tamanho.",
                                "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                        tamanhoCampo.setText("");
                        tamanhoCampo.requestFocus();
                    }

                }

            }
        });

        //colocando as caixas de seleção
        JPanel firstPanel = new JPanel();
        firstPanel.add(new JLabel("Escolha uma entrada: "));
        firstPanel.add(numPosi);
        firstPanel.add(numNega);
        firstPanel.add(words);

        //estabelece o rótulo de tamanho e campo
        JPanel secondPanel = new JPanel();
        secondPanel.add(new JLabel("Tamanho da árvore: "));
        secondPanel.add(tamanhoCampo);

        //dispondo o botão gerar
        JPanel thirdPanel = new JPanel();
        thirdPanel.add(geracao);

        //adicionando todos os sub painéis ao painel principal
        painelPrincipal.add(firstPanel);
        painelPrincipal.add(secondPanel);
        painelPrincipal.add(thirdPanel);

        //adicionando o painel principal a este quadro
        this.add(painelPrincipal);
    }

    /**
     * Metodos de acesso*
     */
    public boolean getNumeroPositivo() {
        return numeroPositivo;
    }

    public boolean getNumeroNegativo() {
        return numeroNegativo;
    }

    public boolean getPalavra() {
        return palavras;
    }
}
