import java.util.ArrayList;

public class Expr2 extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;
	
	private String operation;

	private String thisString;

	public Expr2(String pattern) {
		super("expr2",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "expr2 * expr3":
				nt1 = (NonTerminal)getComponent("expr2");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr3");
				nt2.interpret();
				operation = "*";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr2 / expr3":
				nt1 = (NonTerminal)getComponent("expr2");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr3");
				nt2.interpret();
				operation = "/";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr2 % expr3":
				nt1 = (NonTerminal)getComponent("expr2");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr3");
				nt2.interpret();
				operation = "%";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr3":
				nt1 = (NonTerminal)getComponent("expr3");
				nt1.interpret();
				operation = "";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(operation) {
			case "*":
				nt1.execute();
				nt2.execute();

				String type1 = nt1.getAsString("type");
				String type2 = nt2.getAsString("type");
				boolean error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsInt("value") * nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsInt("value") * nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsInt("value") * nt2.getAsString("value").charAt(0));
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
								put("type","float");
								put("value",nt1.getAsDouble("value") * nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsDouble("value") * nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","float");
								put("value",nt1.getAsDouble("value") * nt2.getAsString("value").charAt(0));
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
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) * nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsString("value").charAt(0) * nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) * nt2.getAsString("value").charAt(0));
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
								put("type","array");
								put("value",nt1.getAsArray("value"));
								ArrayList<Object> obs = new ArrayList<Object>();
								Object[] arrayValue = getAsArray("value");
								for(int i = 0; i < nt2.getAsInt("value"); i++) {
									for(Object o : arrayValue) {
										obs.add(o);
									}
								}
								put("value",obs.toArray(new Object[1]));
								break;
							case "char":
								put("type","array");
								put("value",nt1.getAsArray("value"));
								obs = new ArrayList<Object>();
								arrayValue = getAsArray("value");
								for(int i = 0; i < nt2.getAsString("value").charAt(0); i++) {
									for(Object o : arrayValue) {
										obs.add(o);
									}
								}
								put("value",obs.toArray(new Object[1]));
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
								put("type","string");
								String str = "";
								for(int i = 0; i < nt2.getAsInt("value"); i++ ) {
									str += nt1.getAsString("value");
								}
								put("value",str);
								break;
							case "char":
								put("type","string");
								str = "";
								for(int i = 0; i < nt2.getAsInt("value"); i++ ) {
									str += nt1.getAsString("value");
								}
								put("value",str);
								break;
							case "float":
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					default:
						error = true;
				}
				if( error ) {
								put("type","error");
					System.out.println("Unsupported operation " 
													+ nt1 + "*" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				} else {
					updateString();
				}
				break;
			case "/":
				nt1.execute();
				nt2.execute();

				nt1 = (Expr2)nt1;
				type1 = nt1.getAsString("type");
				
				nt2 = (Expr3)nt2;
				type2 = nt2.getAsString("type");
				error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsInt("value") / nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsInt("value") / nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsInt("value") / nt2.getAsString("value").charAt(0));
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
								put("type","float");
								put("value",nt1.getAsDouble("value") / nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsDouble("value") / nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","float");
								put("value",nt1.getAsDouble("value") / nt2.getAsString("value").charAt(0));
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
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) / nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsString("value").charAt(0) / nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) / nt2.getAsString("value").charAt(0));
								break;
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
					default:
						error = true;
				}
				if( error ) {
								put("type","error");
					System.out.println("Unsupported operation " 
													+ nt1 + "/" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				} else {
					updateString();
				}
				break;
			case "%":
				nt1.execute();
				nt2.execute();

				nt1 = (Expr2)nt1;
				type1 = nt1.getAsString("type");
				
				nt2 = (Expr3)nt2;
				type2 = nt2.getAsString("type");
				error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsInt("value") % nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsInt("value") % nt2.getAsString("value").charAt(0));
								break;
							case "float":
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "char":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) % nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) % nt2.getAsString("value").charAt(0));
								break;
							case "float":
							case "array":
							case "string":
							default:
								error = true;
						}
						break;
					case "float":
					case "array":
					case "string":
					default:
						error = true;
				}
				if( error ) {
						put("type","error");
					System.out.println("Unsupported operation " 
													+ nt1 + "%" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				} else {
					updateString();
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
						put("value",nt1.getAsString("value").charAt(0));
						thisString = "" + getAsString("value");
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
				thisString = "" + getAsString("value");
				break;
			case "array":
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
				thisString = getAsString("value");
				break;
			default:
		}
	}

	public String toString() {
		return thisString;
	}
}