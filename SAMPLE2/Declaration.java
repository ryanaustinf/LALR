public class Declaration extends NonTerminal {
	private ParseObject[] varlist;

	public Declaration(String pattern) {
		super("declaration",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		NonTerminal type = (NonTerminal)getComponent("type");
		propagate(type);
		type.interpret();
		put("vartype",type.getAsString("type"));
		put("lineNo",type.getAsInt("lineNo"));
		if( getAsString("vartype").equals("void")) {
			throw new Exception("Cannot declare void variable at line " 
							+ type.getAsInt("lineNo"));
		}

		NonTerminal varlist = (NonTerminal)getComponent("var_list");
		propagate(varlist);
		varlist.interpret();
		this.varlist = (ParseObject[])varlist.getAsArray("vars");
	}

	public void execute() {
		SymbolTable st = SymbolTable.instance();
		for(ParseObject var : varlist) {
			if( var instanceof Token) {
				Token t = (Token)var;
				boolean ok = st.declare(t.token(),getAsString("vartype"));
				if( !ok ) {
					System.out.println("Error: " + t.token() 
										+ " already declared at line " 
										+ t.lineNo());
				}
				// System.out.println(st.callStack());
				// System.out.println(st.symbolTable());
			} else {
				NonTerminal nt = (NonTerminal)var;
				nt.put("vartype",getAsString("vartype"));
				nt.execute();
				// System.out.println(st.callStack());
				// System.out.println(st.symbolTable());
			}
		}
	}
}