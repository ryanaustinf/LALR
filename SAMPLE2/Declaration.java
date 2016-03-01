public class Declaration extends NonTerminal {
	private ParseObject[] varlist;

	public Declaration(String pattern) {
		super("declaration",pattern);
	}

	public void interpret() throws Exception {
		NonTerminal type = (NonTerminal)getComponent("type");
		type.interpret();
		put("type",type.getAsString("type"));
		if( getAsString("type").equals("void")) {
			throw new Exception("Cannot declare void variable at line " 
							+ type.getAsInt("lineNo"));
		}

		NonTerminal varlist = (NonTerminal)getComponent("var_list");
		varlist.interpret();
		this.varlist = (ParseObject[])varlist.getAsArray("vars");
	}

	public void execute() {
		SymbolTable st = SymbolTable.instance();
		for(ParseObject var : varlist) {
			if( var instanceof Token) {
				Token t = (Token)var;
				boolean ok = st.declare(t.token(),getAsString("type"));
				if( !ok ) {
					System.out.println("Error: " + t.token() 
										+ " already declared at line " 
										+ t.lineNo());
				}
			} else {
				NonTerminal nt = (NonTerminal)var;
				nt.put("type",getAsString("type"));
				nt.execute();
			}
		}
	}
}