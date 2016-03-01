import java.util.ArrayList;

public class Expr4 extends NonTerminal {
	private NonTerminal nt1;
	
	private String thisString;

	public Expr4(String pattern) {
		super("expr4",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "+ expr4":
				nt1 = (NonTerminal)getComponent("expr4");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "- expr4":
				nt1 = (NonTerminal)getComponent("expr4");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr5":
				nt1 = (NonTerminal)getComponent("expr5");
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(getProdString()) {
			case "+ expr4":
				nt1.execute();
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
						thisString = "" + getAsString("value").charAt(0);
						break;
					case "array":
					case "string":
						break;
					default:
						put("type","error");
						System.out.println("Unsupported operation +" + nt1 
											+ " at line " + getAsInt("lineNo"));
				}
				break;
			case "- expr4":
				nt1.execute();
				put("type",nt1.getAsString("type"));
				switch(getAsString("type")) {
					case "int":
						put("value",-nt1.getAsInt("value"));
						thisString = "" + -getAsInt("value");
						break;
					case "float":
						put("value",-nt1.getAsDouble("value"));
						thisString = "" + -getAsDouble("value");
						break;
					case "char":
						put("value",-nt1.getAsString("value").charAt(0) + "");
						thisString = "" + -getAsString("value").charAt(0);
						break;
					case "array":
					case "string":
						break;
					default:
						put("type","error");
						System.out.println("Unsupported operation +" + nt1 
											+ " at line " + getAsInt("lineNo"));
				}
				break;
			default:
				nt1.execute();
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
						thisString = "" + getAsString("value").charAt(0);
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
		}
	}

	private void updateString() {
		switch(getAsString("type")) {
			case "int":
				thisString = "" + getAsInt("value");
				break;
			case "float":
				thisString = "" + getAsDouble("value");
				break;
			case "char":
				thisString = "" + getAsString("value").charAt(0);
				break;
			case "array":
				thisString = "";
				Object[] arrayValue = getAsArray("value");
				for(int i = 0; i < arrayValue.length; i++){
					if( i > 0 ) {
						thisString += ",";
					}
					thisString += arrayValue[i].toString();
				} 
				break;
			case "string":
				thisString = getAsString("value");
				break;
			default:
		}
	}

	public String toString() {
		return thisString;
	}
}