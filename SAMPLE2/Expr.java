public class Expr extends NonTerminal {
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

	public Expr(String pattern) {
		super("expr",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "expr + expr2":
				nt1 = (NonTerminal)getComponent("expr");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr2");
				nt2.interpret();
				operation = "+";
				break;
			case "expr - expr2":
				operation = "+";
				break;
			case "expr2":
				nt1 = (NonTerminal)getComponent("expr2");
				nt1.interpret();
				operation = "";
				lineNo = ((Expr2)nt1).lineNo();
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
			case "+":
				nt1.execute();
				nt2.execute();
				if( ((Expr)nt1).getType().equals("string") 
						|| ((Expr2)nt2).getType().equals("string") ) {
					type = "string";
					thisString = strValue = nt1.toString() + nt2.toString();
				}
				break;
			case "-":
				break;
			default:
				nt1.execute();
				Expr2 expr = ((Expr2)nt1);
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