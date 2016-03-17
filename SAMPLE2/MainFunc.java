public class MainFunc extends NonTerminal {
	private Code code;

	public MainFunc(String pattern) {
		super("main_func",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		printIndent("TARDIS");
		printIndent("(");
		printIndent(")");
		printIndent("{");
		code = (Code)getComponent("code");
		propagate(code);
		code.interpret();
		printIndent("}");
	}

	public void execute() {
		SymbolTable.instance().pushContext();
		CodeLine[] codes = (CodeLine[])code.getAsArray("lines");
		boolean fail = false;
		for(CodeLine cl: codes) {
			switch( cl.getType() ) {
				case "return":
					break;
				case "break":
					System.out.println("Illegal DELETE at line " 
											+ cl.getAsInt("lineNo"));
					fail = true;
					break;
				case "continue":
					System.out.println("Illegal REGENERATE at line " 
											+ cl.getAsInt("lineNo"));
					fail = true;
					break;
				default:
					cl.execute();
					Object status;
					if( (status = cl.getAsObject("status")) != null ) {
						switch(status.toString()) {
						 	case "return":
								break;
							case "break":
								System.out.println("Illegal DELETE at line " 
														+ cl.getAsInt("lineNo"));
								fail = true;
								break;
							case "continue":
								System.out.println("Illegal REGENERATE at line " 
														+ cl.getAsInt("lineNo"));
								fail = true;
								break;
						 }
					}
			}
			if( fail ) {
				break;
			}
		}
	}
}