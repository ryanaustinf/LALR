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
				printIndent("(");
				nt1 = (NonTerminal)getComponent("assignment");
				propagate(nt1);
				nt1.interpret();
				printIndent(")");
				put("lineNo", nt1.getAsInt("lineNo"));
				break;
			case "( cond )":
				printIndent("(");
				nt1 = (NonTerminal)getComponent("cond");
				propagate(nt1);
				nt1.interpret();
				printIndent(")");
				put("lineNo", nt1.getAsInt("lineNo"));
				break;
			case "value":
				nt1 = (NonTerminal)getComponent("value");
				propagate(nt1);
				nt1.interpret();
				put("lineNo", nt1.getAsInt("lineNo"));
				break;
			case "varname":
				printIndent(((Token)getComponent("varname")).token());
				put("varname",((Token)getComponent("varname")).token());
				put("lineNo",((Token)getComponent("varname")).lineNo());
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
				SymbolTable st = SymbolTable.instance();
				Variable v = st.get(getAsString("varname"));
				if( v == null ) {
					System.out.println("Undeclared variable " 
										+ getAsString("varname") + " at line " 
										+ getAsInt("lineNo"));
				} else {
					put("type",v.type());
					switch(getAsString("type")) {
						case "int":
							put("value",v.getAsInt());
							break;
						case "float":
							put("value",v.getAsDouble());
							break;
						case "char":
							put("value",v.getAsString());
							break;
						case "string":
							put("value",v.getAsString());
							break;
						case "boolean":
							put("value",v.getAsBoolean());
							break;
						case "array":
							put("value",v.getAsArray());
							break;
						default:
					}
				} 
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