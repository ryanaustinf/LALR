public class Type extends NonTerminal {
	public Type(String pattern) {
		super("type",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "rose":
				put("lineNo",((Token)getComponent("rose")).lineNo());
				put("type","void");
				break;
			case "martha":
				put("lineNo",((Token)getComponent("martha")).lineNo());
				put("type","int");
				break;
			case "donna":
				put("lineNo",((Token)getComponent("donna")).lineNo());
				put("type","float");
				break;
			case "amy":
				put("lineNo",((Token)getComponent("amy")).lineNo());
				put("type","char");
				break;
			case "clara":
				put("lineNo",((Token)getComponent("clara")).lineNo());
				put("type","string");
				break;
			case "jo":
				put("lineNo",((Token)getComponent("jo")).lineNo());
				put("type","boolean");
				break;
			case "sarahJane":
				put("lineNo",((Token)getComponent("sarahJane")).lineNo());
				put("type","array");
				break;
			default:
		}
	}

	public void execute() {}
}