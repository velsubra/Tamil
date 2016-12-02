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
   :  (CONSTANT_SET | LETTER_SYMBOL+)
   ;


CONSTANT_SET
   : ('[' (LETTER_SYMBOL ','?)* ']')
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


TAMIL_LETTER_SYMBOL
   : ('\u0B80' .. '\u0BFF')
   ;

LETTER_SYMBOL
   : ('a' .. 'z') | ('A' .. 'Z') | TAMIL_LETTER_SYMBOL
   ;



WS
   : [ \r\n\t]+ -> channel (HIDDEN)
   ;