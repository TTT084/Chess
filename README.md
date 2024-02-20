# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)
[![Sequence Diagram]()](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i1AIjqqcnCShYggp0g0DbvfLD-zs7i9XnRBFjXdYiBfBQEAgySNYjyfZYalfTYHR+O4IlFEFwUhL1YQAMgVPlNnhREvWjR1Y0LC9cwNWJeAsMRiM-A0fxjE1HjAC0rW7R99ygsC31-NQEMBJCIShLsKxnBEICRJjfHwuDuPPRNqJTG9BMzGp93fXVTnkwtT1iRT0GUj47SrahKELRdG1bdtSh0tA9NtapezQftTHMKxbDMFBQ0ndhLGYGw-ACIJkAbf4tMSGQwWBdJgU3bcuF3ex0yEwyYhknM5OvW8p28uYrIOKj1OvWiCJNACgKDcSWOyhLM1gp0uB42I+JQqycNEmBxMk2qiNk-L81TcjKO66kNPCLj7mmSoIBocqhMq5ihJqwjwlFMLwUitM5szFqxP9VTLy-FRNJXMioAopLjIiUzYnMzA+wHZzh2mDQJzcGAAHE7UxXy5wCkJmEGEKEleiLN3YO1iiss6z3CPKhvShj3uqLhZvLardpI4bRv-QDgOmzNkZYhaMXqxqBKq0MRO2qNRq61Kesmfq0bS3qUEKqT7nhj65kJ+Clt48KUNBxGtrej6OsIlKPzpolBdUUE3EZqWWYiLSZa4OWeEhkzAowK62w7GAACJVYN+IEhKI27TFGQTZgABGJsAGYABY6gNvzAnZZ8UBNg2Ddd7QEFAINPeWH2-cNmWJTtWJfa6GBMnsxzBxcmxsAsKBsAQAw4DNAwEYML7-IXbW-uXIzV2SNIslyGXwbJtAO0ju0TxXCW1Nh5n-1z-P8aEmom+qXLBqvZnWdqrHStx9Be9R6neaBfnSY28ncMWMWibbvbSMWKPqgVjvvxGui-2ZFBWR7nefQH+11550VJWlWUCl32pr7DTVVdv6ToeH-abxlq2+8R7fmVkdABMhNYXRLrrCyJQI6W2tvEe2zsYCJzukOWwjgUBkggN4GAAApCAD4RbVFsAHIOxdfrBSOmuZ41cci13isvBupRTJwAgHeKAdRwEt3LpvdG6UABWRC0A9ysjUNhHDoD9wQUPWmB8DpHyKifbGZV64z3QNzbi88SbrRRivVqa9qb8KZpMS+e8YbAMUZjEqwF85WzmOArRdUdGLxgOA4Wn9jE-3kVYm8DNLF-zHrGWIwiHwX2fj6SRnCZHVCts4+qD8PRP0oC-CRJd2ExI8eqD+otvGBNIgE3+NFQHl2OqdU8WtfowJug5dBKczDAGcIgM+sBgDYEzoQT8s4i6mWoWU0K4VIqbiMJrHxksFE3hAFnPAXMgFBKUWzf8MyoCeyRgk+eMAVoRWBF6TEFM9lfzqiYxW5SBq+IWTYlZU80DrKOfVbZa0rL7Nwu1fJxT5JnPmTRRZ49pmtPzncuey0hm7NVsLQF9yTmTK+QUjSpSYhfMqVA6p11bpAA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
