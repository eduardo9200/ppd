package br.edu.ifce.dashboard;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.exceptions.JogadorInvalidoException;

public class GameGeneralRules {

	private List<Integer> tabuleiroJogador_1;
	private List<Integer> tabuleiroJogador_2;
	
	private static int NUMBER_OF_POSITIONS = 7; //Número de casas do tabuleiro para cada jogador;
	private static int KALLAH_POSITION = 6;
	
	/**
	 * Cria os elementos mais básicos do tabuleiro, que são as casas
	 * a serem utilizadas e a quantidade de sementes que cada casa
	 * terá ao iniciar o jogo.
	 *
	 */
	public void criaTabuleiro() {
		this.tabuleiroJogador_1 = new ArrayList<Integer>();
		this.tabuleiroJogador_2 = new ArrayList<Integer>();
		
		//As listas possuirão 7 casas (6 casas + 1 Kallah)
		//Posições 0 a 5 -> casas 1 a 6, da esquerda para a direita;
		//Posição  6     -> casa kallah.
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
	
	public void moveSementes(Integer casaEscolhida, Jogador jogador) {
		
	}
	
	public void capturaSementesAdversarias(Integer casa, Jogador jogador) throws JogadorInvalidoException {
		int sementesCapturadas = 0;
		int minhasSementes     = 0;
		int sementesParaKallah = 0;
		int valorAntigoKallahJogador_1 = this.tabuleiroJogador_1.get(KALLAH_POSITION);
		int valorAntigoKallahJogador_2 = this.tabuleiroJogador_2.get(KALLAH_POSITION);
		
		if(jogador == Jogador.UM) {
			minhasSementes = this.tabuleiroJogador_1.get(casa);
			sementesCapturadas = this.tabuleiroJogador_2.get(casa);
			sementesParaKallah = minhasSementes + sementesCapturadas;
			this.tabuleiroJogador_1.set(KALLAH_POSITION, (valorAntigoKallahJogador_1 + sementesParaKallah));
			this.tabuleiroJogador_1.set(casa, 0);
			this.tabuleiroJogador_2.set(casa, 0);
		
		} else if(jogador == Jogador.DOIS) {
			minhasSementes = this.tabuleiroJogador_2.get(casa);
			sementesCapturadas = this.tabuleiroJogador_1.get(casa);
			sementesParaKallah = minhasSementes + sementesCapturadas;
			this.tabuleiroJogador_2.set(KALLAH_POSITION, (valorAntigoKallahJogador_2 + sementesParaKallah));
			this.tabuleiroJogador_2.set(casa, 0);
			this.tabuleiroJogador_1.set(casa, 0);
			
		} else {
			throw new JogadorInvalidoException("Jogador inválido.");
		}
	}
	
	public Jogador finalizaJogo() {
		int somaJogador_1 = 0;
		int somaJogador_2 = 0;
		
		for(int i = 0; i < (NUMBER_OF_POSITIONS - 1); i++) { //-1 para não iterar o kallah, que é a última posição da lista;
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
	
	public void printTabuleiro() {
		for(int i = 0; i < NUMBER_OF_POSITIONS; i++) {
			System.out.println("i=" + (i+1) + "/" + this.tabuleiroJogador_1.get(i) + "/" + this.tabuleiroJogador_2.get(i));
		}
	}
}
