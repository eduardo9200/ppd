package br.edu.ifce.protocolo;

import lombok.Getter;

public enum Acao {
	COMANDO("cmd"),
	MENSAGEM("msg"),
	COMUNICACAO("com");
	
	@Getter
	private String descricao;
	
	private Acao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Acao getByDescricao(String descricao) {
		for(Acao acao : values()) {
			if(acao.getDescricao().equals(descricao)) {
				return acao;
			}
		}
		throw new IllegalArgumentException("Ação de comando inválida ou inexistente");
	}
}
