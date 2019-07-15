%% @author Md Hasibul Huq


-module(bank).
-export ([process/2]).
-import(money,[print_rest_bank/2]).

process(Name,Amount) ->
	receive
		{H1,H2,T1,Money,Banknames} ->
			if
				Amount>Money ->
					Restmoney = Amount - Money,
					main ! {H1,Name,T1,Money,Banknames,"Accepted"};
				true ->
					Restmoney = Amount,
					lists:delete(H2, Banknames),
					main ! {H1,Name,T1,Money,Banknames,"Rejected"}
			end,
			process(Name,Restmoney)
	after 800 ->
           	if
           		Amount>0 ->
           			main ! {Name,Amount},
           			exit(whereis(Name), ok)
           			%io:format("~p  has  ~w  dollar(s) remaining. ~n",[Name,Amount]),
           			
           	end
end.