package br.edu.ifce.protocolo;

public class ProtocoloMensagens {

	/**
	 * Protocolo:
	 * 
	 * Ação do Jogador:
	 * Comando  -> cmd:string/string/string
	 * Mensagem -> msg:string
	 * 
	 * Ação automática: comunicação entre tabuleiros durante a conexão para definir quem é o jogador que inicia e qual jogador vai ser o 1 ou o 2
	 * comunicação -> com:string
	 * 
	 * */
	
	public String encode(Acao acao, String mensagem) {
		StringBuilder builder = new StringBuilder();
		builder.append(acao.getDescricao());
		builder.append(":");
		builder.append(mensagem);
		
		return builder.toString();
	}
	
	public EstruturaProtocolo decode(String mensagem) {
		String[] actionSplitted = mensagem.split(":");
		EstruturaProtocolo estruturaProtocolo = new EstruturaProtocolo();
		
		estruturaProtocolo.setAcao(Acao.getByDescricao(actionSplitted[0]));
		estruturaProtocolo.setMensagem(actionSplitted[1]);
		
		return estruturaProtocolo;
	}
}
