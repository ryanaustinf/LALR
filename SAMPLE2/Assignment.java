public class Assignment extends NonTerminal {
	private NonTerminal sub;
	private boolean array;

	public Assignment(String pattern) {
		super("assignment",pattern);
	}

	public void interpret() throws Exception {
		put("varname",((Token)getComponent("varname")).token());
		put("lineNo",((Token)getComponent("varname")).lineNo());
		switch(getProdString()) {
			case "varname = assignment":
				sub = (NonTerminal)getComponent("assignment");
				sub.interpret();
				array = false;
				break;
			case "varname = cond":
				sub = (NonTerminal)getComponent("cond");
				sub.interpret();
				array = false;
				break;
			case "varname = func_call":
				sub = (NonTerminal)getComponent("func_call");
				sub.interpret();
				array = false;
				//TODO
				break;
			case "varname array_index = assignment":
				sub = (NonTerminal)getComponent("assignment");
				sub.interpret();
				array = true;
				break;
			case "varname array_index = cond":
				sub = (NonTerminal)getComponent("cond");
				sub.interpret();
				array = true;
				break;
			case "varname array_index = func_call":
				sub = (NonTerminal)getComponent("func_call");
				sub.interpret();
				array = true;
				//TODO
				break;
			default:
		}
	}

	public void execute() {
		SymbolTable st = SymbolTable.instance();
		sub.execute();
		String type = getAsString("vartype");
		String type2 = sub.getAsString("type");
		if( type == null ) {
			Variable v = st.get(getAsString("varname"));
			if( v == null ) {
				System.out.println("Error: Undeclared variable " 
									+ getAsString("varname") + " at line " 
									+ getAsInt("lineNo"));
			} else {
				type = v.type();
			}
		} else {
			if(!st.declare(getAsString("varname"),getAsString("vartype"))) {
				System.out.println("Error: " + getAsString("varname") 
										+ " already declared at line " 
										+ getAsInt("lineNo"));
			}
		}
		
		if( array ) {
			//TODO
		} else {
			switch(type) {
				case "int":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),sub.getAsInt("value"));
							put("value",sub.getAsInt("value"));
							break;
						case "float":
							st.assign(getAsString("varname"),(int)sub.getAsDouble("value"));
							put("value",(int)sub.getAsDouble("value"));
							break;
						case "char":
							st.assign(getAsString("varname"),sub.getAsString("value").charAt(0));
							put("value",sub.getAsString("value").charAt(0));
							break;
						case "string":
							try {
								st.assign(getAsString("varname"),Integer.parseInt(sub.getAsString("value")));
								put("value",Integer.parseInt(sub.getAsString("value")));
							} catch(Exception e) {
								System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsString("value") 
													+ " cannot be converted to int.");
							}
							break;
						case "boolean":
							st.assign(getAsString("varname"),sub.getAsBoolean("value") ? 1 : 0);
							put("value",sub.getAsBoolean("value") ? 1 : 0);
							break;
						case "array":
							System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsArray("value") 
													+ " cannot be converted to int.");
							break;
						default:				
					}
					break;
				case "float":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),1.0 * sub.getAsInt("value"));
							put("value",sub.getAsInt("value") * 1.0);
							break;
						case "float":
							st.assign(getAsString("varname"),sub.getAsDouble("value"));
							put("value",sub.getAsDouble("value"));
							break;
						case "char":
							st.assign(getAsString("varname"),1.0 * sub.getAsString("value").charAt(0));
							put("value",1.0 * sub.getAsString("value").charAt(0));
							break;
						case "string":
							try {
								st.assign(getAsString("varname"),Double.parseDouble(sub.getAsString("value")));
								put("value",Double.parseDouble(sub.getAsString("value")));
							} catch(Exception e) {
								System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsString("value") 
													+ " cannot be converted to float.");
							}
							break;
						case "boolean":
							st.assign(getAsString("varname"),sub.getAsBoolean("value") ? 1.0 : 0.0);
							put("value",sub.getAsBoolean("value") ? 1.0 : 0.0);
							break;
						case "array":
							System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsArray("value") 
													+ " cannot be converted to float.");
							break;
						default:				
					}
					break;
				case "char":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),"" + (char)sub.getAsInt("value"));
							put("value","" + (char)sub.getAsInt("value"));
							break;
						case "float":
							st.assign(getAsString("varname"),"" + (char)sub.getAsDouble("value"));
							put("value","" + (char)sub.getAsDouble("value"));
							break;
						case "char":
							st.assign(getAsString("varname"),sub.getAsString("value"));
							put("value",sub.getAsString("value"));
							break;
						case "string":
							String temp = sub.getAsString("value");
							if( temp.length() == 1 ) {
								st.assign(getAsString("varname"),temp);
								put("value",temp);
							} else {
								System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsString("value") 
													+ " cannot be converted to char.");
							}
							break;
						case "boolean":
							st.assign(getAsString("varname")
										,sub.getAsBoolean("value") 
											? ("" + (char)1) : ("" + (char)0));
							put("value",sub.getAsBoolean("value") 
											? ("" + (char)1) : ("" + (char)0));
							break;
						case "array":
							System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsArray("value") 
													+ " cannot be converted to char.");
							break;
						default:				
					}
					break;
				case "string":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),"" + sub.getAsInt("value"));
							put("value",sub.getAsInt("value") + "");
							break;
						case "float":
							st.assign(getAsString("varname"),"" + sub.getAsDouble("value"));
							put("value","" + sub.getAsDouble("value"));
							break;
						case "char":
							st.assign(getAsString("varname"),sub.getAsString("value"));
							put("value",sub.getAsString("value"));
							break;
						case "string":
							st.assign(getAsString("varname"),sub.getAsString("value"));
							put("value",sub.getAsString("value"));
							break;
						case "boolean":
							st.assign(getAsString("varname"),sub.getAsBoolean("value") + "");
							put("value",sub.getAsBoolean("value") + "");
							break;
						case "array":
							Object[] obs = sub.getAsArray("value");
							String temp = "";
							for(Object o : obs) {
								temp += o.toString();
							}
							break;
						default:				
					}
					break;
				case "boolean":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),sub.getAsInt("value") != 0);
							put("value",sub.getAsInt("value") != 0);
							break;
						case "float":
							st.assign(getAsString("varname"),sub.getAsDouble("value") != 0.0);
							put("value",sub.getAsDouble("value") != 0.0);
							break;
						case "char":
							st.assign(getAsString("varname"),!sub.getAsString("value").equals("\0"));
							put("value",!sub.getAsString("value").equals("\0"));
							break;
						case "string":
							st.assign(getAsString("varname"),sub.getAsString("value").equals("true"));
							put("value",sub.getAsString("value").equals("true"));
							break;
						case "boolean":
							st.assign(getAsString("varname"),sub.getAsBoolean("value"));
							put("value",sub.getAsBoolean("value"));
							break;
						case "array":
							System.out.println("Error at line " 
													+ getAsInt("lineNo") + ": " 
													+ sub.getAsArray("value") 
													+ " cannot be converted to char.");
							break;
						default:				
					}
					break;
				case "array":
					switch(type2) {
						case "int":
							st.assign(getAsString("varname"),new Object[]{sub.getAsInt("value")});
							put("value",new Object[sub.getAsInt("value")]);
							break;
						case "float":
							st.assign(getAsString("varname"),new Object[]{sub.getAsDouble("value")});
							put("value",new Object[]{sub.getAsDouble("value")});
							break;
						case "char":
							st.assign(getAsString("varname"),new Object[]{sub.getAsString("value")});
							put("value",new Object[]{sub.getAsString("value")});
							break;
						case "string":
							st.assign(getAsString("varname"),new Object[]{sub.getAsString("value")});
							put("value",new Object[]{sub.getAsString("value")});
							break;
						case "boolean":
							st.assign(getAsString("varname"),new Object[]{sub.getAsBoolean("value")});
							put("value",new Object[]{sub.getAsBoolean("value")});
							break;
						case "array":
							st.assign(getAsString("varname"),sub.getAsArray("value"));
							put("value",sub.getAsArray("value"));
							break;
						default:				
					}
					break;
				default:
			}
		}
		put("type",type);
	}

	public String toString() {
		if( getAsString("type").equals("array"))  {
			String ret = "";
			boolean first = true;
			for( Object o : getAsArray("value") ) {
				if( !first ) {
					ret += ",";
				}
				first = false;
				ret += o.toString();
			}
			return ret;
		} else {
			return getAsObject("value").toString();
		}
	}
}