import java.util.ArrayList;

public class Expr extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;
	
	private String operation;

	private String thisString;

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
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr - expr2":
				nt1 = (NonTerminal)getComponent("expr");
				nt1.interpret();
				nt2 = (NonTerminal)getComponent("expr2");
				nt2.interpret();
				operation = "-";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr2":
				nt1 = (NonTerminal)getComponent("expr2");
				nt1.interpret();
				operation = "";
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(operation) {
			case "+":
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
								put("value",nt1.getAsInt("value") + nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsInt("value") + nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsInt("value") + nt2.getAsString("value").charAt(0));
								break;
							case "string":
								put("type","string");
								put("value",nt1.getAsInt("value") + nt2.getAsString("value"));
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "float":
						switch(type2) {
							case "int":
								put("type","float");
								put("value",nt1.getAsDouble("value") + nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsDouble("value") + nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","float");
								put("value",nt1.getAsDouble("value") + nt2.getAsString("value").charAt(0));
								break;
							case "string":
								put("type","string");
								put("value",nt1.getAsDouble("value") + nt2.getAsString("value"));
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "char":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) + nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsString("value").charAt(0) + nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) + nt2.getAsString("value").charAt(0));
								break;
							case "string":
								put("type","string");
								put("value",nt1.getAsString("value").charAt(0) + nt2.getAsString("value"));
								break;
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
						put("type","array");
						put("value",nt1.getAsArray("value"));
						ArrayList<Object> obs = new ArrayList<Object>();
						Object[] arrayValue = getAsArray("value");
						for(int i = 0; i < nt2.getAsInt("value"); i++) {
							for(Object o : arrayValue) {
								obs.add(o);
							}
						}
						switch(type2) {
							case "int":
								obs.add(nt2.getAsInt("value"));
								break;
							case "char":
								obs.add(nt2.getAsString("value").charAt(0));
								break;
							case "float":
								obs.add(nt2.getAsDouble("value"));
								break;
							case "array":
								for(Object o: nt2.getAsArray("value")) {
									obs.add(o);	
								}
								break;
							case "string":
								obs.add(nt2.getAsString("value"));
							default:
								error = true;
						}
						put("value",obs.toArray(new Object[1]));
						break;
					case "string":
						put("type","string");
						String strValue = nt1.getAsString("value");
						switch(type2) {
							case "int":
								strValue += nt2.getAsInt("value");
								break;
							case "char":
								strValue += nt2.getAsString("value").charAt(0);
								break;
							case "float":
								strValue += nt2.getAsDouble("value");
								break;
							case "array":
								for(Object o: nt2.getAsArray("value")) {
									strValue += o.toString();
								}
								break;
							case "string":
								strValue += nt2.getAsString("value");
								break;
							case "boolean":
								put("type","string");
								strValue += nt2.getAsBoolean("value");
								break;
							default:
								error = true;
						}
						if( !error ) {
							put("value",strValue);
						}
						break;
					case "boolean":
						switch(type2) {
							case "string":
								put("type","string");
								put("value",nt1.getAsBoolean("value") 
											+ nt2.getAsString("value"));
								break;
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
													+ nt1 + "+" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				} else {
					updateString();
				}
				break;
			case "-":
				nt1.execute();
				nt2.execute();

				nt1 = (Expr)nt1;
				type1 = nt1.getAsString("type");
				
				nt2 = (Expr2)nt2;
				type2 = nt2.getAsString("type");
				error = false;
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("type","int");
								put("value",nt1.getAsInt("value") - nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsInt("value") - nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsInt("value") - nt2.getAsString("value").charAt(0));
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
								put("type","float");
								put("value",nt1.getAsDouble("value") - nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsDouble("value") - nt2.getAsDouble("value"));
								break;
							case "char":
								put("type","float");
								put("value",nt1.getAsDouble("value") - nt2.getAsString("value").charAt(0));
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
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) - nt2.getAsInt("value"));
								break;
							case "float":
								put("type","float");
								put("value",nt1.getAsString("value").charAt(0) - nt2.getAsInt("value"));
								break;
							case "char":
								put("type","int");
								put("value",nt1.getAsString("value").charAt(0) - nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
						put("type","array");
						put("value",nt1.getAsArray("value"));
						ArrayList<Object> obs = new ArrayList<Object>();
						Object[] arrayValue = getAsArray("value");
						for(int i = 0; i < nt2.getAsInt("value"); i++) {
							for(Object o : arrayValue) {
								obs.add(o);
							}
						}
						switch(type2) {
							case "int":
								if(obs.indexOf(nt2.getAsInt("value")) != -1) {
									obs.remove(obs.indexOf(nt2.getAsInt("value")));
								}
								break;
							case "char":
								if(obs.indexOf(nt2.getAsString("value").charAt(0)) != -1) {
									obs.remove(obs.indexOf(nt2.getAsString("value").charAt(0)));
								}
								break;
							case "float":
								if(obs.indexOf(nt2.getAsDouble("value")) != -1) {
									obs.remove(obs.indexOf(nt2.getAsDouble("value")));
								}
								break;
							case "array":
								for(Object o: nt2.getAsArray("value")) {
									if(obs.indexOf(o) != -1) {
										obs.remove(obs.indexOf(o));
									}	
								}
								break;
							case "string":
								if(obs.indexOf(nt2.getAsString("value")) != -1) {
									obs.remove(obs.indexOf(nt2.getAsString("value")));
								}
							default:
								error = true;
						}
						put("value",obs.toArray(new Object[1]));
						break;
					case "string":
						put("type","string");
						put("value",nt1.getAsString("value"));
						switch(type2) {
							case "int":
								put("value",getAsString("value")
									.replaceAll("" + nt2.getAsInt("value"),""));
								break;
							case "char":
								put("value",getAsString("value")
									.replaceAll("" + nt2.getAsString("value").charAt(0),""));
								break;
							case "float":
								put("value",getAsString("value")
									.replaceAll("" + nt2.getAsDouble("value"),""));
								break;
							case "string":
								put("value",getAsString("value")
									.replaceAll("" + nt2.getAsString("value"),""));
								break;
							case "array":
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
													+ nt1 + "-" + nt2 
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