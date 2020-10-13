package br.edu.ifce.dashboard;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.exceptions.JogadorInvalidoException;

public class GameGeneralRules {

	private List<Integer> tabuleiroJogador_1;
	private List<Integer> tabuleiroJogador_2;
	
	private static int NUMBER_OF_POSITIONS = 7; //Número de casas do tabuleiro para cada jogador (6 casas + 1 Kallah);
	private static int KALLAH_POSITION 	   = 6; //Posição na lista da Kallah de cada jogador;
	
	/**
	 * Cria os elementos mais básicos do tabuleiro, que são as casas
	 * a serem utilizadas e a quantidade de sementes que cada casa
	 * terá ao iniciar o jogo.
	 *
	 *@return void
	 */
	public void criaTabuleiro() {
		this.tabuleiroJogador_1 = new ArrayList<Integer>();
		this.tabuleiroJogador_2 = new ArrayList<Integer>();
		
		//As listas possuirão 7 casas (6 casas + 1 Kallah)
		//Posições 0 a 5 -> casas 1 a 6, da esquerda para a direita;
		//Posição  6     -> casa kallah.
		for(int i = 0; i < NUMBER_OF_POSITIONS; i++) {
			if(i == KALLAH_POSITION) {
				this.tabuleiroJogador_1.add(0);
				this.tabuleiroJogador_2.add(0);
			} else {
				this.tabuleiroJogador_1.add(4);
				this.tabuleiroJogador_2.add(4);
			}
		}
	}
	
	/**
	 * Distribui as sementes nas casas do tabuleiro e também implementa a regra de não poder incluir semente no kallah do adversário
	 * */
	public CasaUltimaSemente moveSementes(Integer casaEscolhida, Jogador jogador) throws JogadorInvalidoException {
		
		if(jogador == Jogador.UM) {
			return this.mover(casaEscolhida, this.tabuleiroJogador_1, this.tabuleiroJogador_2, jogador);
			
		} else if (jogador == Jogador.DOIS) {
			return this.mover(casaEscolhida, this.tabuleiroJogador_2, this.tabuleiroJogador_1, jogador);
			
		} else {
			throw new JogadorInvalidoException("Jogador inválido");
		}
	}
	
	/**
	 * 
	 * */
	private CasaUltimaSemente mover(Integer casaEscolhida, List<Integer> meuTabuleiro, List<Integer> tabuleiroAdversario, Jogador jogador) {
		
		CasaUltimaSemente casaUltimaSemente = new CasaUltimaSemente();
		
		int casaInicial = casaEscolhida.intValue() + 1;
		int numSementesCasaEscolhida = meuTabuleiro.get(casaEscolhida);
		
		meuTabuleiro.set(casaEscolhida, 0);
		
		int casaAtual = casaInicial;
		int qtdCasaAtual = 0;
		
		Long tabuleiro = jogador.getId();
		
		Long meuTab = (jogador == Jogador.UM)
					 ? Jogador.UM.getId()
					 : ((jogador == Jogador.DOIS) ? Jogador.DOIS.getId() : null);
		
		Long advTab = (jogador == Jogador.UM)
					 ? Jogador.DOIS.getId()
					 : ((jogador == Jogador.DOIS) ? Jogador.UM.getId() : null);
					 
		int foraDoIndiceListas = KALLAH_POSITION + 1;
		
		for(int i = 0; i < numSementesCasaEscolhida; i++) {
			if(casaAtual == foraDoIndiceListas && tabuleiro == meuTab) {
				casaAtual = 0;
				tabuleiro = advTab;
				
			} else if(casaAtual == foraDoIndiceListas && tabuleiro == advTab) {
				casaAtual = 0;
				tabuleiro = meuTab;
			}
			
			if(tabuleiro == meuTab) {
				qtdCasaAtual = 0;
				qtdCasaAtual = meuTabuleiro.get(casaAtual);
				meuTabuleiro.set(casaAtual, qtdCasaAtual + 1);
				
			} else if(tabuleiro == advTab) {
				if(casaAtual != KALLAH_POSITION) {
					qtdCasaAtual = 0;
					qtdCasaAtual = tabuleiroAdversario.get(casaAtual);
					tabuleiroAdversario.set(casaAtual, qtdCasaAtual + 1);
				} else {
					i--;
				}
			}
			
			casaAtual++;
			
			if(i == (numSementesCasaEscolhida - 1)) {
				casaUltimaSemente.setCasa(casaAtual - 1); //O valor de 'casaAtual' será a posição real, se as casas forem contadas a partir de 1 (um). Porém, a lista começa a ser contada a partir do índice 0 (zero), então subtrai-se uma unidade.
				casaUltimaSemente.setIdTabuleiro(tabuleiro);
			}
		}
		
		return casaUltimaSemente;
	}
	
