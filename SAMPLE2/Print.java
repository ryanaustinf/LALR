public class Print extends NonTerminal {
	private NonTerminal nt;

	public Print(String pattern) {
		super("print_stmt",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "demat ( cond )":
			case "demater ( cond )":
				nt = (NonTerminal)getComponent("cond");
				nt.interpret();
				put("lineNo",nt.getAsInt("lineNo"));
				break;
			case "demat ( func_call )":
			case "demater ( func_call )":
				//TODO
				break;
			case "demat ( assignment )":
			case "demater ( assignment )":
				//TODO
				break;
			default:
		}
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