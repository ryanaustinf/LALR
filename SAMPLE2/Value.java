public class Value extends NonTerminal {
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
				put("type","int");
				token = (Token)getComponent("int");
				put("value",Integer.parseInt(token.token()));
				thisString = "" + intValue;
				break;
			case "float":
				put("type","float");
				token = (Token)getComponent("float");
				put("value",Double.parseDouble(token.token()));
				thisString = "" + floatValue;
				break;
			case "char":
				put("type","char");
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
				put("value",str.charAt(0));
				thisString = "" + charValue;
				break;
			case "array":
				put("type","array");
				break;
			case "string":
				put("type","string");
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
				put("value",thisString = str);
				break;
			default:
		}
		put("lineNo",token.lineNo());
	}

	public void execute() {}

	public String toString() {
		return thisString;
	}
}