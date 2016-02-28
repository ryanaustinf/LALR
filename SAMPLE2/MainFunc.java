public class MainFunc extends NonTerminal {
	private Code code;

	public MainFunc(String pattern) {
		super("main_func",pattern);
	}

	public void interpret() throws Exception {
		code = (Code)getComponent("code");
		code.interpret();
	}

	public void execute() {
		CodeLine[] codes = code.getCodes();
		boolean fail = false;
		for(CodeLine cl: codes) {
			switch( cl.getType() ) {
				case "return":
					break;
				case "break":
					System.out.println("Illegal DELETE at line " 
											+ cl.lineNo());
					fail = true;
					break;
				case "continue":
					System.out.println("Illegal REGENERATE at line " 
											+ cl.lineNo());
					fail = true;
					break;
				default:
					cl.execute();
			}
			if( fail ) {
				break;
			}
		}
	}
}