grammar TamilLetterSet;


expression
   : term ((UNION | INTERSECTION | SUBTRACTION | MULTIPLICATION ) term)*
   ;



term
   : direct_part = SIMPLE_PART
   | simple_expression = closed_expression
   | negated_expression = NEGATION closed_expression
   | negated_part = NEGATION SIMPLE_PART
   ;

closed_expression
   : LPAREN expression RPAREN
   ;



UNION
  : UNION_SYMBOL | ALLATHU
  ;


ALLATHU
  : 'allathu' | 'அல்லது'
  ;


SIMPLE_PART
   :  (CONSTANT_SET | VARIABLE_SET)
   ;

VARIABLE_SET
   : LETTER_SYMBOL+
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



UNION_SYMBOL
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