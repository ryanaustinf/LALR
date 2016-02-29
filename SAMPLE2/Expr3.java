import java.util.ArrayList;

public class Expr3 extends NonTerminal {
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

	public Expr3(String pattern) {
		super("expr3",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "expr4 ^ expr3":
				operation = "^";
				nt1 = (NonTerminal)getComponent("expr4");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr3");
				nt2.interpret();
				lineNo = ((Expr4)nt1).lineNo();
				break;
			case "expr4":
				nt1 = (NonTerminal)getComponent("expr4");
				nt1.interpret();
				operation = "";
				lineNo = ((Expr4)nt1).lineNo();
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
			case "^":
				nt1.execute();
				nt2.execute();

				Expr4 x1 = (Expr4)nt1;
				String type1 = ((Expr4)nt1).getType();
				
				Expr3 x2 = (Expr3)nt2;
				String type2 = ((Expr3)nt2).getType();
				boolean error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								type = "int";
								intValue = (int)Math.pow(x1.intValue()
															,x2.intValue());
								break;
							case "float":
								type = "float";
								floatValue = Math.pow(x1.intValue()
														,x2.floatValue());
								break;
							case "char":
								type = "int";
								intValue = (int)Math.pow(x1.intValue()
															,x2.charValue());
								break;
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "float":
						switch(type2) {
							case "int":
								type = "float";
								floatValue = Math.pow(x1.floatValue()
														,x2.intValue());
								break;
							case "float":
								type = "float";
								floatValue = Math.pow(x1.floatValue()
														,x2.floatValue());
								break;
							case "char":
								type = "float";
								floatValue = Math.pow(x1.floatValue()
														,x2.charValue());
								break;
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "char":
						switch(type2) {
							case "int":
								type = "int";
								intValue = (int)Math.pow(x1.charValue()
														,x2.intValue());
								break;
							case "float":
								type = "float";
								floatValue = Math.pow(x1.charValue()
														,x2.intValue());
								break;
							case "char":
								type = "int";
								intValue = (int)Math.pow(x1.charValue()
														,x2.charValue());
								break;
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "array":
						switch(type2) {
							case "int":
								type = "array";
								arrayValue = x1.arrayValue();
								ArrayList<Object> obs = new ArrayList<Object>();
								for(int i = 0; i < x2.intValue(); i++) {
									for(Object o : arrayValue) {
										obs.add(o);
									}
								}
								arrayValue = obs.toArray(new Object[1]);
								break;
							case "char":
								type = "array";
								arrayValue = x1.arrayValue();
								obs = new ArrayList<Object>();
								for(int i = 0; i < x2.charValue(); i++) {
									for(Object o : arrayValue) {
										obs.add(o);
									}
								}
								arrayValue = obs.toArray(new Object[1]);
								break;
							case "float":
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "string":
						switch(type2) {
							case "int":
								type = "string";
								String str = "";
								for(int i = 0; i < x2.intValue(); i++ ) {
									str += x1.strValue();
								}
								strValue = str;
								break;
							case "char":
								type = "string";
								str = "";
								for(int i = 0; i < x2.intValue(); i++ ) {
									str += x1.strValue();
								}
								strValue = str;
								break;
							case "float":
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					default:
				}
				if( error ) {
					type = "error";
				} else {
					updateString();
				}
				break;
			default:
				nt1.execute();
				Expr4 expr = ((Expr4)nt1);
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

	private void updateString() {
		switch(type) {
			case "int":
				thisString = "" + intValue;
				break;
			case "float":
				thisString = "" + floatValue;
				break;
			case "char":
				thisString = "" + charValue;
				break;
			case "array":
				thisString = "";
				for(int i = 0; i < arrayValue.length; i++) {
					if( i > 0 ) {
						thisString += ",";
					}
					thisString += arrayValue[i].toString();
				} 
				break;
			case "string":
				thisString = strValue;
				break;
			default:
		}
	}

	public String toString() {
		return thisString;
	}
}