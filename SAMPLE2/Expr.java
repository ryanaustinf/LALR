import java.util.ArrayList;

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
				lineNo = ((Expr)nt1).lineNo();
				break;
			case "expr - expr2":
				nt1 = (NonTerminal)getComponent("expr");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr2");
				nt2.interpret();
				operation = "-";
				lineNo = ((Expr)nt1).lineNo();
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

				Expr x1 = (Expr)nt1;
				String type1 = x1.getType();
				
				Expr2 x2 = (Expr2)nt2;
				String type2 = x2.getType();
				boolean error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								type = "int";
								intValue = x1.intValue() + x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.intValue() + x2.floatValue();
								break;
							case "char":
								type = "int";
								intValue = x1.intValue() + x2.charValue();
								break;
							case "string":
								type = "string";
								strValue = x1.intValue() + x2.strValue();
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "float":
						switch(type2) {
							case "int":
								type = "float";
								floatValue = x1.floatValue() + x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.floatValue() + x2.floatValue();
								break;
							case "char":
								type = "float";
								floatValue = x1.floatValue() + x2.charValue();
								break;
							case "string":
								type = "string";
								strValue = x1.floatValue() + x2.strValue();
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "char":
						switch(type2) {
							case "int":
								type = "int";
								intValue = x1.charValue() + x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.charValue() + x2.intValue();
								break;
							case "char":
								type = "int";
								intValue = x1.charValue() + x2.charValue();
								break;
							case "string":
								type = "string";
								strValue = x1.charValue() + x2.strValue();
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
						type = "array";
						arrayValue = x1.arrayValue();
						ArrayList<Object> obs = new ArrayList<Object>();
						for(int i = 0; i < x2.intValue(); i++) {
							for(Object o : arrayValue) {
								obs.add(o);
							}
						}
						switch(type2) {
							case "int":
								obs.add(x2.intValue());
								break;
							case "char":
								obs.add(x2.charValue());
								break;
							case "float":
								obs.add(x2.floatValue());
								break;
							case "array":
								for(Object o: x2.arrayValue()) {
									obs.add(o);	
								}
								break;
							case "string":
								obs.add(x2.strValue());
							default:
								error = true;
						}
						arrayValue = obs.toArray(new Object[1]);
						break;
					case "string":
						type = "string";
						strValue = x1.strValue();
						switch(type2) {
							case "int":
								strValue += x2.intValue();
								break;
							case "char":
								strValue += x2.charValue();
								break;
							case "float":
								strValue += x2.floatValue();
								break;
							case "array":
								for(Object o: x2.arrayValue()) {
									strValue += o.toString();
								}
								break;
							case "string":
								strValue += x2.strValue();
								break;
							default:
								error = true;
						}
						break;
					default:
				}
				if( error ) {
					type = "error";
					System.out.println("Unsupported operation " 
													+ nt1 + "+" + nt2 
													+ " at line " + lineNo);
				} else {
					updateString();
				}
				break;
			case "-":
				nt1.execute();
				nt2.execute();

				x1 = (Expr)nt1;
				type1 = x1.getType();
				
				x2 = (Expr2)nt2;
				type2 = x2.getType();
				error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								type = "int";
								intValue = x1.intValue() - x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.intValue() - x2.floatValue();
								break;
							case "char":
								type = "int";
								intValue = x1.intValue() - x2.charValue();
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "float":
						switch(type2) {
							case "int":
								type = "float";
								floatValue = x1.floatValue() - x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.floatValue() - x2.floatValue();
								break;
							case "char":
								type = "float";
								floatValue = x1.floatValue() - x2.charValue();
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "char":
						switch(type2) {
							case "int":
								type = "int";
								intValue = x1.charValue() - x2.intValue();
								break;
							case "float":
								type = "float";
								floatValue = x1.charValue() - x2.intValue();
								break;
							case "char":
								type = "int";
								intValue = x1.charValue() - x2.charValue();
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
						type = "array";
						arrayValue = x1.arrayValue();
						ArrayList<Object> obs = new ArrayList<Object>();
						for(int i = 0; i < x2.intValue(); i++) {
							for(Object o : arrayValue) {
								obs.add(o);
							}
						}
						switch(type2) {
							case "int":
								if(obs.indexOf(x2.intValue()) != -1) {
									obs.remove(obs.indexOf(x2.intValue()));
								}
								break;
							case "char":
								if(obs.indexOf(x2.charValue()) != -1) {
									obs.remove(obs.indexOf(x2.charValue()));
								}
								break;
							case "float":
								if(obs.indexOf(x2.floatValue()) != -1) {
									obs.remove(obs.indexOf(x2.floatValue()));
								}
								break;
							case "array":
								for(Object o: x2.arrayValue()) {
									if(obs.indexOf(o) != -1) {
										obs.remove(obs.indexOf(o));
									}	
								}
								break;
							case "string":
								if(obs.indexOf(x2.strValue()) != -1) {
									obs.remove(obs.indexOf(x2.strValue()));
								}
							default:
								error = true;
						}
						arrayValue = obs.toArray(new Object[1]);
						break;
					case "string":
						type = "string";
						strValue = x1.strValue();
						switch(type2) {
							case "int":
								strValue = strValue.replaceAll("" 
												+ x2.intValue(),"");
								break;
							case "char":
								strValue = strValue.replaceAll("" 
												+ x2.charValue(),"");
								break;
							case "float":
								strValue = strValue.replaceAll("" 
												+ x2.floatValue(),"");
								break;
							case "string":
								strValue = strValue.replaceAll("" 
												+ x2.strValue(),"");
								break;
							case "array":
							default:
								error = true;
						}
						break;
					default:
				}
				if( error ) {
					type = "error";
					System.out.println("Unsupported operation " 
													+ nt1 + "-" + nt2 
													+ " at line " + lineNo);
				} else {
					updateString();
				}
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