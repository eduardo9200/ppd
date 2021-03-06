package dashboard;

import exception.JogadorInvalidoException;

public enum Jogador {
	AMARELO (1L, "Jogador Amarelo"),
	AZUL (2L, "Jogador Azul"),
	NENHUM(3l, "Empate");
	
	
	private Long id;
	private String descricao;
	
	private Jogador(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static Jogador getById(Long id) throws JogadorInvalidoException {
		for(Jogador jogador : values()) {
			if(jogador.getId() == id) {
				return jogador;
			}
		}
		throw new JogadorInvalidoException("Jogador inv�lido");
	}
	
}
