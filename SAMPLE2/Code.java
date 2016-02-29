import java.util.ArrayList;

public class Code extends NonTerminal {
	private ArrayList<CodeLine> codes;

	public Code(String pattern) {
		super("code",pattern);
		codes = new ArrayList<CodeLine>();
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "code_line code":
				CodeLine cl = (CodeLine)getComponent("code_line");
				cl.interpret();
				codes.add(cl);
				Code code = (Code)getComponent("code");
				code.interpret();
				CodeLine[] morecodes = code.getCodes();
				for(CodeLine temp: morecodes) {
					codes.add(temp);
				}
				break;
			case "code_line":
				cl = (CodeLine)getComponent("code_line");
				cl.interpret();
				codes.add(cl);
				break;
			default:
		}
	}

	public CodeLine[] getCodes() {
		return codes.toArray(new CodeLine[1]);
	}

	public void execute() {
		//containers should determine how this executes
	}
}