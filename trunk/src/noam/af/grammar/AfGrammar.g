header {
	package noam.af.grammar;
	import noam.af.IAutomataBuilder;
	import noam.af.Terminal;
	import noam.af.Transition;
}

class AfParser extends Parser;

options 
{
	buildAST = false;
	defaultErrorHandler=false;	
}

automata[IAutomataBuilder b] : 
	LANGLE 
		LPAREN estados[b] RPAREN COMA
		LPAREN terminales[b] RPAREN COMA
		LPAREN transiciones[b] RPAREN COMA
		estadoInicial[b] COMA
		LPAREN estadosFinales[b] RPAREN
	RANGLE;

estados[IAutomataBuilder b]
	{ String e; } 
	: e=estado { b.addState(e); } (COMA e=estado { b.addState(e); })*	
	;
	
terminales[IAutomataBuilder b]
	{ String t; }
	: t=terminal { b.addTerminal(t); } (COMA t=terminal { b.addTerminal(t); })*
	| /* empty rule */
	;
	
transiciones[IAutomataBuilder b] 
	{ Transition t; }
	: t=transicion { b.addTransition(t); } (t=transicion { b.addTransition(t); })*
	| /* empty rule */
	;
	
estadoInicial[IAutomataBuilder b]
	{ String e = null; } 
	: e=estado { b.setInitialState(e); } ;

estadosFinales[IAutomataBuilder b] 
	{ String e = null; } 
	: e=estado { b.addFinalState(e); } (COMA e=estado { b.addFinalState(e); })*
	;
	
transicion returns [Transition r]
	{ String f = null, l = null, t = null;}
	: LPAREN f=estado COMA (l=terminal | LAMBDA {l = Terminal.LAMBDA;}) COMA t=estado RPAREN
	{ r = new Transition(f,l,t); }
	;

estado returns [String r]
	: m:MAYUS { r = m.getText(); }
	;
	
terminal returns [String r]
	: d:DIGIT { r = d.getText(); }
	| m:MINUS { r = m.getText(); }
	;	

class AfLexer extends Lexer;
options 
{
	k = 2;
}

LAMBDA : "LAMBDA";
LANGLE : '<';
RANGLE : '>';
LPAREN : '(';
RPAREN : ')';
COMA : ',';
MAYUS : 'A'..'Z';
MINUS : 'a'..'z';
DIGIT : '0'..'9';

// Whitespace -- ignored
WS	:	(	' ' 
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"	// Evil DOS
			|	'\r'	// Macintosh
			|	'\n'	// Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;

