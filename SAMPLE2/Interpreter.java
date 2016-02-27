public class Interpreter {
	private Tokenizer tokenizer;
	private Parser parser;
	private TableGenerator tableGen;
	private NonTerminalFactory ntf;
	private State[] parseTable;
	private String code;

	public Interpreter(Tokenizer tokenizer,NonTerminalFactory ntf, String code
						,String cfgFile) throws Exception {
		this.tokenizer = tokenizer;
		this.code = code;
		this.ntf = ntf;
		try {
			long time = System.currentTimeMillis();
			tableGen = new TableGenerator(cfgFile);
			parseTable = tableGen.generateParseTable();
			System.out.println((System.currentTimeMillis() - time) / 1000.0 + " secs" );
			parser = new Parser(ntf,parseTable);
			// parser = new Parser(ntf,null);
			if( parseTable == null ) {
				throw new Exception("Cannot interpret due to conflicts in " 
									+ "grammar");
			}
		} catch(Exception e) {
			throw e;
		}
	}

	public void interpret() throws Exception {
		tokenizer.setCode(code);
		tokenizer.tokenize();
		// for(Token t : tokenizer.getTokens() ) {
		// 	System.out.println(t);
		// }
		parser.setTokens(tokenizer.getTokens());
		NonTerminal start = parser.parse();
		if( start != null ) {
			System.out.println("EXECUTING");
			start.execute();
		} else {
			System.out.println("Program terminated without execution.");
		}
	}
}