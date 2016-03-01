public class Print extends NonTerminal {
	private NonTerminal nt;

	public Print(String pattern) {
		super("print_stmt",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "demat ( cond )":
				nt = (NonTerminal)getComponent("cond");
				nt.interpret();
				put("lineNo",nt.getAsInt("lineNo"));
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

	public void execute() {
		nt.execute();
		System.out.print(nt);
	}
}