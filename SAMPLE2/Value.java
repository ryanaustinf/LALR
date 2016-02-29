public class Value extends NonTerminal {
	private int intValue;
	private double floatValue;
	private char charValue;
	private Object[] arrayValue;
	private String strValue;
	private String type;
	
	private Token token;
	
	private String operation;

	private String thisString;

	private int lineNo;

	public Value(String pattern) {
		super("value",pattern);
	}

	public void interpret() {
		switch(getProdString()) {
			case "int":
				type = "int";
				token = (Token)getComponent("int");
				intValue = Integer.parseInt(token.token());
				thisString = "" + intValue;
				break;
			case "float":
				type = "float";
				token = (Token)getComponent("float");
				floatValue = Double.parseDouble(token.token());
				thisString = "" + floatValue;
				break;
			case "char":
				type = "char";
				token = (Token)getComponent("char");
				String str = token.token();
				str = str.substring(1,str.length() - 1)
						.replaceAll("\\\\n","\n")
						.replaceAll("\\\\t","\t")
						.replaceAll("\\\\b","\b")
						.replaceAll("\\\\r","\r")
						.replaceAll("\\\\f","\f")
						.replaceAll("\\\\\'","'")
						.replaceAll("\\\\\"","\"")
						.replaceAll("\\\\\\\\","\\");
				charValue = str.charAt(0);
				thisString = "" + charValue;
				break;
			case "array":
				type = "array";
				break;
			case "string":
				type = "string";
				token = (Token)getComponent("string");
				str = token.token();
				str = str.substring(1,str.length() - 1)
						.replaceAll("\\\\n","\n")
						.replaceAll("\\\\t","\t")
						.replaceAll("\\\\b","\b")
						.replaceAll("\\\\r","\r")
						.replaceAll("\\\\f","\f")
						.replaceAll("\\\\\'","'")
						.replaceAll("\\\\\"","\"")
						.replaceAll("\\\\\\\\","\\");
				strValue = thisString = str;
				break;
			default:
		}
		lineNo = token.lineNo();
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

	public void execute() {}

	public String toString() {
		return thisString;
	}
}