import java.util.ArrayList;

public class IfStmt extends NonTerminal {
	private ArrayList<CodeLine> codes;
	private ArrayList<CodeLine> codes2;

	public IfStmt(String pattern) {
		super("if_stmt",pattern);
		codes = new ArrayList<CodeLine>();
		codes2 = new ArrayList<CodeLine>();
	}

	public void interpret() throws Exception {
		printBranch();
		printIndent("dontblink");
		printIndent("(");

		switch(getProdString()) {
			case "dontblink ( cond ) code_body":
				NonTerminal nt = (NonTerminal)getComponent("cond");
				propagate(nt);
				nt.interpret();
				printIndent(")");
				nt = (NonTerminal)getComponent("code_body");
				propagate(nt);
				nt.interpret();
				CodeLine[] cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				break;
			case "dontblink ( cond ) code_body blinkandyouredead code_line":
				nt = (NonTerminal)getComponent("cond");
				propagate(nt);
				nt.interpret();
				printIndent(")");
				nt = (NonTerminal)getComponent("code_body");
				propagate(nt);
				nt.interpret();
				printIndent("blinkandyouredead");
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				NonTerminal nt2 = (NonTerminal)getComponent("code_line");
				propagate(nt2);
				nt2.interpret();
				codes2.add((CodeLine)getComponent("code_line"));
				break;
			case "dontblink ( cond ) code_body blinkandyouredead { code }":
				nt = (NonTerminal)getComponent("cond");
				propagate(nt);
				nt.interpret();
				printIndent(")");
				nt = (NonTerminal)getComponent("code_body");
				propagate(nt);
				nt.interpret();
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				printIndent("blinkandyouredead");
				printIndent("{");
				nt = (NonTerminal)getComponent("code");
				propagate(nt);
				nt.interpret();
				printIndent("}");
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes2.add(line);
				}
				break;
			case "dontblink ( cond ) code_body blinkandyouredead { }":
				nt = (NonTerminal)getComponent("cond");
				propagate(nt);
				nt.interpret();
				printIndent(")");
				nt = (NonTerminal)getComponent("code_body");
				propagate(nt);
				nt.interpret();
				printIndent("blinkandyouredead");
				printIndent("{");
				printIndent("}");
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				break;
			default:
		}
	}

	public void execute() {
		SymbolTable st = SymbolTable.instance();
		NonTerminal cond = (NonTerminal)getComponent("cond");
		cond.execute();
		if( cond.getAsString("type").equals("boolean")) {
			st.pushContext();
			if( cond.getAsBoolean("value")) {
				run(codes);
			} else {
				run(codes2);
			}
			st.popContext();
		} else {
			System.out.println("Illegal " + cond.getAsString("type") 
								+ " as condition on line " 
								+ cond.getAsInt("lineNo"));
		}
	}

	public void run(ArrayList<CodeLine> codes) {
		boolean stop = false;
		for(CodeLine cl: codes) {
			switch( cl.getType() ) {
				case "return":
					put("status","return");
					put("lineNo",cl.getAsInt("lineNo"));
					break;
				case "break":
					put("status","break");
					put("lineNo",cl.getAsInt("lineNo"));
					stop = true;
					break;
				case "continue":
					put("status","continue");
					put("lineNo",cl.getAsInt("lineNo"));
					stop = true;
					break;
				default:
					cl.execute();
					Object status;
					if( (status = cl.getAsObject("status")) != null ) {
						switch(status.toString()) {
						 	case "return":
						 		put("status","return");
						 		put("lineNo",cl.getAsInt("lineNo"));
								break;
							case "break":
								put("status","break");
								put("lineNo",cl.getAsInt("lineNo"));
								stop = true;
								break;
							case "continue":
								put("status","continue");
								put("lineNo",cl.getAsInt("lineNo"));
								stop = true;
								break;
						 }
					}
			}
			if( stop ) {
				break;
			}
		}
	}
}