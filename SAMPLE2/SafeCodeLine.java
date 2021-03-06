public class SafeCodeLine extends NonTerminal {
	private NonTerminal code;

	public SafeCodeLine(String pattern) {
		super("safe_code_line",pattern);
	}

	public void interpret() throws Exception {
		if(Driver.SHOW_TREE) {
			printBranch();
		}
		switch(getProdString()) {
			case "declaration ;":
				put("type","declaration");
				code = (NonTerminal)getComponent("declaration");
				propagate(code);
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				printIndent(";");
				break;
			case "assignment ;":
				put("type","assignment");
				code = (NonTerminal)getComponent("assignment");
				propagate(code);
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				printIndent(";");
				break;
			case "return_stmt ;":
				put("type","return");
				printIndent(";");
				//TODO
				break;
			case "DELETE ;":
				printIndent("DELETE");
				printIndent(";");
				put("type","break");
				put("lineNo",((Token)getComponent("DELETE")).lineNo());
				break;
			case "REGENERATE ;":
				printIndent("REGENERATE");
				printIndent(";");
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
				propagate(code);
				code.interpret();
				printIndent(";");
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "scan_stmt ;":
				//TODO
				put("type","scan");
				printIndent(";");
				break;
			case "func_call ;":
				//TODO
				put("type","func_call");
				printIndent(";");
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