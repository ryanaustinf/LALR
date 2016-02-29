public class Expr4 extends NonTerminal {
	private int intValue;
	private double floatValue;
	private char charValue;
	private Object[] arrayValue;
	private String strValue;
	private String type;
	
	private NonTerminal nt1;
	private NonTerminal nt2;
	
	private String operation;

	private String thisString;

	private int lineNo;

	public Expr4(String pattern) {
		super("expr4",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "( assignment )":
				break;
			case "( expr )":
				nt1 = (NonTerminal)getComponent("expr");
				nt1.interpret();
				lineNo = ((Expr)nt1).lineNo();
				break;
			case "value":
				nt1 = (NonTerminal)getComponent("value");
				nt1.interpret();
				lineNo = ((Value)nt1).lineNo();
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

	public int lineNo() {
		return lineNo;
	}

	public int intValue() {
		return intValue;
	}

	public double floatValue() {
		return floatValue;
	}

	public char charValue() {
		return charValue;
	}

	public Object[] arrayValue() {
		return arrayValue;
	}

	public String strValue() {
		return strValue;
	}

	public String getType() {
		return type;
	}

	public void execute() {
		switch(getProdString()) {
			case "( assignment )":
				break;
			case "( expr )":
				Expr x = (Expr)nt1;
				x.execute();
				type = x.getType();
				switch(type) {
					case "int":
						intValue = x.intValue();
						thisString = "" + intValue;
						break;
					case "float":
						floatValue = x.floatValue();
						thisString = "" + floatValue;
						break;
					case "char":
						charValue = x.charValue();
						thisString = "" + charValue;
						break;
					case "array":
						arrayValue = x.arrayValue();
						thisString = "";
						for(int i = 0; i < arrayValue.length; i++) {
							if( i > 0 ) {
								thisString += ",";
							}
							thisString += arrayValue[i].toString();
						} 
						break;
					case "string":
						strValue = x.strValue();
						thisString = strValue;
						break;
					default:
				}
				break;
			case "value":
				Value v = (Value)nt1;
				type = v.getType();
				switch(type) {
					case "int":
						intValue = v.intValue();
						thisString = "" + intValue;
						break;
					case "float":
						floatValue = v.floatValue();
						thisString = "" + floatValue;
						break;
					case "char":
						charValue = v.charValue();
						thisString = "" + charValue;
						break;
					case "array":
						arrayValue = v.arrayValue();
						thisString = "";
						for(int i = 0; i < arrayValue.length; i++) {
							if( i > 0 ) {
								thisString += ",";
							}
							thisString += arrayValue[i].toString();
						} 
						break;
					case "string":
						strValue = v.strValue();
						thisString = strValue;
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