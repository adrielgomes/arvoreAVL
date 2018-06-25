package com.br.arvore;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

/**
 * Esta classe é usada para criar e exibir o quadro principal Ele cria e
 * inicializa todos os componentes necessários para serem exibidos nesse quadro
 * A classe também manipula todos os eventos necessários do usuário e executa
 * conforme necessário.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 *
 */
@SuppressWarnings("serial")
public class QuadroPrincipal extends JFrame implements ActionListener {

    private PainelDaArvore arvorePainel;
    private Arvore arvore;
    public JPanel painelDireito, centro, botao;
    private JLabel altura, tamanho;
    private JTextField inserirCampo, apagarCampo, paraCampo, doCampo;
    private JTextArea historia;
    private JButton botaoInserir, botaoApagar, boatoLimpar, botaoProcurar,
            botaoReiniciar, botaoIniciar, botaoPausar, botaoProximo, botaoVoltar;
    private JMenu menuArquivo, menuImprimir, menuOpcoes, menuJanela;
    private JMenuItem itemNovo, itemAbrir, itemSair, itemEmOrdem, itemPreOrdem,
            itemPosOrdem, itemMin, itemImagem, itemTextoArquivo,
            itemProcurarBotaoMax, itemProcurarBotaoMin, itemGerarArvore;
    private JCheckBoxMenuItem profundidadeNome, profundidadeLinha;
    private boolean alterarNomeProfundidade = true, alteraLinhaProfundidade = true;
    private Notas notas;
    private boolean esperar = false;
    private int PASSOS = 10;
    private JSlider controleDeslisante;
    private ArrayList<String> palavras;
    private Stack<String> pilhas;
    private boolean abrirF = false;
    public static final int PREF_W = 1200;
    public static final int PREF_H = 600;

    public QuadroPrincipal() {

        this.setSize(1100, 680);
        this.setLocation(150, 100);
        this.setTitle("IFRR - TADS 2017.1 - Trabalho Estrutura de Dados 2018.1 - Árvore AVL");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(PREF_W, PREF_H));

        arvorePainel = new PainelDaArvore();
        arvore = new Arvore(this);
        pilhas = new Stack<String>();

        this.formaComponente();

