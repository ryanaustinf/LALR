public class Print extends NonTerminal {
	private NonTerminal nt;

	private int lineNo;

	public Print(String pattern) {
		super("print_stmt",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "demat ( expr )":
				nt = (NonTerminal)getComponent("expr");
				nt.interpret();
				lineNo = ((Expr)nt).lineNo();
				break;
			case "demat ( cond )":
				//TODO
				break;
			case "demat ( func_call )":
				//TODO
				break;
			case "demat ( assignment )":
				//TODO
				break;
			default:
		}
	}

	public int lineNo() {
		return lineNo;
	}

	public void execute() {
		nt.execute();
		System.out.print(nt);
	}
}