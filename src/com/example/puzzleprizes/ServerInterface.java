package com.example.puzzleprizes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ServerInterface {

    public static String SERVER_URL ;

    /**
     * Gets the list of businesses from the server.
     * @param server_url is passed, defined in resources R.string.server_url
     * @return A string containing a semicolon and comma-delimited list of businesses.
     * The semicolons separate business classes, the commas the businesses
     * within each class.
     */
    public static String getBusinessList(String server_url) {
            /*
             * Let's construct the query string. It should be a key/value pair. In
             * this case, we just need to specify the command, so no additional
             * arguments are needed.
             * @param server_url is passed, defined in resources R.string.server_url
             */
    		SERVER_URL = server_url ;
            String data = "command=" + URLEncoder.encode("getCurrentOffers");
            return executeHttpRequest(data);
    }

    /**
     * Gets the sound that the animal makes from the server.
     * @param animal String specifying the animal.
     * @return A string representing the sound an animal makes.
     */
    public static String getPuzzleData(String business, String server_url) {
            /*
             * Let's construct the query string. We need the command getPuzzleData.
             * In addition, we need to set 
             * @param business to its name string.
             * @param server_url is passed, defined in resources R.string.server_url
             * @return the response string.
             */
    		SERVER_URL = server_url ;
            String data = "command=" + URLEncoder.encode("getPuzzleData");
            data += "&business=" + URLEncoder.encode(business);
            return executeHttpRequest(data);
    }

    /**
     * Helper function used to communicate with the server by sending/receiving
     * POST commands.
     * @param data String representing the command and (possibly) arguments.
     * @return String response from the server.
     */
    private static String executeHttpRequest(String data) {
            String result = "";
            try {
                    URL url = new URL(SERVER_URL);
                    URLConnection connection = url.openConnection();
                    
                    /*
                     * We need to make sure we specify that we want to provide input and
                     * get output from this connection. We also want to disable caching,
                     * so that we get the most up-to-date result. And, we need to 
                     * specify the correct content type for our data.
                     */
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Send the POST data
                    DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
                    dataOut.writeBytes(data);
                    dataOut.flush();
                    dataOut.close();

                    // get the response from the server and store it in result
                    DataInputStream dataIn = new DataInputStream(connection.getInputStream()); 
                    String inputLine;
                    while ((inputLine = dataIn.readLine()) != null) {
                            result += inputLine;
                    }
                    dataIn.close();
            } catch (IOException e) {
                    /*
                     * In case of an error, we're going to return a null String. This
                     * can be changed to a specific error message format if the client
                     * wants to do some error handling. For our simple app, we're just
                     * going to use the null to communicate a general error in
                     * retrieving the data.
                     */
                    e.printStackTrace();
                    result = null;
            }

            return result;
    }
}
