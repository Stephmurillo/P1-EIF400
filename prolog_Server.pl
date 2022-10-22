child_of(joe, ralf).
child_of(mary, joe).
child_of(steve, joe).
descendent_of(X, Y):-
	child_of(X, Y).
descendent_of(X, Y):-
	child_of(Z, Y),
	descendent_of(X, Z).

    VERB: POST
Body 
    Expects:JSON {"a":Some_Number1, "b":Some_Number2 }
    
Returns: {"accepted":true, "answer":Some_Number1+Some_Number2}    if data ok
         {"accepted":false, "answer":0, "msg":some_error_message} othwerwise