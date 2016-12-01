grammar TamilLetterSet;


expression
   : term ((UNION | INTERSECTION | SUBTRACTION | MULTIPLICATION) term)*
   ;



term
   : direct_variable = VARIABLE
   | simple_expression = closed_expression
   | negated_expression = NEGATION closed_expression
   | negated_variable = NEGATION VARIABLE
   ;

closed_expression
   : LPAREN expression RPAREN
   ;

VARIABLE
   :  LETTER_SYMBOL+
   ;


LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


UNION
   : '∪' | '+' | '|'
   ;


INTERSECTION
   : '∩' | '&'
   ;

SUBTRACTION
   : '-'
   ;

MULTIPLICATION
   : '*'
   ;

NEGATION
   : '!'
   ;




LETTER_SYMBOL
   : ('a' .. 'z') | ('A' .. 'Z') | ('\u0B80' .. '\u0BFF')
   ;



WS
   : [ \r\n\t]+ -> channel (HIDDEN)
   ;