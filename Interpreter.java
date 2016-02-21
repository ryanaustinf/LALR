public class Interpreter {
	private Tokenizer tokenizer;
	private Parser parser;
	private TableGenerator tableGen;
	private NonTerminalFactory ntf;
	private String code;

	public Interpreter(Tokenizer tokenizer,NonTerminalFactory ntf, String code
						,String cfgFile) {
		this.tokenizer = tokenizer;
		this.code = code;
		this.ntf = ntf;
		try {
			tableGen = new TableGenerator(cfgFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
		parser = new Parser(ntf);
	}

	public void interpret() {
		tokenizer.setCode(code);
		tokenizer.tokenize();
		parser.setTokens(tokenizer.getTokens());
		NonTerminal start = parser.parse();
		if( start != null ) {
			start.execute();
		}
	}
}