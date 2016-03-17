import java.util.ArrayList;

public class CodeBody extends NonTerminal {
	public CodeBody(String pattern) {
		super("code_body",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		ArrayList<CodeLine> codes = new ArrayList<CodeLine>();
		switch(getProdString()) {
			case "safe_code_line":
				CodeLine cl = (CodeLine)getComponent("safe_code_line");
				propagate(cl);
				cl.interpret();
				codes.add(cl);
				break;
			case "{ code }":
				printIndent("{");
				Code c = (Code)getComponent("code");
				propagate(c);
				c.interpret();
				printIndent("}");
				CodeLine[] codeList = (CodeLine[])c.getAsArray("lines");
				for( CodeLine line: codeList ) {
					codes.add(line);
				}
				break;
			case "{ }":
			default:
		}
		put("lines",codes.toArray(new CodeLine[0]));
	}

	public void execute() {

	}
}