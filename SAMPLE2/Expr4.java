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
				break;
			case "value":
				Value v = (Value)nt1;
				type = v.getType();
				switch(type) {
					case "int":
						break;
					case "double":
						break;
					case "char":
						break;
					case "array":
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