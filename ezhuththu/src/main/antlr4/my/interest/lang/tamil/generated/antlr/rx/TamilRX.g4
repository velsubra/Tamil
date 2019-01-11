grammar TamilRX;


rx_list : rx+                                                                    #Expression
        ;

rx :   simple_rx ONE_OR_MORE                                                     #OneOrMore
     | simple_rx ZERO_OR_MORE                                                    #ZeroOrMore
     | simple_rx '{' POSITIVE_INTEGER '}'                                        #FixedCount
     | or_rx                                                                     #ORExpression
     | and_rx                                                                    #ANDExpression
     | LPAREN  QUESTION_MARK  EXCLAMATION_MARK lahead=rx_list RPAREN             #NegativeLookAhead
     | LPAREN  QUESTION_MARK  EQUAL_MARK lahead=rx_list RPAREN                   #PositiveLookAhead
     | grouped_rx                                                                #GroupedExpression
     | simple_rx                                                                 #SimpleExpression



//     | LPAREN  QUESTION_MARK  LESS_THAN_MARK EQUAL_MARK lbehind=simple_rx RPAREN  ex=rx_list        #PositiveLookBehind
//     | LPAREN  QUESTION_MARK  LESS_THAN_MARK EXCLAMATION_MARK lbehind=rx_list RPAREN  ex=rx_list    #NegativeLookBehind

     ;

grouped_rx
    : LPAREN QUESTION_MARK LESS_THAN_MARK groupname=GROUP_NAME GREATER_THAN_MARK ex=simple_rx RPAREN
    ;


simple_rx   : TAMIL_RX                 #SingleExpression
            | closed_rx                #ClosedExpression
            ;

or_rx    : simple_rx  ( OR simple_rx) +
         ;

and_rx   : simple_rx  ( AND simple_rx) +
         ;


closed_rx
   : LPAREN ex=rx_list RPAREN
   ;

control_rx : WHITE_SPACE
           | WORD_BOUNDARY
           | NON_WORD_BOUNDARY
           ;




TAMIL_LETTER_SYMBOL
   : ('\u0B80' .. '\u0BFF')
   ;

WHITE_SPACE
  : '\\s'
  ;

WORD_BOUNDARY
  : '\\b'
  ;

NON_WORD_BOUNDARY
  : '\\B'
  ;

ONE_OR_MORE
   : '+';


ZERO_OR_MORE
   : '*';


OR
   : '|';

AND
    : '&';



LPAREN
   : '('
   ;

QUESTION_MARK
   : '?'
   ;

EQUAL_MARK
   : '='
   ;

LESS_THAN_MARK
   : '<'
   ;

GREATER_THAN_MARK
    : '>'
    ;

EXCLAMATION_MARK
   : '!'
   ;

RPAREN
   : ')'
   ;

 LETTER_SYMBOL
      : ('a' .. 'z') | ('A' .. 'Z') | TAMIL_LETTER_SYMBOL | '_'
      ;

GROUP_NAME
   : LETTER_SYMBOL+
   ;

POSITIVE_INTEGER
   : [0-9][0-9]*
   ;


TAMIL_RX_START
  : '${';

TAMIL_RX
   : (TAMIL_RX_START ex=EXPRESSION '}');


EXPRESSION
   : .+?;



WS
   : [ \r\n\t]+ -> channel (HIDDEN)
   ;