	/**
	 * 
	 * */
	public Boolean temDireitoANovaJogada(CasaUltimaSemente ultimaCasa, Jogador jogador) throws NullPointerException {
		if(ultimaCasa == null || jogador == null) {
			throw new NullPointerException("Existe parâmetro nulo no método temDireitoANovaJogada");
		}
		
		Long idTabuleiro = ultimaCasa.getIdTabuleiro();
		
		switch(jogador) {
			case UM:
				return (idTabuleiro == Jogador.UM.getId() && ultimaCasa.getCasa() == KALLAH_POSITION);
				
			case DOIS:
				return (idTabuleiro == Jogador.DOIS.getId() && ultimaCasa.getCasa() == KALLAH_POSITION);
			
			default:
				return Boolean.FALSE;
		}
	}
	
	/**
	 * 
	 * */
	public Boolean temDireitoACapturaDeSementes(CasaUltimaSemente ultimaCasa, Jogador jogador) throws NullPointerException {
		if(ultimaCasa == null || jogador == null) {
			throw new NullPointerException("Existe parâmetro nulo no método temDireitoACapturaDeSementes");
		}
		
		Long idTabuleiro = ultimaCasa.getIdTabuleiro();
		int qtdUltimaCasa;
		
		switch(jogador) {
			case UM:
				qtdUltimaCasa = this.tabuleiroJogador_1.get(ultimaCasa.getCasa());
				return (idTabuleiro == Jogador.UM.getId() && ultimaCasa.getCasa() != KALLAH_POSITION && qtdUltimaCasa == 1);
			
			case DOIS:
				qtdUltimaCasa = this.tabuleiroJogador_2.get(ultimaCasa.getCasa());
				return (idTabuleiro == Jogador.DOIS.getId() && ultimaCasa.getCasa() != KALLAH_POSITION && qtdUltimaCasa == 1);
			
			default:
				return Boolean.FALSE;
		}
	}
	
	/**
	 * Implementa a captura das sementes adversárias, quando for necessário.
	 * Os tabuleiros estão de cabeça para baixo de um em relação ao outro. Portanto,
	 * é necessário fazer a correspondência entre as casas dos jogadores para que a
	 * captura ocorra no local correto.
	 * 
	 * @param casa		um valor inteiro indicando a casa do jogador que fez a jogada e realizará a captura.
	 * @param jogador	um valor enumerado, indicando qual o jogador que está fazendo a jogada no momento.
	 * @return void
	 * @throws JogadorInvalidoException se um Jogador não existe no enum Jogador ou se conter o valor NENHUM.
	 * */
	public void capturaSementesAdversarias(Integer casa, Jogador jogador) throws JogadorInvalidoException {
		int valorAntigoMinhaKallah;
		
		if(jogador == Jogador.UM) {
			valorAntigoMinhaKallah = this.tabuleiroJogador_1.get(KALLAH_POSITION);
			this.captura(casa, tabuleiroJogador_1, tabuleiroJogador_2, valorAntigoMinhaKallah);
		
		} else if(jogador == Jogador.DOIS) {
			valorAntigoMinhaKallah = this.tabuleiroJogador_2.get(KALLAH_POSITION);
			this.captura(casa, tabuleiroJogador_2, tabuleiroJogador_1, valorAntigoMinhaKallah);
			
		} else {
			throw new JogadorInvalidoException("Jogador inválido.");
		}
	}
	
	/**
	 * Faz a captura das sementes do adversário, propriamente dita, e as adiciona na sua kallah.
	 * 
	 *  @param casa						um valor inteiro representando a casa que o jogador está e fará a captura;
	 *  @param meuTabuleiro				uma lista de inteiros representando o tabuleiro do jogador da jogada;
	 *  @param tabuleiroAdversario		uma lista de inteiros representando o tabuleiro do jogador adversário, o qual terá suas sementes capturadas;
	 *  @param valorAntigoMinhaKallah 	um valor inteiro representando a quantidade que o jogador da jogada possuía em sua kallah, que será somado à quantidade de sementes capturadas do jogador adversário;
	 *  @return void
	 * */
	public void captura(Integer casa, List<Integer> meuTabuleiro, List<Integer> tabuleiroAdversario, Integer valorAntigoMinhaKallah) {
		int minhasSementes 	   = meuTabuleiro.get(casa);
		int sementesCapturadas = tabuleiroAdversario.get(this.getCasaCorrespondente(casa));
		int sementesParaKallah = minhasSementes + sementesCapturadas;
		
		meuTabuleiro.set(KALLAH_POSITION, (valorAntigoMinhaKallah + sementesParaKallah));
		meuTabuleiro.set(casa, 0);
		tabuleiroAdversario.set(this.getCasaCorrespondente(casa), 0);
		return;
	}
	
