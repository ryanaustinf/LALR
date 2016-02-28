public class Expr2 extends NonTerminal {
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

	public Expr2(String pattern) {
		super("expr2",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "expr2 * expr3":
				operation = "*";
				break;
			case "expr2 / expr3":
				operation = "/";
				break;
			case "expr2 % expr3":
				operation = "%";
				break;
			case "expr3":
				nt1 = (NonTerminal)getComponent("expr3");
				nt1.interpret();
				operation = "";
				lineNo = ((Expr3)nt1).lineNo();
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
		switch(operation) {
			case "*":
				break;
			case "/":
				break;
			case "%":
				break;
			default:
				nt1.execute();
				Expr3 expr = ((Expr3)nt1);
				type = expr.getType();
				switch(type) {
					case "int":
						intValue = expr.intValue();
						thisString = "" + intValue;
						break;
					case "float":
						floatValue = expr.floatValue();
						thisString = "" + floatValue;
						break;
					case "char":
						charValue = expr.charValue();
						thisString = "" + charValue;
						break;
					case "array":
						arrayValue = expr.arrayValue();
						thisString = "";
						for(int i = 0; i < arrayValue.length; i++) {
							if( i > 0 ) {
								thisString += ",";
							}
							thisString += arrayValue[i].toString();
						} 
						break;
					case "string":
						strValue = expr.strValue();
						thisString = strValue;
						break;
					default:
				}
		}
	}

	public String toString() {
		return thisString;
	}
}