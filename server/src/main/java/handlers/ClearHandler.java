package handlers;

import Services.ClearService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class ClearHandler {
    public Object handleRequest(Request req, Response res) {
        ClearService clearServ = new ClearService();
        Responses.Response response = new Responses.Response();
        clearServ.Clear();
        Gson json = new Gson();
    return json.toJson(response);
    }
}
