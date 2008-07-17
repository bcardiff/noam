header {
	package noam.gr.grammar;
	import noam.gr.IGrammarBuilder;
	import noam.gr.Production;
}

class GrParser extends Parser;

options 
{
	buildAST = false;
	defaultErrorHandler=false;	
}

gramatica[IGrammarBuilder b] : 
	{ String s; }
	LANGLE 
		LPAREN noterminales[b] RPAREN COMA
		LPAREN terminales[b] RPAREN COMA
		LPAREN producciones[b] RPAREN COMA
		s=noterminal { b.setDistinguishedSymbol(s); }
	RANGLE
	;

noterminales[IGrammarBuilder b]
	{ String nt; }
	: nt=noterminal { b.addNonTerminal(nt); } (COMA nt=noterminal { b.addNonTerminal(nt); })*
	| /* empty rule */
	;

terminales[IGrammarBuilder b]
	{ String t; }
	: t=terminal { b.addTerminal(t); } (COMA t=terminal { b.addTerminal(t); })*
	| /* empty rule */
	;

producciones[IGrammarBuilder b]
	{ Production p; }
	: p=produccion { b.addProduction(p); } (p=produccion { b.addProduction(p); })*
	| /* empty rule */
	;

terminal returns [String r]
	: d:DIGIT { r = d.getText(); }
	| m:MINUS { r = m.getText(); }
	;	
	
noterminal returns [String r]
	: m:MAYUS { r = m.getText(); }
	;	

produccion returns [Production p]
	{ String t;}
	: LPAREN
		t=noterminal { p = new Production(t); } COMA
	    	(t=terminal { p.addRight(t); } 
				(COMA t=noterminal { p.addRight(t); } | /* empty rule */ )
			| LAMBDA)
	RPAREN
	;

class GrLexer extends Lexer;
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

