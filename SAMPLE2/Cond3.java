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
				nt1 = (NonTerminal)getComponent("cond4");
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
				if( nt1.getAsString("type").equals("boolean")) {
					put("value",!nt1.getAsBoolean("value"));
					put("type","boolean");
				} else {
					put("type","error");
					System.out.println("Unsupported operation !" + nt1 
										+ " at line " + getAsInt("lineNo"));
				}
				break;
			case "cond4":
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