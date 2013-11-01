package com.dplay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Http {
    private static final String ENCODING = "utf-8";
    
    private static final int BUFFER_SIZE = 1024;

    private static final int TIMEOUT = 10000;
	
    public static String get(String baseUrl, Map<String, String> params)
            throws MalformedURLException, IOException {



        URL url = getURL(baseUrl + "?" + encodeParameters(params));
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        return request(connection);
    }
    
    private static URL getURL(String path) throws IOException {
        return new URL(path);
    }
    
    static String request(HttpURLConnection connection) throws IOException {
        InputStream inputStream = null;

        try {
            int status = -1;
            String content = null;

            // Open the connection, note that httpConnection could be either a HttpsURLConnection
            // or a HttpURLConnection
            connection.setReadTimeout(TIMEOUT);

            connection.connect();

            status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                content = readInputStream(inputStream);
            }
            else if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                content = readInputStream(connection.getErrorStream());
            }

            return content;
        } catch(IOException ioe) {
            throw new IOException(ioe);
        } finally {
            // Always disconnect and close the inputstream
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    static String encodeParameters(Map<String, String> parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> e : parameters.entrySet()) {
            try {
                String ekey = URLEncoder.encode(e.getKey(), ENCODING);
                String eval = URLEncoder.encode(e.getValue(), ENCODING);

                sb.append(ekey);
                sb.append("=");
                sb.append(eval);
                sb.append("&");
            } catch (UnsupportedEncodingException ee) {
            }
        }
        // remove last '&' (or '?' if no parameters)
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    
    static String readInputStream(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = null;
        try {
            // Create readers
            InputStreamReader charReader = new InputStreamReader(inputStream,ENCODING);
            bufferedReader = new BufferedReader(charReader, BUFFER_SIZE);
            // Read the stream private static final String GRAPH_PICTURE_URI = "/picture";

            String readLine;
            StringBuilder resultBuilder = new StringBuilder();
            readLine = bufferedReader.readLine();
            while(readLine != null) {
                resultBuilder.append(readLine);
                readLine = bufferedReader.readLine();
            }
            // Return
            return resultBuilder.toString();
        } finally {
            // Always close readers, charReader is closed by buffered reader.
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }
}
