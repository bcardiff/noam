header {
	package noam.er.grammar;
	import noam.er.ER;
	import noam.er.ERChoice;
	import noam.er.ERConcat;
	import noam.er.ERClosure;
	import noam.er.EREmpty;
	import noam.er.ERTerminal;
	import noam.er.ERLambda;
}

class ERParser extends Parser;

options 
{
	buildAST = false;
	defaultErrorHandler=false;	
}

regexp returns [ER res]
	{ ER re = null; }
	: res= reL3 (OR re= regexp {res = new ERChoice(res, re); })? 
	;
	
reL3 returns [ER res]
	{ ER re= null; }
	: res= reL2 (CONCAT re= reL3 {res = new ERConcat(res, re); })? 
	;
	
reL2 returns [ER res]
	: res= reL1 (ASTERISK  {res = new ERClosure(res); } )?
	;
	
reL1 returns [ER res]
	{ String t; }
	: t= terminal {res = new ERTerminal(t); }
	| LPAREN res= regexp RPAREN 
	| EMPTY  {res = new EREmpty(); }
	;

	
terminal returns [String r]
	: d:DIGIT { r = d.getText(); }
	| m:MINUS { r = m.getText(); }
	;	

class ERLexer extends Lexer;
options 
{
	k = 2;
}

LAMBDA : "LAMBDA";
EMPTY : "VACIO";
LPAREN : '(';
RPAREN : ')';
OR : '|';
CONCAT : '.';
ASTERISK : '*';
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

