package br.edu.ifce.main;

import br.edu.ifce.dashboard.GameGeneralRules;
import br.edu.ifce.dashboard.Jogador;

public class Teste {

	public void testar() {
		GameGeneralRules game = new GameGeneralRules();
		game.criaTabuleiro();
		game.printTabuleiro();
		System.out.println("----");
		try {
			/*game.capturaSementesAdversarias(2, Jogador.UM);
			game.printTabuleiro();
			System.out.println("----");
			game.capturaSementesAdversarias(3, Jogador.DOIS);
			game.printTabuleiro();
			System.out.println("----");*/
			
			/*game.capturaSementesAdversarias(0, Jogador.UM);
			game.printTabuleiro();
			System.out.println("----");
			game.capturaSementesAdversarias(1, Jogador.DOIS);
			game.printTabuleiro();*/
			
			/*System.out.println("----");
			CasaUltimaSemente casa = game.moveSementes(2, Jogador.DOIS);
			game.printTabuleiro();
			System.out.println("Tem direito a nova jogada? " + game.temDireitoANovaJogada(casa, Jogador.DOIS));
			System.out.println("Tem direito a captura? " + game.temDireitoACapturaDeSementes(casa, Jogador.DOIS));
			
			System.out.println(casa.getCasa() + "/" + casa.getIdTabuleiro());
			
			System.out.println("----");
			Jogador vencedor = game.finalizaJogo();
			game.printTabuleiro();
			System.out.println(vencedor.getDescricao());*/
			
			System.out.println("É necessário finalizar o jogo? " + game.isNecessarioFinalizarJogo());
			
			if(game.isNecessarioFinalizarJogo()) {
				System.out.println("----");
				Jogador vencedor = game.finalizaJogo();
				game.printTabuleiro();
				System.out.println("Vencedor = " + vencedor.getDescricao());
			}
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