        controleDeslisante.setValue(50);
        arvore.setDS(notas);
        arvorePainel.isso(alterarNomeProfundidade, alteraLinhaProfundidade);
        arvorePainel.setObjetos(arvore, this);
        arvorePainel.iniciarSegmentoDesenho();
    }

    /**
     * O método a seguir inicializa e adiciona cada componente à janela.
     */
    public void formaComponente() {
        menuCriar();

        centro = new JPanel(new BorderLayout());
        centro.add(arvorePainel);
        this.add(centro, BorderLayout.CENTER);

        createRightPanel();

        botao = new JPanel(new BorderLayout());
        botao.setBackground(Color.WHITE);
        notas = new Notas();
        LineBorder line = new LineBorder(Color.blue, 2, true);
        botao.setBorder(line);
        JScrollPane sp = new JScrollPane(notas);
        botao.add(sp);
        this.add(botao, BorderLayout.SOUTH);

        botaoInserir.addActionListener(this);
        botaoApagar.addActionListener(this);
        boatoLimpar.addActionListener(this);
        botaoProcurar.addActionListener(this);
        botaoReiniciar.addActionListener(this);
        botaoIniciar.addActionListener(this);
        botaoProximo.addActionListener(this);
        botaoPausar.addActionListener(this);
        botaoVoltar.addActionListener(this);
    }

    /**
     * Este método é usado para criar uma barra de menu simples para o quadro
     * que permite ao usuário visualizar ou selecionar outras opções.
     */
    public void menuCriar() {

        JMenuBar barraDeMenu = new JMenuBar();

        menuArquivo = new JMenu("Arquivo");

        itemNovo = new JMenuItem("Novo");
        itemAbrir = new JMenuItem("Abrir");
        JMenu salvarComo = new JMenu("Salvar como");
        itemTextoArquivo = new JMenuItem("Arquivo de texto");
        itemImagem = new JMenuItem("Imagem");
        itemSair = new JMenuItem("Sair");

        salvarComo.add(itemTextoArquivo);
        salvarComo.add(itemImagem);

        menuArquivo.add(itemNovo);
        menuArquivo.add(itemAbrir);
        menuArquivo.addSeparator();
        menuArquivo.add(salvarComo);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);

        menuImprimir = new JMenu("Impressão Transversal");

        itemEmOrdem = new JMenuItem("Em Ordem");
        itemPreOrdem = new JMenuItem("Pre Ordem");
        itemPosOrdem = new JMenuItem("Pos Ordem");
        menuImprimir.add(itemEmOrdem);
        menuImprimir.add(itemPreOrdem);
        menuImprimir.add(itemPosOrdem);

        menuOpcoes = new JMenu("Opcoes");
        profundidadeNome = new JCheckBoxMenuItem("Nome Profundidade", true);
        profundidadeLinha = new JCheckBoxMenuItem("Linha Profundidade", true);
        itemProcurarBotaoMin = new JMenuItem("Obter um nó mínimo");
        itemProcurarBotaoMax = new JMenuItem("Obter um nó máximo");
        itemGerarArvore = new JMenuItem("Gerar Árvore Aleatória");

        menuOpcoes.add(itemGerarArvore);
        menuOpcoes.addSeparator();
        menuOpcoes.add(itemProcurarBotaoMin);
        menuOpcoes.add(itemProcurarBotaoMax);
        menuOpcoes.addSeparator();
        menuOpcoes.add(profundidadeNome);
        menuOpcoes.add(profundidadeLinha);

        menuJanela = new JMenu("Janela");

        itemMin = new JMenuItem("Minimizar");
        menuJanela.add(itemMin);

        barraDeMenu.add(menuArquivo);
        barraDeMenu.add(menuImprimir);
        barraDeMenu.add(menuOpcoes);
        barraDeMenu.add(menuJanela);

        itemNovo.addActionListener(this);
        itemAbrir.addActionListener(this);
        itemTextoArquivo.addActionListener(this);
        itemImagem.addActionListener(this);
        itemSair.addActionListener(this);
        itemGerarArvore.addActionListener(this);
        itemProcurarBotaoMax.addActionListener(this);
        itemProcurarBotaoMin.addActionListener(this);
        profundidadeNome.addActionListener(this);
        profundidadeLinha.addActionListener(this);
        itemMin.addActionListener(this);
        itemEmOrdem.addActionListener(this);
        itemPreOrdem.addActionListener(this);
        itemPosOrdem.addActionListener(this);

        this.setJMenuBar(barraDeMenu);
    }

    /**
     * Este método é usado para criar o painel direito que permite ao usuário
     * interagir com a árvore.
     */
    public void createRightPanel() {
        botaoInserir = new JButton("Inserir");
        botaoApagar = new JButton("Apagar");
        inserirCampo = new JTextField(5);
        apagarCampo = new JTextField(5);
        altura = new JLabel("Altura: -1");
        tamanho = new JLabel("Tamanho: 0");
        boatoLimpar = new JButton("Apagar Árvore");
        botaoVoltar = new JButton("<<");

        painelDireito = new JPanel(new GridLayout(3, 1));
        painelDireito.setBackground(Color.lightGray);
        painelDireito.setPreferredSize(new Dimension(310, 100));

        JPanel arvorePlana = new JPanel(new GridLayout(4, 1));
        JPanel arvore1 = new JPanel();
        JPanel arvore2 = new JPanel();
        JPanel arvore3 = new JPanel();
        JPanel arvore4 = new JPanel();

        arvore1.add(botaoInserir);
        arvore1.add(inserirCampo);
        arvore4.add(botaoApagar);
        arvore4.add(apagarCampo);

        arvore2.add(altura);
        arvore2.add(Box.createHorizontalStrut(50));
        arvore2.add(tamanho);
        arvore3.add(boatoLimpar);

        arvorePlana.add(arvore1);
        arvorePlana.add(arvore4);
        arvorePlana.add(arvore2);
        arvorePlana.add(arvore3);
        arvorePlana.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Propriedades da árvore"));
        painelDireito.add(arvorePlana);

        JPanel painel1 = new JPanel(new GridLayout(2, 1));

        JPanel tamanhoPlano = new JPanel(new GridLayout(2, 1));
        JPanel tamanho1 = new JPanel();
        JPanel tamanho2 = new JPanel();
        JLabel paraEtiqueta = new JLabel("Para:");
        JLabel etiquetaFormulario = new JLabel("A partir de:");
        paraCampo = new JTextField(3);
        doCampo = new JTextField(3);
        botaoProcurar = new JButton("Pesquisar");
        botaoReiniciar = new JButton("Reiniciar");

        tamanho1.add(etiquetaFormulario);
        tamanho1.add(doCampo);
        tamanho1.add(Box.createHorizontalStrut(50));
        tamanho1.add(paraEtiqueta);
        tamanho1.add(paraCampo);

        tamanho2.add(botaoProcurar);
        tamanho2.add(botaoReiniciar);

        tamanhoPlano.add(tamanho1);
        tamanhoPlano.add(tamanho2);
        tamanhoPlano.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Pesquisa de intervalo"));
        painel1.add(tamanhoPlano);

        botaoIniciar = new JButton(">");
        botaoPausar = new JButton("||");
        botaoIniciar.setEnabled(false);
        botaoProximo = new JButton(">>");
        botaoVoltar.setSize(1, 1);

        botaoProximo.setEnabled(false);
        controleDeslisante = new JSlider(1, 250);

        JPanel animacaoPainel = new JPanel(new GridLayout(2, 1));
        JPanel animacaoBotao = new JPanel(new GridLayout(1, 4));

        animacaoBotao.add(botaoIniciar);
        animacaoBotao.add(botaoPausar);
        animacaoBotao.add(botaoVoltar);
        animacaoBotao.add(botaoProximo);
        animacaoPainel.add(animacaoBotao);

        JPanel animacaoBotao2 = new JPanel();
        JLabel velocidade = new JLabel("Vel:");
        Font f = velocidade.getFont();
        velocidade.setFont(f.deriveFont(f.getStyle() ^ Font.ITALIC));

        animacaoBotao2.add(velocidade);
        animacaoBotao2.add(new JLabel("+"));
        animacaoBotao2.add(controleDeslisante);
        animacaoBotao2.add(new JLabel("-"));
        animacaoPainel.add(animacaoBotao2);
        animacaoPainel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Opções de Animação"));
        painel1.add(animacaoPainel);

        painelDireito.add(painel1);

        JPanel painel3 = new JPanel(new BorderLayout());
        historia = new JTextArea(8, 23);
        JScrollPane rolarPainel = new JScrollPane(historia);
        historia.setEditable(false);
        historia.setLineWrap(true);
        historia.setWrapStyleWord(true);

        painel3.add(rolarPainel);
        painel3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Registro"));

        painelDireito.add(painel3);
        this.add(painelDireito, BorderLayout.EAST);
    }

    /**
     * O método a seguir atualiza a janela principal, atualizando a altura e o
     * tamanho da árvore à medida que cada nó é adicionado / removido Ele também
     * define a alternância de nomes e linhas de profundidade.
     */
    public void atualizar() {
        arvorePainel.isso(alterarNomeProfundidade, alteraLinhaProfundidade);
        altura.setText("Altura: " + arvore.profundidadeMaxima(arvore.pegarRaiz()));
        tamanho.setText("Tamanho: " + arvore.contarNo());
    }

    /**
     * Esse método é usado para abrir um arquivo no menu e carregar uma árvore
     * pré-salva no painel.
     */
    public void abrirArquivo() {
        boolean verificar = true;

        if (this.arvore.pegarRaiz() != null) {
            int resposta = JOptionPane.showConfirmDialog(null, "A árvore atual será limpa. \nGostaria de continuar?",
                    "Mensagem de aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                arvore.esvaziarArvore();
                atualizar();
                verificar = true;
            } else {
                verificar = false;
            }
        }

        if (verificar) {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));
            fc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            int result = fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                historia.append("~ Selected file: " + selectedFile.getAbsolutePath() + "\n");

                String ext = fc.getSelectedFile().getAbsolutePath();

                if (!ext.contains(".txt")) {
                    JOptionPane.showMessageDialog(null, "Formato de arquivo inválido!!!\nPor favor, selecione um arquivo .txt.",
                            "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedFile.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Este arquivo não contém dados.",
                            "Erro de arquivo", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    FileReader escrever = new FileReader(selectedFile);
                    Scanner entrada = new Scanner(escrever);
                    arvore.matrizVazia();

                    while (entrada.hasNextLine()) {
                        String line = entrada.nextLine();
                        arvore.pegarTemp().add(line);
                    }
                    entrada.close();
                    escrever.close();
                } catch (FileNotFoundException e) {
                    JOptionPane.showConfirmDialog(null, "Arquivo não encontrado!!!",
                            "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                } catch (IOException x) {
                    JOptionPane.showConfirmDialog(null, "Arquivo nao encotrado!!!",
                            "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                }

                int reply = JOptionPane.showConfirmDialog(null,
                        "Deseja carregar a árvore \ncom animação passo a passo??",
                        "Mensagem de informacao", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (reply == JOptionPane.YES_OPTION) {
                    ativarBotaoPause();
                } else {
                    desativarBotaoPause();
                }

                ativarBotaoProximo();
                MetodoPrincipal localThread = new MetodoPrincipal(this, 4, "", "");
                localThread.start();
            }
        }

    }

    /**
     * Este método é usado para exportar um percurso de uma árvore para um
     * arquivo de texto externo.
     *
     * @param nomeOperacao String
     */
    public void gravarArquivo(String nomeOperacao, String metodo) {
        JFileChooser seletor = new JFileChooser();
        seletor.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        int recuperacao = seletor.showSaveDialog(null);

        if (recuperacao == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter gravarArquivo = new FileWriter(seletor.getSelectedFile() + ".txt");
                String nomeArquivo = seletor.getSelectedFile().getName() + ".txt";
                String teste = "" + seletor.getCurrentDirectory();
                String saidaTexto = null;

                if (metodo.compareTo("traversal") == 0) {
                    saidaTexto = imprimirLista();
                } else {
                    saidaTexto = imprimirPilha();
                }

                gravarArquivo.write(saidaTexto);
                gravarArquivo.close();

                if (!nomeOperacao.isEmpty()) {
                    historia.append("~ Exportado " + nomeOperacao + " árvore como " + nomeArquivo + " para: " + teste + ".\n");
                } else {
                    historia.append("~ Árvore exportada como " + nomeArquivo + " para: " + teste + ".\n");
                }
            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(null, "Erro ao gravar arquivo!!!",
                        "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Este método é usado para verificar o conteúdo do array temporário na
     * classe tree e adicioná-lo a uma string.
     *
     * @return String text
     */
    private String imprimirLista() {
        String texto = "";
        for (int i = 0; i < arvore.tamanhoLista(); i++) {
            texto += arvore.pegarTemp().get(i) + " ";
        }
        return texto;
    }

    private String imprimirListaComSuporte() {
        String texto = "";
        for (int i = 0; i < arvore.tamanhoLista(); i++) {
            texto += "[" + arvore.pegarTemp().get(i) + "] ";
        }
        return texto;
    }

    /**
     * Esse método pega as pilhas e gera cada string seguida por uma nova linha.
     *
     * @return text String
     */
    private String imprimirPilha() {
        String texto = "";
        for (int i = 0; i < pilhas.size(); i++) {
            texto += pilhas.get(i) + "\n";
        }
        return texto;
    }

    /**
     * O método a seguir verifica se há uma árvore atual presente e dá a opção
     * para o usuário voltar e salvar a árvore ou continuar a criar uma nova
     * árvore.
     *
     * @param mensagem String
     * @param novaMensagem String
     */
    public void novaArvore(String mensagem, String novaMensagem) {
        boolean novaArvore = true;
        if (this.arvore.pegarRaiz() != null) {
            int resposta = JOptionPane.showConfirmDialog(null,
                    mensagem, "Mensagem de aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resposta == JOptionPane.YES_OPTION) {
                novaArvore = true;
            } else {
                novaArvore = false;
            }
        }

        if (novaArvore) {
            historia.append("~ " + novaMensagem + "\n");
            arvore.esvaziarArvore();
            arvore.pegarNotas().setNotas("");
            pilhas.clear();
            atualizar();
        }
    }

    /**
     * Limpa todos os campos de entrada
     */
    public void limparCamposDeEntrada() {
        inserirCampo.setText("");
        apagarCampo.setText("");
        doCampo.setText("");
        paraCampo.setText("");
    }

    /**
     * Este método usa a string de entrada para 3 caracteres se for maior que 3
     * A string de entrada é então marcada se estiver vazia ou se contiver um
     * decimal e lançará um erro se isso ocorrer Caso contrário, retornará a
     * string de volta.
     *
     *
     * @param palavra String
     * @return s
     */
    public String separarPalavra(String palavra) {
        palavra = palavra.trim();

        if (palavra.length() > 3) {
            palavra = palavra.substring(0, 3);
        }

        if (palavra.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você não inseriu uma entrada válida.",
                    "Erro: Entrada Vazia Detectada!", JOptionPane.ERROR_MESSAGE);
            return null;
        } else if (palavra.contains(".")) {
            JOptionPane.showMessageDialog(null, "Por favor insira um valor não decimal.",
                    "Erro: Decimal detectado!", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return palavra;
        }
    }

    /**
     * O método a seguir recebe uma entrada e verifica se há mais palavras na
     * entrada Se houver, um scanner digitalizará cada palavra e cortará se
     * tiver mais de três caracteres Essa palavra separada agora é adicionada à
     * lista de arrays.
     *
     *
     * @param entrada String
     */
    @SuppressWarnings("resource")
    public void multiplasEntrada(String entrada) {
        Scanner novaEntrada = new Scanner(entrada);
        arvore.matrizVazia();
        String[] caracterEspecial = {"!", "@", "]", "#", "$", "%", "^", "&", "*", ",", "+", "=", ")", "£"};

        while (novaEntrada.hasNext()) {
            String palavra = novaEntrada.next();
            String separado = separarPalavra(palavra);

            if (separado == null) {
                return;
            }

            arvore.pegarTemp().add(separado);

            for (int i = 0; i < caracterEspecial.length; i++) {
                if (separado.contains(caracterEspecial[i])) {
                    JOptionPane.showMessageDialog(null, "Você inseriu uma entrada (s) usando caracteres especiais!",
                            "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    arvore.matrizVazia();
                    return;
                }
            }
        }
    }

    /**
     * Este método exibe uma mensagem de erro de que a árvore está vazia e todos
     * os campos de entrada.
     */
    public void erroArvoreVazia() {
        JOptionPane.showMessageDialog(null, "A árvore está vazia.",
                "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
        limparCamposDeEntrada();
    }

    /**
     * Esse método é usado para ouvir eventos e executar as ações necessárias
     * necessárias.
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object evento = e.getSource();
        AbstractButton botaoABS = (AbstractButton) e.getSource();

        boolean selected = botaoABS.getModel().isSelected();

        if (evento == botaoInserir) {

            String texto = separarPalavra(inserirCampo.getText());

            if (texto != null) {
                multiplasEntrada(inserirCampo.getText());
                ativarBotaoProximo();
                MetodoPrincipal metodoPrincipal = new MetodoPrincipal(this, 0, texto, "");
                metodoPrincipal.start();
            }
            limparCamposDeEntrada();
            inserirCampo.requestFocus();
        } else if (evento == botaoApagar) {
            /**
             * Se o botão excluir for pressionado, o programa enviará a entrada
             * do usuário para o método trimString para que a cadeia seja
             * processada e retornada Se a string for válida, o thread principal
             * é iniciado e o programa verifica se o nó está presente na árvore
             * e o exclui, se encontrado.
             */
            String texto = separarPalavra(apagarCampo.getText());

            if (texto != null) {
                multiplasEntrada(apagarCampo.getText());
                ativarBotaoProximo();
                MetodoPrincipal metodoPrincipal = new MetodoPrincipal(this, 1, texto, "");
                metodoPrincipal.start();
            }
            limparCamposDeEntrada();
            apagarCampo.requestFocus();
        } else if (evento == boatoLimpar) {
            novaArvore("Tem certeza de que deseja limpar a árvore atual?", "Árvore atual limpa.");
        } else if (evento == itemSair) {
            System.exit(0);
        } else if (evento == profundidadeNome) {

            if (selected) {
                historia.append("~ Alternar nome de profundidade - ativado.\n");
                alterarNomeProfundidade = true;
                atualizar();
            } else {
                historia.append("~ Alternar nome de profundidade - desativado.\n");
                alterarNomeProfundidade = false;
                atualizar();
            }
        } else if (evento == profundidadeLinha) {

            if (selected) {
                historia.append("~ Alternar linhas de profundidade - ativado.\n");
                alteraLinhaProfundidade = true;
                atualizar();
            } else {
                historia.append("~ Alternar Linhas de Profundidade - Desativado.\n");
                alteraLinhaProfundidade = false;
                atualizar();
            }
        } else if (evento == itemMin) {
            this.setState(Frame.ICONIFIED);
        } else if (evento == botaoIniciar) {
            desativarBotaoPause();
            historia.append("~ Retomando Animação.\n");
        } else if (evento == botaoPausar) {
            ativarBotaoPause();
            historia.append("~ Pausar Animação.\n");
        } else if (evento == botaoProximo) {

            this.arvore.avancarAnimacaoPassoApasso();
        } else if (evento == itemImagem) {
            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
                return;
            } else {

                fazerImagemPainel(arvorePainel);
            }
        } else if (evento == botaoProcurar) {
            /**
             * Quando o usuário procura por um intervalo, o programa primeiro
             * verifica se ambos os campos estão vazios ou não, ele verificará
             * se as duas entradas são números, para que possam ser comparados
             * como ints para verificação de erros, caso contrário serão
             * comparados como strings. Se todas as verificações de erro forem
             * aprovadas, o início da thread principal e a pesquisa de intervalo
             * começarão.
             *
             */
            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
                return;
            }

            String aPartirDe = separarPalavra(doCampo.getText());
            String para = separarPalavra(paraCampo.getText());

            boolean processado = false;

            if (aPartirDe != null && para != null) {
                if (arvore.numero(aPartirDe) && arvore.numero(para)) {
                    int aPartirInt = Integer.parseInt(aPartirDe);
                    int paraInt = Integer.parseInt(para);

                    if (aPartirInt > paraInt) {
                        JOptionPane.showMessageDialog(null,
                                "A entrada de campo 'De' não pode ser maior que a entrada de campo 'Para'.",
                                "Erro: entrada inválida!", JOptionPane.ERROR_MESSAGE);
                        limparCamposDeEntrada();
                    } else {
                        processado = true;
                    }
                } else if (aPartirDe.compareTo(para) > 0) {
                    JOptionPane.showMessageDialog(null,
                            "A entrada de campo 'De' não pode ser maior que a entrada de campo 'Para'.",
                            "Erro: entrada inválida!", JOptionPane.ERROR_MESSAGE);
                } else {
                    processado = true;
                }
            }

            if (processado) {
                arvore.resetarCorNo();
                historia.append("~ Faixa de pesquisa de " + aPartirDe + " para " + para + ".\n");
                ativarBotaoProximo();
                MetodoPrincipal localThread = new MetodoPrincipal(this, 2, aPartirDe, para);
                localThread.start();
            }

            limparCamposDeEntrada();
            doCampo.requestFocus();
        } else if (evento == botaoReiniciar) {
            /**
             * Se o botão de reiniciar for pressionado pelo usuário, todas as
             * cores dos nós na árvore atual serão redefinidas para a cor
             * original.
             */
            arvore.resetarCorNo();
            notas.apagarNotas();
            limparCamposDeEntrada();
            historia.append("~ Redefinindo a pesquisa de intervalo.\n");
        } else if (evento == itemNovo) {
            novaArvore("Todas as alterações não salvas feitas na árvore atual serão perdidas. \nGostaria de continuar?",
                    "Item de menu: Novo selecionado.");
        } else if (evento == itemAbrir) {
            historia.append("~ Item de menu: Abrir selecionado.\n");
            abrirArquivo();
        } else if (evento == itemProcurarBotaoMin) {
            historia.append("~ Encontrando o nó mínimo. \n");

            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
            } else {
                JOptionPane.showMessageDialog(null, "O nó mínimo na árvore atual é "
                        + "Nó [" + arvore.findMin() + "].");
            }
        } else if (evento == itemProcurarBotaoMax) {
            historia.append("~ Encontrando o nó máximo. \n");

            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
            } else {
                JOptionPane.showMessageDialog(null, "O nó máximo na árvore atual é "
                        + "Nó [" + arvore.procurarMaximo() + "].");
            }
        } else if (evento == itemPosOrdem) {
            arvore.matrizVazia();
            arvore.mostrarPosOrdem(arvore.pegarRaiz());

            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
            } else {
                int reply = JOptionPane.showConfirmDialog(null,
                        "Pos ordem: " + imprimirListaComSuporte()
                        + "\nVocê gostaria de salvar esses detalhes em um arquivo de texto?",
                        "Mensagem de informacao", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (reply == JOptionPane.YES_OPTION) {
                    gravarArquivo("postorder", "traversal");
                }
            }

        } else if (evento == itemPreOrdem) {
            arvore.matrizVazia();
            arvore.mostrarPreOrdem(arvore.pegarRaiz());

            //if the tree is empty then throw an error
            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
            } else {
                int reply = JOptionPane.showConfirmDialog(null,
                        "Pre Ordem: " + imprimirListaComSuporte()
                        + "\nVocê gostaria de salvar esses detalhes em um arquivo de texto?",
                        "Mensagem de informacao", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (reply == JOptionPane.YES_OPTION) {
                    gravarArquivo("preorder", "traversal");
                }
            }
        } else if (evento == itemEmOrdem) {
            arvore.matrizVazia();
            arvore.mostrarEmOrdem(arvore.pegarRaiz());

            //if the tree is empty then throw an error
            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
            } else {
                int reply = JOptionPane.showConfirmDialog(null,
                        "Em Ordem: " + imprimirListaComSuporte()
                        + "\nVocê gostaria de salvar esses detalhes em um arquivo de texto?",
                        "Mensagem de informacao", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (reply == JOptionPane.YES_OPTION) {
                    gravarArquivo("inorder", "traversal");
                }
            }
        } else if (evento == itemTextoArquivo) {
            if (arvore.pegarRaiz() == null) {
                erroArvoreVazia();
                return;
            } else {
                gravarArquivo("", "stack");
            }
        } else if (evento == itemGerarArvore) {
            new GeradorDeArvoreAleatoria(this);
        } else if (evento == botaoVoltar) {
            historia.append("~ Recuou.\n");
            desativarBotaoPause();
            arvore.esvaziarArvore();
            arvore.pegarNotas().setNotas("");
            arvore.matrizVazia();
            atualizar();
            setPassos(1);

            if (!pilhas.empty()) {
                pilhas.pop();
            }

            if (pilhas.isEmpty()) {
                arvore.esvaziarArvore();
                atualizar();
            } else {
                for (int i = 0; i < pilhas.size(); i++) {
                    arvore.pegarTemp().add(pilhas.get(i));
                }

                pilhas.clear();
            }

            ativarBotaoProximo();
            MetodoPrincipal localThread = new MetodoPrincipal(this, 4, "", "");
            localThread.start();
        }
    }

    /**
     * O método a seguir gera uma árvore aleatória onde o usuário pode escolher
     * quais métodos de entrada eles desejam ter da classe Random Arvore
     * Generator.
     *
     * @param positivo boolean
     * @param negativo boolean
     * @param palavra boolean
     * @param tamanho int
     */
    public void gerarArvoreAleatorio(boolean positivo, boolean negativo, boolean palavra, int tamanho) {
        arvore.matrizVazia();

        if (positivo && !negativo && !palavra) {
            gerarMatriz(tamanho, 100, 0, true);
        } else if (!positivo && negativo && !palavra) {
            gerarMatriz(tamanho, 100, 99, true);
        } else if (!positivo && !negativo && palavra) {
            gerarPalavras(tamanho);
        } else if (positivo && negativo && !palavra) {
            gerarMatriz(tamanho, 100, 50, true);
        } else if (positivo && !negativo && palavra) {
            if (tamanho % 2 == 0) {
                int tamanho0 = tamanho / 2;
                gerarMatriz(tamanho0, 100, 0, true);
                gerarPalavras(tamanho0);
            } else {
                int tamanho1 = tamanho / 2 + 1;
                int tamanho2 = tamanho / 2;
                gerarMatriz(tamanho1, 100, 0, true);
                gerarPalavras(tamanho2);
            }
        } else if (!positivo && negativo && palavra) {
            if (tamanho % 2 == 0) {
                int tamanho0 = tamanho / 2;
                gerarMatriz(tamanho0, 100, 99, true);
                gerarPalavras(tamanho0);
            } else {
                int tamanho1 = tamanho / 2 + 1;
                int tamanho2 = tamanho / 2;
                gerarMatriz(tamanho1, 100, 99, true);
                gerarPalavras(tamanho2);
            }
        } else if (positivo && negativo && palavra) {
            if (tamanho % 3 == 0) {
                int tamanho0 = tamanho / 3;
                gerarMatriz(tamanho0, 100, 0, true);
                gerarMatriz(tamanho0, 100, 99, true);
                gerarPalavras(tamanho0);
            } else {
                int tamanho1 = tamanho / 2 + 1;
                int tamanho2 = (tamanho - tamanho1) / 2;
                gerarMatriz(tamanho1, 100, 0, true);
                gerarMatriz(tamanho2, 100, 99, true);
                gerarPalavras(tamanho2);
            }
        }

        ativarBotaoProximo();
        MetodoPrincipal localThread = new MetodoPrincipal(this, 3, "", "");
        localThread.start();
    }

    /**
     * O método a seguir inicializa uma lista de matriz e adiciona cada palavra
     * do arquivo de texto 'palavra.txt' a ela.
     *
     * @param tamanho int
     */
    private void gerarPalavras(int tamanho) {
        palavras = new ArrayList<String>();
        try {
            Scanner entrada = new Scanner(new FileReader("palavra.txt"));

            while (entrada.hasNextLine()) {
                palavras.add(entrada.nextLine());
            }

            entrada.close();
            gerarMatriz(tamanho, palavras.size() - 1, 0, false);

        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Esse método gera uma matriz aleatória usando um objeto aleatório para
     * selecionar qual índice da matriz 'palavra' para obter ou escolhe um
     * número aleatório e o adiciona à matriz 'arvore'.
     *
     * @param tamanho int
     * @param parada int
     * @param subvalor int
     * @param inteiro boolean
     */
    private void gerarMatriz(int tamanho, int parada, int subvalor, boolean inteiro) {
        Random ran = new Random();

        for (int i = 0; i < tamanho; i++) {
            //seleciona uma posição de índice aleatório ao selecionar palavras 
            // seleciona um aleatório a ser inserido na árvore.
            int inteiroAleatorio = ran.nextInt(parada) - subvalor;
            int contar = 0;

            if (inteiro) {
                //se o usuário selecionar um número, um número aleatório será inserido na 
                //árvore arrayList. Este arrayList é então verificado se ele contém alguma 
                //duplicata. Se isso acontecer, o número será removido e 'i' será diminuído em 1.
                String numToAdd = "" + inteiroAleatorio;
                arvore.pegarTemp().add(numToAdd);

                for (int j = 0; j < arvore.tamanhoLista(); j++) {
                    if (numToAdd.compareTo(arvore.pegarTemp().get(j)) == 0) {
                        contar++;

                        if (contar >= 2) {
                            arvore.pegarTemp().remove(j);
                            i--;
                        }
                    }
                }
            } else {

                String palavraAdcionar = palavras.get(inteiroAleatorio);
                arvore.pegarTemp().add(palavraAdcionar);

                if (arvore.tamanhoLista() > 0) {
                    for (int j = 0; j < arvore.tamanhoLista(); j++) {
                        if (palavraAdcionar.compareTo(arvore.pegarTemp().get(j)) == 0) {
                            contar++;

                            if (contar >= 2) {
                                arvore.pegarTemp().remove(j);
                                i--;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * O método a seguir desativa o próximo botão, além de ativar e desativar
     * outros botões na janela principal.
     */
    private void desativarBotaoProximo() {
        botaoInserir.setEnabled(true);
        botaoApagar.setEnabled(true);
        boatoLimpar.setEnabled(true);
        botaoProcurar.setEnabled(true);
        botaoReiniciar.setEnabled(true);
        botaoProximo.setEnabled(false);
        botaoVoltar.setEnabled(true);
    }

    /**
     * O método a seguir ativa o próximo botão, além de ativar e desativar
     * outros botões na janela principal.
     */
    private void ativarBotaoProximo() {
        botaoInserir.setEnabled(false);
        botaoApagar.setEnabled(false);
        boatoLimpar.setEnabled(false);
        botaoProcurar.setEnabled(false);
        botaoReiniciar.setEnabled(false);
        botaoProximo.setEnabled(true);
        botaoVoltar.setEnabled(false);
    }

    private void ativarBotaoPause() {
        this.esperar = true;
        botaoPausar.setEnabled(false);
        botaoIniciar.setEnabled(true);
        botaoVoltar.setEnabled(false);
    }

    private void desativarBotaoPause() {
        this.esperar = false;
        botaoIniciar.setEnabled(false);
        botaoPausar.setEnabled(true);
        botaoVoltar.setEnabled(true);
    }

    /**
     * Esse método é usado para executar as operações necessárias que o usuário
     * escolhe na janela principal e atualiza o tamanho e o campo de altura de
     * acordo.
     *
     * @param opcao int
     * @param opcao1 String
     * @param opcao2 String
     */
    public void opercacoesDePerformace(int opcao, String opcao1, String opcao2) {
        if (opcao == 0) {
            for (int i = 0; i < arvore.tamanhoLista(); i++) {
                arvore.inserir(arvore.pegarTemp().get(i));
                historia.append("~ Inserindo: " + arvore.pegarTemp().get(i) + "\n");
                atualizar();
            }
        } else if (opcao == 1) {
            for (int i = 0; i < arvore.tamanhoLista(); i++) {
                arvore.apagar(arvore.pegarTemp().get(i));
                historia.append("~ Deletando: " + arvore.pegarTemp().get(i) + "\n");
                atualizar();
            }
        } else if (opcao == 2) {
            arvore.pesquisarIntervalo(opcao1, opcao2);
            atualizar();
        } else if (opcao == 3) {
            for (int i = 0; i < arvore.tamanhoLista(); i++) {
                arvore.inserir(arvore.pegarTemp().get(i));
                historia.append("~ Inserindo: " + arvore.pegarTemp().get(i) + "\n");
                pilhas.push("i " + arvore.pegarTemp().get(i));
                atualizar();
            }
        } else if (opcao == 4) {
            if (this.esperar == false) {
                abrirF = true;
            }

            for (int i = 0; i < arvore.tamanhoLista(); i++) {
                String linha = arvore.pegarTemp().get(i);

                String[] palavra = linha.split(" ");

                if (palavra[0].compareTo("i") == 0) {
                    arvore.inserir(palavra[1]);
                } else if (palavra[0].compareTo("d") == 0) {
                    arvore.apagar(palavra[1]);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Este arquivo é inválido/corrompido.",
                            "Erro entrada inválida", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                atualizar();
            }
            abrirF = false;
            setPassos(10);
        }
        desativarBotaoProximo();
    }

    /**
     * O método a seguir usa um painel como parâmetro e o converte em uma imagem
     * em buffer pronta para ser exportada para um arquivo externo.
     *
     * @param componente Component
     */
    private void fazerImagemPainel(Component componente) {
        Dimension tamanho = componente.getSize();
        BufferedImage imagem = new BufferedImage(
                tamanho.width, tamanho.height, BufferedImage.TYPE_INT_ARGB);
        componente.paint(imagem.createGraphics());

        JFileChooser seletor = new JFileChooser();
        seletor.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        int recuperacao = seletor.showSaveDialog(null);

        if (recuperacao == JFileChooser.APPROVE_OPTION) {
            try {
                File arquivo = seletor.getSelectedFile();
                String teste = arquivo.getAbsolutePath();
                ImageIO.write(imagem, "png", new File(teste));
                historia.append("~ Árvore exportada como uma imagem para: " + teste + ".\n");
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Metodos de Acesso*
     */
    public PainelDaArvore getPainelArvore() {
        return arvorePainel;
    }

    public JSlider getVelocidadeDeslizante() {
        return controleDeslisante;
    }

    public boolean pausa() {
        return esperar;
    }

    public int getPassos() {
        return PASSOS;
    }

    public boolean getAbrirArquivo() {
        return abrirF;
    }

    public void setPassos(int s) {
        PASSOS = s;
    }

    public Stack<String> getPilha() {
        return pilhas;
    }

    public int getTamanhoPilha() {
        return pilhas.size();
    }
}