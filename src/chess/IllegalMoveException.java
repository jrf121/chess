package chess;

public class IllegalMoveException extends IllegalArgumentException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalMoveException(String s){
		super(s);
	}
}
