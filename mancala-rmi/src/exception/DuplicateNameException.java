package exception;

public class DuplicateNameException extends ChatException {

	private static final long serialVersionUID = 1L;

	public DuplicateNameException(String message) {
		super(message);
	}
}
