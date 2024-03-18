package ui;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ClientCommunicator {
    public <T> T doPost(String urlString, Object request, Class<T> responseClass) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        String reqData = "";
        if(request!=null){
            connection.addRequestProperty("authorization", "application/json");
            reqData = new Gson().toJson(request);
        }

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");

        connection.connect();


        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            requestBody.write(reqData.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //String auth = connection.getHeaderField("authorization");

            //InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            try (InputStream respBody = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                //System.out.println(new Gson().fromJson(inputStreamReader,));
                T response = new Gson().fromJson(inputStreamReader,responseClass);
                return response;
            }
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            //InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
        }
        return null;
    }
    public void doGet(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        // connection.addRequestProperty("Authorization", "fjaklc8sdfjklakl");

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read and process response body from InputStream ...
        } else {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
        }
    }
    public void doPut(String urlString, Object request /*,Class<T> */) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        String reqData = "";
        if(request!=null){
            connection.addRequestProperty("authorization", "application/json");
            reqData = new Gson().toJson(request);
        }

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");

        connection.connect();


        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            requestBody.write(reqData.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //String auth = connection.getHeaderField("authorization");

            //InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            try (InputStream respBody = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                //System.out.println(new Gson().fromJson(inputStreamReader,));
                //T response = new Gson().fromJson(inputStreamReader,responseClass);
                //return response;
            }
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            //InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
        }
        //return null;
    }
}
