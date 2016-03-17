public class Cond4 extends NonTerminal {
	private NonTerminal nt1;
	private NonTerminal nt2;

	public Cond4(String pattern) {
		super("cond4",pattern);
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "expr <= expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent("<=");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr >= expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent(">=");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr < expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent("<");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr > expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent(">");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr == expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent("==");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "expr != expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				printIndent("!=");
				nt2 = (NonTerminal)getComponent("expr",1);
				propagate(nt2);
				nt2.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			case "varname":
				printIndent(((Token)getComponent("varname")).token());
				break;
			case "expr":
				nt1 = (NonTerminal)getComponent("expr");
				propagate(nt1);
				nt1.interpret();
				put("lineNo",nt1.getAsInt("lineNo"));
				break;
			default:
		}
	}

	public void execute() {
		switch(getProdString()) {
			case "expr <= expr":
				nt1.execute();
				nt2.execute();

				String type1 = nt1.getAsString("type");
				String type2 = nt2.getAsString("type");
				boolean error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") <= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") <= nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") <= nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") <= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") <= nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") <= nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) <= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) <= nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) <= nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												<= 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + "<=" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}
				break;
			case "expr >= expr":
				nt1.execute();
				nt2.execute();

				type1 = nt1.getAsString("type");
				type2 = nt2.getAsString("type");
				error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") >= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") >= nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") >= nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") >= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") >= nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") >= nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) >= nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) >= nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) >= nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												>= 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + ">=" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}
				break;
			case "expr < expr":
				nt1.execute();
				nt2.execute();

				type1 = nt1.getAsString("type");
				type2 = nt2.getAsString("type");
				error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") < nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") < nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") < nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") < nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") < nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") < nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) < nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) < nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) < nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												< 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + "<" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}
				break;
			case "expr > expr":
				nt1.execute();
				nt2.execute();

				type1 = nt1.getAsString("type");
				type2 = nt2.getAsString("type");
				error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") > nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") > nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") > nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") > nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") > nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") > nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) > nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) > nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) > nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												> 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + ">" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}
				break;
			case "expr == expr":
				nt1.execute();
				nt2.execute();

				type1 = nt1.getAsString("type");
				type2 = nt2.getAsString("type");
				error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") == nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") == nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") == nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") == nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") == nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") == nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) == nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) == nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) == nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												== 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + "==" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}

				break;
			case "expr != expr":
				nt1.execute();
				nt2.execute();

				type1 = nt1.getAsString("type");
				type2 = nt2.getAsString("type");
				error = false;
				put("type","boolean");
				switch(type1) {
					case "int":
						switch(type2) {
							case "int":
								put("value",nt1.getAsInt("value") != nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsInt("value") != nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsInt("value") != nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsDouble("value") != nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsDouble("value") != nt2.getAsDouble("value"));
								break;
							case "char":
								put("value",nt1.getAsDouble("value") != nt2.getAsString("value").charAt(0));
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
								put("value",nt1.getAsString("value").charAt(0) != nt2.getAsInt("value"));
								break;
							case "float":
								put("value",nt1.getAsString("value").charAt(0) != nt2.getAsInt("value"));
								break;
							case "char":
								put("value",nt1.getAsString("value").charAt(0) != nt2.getAsString("value").charAt(0));
								break;
							case "string":
							case "array":
							default:
								error = true;
						}
						break;
					case "array":
					case "string":
						switch(type2) {
							case "string":
								put("value",nt1.getAsString("value")
											.compareTo(nt2.getAsString("value")) 
												!= 0);
								break;
							case "int":
							case "char":
							case "float":
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
													+ nt1 + "!=" + nt2 
													+ " at line " 
													+ getAsInt("lineNo"));
				}
				break;
			case "expr":
				nt1.execute();
				put("type",nt1.getAsString("type"));
				switch(getAsString("type")) {
					case "string":
					case "char":
						put("value",nt1.getAsString("value"));
						break;
					case "int":
						put("value",nt1.getAsInt("value"));
						break;
					case "float":
						put("value",nt1.getAsDouble("value"));
						break;
					case "array":
						put("value",nt1.getAsArray("value"));
						break;
					case "boolean":
						put("value",nt1.getAsBoolean("value"));
						break;
					default:
				}
				break;
			default:
		}
	}

	public String toString() {
		switch(getAsString("type")) {
			case "string":
			case "char":
				return "" + getAsString("value");
			case "int":
				return "" + getAsInt("value");
			case "float":
				return "" + getAsDouble("value");
			case "array":
				return "" + getAsArray("value");
			case "boolean":
				return "" + getAsBoolean("value");
			default:
				return "";
		}
	}
}