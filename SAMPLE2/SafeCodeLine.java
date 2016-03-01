public class SafeCodeLine extends NonTerminal {
	private NonTerminal code;
	private String type;
	private int lineNo;

	public SafeCodeLine(String pattern) {
		super("safe_code_line",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "declaration ;":
				type = "declaration";
				//TODO
				break;
			case "assignment ;":
				type = "assignment";
				//TODO
				break;
			case "return_stmt ;":
				type = "return";
				//TODO
				break;
			case "DELETE ;":
				type = "break";
				//TODO
				break;
			case "REGENERATE ;":
				type = "continue";
				//TODO
				break;
			case "switch_stmt":
				type = "switch";
				//TODO
				break;
			case "print_stmt ;":
				type = "print";
				code = (NonTerminal)getComponent("print_stmt");
				code.interpret();
				put("lineNo",code.getAsInt("lineNo"));
				break;
			case "scan_stmt ;":
				//TODO
				type = "scan";
				break;
			case "func_call ;":
				//TODO
				type = "func_call";
				break;
			case "loop":
				type = "loop";
				//TODO
				break;
			default:
		}
	}

	public int lineNo() {
		return lineNo;
	}

	public String getType() {
		return type;
	}

	public void execute() {
		code.execute();
	}
}