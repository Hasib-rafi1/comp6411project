# comp6411project

For the final project, we will take a look at Erlang. Note that while Erlang is a
functional language, in the same style as Clojure, our focus here is the concurrency model provided
by Erlang. In particular, this assignment will require you to gain some familiarity with the concept
of message passing. In fact, Erlang does this more effectively than any other modern programming
language.

That said, there is a “twist” with the project. The course is called “Comparative Programming
Languages” for a reason. The purpose is not just to learn something about other languages but to
get a better sense of how problems can be solved differently, depending on the language used.
Often, a well-chosen language can make the job much easier and much more intuitive.

So, in addition to implementing the application in Erlang, you will implement the same application
using Java, arguably the most popular imperative language in use today. While Java will be far more
comfortable for many of you, it is a general purpose language that was not designed specifically for
concurrency (though it has always provided support for this).

In short, the project emphasizes the “comparative” element in the course’s title. Note, however,
that this does not mean that the project is massive in size. The application itself is not large, so
neither the Erlang program nor the Java program will require a huge amount of source code.
Instead, you will have to look at the problem differently in the two cases.


### TO run this :

For java you need java environment.
For erlang you have to setup erglang
Compile commands:
`erlc bank.erl`
`erlc money.erl`
`erlc customer.erl`
run command : `erl -noshell -s money start -s init stop`
