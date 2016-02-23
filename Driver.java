public class Driver {
	public static void main(String[] args) {
		try {
			Interpreter interpreter = new Interpreter(null,null,"",args[0]);
			interpreter.interpret();
		} catch(Exception e) {
			if( e.getMessage() != null) {
				System.out.println(e.getMessage());
			} else {
				e.printStackTrace();
			}
		}
	}
}