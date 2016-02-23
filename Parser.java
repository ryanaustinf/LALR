import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Parser {
	private List<Token> tokens;
	private int currIndex;
	private NonTerminalFactory ntf;
	private State[] parseTable;

	public Parser(NonTerminalFactory ntf, State[] parseTable) {
		this.ntf = ntf;
		this.parseTable = parseTable;
	}

	public NonTerminal parse() {
		return null;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
		currIndex = 0;
	}
}