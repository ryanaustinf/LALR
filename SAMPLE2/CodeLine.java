public class CodeLine extends NonTerminal {
	SafeCodeLine code;
	String type;
	private int lineNo;

	public CodeLine(String pattern) {
		super("code_line",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "safe_code_line":
				code = (SafeCodeLine)getComponent("safe_code_line");
				code.interpret();
				type = code.getType();
				lineNo = code.lineNo();
				break;
			case "if_stmt":
				type = "if";
				//do later
				break;
			case "unless_stmt":
				type = "unless";
				//do later
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