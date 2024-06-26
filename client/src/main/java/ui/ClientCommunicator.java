package ui;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientCommunicator {
    public <T> T doPost(String urlString, Object request, Class<T> responseClass, String authToken) throws IOException {
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
        String[] split = reqData.split(":");
        if(split[0].contains("authToken")){
            String[] auth = split[1].split(",");
            connection.setRequestProperty("authorization",auth[0]);
        }
        if(authToken!=null){
            connection.setRequestProperty("authorization",authToken);
        }
        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");

        connection.connect();


        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            requestBody.write(reqData.getBytes());
        }
        return response(connection,responseClass);
    }
    private <T> T response(HttpURLConnection connection,Class<T> responseClass) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            try (InputStream respBody = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                //System.out.println(new Gson().fromJson(inputStreamReader,));
                T response = new Gson().fromJson(inputStreamReader,responseClass);
                return response;
            }
        }
        else {
            InputStream responseBody = connection.getErrorStream();
        }
        return null;
    }
    public <T> T doGet(String urlString,Object request, Class<T> responseClass,String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        if(authToken!=null){
            connection.setRequestProperty("authorization",authToken);
        }

        connection.connect();

        return response(connection,responseClass);
    }
    public void doPut(String urlString, Object request /*,Class<T> */,String authToken) throws IOException {
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
        if(authToken!=null){
            connection.setRequestProperty("authorization",authToken);
        }

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream
            requestBody.write(reqData.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream respBody = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            }
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            String msg = String.valueOf(connection.getResponseCode());
            throw new IOException(msg);
        }
        //return null;
    }
    public void doDelete(String urlString, Object request /*,Class<T> */,String authToken) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator();
            URL url = new URL(urlString);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("DELETE");

                if(authToken!=null){
                    connection.setRequestProperty("authorization",authToken);
                }
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Logout request successful");
                } else {
                    // Handle error response
                    System.out.println("DELETE request failed with response code: " + responseCode);
                }
        } catch (IOException e) {
                System.out.println("DELETE request failed");

            }
    }
}
