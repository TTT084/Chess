package server;

import spark.*;
import handlers.*;

public class Server {
    public static void main(String[] args){

    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (req, res) ->
                (new RegisterHandler()).handleRequest(req, res));
        Spark.post("/session", (req, res) ->
                (new LoginHandler()).handleRequest(req, res));


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
