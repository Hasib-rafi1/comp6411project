%% @author Md Hasibul Huq


-module(money).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,customer/3]).

% shorten paths
%-import(bank, [b/1]).
%-import(customer, [c/2]).

start() ->
	{_,Customers} = file:consult("customers.txt"),
	{_,Customers_extra} = file:consult("customers.txt"),
	io:format("** Customers and loan objectives **~n"),
	list_printer(Customers),
	{_,Banks} = file:consult("banks.txt"),
	io:format("~n** Banks and financial resources **~n"),
	list_printer(Banks),
	create_thread(Banks),
	register(list_to_atom("main"),spawn(money,customer,[Banks,Customers,Customers_extra])),
	lists:map(fun(Line) -> main ! {Line} end, Customers),
	timer:sleep(1).

list_printer([])->[];

list_printer([H|T]) ->	
	{H1,T1} = H,
	io:format("~p:~p~n",[H1,T1]),
	list_printer(T).

create_thread([]) ->[];

create_thread([H|T]) ->
	{H1,T1} = H,
	register(H1,spawn(bank,process,[H1,T1])),
	create_thread(T).


print_rest([],[])->[];

print_rest([H|T],[H1|T1])->
	{H2,T2} = H,
	{H3,T3} = H1,
	if
		T3>0 ->
			T5 = T2-T3,
			io:format("~p was only able to borrow ~w  dollar(s). Boo Hoo! ~n",[H2,T5]);
		true->
			io:format("~p has reached the objective of ~w  dollar(s). Woo Hoo! ~n",[H2,T2])
	end,
	print_rest(T,T1).

customer(Banks,Customers,Customers_extra) ->
	Bankname = lists:map(fun ({V, _}) -> V end, Banks),
	receive
		
		{Line} ->
			{H1,T1} = Line,
			register(H1,spawn(customer,request,[])),
			H1 ! {H1,T1,Bankname},
			customer(Banks,Customers,Customers_extra);

		{H1,H2,T1,Money,Banknames} ->
			io:format("~p requests a loan of ~w   dollar(s) from ~p ~n",[H1,Money,H2]),
			H2 ! {H1,H2,T1,Money,Banknames},
			customer(Banks,Customers,Customers_extra);
		{Name, Amount} -> 
			io:format("~p  has  ~w  dollar(s) remaining. ~n",[Name,Amount]),
			customer(Banks,Customers,Customers_extra);
		{H1,H2,T1,Money,Banknames,Msg} ->
			if 
				Msg == "Accepted" ->
					io:format("~p approves a loan of ~w  dollars from ~p ~n",[H2,Money,H1]),
					T2 = T1-Money,
					ChangedList = lists:keyreplace(H1,1, Customers_extra, {H1 , T2}),
					H1 ! {H1,T2,Banknames},
					customer(Banks,Customers,ChangedList);
				true ->
					io:format("~p denies a loan of ~w  dollars from ~p ~n",[H2,Money,H1]),
					H1 ! {H1,T1,Banknames},
					customer(Banks,Customers,Customers_extra)
			end
	after 1000 ->
		print_rest(Customers,Customers_extra),
         io:fwrite("Finished"),
         exit
end.