public class Print extends NonTerminal {
	private NonTerminal nt;

	public Print(String pattern) {
		super("print_stmt",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "demat ( cond )":
			case "demat ( assignment )":
			case "demat ( func_call )":
				printIndent("demat");
				printIndent("(");
				break;		
			case "demater ( cond )":
			case "demater ( func_call )":
			case "demater ( assignment )":
				printIndent("demater");
				printIndent("(");
				break;
			default:
		}	
		switch(getProdString()) {
			case "demat ( cond )":
			case "demater ( cond )":
				nt = (NonTerminal)getComponent("cond");
				propagate(nt);
				nt.interpret();
				put("lineNo",nt.getAsInt("lineNo"));
				break;
			case "demat ( func_call )":
			case "demater ( func_call )":
				//TODO
				break;
			case "demat ( assignment )":
			case "demater ( assignment )":
				nt = (NonTerminal)getComponent("assignment");
				propagate(nt);
				nt.interpret();
				put("lineNo",nt.getAsInt("lineNo"));
				break;
			default:
		}
		printIndent(")");
	}

	public void execute() {
		nt.execute();
		if( getProdString().startsWith("demater")) {
			System.out.println(nt);
		} else {
			System.out.print(nt);
		}
	}
}