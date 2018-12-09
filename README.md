README
======

Link to GitHub repo: https://github.com/falcon-candidate/backend-challenge.

# Running

To start application, run

```
./mvnw clean package -DskipTests  && docker-compose up --build
```

This will skip all tests and directly run the application (you can run all tests by omitting the `-DskipTests` flag, or by running `./mvnw clean test`).

Once the application has started, you can:

## Post a palindrome query. 

The endpoint for this is `http://localhost:8080/palindrome` and only accepts JSON. 

If you are using Postman, make sure to post as raw text and select `application/json` as your content type. 

There is also a Python helper script (`src/test/resources/poster.py`). Run `python poster.py` to post an example valid query.

You can customize what you post via command line flags e.g.:

```
python poster.py --content foobar --timestamp "2018-10-09 00:12:12+0100"
```

where foobar is an example string to search palindromes in, and "2018-10-09 00:12:12+0100" is a valid timestamp.

I tested the Python script with Python 3.4.3.

__Note on Format__

The content field must be characters (lower or upper case) and at most 100 characters long. The size restriction is to simplify storing queries as well as ensure that the processing time does not increase too much. Handling arbitrary length queries is future work (I've added it as an issue on GitHub).

The timestamp field must adhere to the "yyyy-MM-dd HH:mm:ssXXXX" pattern (don't forget to include exactly four digits for the offset).

A valid query must include both the content and the timestamp properties.

Any JSON properties other than `content` and `timestamp` are simply ignored.

Any invalid query will be rejected.

For valid example query is:

```json
    {
      "content": "abrakadabra",
      "timestamp": "2018-10-09 00:12:12+0100"
    }
```

## Retrieve results

The endpoint for this is `http://localhost:8080/palindrome`. You can simply view this in a browser.

The result is an array of JSON objects which include the properties of the original query, along with the longest_palindrome_size property.

## Join as a Websocket client

To do this, open `http://localhost:8080/subscribe.html` in your browser.

This will connect a new Websocket client to `ws://localhost:8080/subscribe_ws`.

Every time a new valid POST request arrives the web page will display the JSON payload (note that it will not display queries that arrived before the client connected).

# Architecture/ Design Choices

`PalindromeRestController` implements the two main REST endpoints.

The `/palindrome` POST endpoint receives palindrome queries.

The `/palindrome` GET endpoint allows a client to retrieve all posted palindrome queries enriched with the max palindrome substring size.

`PalindromeRestController` uses `PalindromeBroadcastService` to broadcast to Websocket clients.

`PalindromeBroadcastService` is a simple `TextWebSocketHandler`. This is sufficient for broadcasting to clients which is why I did not use a more complex protocol like Stomp.

The class implementing palindrome max substring size calculation is `PalindromeSubstringSearchDynamic`.

This algorithm has O(n^{2}) complexity which is not as efficient as Manacher's algorithm [https://en.wikipedia.org/wiki/Longest_palindromic_substring] (linear time).

However, the algorithm I implemented is very intuitive and easy to maintain as opposed to Manacher's algorithm which is a good deal more complex.

Should performance become an issue (either due to increased load, or arbitrary length queries), I would opt for plugging in Manacher's algorithm instead.

Furthermore, caching palindrome queries and responses would be a nice way to improve performance.

# Limitations

The current implementation can benefit from several additional improvements. Unfortunately, I didn't get to implement these. I've documented several improvement ideas as issues on Github.

# Environment

I've tested the code with

- High Sierra 10.13.6
- java version "1.8.0_131"
- Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
- Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)
- Python 3.4.3
- Docker version 18.09.0, build 4d60db4
- Firefox Quantum 63.0.3
- Chrome 70.0.3538.110
