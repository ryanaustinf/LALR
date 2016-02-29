public class Cond extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;

	public Cond(String pattern) {
		super("cond",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "cond || cond2":
				nt1 = (NonTerminal)getComponent("cond");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("cond2");
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "cond2":
				nt1 = (NonTerminal)getComponent("cond2");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(getProdString()) {
			case "cond || cond2":
				nt1.execute();
				boolean val = nt1.getAsBoolean("value");
				if( val ) {
					put("value",val);
				} else {
					nt2.execute();
					put("value",nt2.getAsBoolean("value"));
				}
				break;
			case "cond2":
				nt1.execute();
				put("value",nt1.getAsBoolean("value"));
				break;
			default:
		}
	}
}