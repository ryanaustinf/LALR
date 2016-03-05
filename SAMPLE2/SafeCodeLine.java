public class SafeCodeLine extends NonTerminal {
	private NonTerminal code;

	public SafeCodeLine(String pattern) {
		super("safe_code_line",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "declaration ;":
				put("type","declaration");
				code = (NonTerminal)getComponent("declaration");
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "assignment ;":
				put("type","assignment");
				code = (NonTerminal)getComponent("assignment");
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "return_stmt ;":
				put("type","return");
				//TODO
				break;
			case "DELETE ;":
				put("type","break");
				put("lineNo",((Token)getComponent("DELETE")).lineNo());
				break;
			case "REGENERATE ;":
				put("type","continue");
				put("lineNo",((Token)getComponent("REGENERATE")).lineNo());
				break;
			case "switch_stmt":
				put("type","switch");
				//TODO
				break;
			case "print_stmt ;":
				put("type","print");
				code = (NonTerminal)getComponent("print_stmt");
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "scan_stmt ;":
				//TODO
				put("type","scan");
				break;
			case "func_call ;":
				//TODO
				put("type","func_call");
				break;
			case "loop":
				put("type","loop");
				//TODO
				break;
			default:
		}
	}

	public void execute() {
		try {
			code.execute();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}
}