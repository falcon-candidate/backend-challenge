README
======

# Running

To start application, run `./mvnw clean package -DskipTests  && docker-compose up --build`.

This will skip all tests and directly run the application (you can run all tests by omitting the 
-DskipTests flag, or by running `./mvnw clean test`).

Once the application has started, you can:

## Post a palindrome query. 

The endpoint for this is `http://localhost:8080/palindrome` and only accepts JSON. 

If you are using Postman, make sure to post as raw text and select `application/json` as your content type. 

There is also a Python helper script (`src/test/resources/poster.py`). Run `python poster.py` to post
an example valid query.

You can customize what you post with the script by running `python poster.py -c foobar -t "2018-10-09 00:12:12+0100"`,
where foobar is an example string to search palindromes in, and "2018-10-09 00:12:12+0100" is a valid timestamp.

I tested the Python script with Python Python 3.4.3.

__Note on Format__

The content field must be characters (lower or upper case) and at most 100 characters long. The size restriction
is to simplify storing queries as well as ensure that the processing time does not increase too much. Handling 
arbitrary length queries is future work (I've added it as an issue on GitHub).

The timestamp field must adhere to the "yyyy-MM-dd HH:mm:ssXXXX" pattern (don't forget to include exactly 
four digits for the offset).

A valid query must include both the content and the timestamp properties.

Any JSON properties other than `content` and `timestamp` are simply ignored.

Any invalid query will be rejected.

## Retrieve results

The endpoint for this is `http://localhost:8080/palindrome`. You can simply view this in a browser.

The result is an array of JSON objects which include the properties of the original query, along with the longest_palindrome_size property.

## Join as a Websocket client

To do this, open `http://localhost:8080/subscribe.html` in your browser.

This will connect a new Websocket client to (`ws://localhost:8080/subscribe_ws`).

Every time a new valid POST request arrives the web page will display the JSON payload (note that it will not 
display queries that arrived before the client connected).

# Architecture/ Design Choices

TODO

# Limitations

The current implementation can benefit from several additional improvements. I've included these as issues in the Github repo.
