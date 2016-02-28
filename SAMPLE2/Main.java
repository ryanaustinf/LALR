public class Main extends NonTerminal {
	private MainFunc main;

	public Main(String pattern) {
		super("main",pattern);
	}

	public void interpret() throws Exception {
		// ParseObject funcs = getComponent("func_decs");
		// funcs.interpret();
		main = (MainFunc)getComponent("main_func");
		main.interpret();
	}

	public void execute() {
		main.execute();
	}
}