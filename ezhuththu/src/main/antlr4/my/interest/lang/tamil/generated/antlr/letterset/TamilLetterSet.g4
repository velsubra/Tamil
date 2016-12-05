grammar TamilLetterSet;

//
//expression
//   : term (operation=(UNION | INTERSECTION | SUBTRACTION | MULTIPLICATION ) term)*
//   ;
//
//
//
//term
//   : direct_part = SIMPLE_PART
//   | simple_expression = closed_expression
//   | negated_expression = NEGATION closed_expression
//   | negated_part = NEGATION SIMPLE_PART
//   ;
//
//closed_expression
//   : LPAREN expression RPAREN
//   ;


expr: expr MULTIPLICATION expr                             #Multiplication
    | expr  closed_expr                                    #ImplicitMultiplication1
    | closed_expr  expr                                    #ImplicitMultiplication2
    | expr op=(UNION | INTERSECTION | SUBTRACTION) expr    #StandardOperation
    | CONSTANT_SET                                         #ConstantSet
    | NAMED_SET                                            #NamedSet
    | NEGATION NAMED_SET                                   #NegatedNamedSet
    | NEGATION CONSTANT_SET                                #NegatedConstantSet
    | NEGATION closed_expr                                 #NegatedClosedExpression
    | closed_expr                                          #ClosedExpression
    ;

closed_expr
   : LPAREN expr RPAREN
   ;


UNION
  : UNION_SYMBOL | ALLATHU
  ;


ALLATHU
  : 'allathu' | 'அல்லது'
  ;


//SIMPLE_PART
//   :  (CONSTANT_SET | NAMED_SET)
//   ;

NAMED_SET
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