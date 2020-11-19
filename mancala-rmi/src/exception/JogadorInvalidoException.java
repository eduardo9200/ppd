package exception;

public class JogadorInvalidoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JogadorInvalidoException(String mensagem) {
		super(mensagem);
	}
}
