import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Tokenizer {
	private ArrayList<Token> tokens;
	protected int state;
	protected String code;
	protected String currToken;
	protected int lineNo;

	/**
	 * sets code to code variable
	 * initializes token array
	 * 
	 */
	public Tokenizer(String code) {
		this.code = code;
		tokens = new ArrayList<Token>();
		lineNo = 0;
		currToken = "";
	}

	/**
	 * this method should populate the tokens array with Token objects
	 * use currToken to store the current Token being processed
	 * use addToken to add currToken to the list. addToken clears currToken.
	 * addToken only adds tokens of length > 0
	 * do not forget to implement tokenType
	 */
	public abstract void tokenize();

	protected void addToken() {
		if( currToken.length() > 0 ) {
			Token t = new Token(currToken,tokenType(),lineNo);
			tokens.add(t);
		}
		currToken = "";
	}

	/**
	 * this method should process and return the type of currToken as recognized
	 * by the CFG. the type returned by this method MUST BE IDENTICAL TO THE 
	 * TERMINAL DESIGNATIONS DECLARED IN THE CFG.
	 * e.g
	 * for the production 
	 * IF -> if ( CONDITION ) STATEMENT
	 * for any token "if","(", and ")", this method must return "if","(", and 
	 * ")" respectively
	 */
	public abstract String tokenType();

	public List<Token> getTokens() {
		return tokens;
	}

	public Token getToken(int index) {
		return tokens.get(index);
	}
}