	/**
	 * Os tabuleiros estão de cabeça para baixo, de um em relação ao outro,
	 * então é necessário fazer a correspondência entre as casas do tabuleiro
	 * de cada jogador, como pode ser visto na representação do tabuleiro abaixo.
	 * 
	 * ---------------------------------
	 * |   | 5 | 4 | 3 | 2 | 1 | 0 |   |
	 * | 6 |-----------------------| 6 |
	 * |   | 0 | 1 | 2 | 3 | 4 | 5 |   |
	 * ---------------------------------
	 * 
	 * @param casa	uma casa a qual será feita a correspondência com a casa do jogador adversário;
	 * @return 		a casa do jogador adversário correspondente à casa recebida como parâmetro.
	 * */	
	private Integer getCasaCorrespondente(Integer casa) {
		switch(casa) {
		case 0:
			return 5;
		case 1:
			return 4;
		case 2:
			return 3;
		case 3:
			return 2;
		case 4:
			return 1;
		case 5:
			return 0;
		case 6:
			return 6;
		default:
			return null;
		}
	}
	
	/**
	 * 
	 * */
	public Boolean isNecessarioFinalizarJogo() {
		Boolean tabuleiro_1_vazio = this.tabuleiroJogador_1
				.stream()
				.filter(posicao -> posicao != 0)
				.findAny()
				.isEmpty();
				
		
		Boolean tabuleiro_2_vazio = this.tabuleiroJogador_2
				.stream()
				.filter(posicao -> posicao != 0)
				.findAny()
				.isEmpty();
		
		return tabuleiro_1_vazio || tabuleiro_2_vazio;
	}
	
	/**
	 * Após todas as casas de um dos jogadores não haver mais nenhuma semente,
	 * então o jogo é finalizado e apurado. A finalização consiste em juntar
	 * todas as sementes do outro jogador que ainda restaram no tabuleiro e
	 * adicionar à sua Kallah. Feito isso, o jogo é apurado e o método retorna
	 * o jogador vencedor, caso não tenha havido empate.
	 * 
	 * @return	um enum indicando o jogador vencedor ou se houve empate.
	 * */
	public Jogador finalizaJogo() {
		int somaJogador_1 = 0;
		int somaJogador_2 = 0;
		
		for(int i = 0; i < (NUMBER_OF_POSITIONS - 1); i++) { //-1 para não iterar a kallah, que é a última posição da lista;
			somaJogador_1 += this.tabuleiroJogador_1.get(i);
			somaJogador_2 += this.tabuleiroJogador_2.get(i);
			
			this.tabuleiroJogador_1.set(i, 0);
			this.tabuleiroJogador_2.set(i, 0);
		}
		
		int valorAtualKallah_1 = this.tabuleiroJogador_1.get(KALLAH_POSITION);
		int valorAtualKallah_2 = this.tabuleiroJogador_2.get(KALLAH_POSITION);
		
		this.tabuleiroJogador_1.set(KALLAH_POSITION, (valorAtualKallah_1 + somaJogador_1));
		this.tabuleiroJogador_2.set(KALLAH_POSITION, (valorAtualKallah_2 + somaJogador_2));
		
		Jogador vencedor = apuraJogo();
		return vencedor;
	}
	
	/**
	 * Faz a apuração do jogo após todas a finalização.
	 * Verifica qual jogador juntou mais sementes nas suas respectivas
	 * kallah's e retorna o jogador vencedor ou indica se houve empate.
	 * 
	 * @return	um enum indicando o jogador vencedor ou se houve empate.
	 * */
	private Jogador apuraJogo() {
		int kallahJogador_1 = this.tabuleiroJogador_1.get(KALLAH_POSITION);
		int kallahJogador_2 = this.tabuleiroJogador_2.get(KALLAH_POSITION);
		
		Jogador vencedor = Jogador.NENHUM;
		
		if(kallahJogador_1 > kallahJogador_2) {
			vencedor = Jogador.UM;
		} else if(kallahJogador_2 > kallahJogador_1) {
			vencedor = Jogador.DOIS;
		}
		
		return vencedor;
	}
	
	/**
	 * Faz um print do tabuleiro em determinado momento.
	 * Método utilizado para testes.
	 * 
	 * @return void
	 * */
	public void printTabuleiro() {
		for(int i = 0; i < NUMBER_OF_POSITIONS; i++) {
			System.out.println("i=" + (i+1) + "/" + this.tabuleiroJogador_1.get(i) + "/" + this.tabuleiroJogador_2.get(i));
		}
	}
}
