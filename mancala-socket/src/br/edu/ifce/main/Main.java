package br.edu.ifce.main;

import br.edu.ifce.dashboard.GameGeneralRules;
import br.edu.ifce.dashboard.Jogador;
import br.edu.ifce.exceptions.JogadorInvalidoException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameGeneralRules game = new GameGeneralRules();
		game.criaTabuleiro();
		game.printTabuleiro();
		System.out.println("----");
		try {
			game.capturaSementesAdversarias(2, Jogador.UM);
			game.printTabuleiro();
			System.out.println("----");
			game.capturaSementesAdversarias(3, Jogador.DOIS);
			game.printTabuleiro();
			
			game.capturaSementesAdversarias(0, Jogador.UM);
			game.printTabuleiro();
			System.out.println("----");
			game.capturaSementesAdversarias(1, Jogador.DOIS);
			game.printTabuleiro();
			
			System.out.println("----");
			Jogador vencedor = game.finalizaJogo();
			game.printTabuleiro();
			System.out.println(vencedor.getDescricao());
		} catch(JogadorInvalidoException e) {
			
		}
		
	}

}
