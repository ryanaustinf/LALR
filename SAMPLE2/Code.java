import java.util.ArrayList;

public class Code extends NonTerminal {
	private ArrayList<CodeLine> codes;

	public Code(String pattern) {
		super("code",pattern);
		codes = new ArrayList<CodeLine>();
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "code_line code":
				CodeLine cl = (CodeLine)getComponent("code_line");
				propagate(cl);
				cl.interpret();
				codes.add(cl);
				Code code = (Code)getComponent("code");
				propagate(code);
				code.interpret();
				CodeLine[] morecodes = (CodeLine[])code.getAsArray("lines");
				for(CodeLine temp: morecodes) {
					codes.add(temp);
				}
				break;
			case "code_line":
				cl = (CodeLine)getComponent("code_line");
				propagate(cl);
				cl.interpret();
				codes.add(cl);
				break;
			default:
		}
		put("lines",codes.toArray(new CodeLine[0]));
	}

	public void execute() {
		//containers should determine how this executes
	}
}