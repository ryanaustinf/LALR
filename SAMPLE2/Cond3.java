public class Cond3 extends NonTerminal {
	private NonTerminal nt1;
	
	public Cond3(String pattern) {
		super("cond3",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "! cond3":
				nt1 = (NonTerminal)getComponent("cond3");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "cond4":
				nt1 = (NonTerminal)getComponent("cond3");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(getProdString()) {
			case "! cond3":
				nt1.execute();
				put("value",!nt1.getAsBoolean("value"));
				break;
			case "cond4":
				nt1.execute();
				put("value",nt1.getAsBoolean("value"));
				break;
			default:
		}
	}
}