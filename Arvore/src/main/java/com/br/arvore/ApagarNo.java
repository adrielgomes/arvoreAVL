package com.br.arvore;
/**
 * Essa classe estende o segmento na classe de animação.
 * Este encadeamento é iniciado a partir da classe Arvore, 
 * que então executa o método de execução nesta classe. 
 * O método run, em seguida, exclui um nó da árvore atual, 
 * examinando primeiro a raiz da árvore e, em seguida, 
 * trabalhando o seu próprio caminho para encontrar o nó 
 * que o usuário deseja apagar. Depois que um nó é excluído, 
 * a árvore verifica se precisa de equilíbrio.
 * 
 * @author Adriel Gomes Costa
 * @title Avrvore Binária - TADS 2017.1 Módulo 3 (2018)
 */
public class ApagarNo extends Animacao {

	private Arvore arvore;
	private No no;
	private String valor;

	
	/**Construtor**/
	public ApagarNo(Arvore arvore, String valor)
	{
		
		super(arvore.pegarQuadroPrincipal());
		this.arvore = arvore;
		this.valor = valor;
		
	
		this.no = new No(valor,arvore);
		arvore.setNo(this.no);
		this.no.bgCor(no.buscarCor());
	}

	/**
	 * Esse método é chamado da classe Arvore e verifica cada nó na árvore e o exclui quando encontra esse nó.
	 */
	public void run()
	{
		//Se a árvore estiver vazia, faça o nó "Pesquisar" descer e terminar o processo de pesquisa.
		if(this.arvore.pegarRaiz() == null)
		{
			this.no.avancarParaCimaDaRaiz();
			arvore.pegarNotas().setNotas("A árvore está vazia.");
			esperarEmPausa();
			this.no.descer();
			this.no.bgCor(no.deletarCor());
			arvore.pegarNotas().setNotas("Não encontrado!");
			return;
		}
		else
		{
			//caso contrário, vá para o topo do nó raiz
			No no = this.arvore.pegarRaiz();
			this.no.avancarParaNo(no);
			arvore.pegarNotas().setNotas("Encontrando nó ["+this.valor+"]...");
			esperarEmPausa();

			No pai = arvore.pegarRaiz();	
			No apagarNo = null;			
			No filho = arvore.pegarRaiz();	
			
			boolean sair = false;

			//se o filho não estiver vazia, insira este loop
			while(filho != null)
			{
				pai = no;	
				no = filho;	
				
				boolean menosQue = false;
				boolean maiorQue = false;
				boolean iguais = false;

				// verifique se o novo número já está na árvore
				if(arvore.numero(this.valor) && arvore.numero(no.irDados()))
				{
					int valor = Integer.parseInt(this.valor);
					int novoValor = Integer.parseInt(no.irDados());

					if(valor >= novoValor)
						maiorQue = true;
					else if(valor <= novoValor)
						menosQue = true;

					if(valor == novoValor)
						iguais = true;
				}
				else
				{
					
					if(this.valor.compareTo(no.irDados()) >= 0)
						maiorQue = true;
					else if(this.valor.compareTo(no.irDados()) <= 0)
						menosQue = true;

					if(this.valor.compareTo(no.irDados()) == 0)
						iguais = true;
				}
				
				if(menosQue)
				{
					filho = no.irEsquerda();	//define o nó 'filho' para o filho esquerdo do nó 'no'

					if(!sair)
					{
						arvore.pegarNotas().setNotas("Pesquisando à esquerda desde o nó ["+this.valor+
								"] é menor que o nó ["+no.irDados()+"].");
						this.no.avancarParaNo(no);
					}
				}
				
				if(maiorQue)
				{
					filho = no.irDireita();	//definir o nó 'filho' para o filho direito do nó 'no'
					
					//se o nó 'filho' for nulo, configure o filho para o nó esquerdo do nó 'no'
					if(filho == null)
						filho = no.irEsquerda();	
					
					if(!sair)
					{
						arvore.pegarNotas().setNotas("Pesquisando desde o nó ["+this.valor+
								"] é maior que o nó ["+no.irDados()+"].");
						this.no.avancarParaNo(no);
					}
				}
				
				//insira esta condição se o nó a ser apagar for encontrado
				if(iguais)
				{
					apagarNo = no;
					arvore.pegarNotas().setNotas("Nó encontrado.");
					esperarEmPausa();
					
					this.no.avancarPara(no); //faça o nó  ir para esse nó para ser deletado
					arvore.pegarNotas().setNotas("Excluindo o nó.");
					this.no.bgCor(no.deletarCor());
					sair = true;
					esperarEmPausa();
				}

				if(!sair)
					esperarEmPausa();
			}

			//se o nó do usuário estiver vazio, saia do loop while e remova o nó
			if(apagarNo == null)
			{
				this.no.descer();
				arvore.pegarNotas().setNotas("Nó não encontrado.");
				return;
			}

			//caso contrário, insira essa condição
			if(apagarNo != null)
			{
				apagarNo.setDados(no.irDados());

				if(no.irEsquerda() != null)
					filho = no.irEsquerda();
				else
					filho = no.irDireita();

				//se o nó raiz for igual ao valor do nó apagar, defina a raiz como o nó filho
				if(arvore.pegarRaiz().irDados().compareTo(this.valor) == 0)
					this.arvore.setRaiz(filho);
				else
				{
					if(pai.irEsquerda() == no)
						pai.setEsquerda(filho);
					else
						pai.setDireita(filho);
				}
			}

			this.no.descer();
			
			//reposicione a árvore se a árvore não estiver vazia.
			if(this.arvore.pegarRaiz() != null)
				this.arvore.pegarRaiz().reposicioarArvore();
			
			arvore.pegarNotas().setNotas("Nó excluído.");
			esperarEmPausa();

			//reequilibrar a árvore
			this.arvore.reBalanceamentoDeNo(pai);
		}
		arvore.pegarNotas().setNotas("Exclusão concluída.");
		arvore.pegarQuadroPrincipal().getPilha().push("d "+this.valor);
	}
}
