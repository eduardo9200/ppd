package br.edu.ifce.protocolo;

public class ProtocoloMensagens {

	/**
	 * Protocolo:
	 * 
	 * A��o do Jogador:
	 * Comando  -> cmd:string/string/string
	 * Mensagem -> msg:string
	 * 
	 * A��o autom�tica: comunica��o entre tabuleiros durante a conex�o para definir quem � o jogador que inicia e qual jogador vai ser o 1 ou o 2
	 * comunica��o -> com:string
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
