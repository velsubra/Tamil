lexer grammar TamilLexer;

TAMIL_LETTER_SYMBOL
   : ('\u0B80' .. '\u0BFF')
   ;

LETTER_SYMBOL
   : ('a' .. 'z') | ('A' .. 'Z') | TAMIL_LETTER_SYMBOL
   ;

LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;

POSITIVE_INTEGER
   : [\d]+
   ;

WS
   : [ \r\n\t]+ -> channel (HIDDEN)
   ;



