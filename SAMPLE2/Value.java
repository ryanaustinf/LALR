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
				break;
			case "float":
				type = "float";
				break;
			case "char":
				type = "char";
				break;
			case "array":
				type = "array";
				break;
			case "string":
				type = "string";
				token = (Token)getComponent("string");
				String str = token.token();
				str = str.substring(1,str.length() - 1);
				str = str.replaceAll("\\\\n","\n");
				str = str.replaceAll("\\\\t","\t");
				str = str.replaceAll("\\\\b","\b");
				str = str.replaceAll("\\\\r","\r");
				str = str.replaceAll("\\\\f","\f");
				str = str.replaceAll("\\\\\'","'");
				str = str.replaceAll("\\\\\"","\"");
				str = str.replaceAll("\\\\\\\\","\\");;
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