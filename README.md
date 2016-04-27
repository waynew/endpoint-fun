# Endpoint Fun

This is a simple example of how an agnostic front-end that speaks JSON can
decouple you from the back-end implementation details. For an example of how
this works, pick a language and run the provided command. Then simply navigate
to [http://localhost:8765](http://localhost:8765/) and you should see the page
pop up. Go ahead and play with the form and click submit a few times. Then kill
the server on the command line with <kbd>Ctrl</kbd>+<kbd>C</kbd> and start a
different one. Don't reload the page - just play around with it some more. The
example page should be fully functional as long as it has a server to connect
to.

# Python 2 and 3

In the root directory, simply run:

    $ python python


# Java

In the root directory:

    $ javac -d java java/com/example/EndpointFun.java && java -cp java com.example.EndpointFun
