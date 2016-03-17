public class CodeLine extends NonTerminal {
	NonTerminal code;
	String type;

	public CodeLine(String pattern) {
		super("code_line",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "safe_code_line":
				code = (NonTerminal)getComponent("safe_code_line");
				propagate(code);
				code.interpret();
				type = code.getAsString("type");
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "if_stmt":
				type = "if";
				code = (NonTerminal)getComponent("if_stmt");
				propagate(code);
				code.interpret();
				break;
			case "unless_stmt":
				type = "unless";
				code = (NonTerminal)getComponent("unless_stmt");
				propagate(code);
				code.interpret();
				break;
			default:
		}
	}

	public String getType() {
		return type;
	}

	public void execute() {
		code.execute();
		put("status",code.getAsObject("status"));
	}
}