%% @author Md Hasibul Huq


-module(customer).
-export ([request/0]).


request() ->
	receive
		{H1,T1,Bankname} ->
			Index = rand:uniform(length(Bankname)),
			H2 = lists:nth(Index,Bankname),
			%io:format("~w",[T1]),
			if
				T1>50 ->
					Money = rand:uniform(50);

				true ->
					if T1>0 ->
							Money = rand:uniform(T1);
						true->
							Money = 0
					end				
			end,
			if
				T1 == 0 ->
					io:fwrite("");
				true->
					io:fwrite(""),
					main ! {H1,H2,T1,Money,Bankname}
			end,
			request()
	after 1000 ->
         exit
end.