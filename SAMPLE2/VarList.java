import java.util.ArrayList;

public class VarList extends NonTerminal {
	private ArrayList<ParseObject> vars;

	public VarList(String pattern) {
		super("var_list",pattern);
		vars = new ArrayList<ParseObject>();
	}

	public void interpret() throws Exception {
		printBranch();
		switch(getProdString()) {
			case "varname , var_list":
				vars.add(getComponent("varname"));
				printIndent(((Token)getComponent("varname")).token());
				printIndent(",");
				NonTerminal temp = (NonTerminal)getComponent("var_list");
				propagate(temp);
				temp.interpret();
				ParseObject[] obs = (ParseObject[])temp.getAsArray("vars");
				for(ParseObject po: obs) {
					vars.add(po);
				}
				put("vars",vars.toArray(new ParseObject[1]));
				break;
			case "assignment , var_list":
				vars.add(getComponent("assignment"));
				propagate((NonTerminal)vars.get(0));
				vars.get(0).interpret();
				printIndent(",");
				temp = (NonTerminal)getComponent("var_list");
				propagate(temp);
				temp.interpret();
				obs = (ParseObject[])temp.getAsArray("vars");
				for(ParseObject po: obs) {
					vars.add(po);
				}
				put("vars",vars.toArray(new ParseObject[1]));
				break;
			case "varname":
				printIndent(((Token)getComponent("varname")).token());
				put("vars",new ParseObject[]{getComponent("varname")});
				break;
			case "assignment":
				ParseObject po = getComponent("assignment");
				propagate((NonTerminal)po);
				po.interpret();
				put("vars",new ParseObject[]{po});
				break;
			default:
		}
	}

	public void execute() {}
}