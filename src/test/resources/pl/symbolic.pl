derive(-X,Z,-DX) :- derive(X,Z,DX), !.
derive(X+Y,Z,DX+DY) :- derive(X,Z,DX), derive(Y,Z,DY), !.
derive(X-Y,Z,DX-DY) :- derive(X,Z,DX), derive(Y,Z,DY), !.
derive(X*Y,Z,X*DY+DX*Y) :- derive(X,Z,DX), derive(Y,Z,DY), !.
derive(X,_,0) :- number(X), !.
derive(X,X,1) :- !.
derive(X,Z,0) :- atom(X), X \== Z, !.
derive(X,Z,DA*DF) :- X =.. [F,A], atom(f), concat_atom([F,x],FP), derive(A,Z,DA), DF =.. [FP,A], !.
derive(X,Z,DA*FA+DB*FB) :- X =.. [F,A,B], atom(f), concat_atom([F,x],FX), concat_atom([F,y],FY), derive(A,Z,DA), derive(B,Z,DB), FA =.. [FX,A,B], FB =.. [FY,A,B], !.

d(X,Z,DSX) :- derive(X,Z,DX), s(DX,DSX).

s(X,Y) :- simplify(X,SX), !, ( X \== SX *-> s(SX,Y) ; Y = SX ).

simplify(0*_X,0) :- !.
simplify(_X*0,0) :- !.
simplify(1*X,X) :- !.
simplify(X*1,X) :- !.
simplify(0+X,X) :- !.
simplify(X+0,X) :- !.
% simplify(X+Y, Y+X) :- Y @< X. % vertraegt sich nicht mit der assoziativitätsregel.
% simplify(X*Y, Y*X) :- Y @< X.
simplify(X+(Y+Z), X+Y+Z) :- !.
simplify(X*(Y*Z), X*Y*Z) :- !.
simplify(F,FS) :- F =.. [Op,A|RA], maplist(simplify, [A|RA], SArgs), FS =.. [Op|SArgs], !.
simplify(X,X).

subst(A,A,V,V) :- !.
subst([],_,_,[]) :- !.
subst([H|T],A,V,[HS|TS]) :- !, subst(H,A,V,HS), subst(T,A,V,TS).
subst(X,A,V,R) :- X =.. [Op,H|T], subst([H|T],A,V,SArgs), R =.. [Op|SArgs], !.
subst(X,_,_,X).

sub(X,A,V,R) :- subst(X,A,V,RS), s(RS,R).

:- d( (a+t*ap)*p(x+t*xp) , t, Z), d(Z,t,ZZ), sub(Z,t,0,ZS), sub(ZZ,t,0,ZZS).
