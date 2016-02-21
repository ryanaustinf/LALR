1. Make sure your CFG is complete.
2. Write your CFG in a file following the format
LHS,RHS1 RHS2 RHS3 
where LHS is the left hand side and RHSi is parts of the production
for all productions with the first production's LHS being the start symbol.
All left hand sides must be in all uppercase and all references to variables
must be consistent e.g.

	VAR1 -> VAR2
	VAR2 -> 0

is okay but

	VAR1 -> Var2
	VAR2 -> 0 

is not okay

Do not put unnecessary whitespace or tabs in the grammar.
Do not use the variable "START"

e.g.
For the grammar 

	G = ({S,A},{0,1},P,S)
	P given by
	S -> 0S1 | 0A1
	A -> 1A | epsilon

the file would look like

	//you could put comments at the start of a line like this

	//this is S
	S,0 S 1
	S,0 A 1

	//this is A
	A,1 A
	A,

LEXICAL ANALYSIS
3. Create a concrete subclass implementation of Tokenizer. Refer to Tokenizer's 
implementation

SYNTACTICAL ANALYSIS
4. run TableGenerator using "java -jar TableGenerator.jar <CFG filename>"
	-fix any errors that pop up. If SR or RR conflicts pop up, double check 
		your grammar

SYNTAX DIRECTED TRANSLATION
5. Implement all the NonTerminals as a subclass of NonTerminal. This means 
	implement execute() and interpret()
	Essentially, use getComponent() to get the necessary tokens or NonTerminals
	e.g.
	For the production
	IF -> if ( CONDITION ) STATEMENT
	interpret() would look like
	if(getProdString().equals("if ( CONDITION ) STATEMENT")) {
		ParseObject cond = getComponent("CONDITION");
		cond.interpret();
		condition = cond.getBool(); //condition would be a private attribute in
									//the class IF
		if(cond.getBool()) { //assume that cond has a method getBool() : boolean
							 //which returns whether the held condition is true
			statement = getComponent("STATEMENT"); //statement is also a private attribute
			statement.interpret();
		}
	}

	execute() would look like
	if( condition ) {
		statement.execute();
	}

	also, check the interpret() method of Token if there's any special type 
	tokens in your language

6. Instantiate Interpreter, set the Tokenizer using setTokenizer(Tokenizer), 
	and set code using setCode(filename : String).
7.  Run the Driver by java Driver <filename> and see your code run.