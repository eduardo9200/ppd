package br.edu.ifce.dashboard;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.exceptions.JogadorInvalidoException;

public class GameGeneralRules {

	private List<Integer> tabuleiroJogador_1;
	private List<Integer> tabuleiroJogador_2;
	
	private static int NUMBER_OF_POSITIONS = 7; //N�mero de casas do tabuleiro para cada jogador;
	private static int KALLAH_POSITION = 6;
	
	/**
	 * Cria os elementos mais b�sicos do tabuleiro, que s�o as casas
	 * a serem utilizadas e a quantidade de sementes que cada casa
	 * ter� ao iniciar o jogo.
	 *
	 */
	public void criaTabuleiro() {
		this.tabuleiroJogador_1 = new ArrayList<Integer>();
		this.tabuleiroJogador_2 = new ArrayList<Integer>();
		
		//As listas possuir�o 7 casas (6 casas + 1 Kallah)
		//Posi��es 0 a 5 -> casas 1 a 6, da esquerda para a direita;
		//Posi��o  6     -> casa kallah.
		for(int i = 0; i < NUMBER_OF_POSITIONS; i++) {
			if(i == 6) {
				this.tabuleiroJogador_1.add(0);
				this.tabuleiroJogador_2.add(0);
			} else {
				this.tabuleiroJogador_1.add(4);
				this.tabuleiroJogador_2.add(4);
			}
		}
	}
	
	/**
	 * Distribui as sementes nas casas do tabuleiro e tamb�m implementa a regra de n�o poder incluir semente no kallah do advers�rio
	 * */
	public void moveSementes(Integer casaEscolhida, Jogador jogador) throws JogadorInvalidoException {
		int tabuleiroJogador_1 = Jogador.UM.getId().intValue();
		int tabuleiroJogador_2 = Jogador.DOIS.getId().intValue();
		
		if(jogador == Jogador.UM) {
			int casaInicial = casaEscolhida.intValue() + 1;
			
			int numSementesCasaEscolhida = this.tabuleiroJogador_1.get(casaEscolhida);
			this.tabuleiroJogador_1.set(casaEscolhida, 0);
			
			int qtdCasaAtual = 0;
			int casaAtual = casaInicial;
			int tabuleiro = tabuleiroJogador_1;
			
			for(int i = 0; i < numSementesCasaEscolhida; i++) {
				if(casaAtual == 7 && tabuleiro == tabuleiroJogador_1) {
					casaAtual = 0;
					tabuleiro = tabuleiroJogador_2;
				
				} else if(casaAtual == 7 && tabuleiro == tabuleiroJogador_2) {
					casaAtual = 0;
					tabuleiro = tabuleiroJogador_1;
				}
				
				if(tabuleiro == tabuleiroJogador_1) {
					qtdCasaAtual = 0;
					qtdCasaAtual = this.tabuleiroJogador_1.get(casaAtual);
					this.tabuleiroJogador_1.set(casaAtual, qtdCasaAtual++);
					
				} else if(tabuleiro == tabuleiroJogador_2) {
					if(casaAtual != 6) {
						qtdCasaAtual = 0;
						qtdCasaAtual = this.tabuleiroJogador_2.get(casaAtual);
						this.tabuleiroJogador_2.set(casaAtual, qtdCasaAtual++);	
					}
				}
				
				casaAtual++;
			}
			
		} else if (jogador == Jogador.DOIS) {
			int casaInicial = casaEscolhida.intValue() + 1;
			
			int numSementesCasaEscolhida = this.tabuleiroJogador_2.get(casaEscolhida);
			this.tabuleiroJogador_2.set(casaEscolhida, 0);
			
			int qtdCasaAtual = 0;
			int casaAtual = casaInicial;
			int tabuleiro = tabuleiroJogador_2;
			
			for(int i = 0; i < numSementesCasaEscolhida; i++) {
				if(casaAtual == 7 && tabuleiro == tabuleiroJogador_1) {
					casaAtual = 0;
					tabuleiro = tabuleiroJogador_2;
				
				} else if(casaAtual == 7 && tabuleiro == tabuleiroJogador_2) {
					casaAtual = 0;
					tabuleiro = tabuleiroJogador_1;
				}
				
				if(tabuleiro == tabuleiroJogador_1) {
					if(casaAtual != 6) {
						qtdCasaAtual = 0;
						qtdCasaAtual = this.tabuleiroJogador_1.get(casaAtual);
						this.tabuleiroJogador_1.set(casaAtual, qtdCasaAtual++);
					}
				
				} else if(tabuleiro == tabuleiroJogador_2) {
					qtdCasaAtual = 0;
					qtdCasaAtual = this.tabuleiroJogador_2.get(casaAtual);
					this.tabuleiroJogador_2.set(casaAtual, qtdCasaAtual++);
				}
			}
			
		} else {
			throw new JogadorInvalidoException("Jogador inv�lido");
		}
	}
	
	/**
	 * 
	 * */
	public void capturaSementesAdversarias(Integer casa, Jogador jogador) throws JogadorInvalidoException {
		int sementesCapturadas = 0;
		int minhasSementes     = 0;
		int sementesParaKallah = 0;
		int valorAntigoKallahJogador_1 = this.tabuleiroJogador_1.get(KALLAH_POSITION);
		int valorAntigoKallahJogador_2 = this.tabuleiroJogador_2.get(KALLAH_POSITION);
		
		if(jogador == Jogador.UM) {
			minhasSementes = this.tabuleiroJogador_1.get(casa);
			sementesCapturadas = this.tabuleiroJogador_2.get(getCasaCorrespondente(casa));
			sementesParaKallah = minhasSementes + sementesCapturadas;
			this.tabuleiroJogador_1.set(KALLAH_POSITION, (valorAntigoKallahJogador_1 + sementesParaKallah));
			this.tabuleiroJogador_1.set(casa, 0);
			this.tabuleiroJogador_2.set(casa, 0);
		
		} else if(jogador == Jogador.DOIS) {
			minhasSementes = this.tabuleiroJogador_2.get(casa);
			sementesCapturadas = this.tabuleiroJogador_1.get(getCasaCorrespondente(casa));
			sementesParaKallah = minhasSementes + sementesCapturadas;
			this.tabuleiroJogador_2.set(KALLAH_POSITION, (valorAntigoKallahJogador_2 + sementesParaKallah));
			this.tabuleiroJogador_2.set(casa, 0);
			this.tabuleiroJogador_1.set(casa, 0);
			
		} else {
			throw new JogadorInvalidoException("Jogador inv�lido.");
		}
	}
	
	/**
	 * Como os tabuleiros est�o de cabe�a para baixo, de um em rela��o ao outro,
	 * ent�o � necess�rio fazer essa correspond�ncia, sendo relevante na captura
	 * de pe�as advers�rias.
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
	public Jogador finalizaJogo() {
		int somaJogador_1 = 0;
		int somaJogador_2 = 0;
		
		for(int i = 0; i < (NUMBER_OF_POSITIONS - 1); i++) { //-1 para n�o iterar a kallah, que � a �ltima posi��o da lista;
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
	 * 
	 * */
	public Jogador apuraJogo() {
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
	 * 
	 * */
	public void printTabuleiro() {
		for(int i = 0; i < NUMBER_OF_POSITIONS; i++) {
			System.out.println("i=" + (i+1) + "/" + this.tabuleiroJogador_1.get(i) + "/" + this.tabuleiroJogador_2.get(i));
		}
	}
}