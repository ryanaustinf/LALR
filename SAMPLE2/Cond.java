public class Cond extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;

	public Cond(String pattern) {
		super("cond",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "cond || cond2":
				nt1 = (NonTerminal)getComponent("cond");
				propagate(nt1);
				nt1.interpret();
				printIndent("||");
				nt2 = (NonTerminal)getComponent("cond2");
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "cond2":
				nt1 = (NonTerminal)getComponent("cond2");
				propagate(nt1);
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
				if( nt1.getAsString("type").equals("boolean") ) {
					boolean val = nt1.getAsBoolean("value");
					put("type","boolean");
					if( val ) {
						put("value",val);
					} else {
						nt2.execute();
						if(nt2.getAsString("type").equals("boolean")){
							put("value",nt2.getAsBoolean("value"));
						} else {
							put("type","error");
							System.out.println("Unsupported operation " + nt1 + " || "
												+ nt2 + " at line " 
												+ getAsInt("lineNo"));	
						}
					}
				} else {
					put("type","error");
					System.out.println("Unsupported operation " + nt1 + " || "
										+ nt2 + " at line " 
										+ getAsInt("lineNo"));
				}
				break;
			case "cond2":
				nt1.execute();
				put("type",nt1.getAsString("type"));
				switch(getAsString("type")) {
					case "string":
					case "char":
						put("value",nt1.getAsString("value"));
						break;
					case "int":
						put("value",nt1.getAsInt("value"));
						break;
					case "float":
						put("value",nt1.getAsDouble("value"));
						break;
					case "array":
						put("value",nt1.getAsArray("value"));
						break;
					case "boolean":
						put("value",nt1.getAsBoolean("value"));
						break;
					default:
				}
				break;
			default:
		}
	}

	public String toString() {
		switch(getAsString("type")) {
			case "string":
			case "char":
				return "" + getAsString("value");
			case "int":
				return "" + getAsInt("value");
			case "float":
				return "" + getAsDouble("value");
			case "array":
				return "" + getAsArray("value");
			case "boolean":
				return "" + getAsBoolean("value");
			default:
				return "";
		}
	}
}