import java.util.ArrayList;

public class UnlessStmt extends NonTerminal {
	private ArrayList<CodeLine> codes;
	private ArrayList<CodeLine> codes2;

	public UnlessStmt(String pattern) {
		super("unless_stmt",pattern);
		codes = new ArrayList<CodeLine>();
		codes2 = new ArrayList<CodeLine>();
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "dontevenblink ( cond ) code_body":
				getComponent("cond").interpret();
				NonTerminal nt = (NonTerminal)getComponent("code_body");
				nt.interpret();
				CodeLine[] cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				break;
			case "dontevenblink ( cond ) code_body blinkandyouredead code_line":
				getComponent("cond").interpret();
				nt = (NonTerminal)getComponent("code_body");
				nt.interpret();
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				getComponent("code_line").interpret();
				codes2.add((CodeLine)getComponent("code_line"));
				break;
			case "dontevenblink ( cond ) code_body blinkandyouredead { code }":
				getComponent("cond").interpret();
				nt = (NonTerminal)getComponent("code_body");
				nt.interpret();
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				nt = (NonTerminal)getComponent("code");
				nt.interpret();
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes2.add(line);
				}
				break;
			case "dontevenblink ( cond ) code_body blinkandyouredead { }":
				getComponent("cond").interpret();
				nt = (NonTerminal)getComponent("code_body");
				nt.interpret();
				cl = (CodeLine[])nt.getAsArray("lines");
				for(CodeLine line : cl) {
					codes.add(line);
				}
				break;
			default:
		}
	}

	public void execute() {
		NonTerminal cond = (NonTerminal)getComponent("cond");
		cond.execute();
		if( cond.getAsString("type").equals("boolean")) {
			if( cond.getAsBoolean("value")) {
				run(codes2);
			} else {
				run(codes);
			}
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