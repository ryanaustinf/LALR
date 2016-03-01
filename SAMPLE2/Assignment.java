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
		String type = getAsString("type");
		if( type == null ) {
			Variable v = st.get(getAsString("varname"));
			if( v == null ) {
				System.out.println("Undeclared variable " 
									+ getAsString("varname") + " at line " 
									+ getAsInt("lineNo"));
			} else {
				type = v.type();
			}
		} else {
			st.declare(getAsString("varname"),getAsString("type"));
		}
		
		if( array ) {
			//TODO
		} else {
			switch(type) {
				case "int":
					st.assign(getAsString("varname"),sub.getAsInt("value"));
					put("value",sub.getAsInt("value"));
					break;
				case "float":
					st.assign(getAsString("varname"),sub.getAsDouble("value"));
					put("value",sub.getAsDouble("value"));
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
					st.assign(getAsString("varname"),sub.getAsBoolean("value"));
					put("value",sub.getAsBoolean("value"));
					break;
				case "array":
					st.assign(getAsString("varname"),sub.getAsArray("value"));
					put("value",sub.getAsArray("value"));
					break;
				default:
			}
		}
	}

	public String toString() {
		return getAsString("varname") + " = " + getAsObject("value");
	}
}