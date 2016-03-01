public class Expr5 extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;
	
	private String operation;

	private String thisString;

	private int lineNo;

	public Expr5(String pattern) {
		super("expr5",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "( assignment )":
				break;
			case "( cond )":
				nt1 = (NonTerminal)getComponent("cond");
				nt1.interpret();
				put("lineNo", nt1.getAsInt("lineNo"));
				break;
			case "value":
				nt1 = (NonTerminal)getComponent("value");
				nt1.interpret();
				put("lineNo", nt1.getAsInt("lineNo"));
				break;
			case "varname":
				break;
			case "varname array_index":
				break;
			case "++ varname":
				break;
			case "-- varname":
				break;
			case "varname ++":
				break;
			case "varname --":
				break;
			default:
		}
	}

	public void execute() {
		switch(getProdString()) {
			case "( assignment )":
				break;
			case "( cond )":
				nt1.execute();
				//fall-through
			case "value":
				put("type",nt1.getAsString("type"));
				switch(getAsString("type")) {
					case "int":
						put("value",nt1.getAsInt("value"));
						thisString = "" + getAsInt("value");
						break;
					case "float":
						put("value",nt1.getAsDouble("value"));
						thisString = "" + getAsDouble("value");
						break;
					case "char":
						put("value",nt1.getAsString("value"));
						thisString = "" + getAsString("value");
						break;
					case "array":
						put("value",nt1.getAsArray("value"));
						thisString = "";
						Object[] arrayValue = getAsArray("value");
						for(int i = 0; i < arrayValue.length; i++) {
							if( i > 0 ) {
								thisString += ",";
							}
							thisString += arrayValue[i].toString();
						} 
						break;
					case "string":
						put("value",nt1.getAsString("value"));
						thisString = getAsString("value");
						break;
					case "boolean":
						put("value",nt1.getAsBoolean("value"));
						thisString = "" + getAsBoolean("value");
						break;
					default:
				}
				break;
			case "varname":
				break;
			case "++ varname":
				break;
			case "-- varname":
				break;
			case "varname ++":
				break;
			case "varname --":
				break;
			default:
		}
	}

	public String toString() {
		return thisString;
	}
}