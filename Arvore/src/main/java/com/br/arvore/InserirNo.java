package com.br.arvore;

/**
 * Essa classe estende o segmento na classe de animação Este encadeamento é
 * iniciado a partir da classe Arvore, que então executa o método de execução
 * nesta classe O método run insere um novo no na árvore atual verificando
 * primeiro se o nó já existe na árvore ou não e, em seguida, o adiciona ao seu
 * local correto Depois que o no é inserido, a árvore verifica se precisa de
 * qualquer balanceamento.
 *
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class InserirNo extends Animacao {

    private Arvore arvore;
    private No no;
    private String valor;

    /**
     * Contrutor*
     */
    public InserirNo(Arvore arvore, String valor) {

        super(arvore.pegarQuadroPrincipal());
        this.pq = arvore.pegarQuadroPrincipal();
        this.arvore = arvore;
        this.valor = valor;

        this.no = new No(valor, arvore);
        this.no.bgCor(no.inserirCor());
        arvore.setNo(this.no);
    }

    /**
     * Este método é usado para percorrer a árvore e inserir um novo nó.
     */
    public void run() {
        No noAtual = this.arvore.pegarRaiz();

        //se a árvore está vazia e defina o novo nó como a raiz
        if (this.arvore.pegarRaiz() == null) {
            this.arvore.setRaiz(this.no = new No(this.no));
            this.no.avancarParaRaiz();
            arvore.pegarNotas().setNotas("Inserindo um novo nó raiz [" + no.irDados() + "].");
            esperarEmPausa();
        } else {
            //caso contrário, comece a procurar
            this.no.avancarParaCimaDaRaiz();
            arvore.pegarNotas().setNotas("Começando a inserir o nó [" + this.valor + "].");
            esperarEmPausa();

            while (true) {
                int resultado = 0;
                boolean sair = false;

                //se o novoNo estão sendo pesquisados nao forem números, 
                //então converta seus valores em números e compare-os.
                if (arvore.numero(noAtual.irDados()) && arvore.numero(this.valor)) {
                    int atual = Integer.parseInt(noAtual.irDados());
                    int esseValor = Integer.parseInt(this.valor);

                    if (atual == esseValor) {
                        resultado = 1;
                    }

                    if (esseValor < atual) {
                        resultado = 2;
                    } else if (esseValor > atual) {
                        resultado = 3;
                    }
                } else {

                    if (noAtual.irDados().compareTo(this.valor) == 0) {
                        resultado = 1;
                    }

                    if (this.valor.compareTo(noAtual.irDados()) < 0) {
                        resultado = 2;
                    } else if (this.valor.compareTo(noAtual.irDados()) > 0) {
                        resultado = 3;
                    }
                }

                switch (resultado) {
                    case 1:
                        //se o nó já existir na árvore, remova o
                        arvore.pegarNotas().setNotas("Nó [" + this.valor + "] já existe na árvore.");
                        if (!pq.getAbrirArquivo()) {
                            this.no.bgCor(no.deletarCor());
                        } else {
                            this.no.definirCor(no.corInvisivel(), no.corInvisivel());
                        }
                        esperarEmPausa();
                        this.no.descer();
                        arvore.pegarNotas().setNotas("");
                        esperarEmPausa();
                        return;

                    case 2:
                        //se o novo no for menor que o no que está sendo pesquisado, 
                        //vá para o filho esquerdo. Se o filho da esquerda estiver vazio, 
                        //defina o novo no como o filho da esquerda e conecte ambos.
                        arvore.pegarNotas().setNotas("Verificando o lado esquerdo desde o nó [" + this.valor
                                + "] é menor que o nó [" + noAtual.irDados() + "].");
                        if (noAtual.irEsquerda() != null) {
                            noAtual = noAtual.irEsquerda();
                            break;
                        } else {
                            noAtual.conectarNo(this.no = new No(this.no), "esquerda");
                            arvore.pegarNotas().setNotas("Nó [" + this.valor + "] inserido desde o nó [" + noAtual.irDados()
                                    + "]'filho esquerdo está vazio.");
                            sair = true;
                            break;
                        }

                    case 3:
                        //se o novo no for maior que o no que está sendo pesquisado, 
                        //vá para o filho direito. Se o filho direito estiver vazio, defina o 
                        //novo no como o filho direito e conecte ambos.
                        arvore.pegarNotas().setNotas("Indo para o lado direito desde o nó [" + this.valor
                                + "] é maior que o nó [" + noAtual.irDados() + "].");

                        if (noAtual.irDireita() != null) {
                            noAtual = noAtual.irDireita();
                            break;
                        } else {

                            this.no = new No(this.no);
                            noAtual.conectarNo(this.no, "right");
                            arvore.pegarNotas().setNotas("Nó [" + this.valor + "] inserido desde o nó [" + noAtual.irDados()
                                    + "]o filho direito está vazio.");
                            sair = true;
                            break;
                        }

                    default:
                        break;
                }

                if (sair) {
                    break;
                }

                this.no.avancarParaNo(noAtual);
                esperarEmPausa();
            }

            this.no = (this.arvore.no = null);

            if (this.arvore.pegarRaiz() != null) {
                this.arvore.pegarRaiz().reposicioarArvore();
            }

            esperarEmPausa();

            this.arvore.reBalanceamentoDeNo(noAtual);
        }
        arvore.pegarNotas().setNotas("Inserção Completa.");
        arvore.pegarQuadroPrincipal().getPilha().push("i " + this.valor);
    }
}
