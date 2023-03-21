/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplewebbrowsermenu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OpenHTTPWebAuth {
    public static void openWeb() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("------------------------");
            System.out.println("ENTER URL:");
            String url = reader.readLine();
            System.out.println("------------------------");
            System.out.println("ENTER USERNAME:");
            String username = reader.readLine();
            System.out.println("------------------------");
            System.out.println("ENTER PASSWORD:");
            String password = reader.readLine();

            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 80 : uri.getPort();
            String path = uri.getRawPath().isEmpty() ? "/" : uri.getRawPath();

            // Create a new socket object
            Socket socket = new Socket(host, port);

            // Obtain the input and output streams
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create the HTTP request
            String authString = username + ":" + password;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            String request = "GET " + path + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Authorization: Basic " + encodedAuthString + "\r\n" +
                    "Connection: close\r\n\r\n";

            // Send the HTTP request
            out.write(request);
            out.flush();

            // Read the HTTP response
            String inputLine;
            boolean isBody = false;
            StringBuilder body = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                if (isBody) {
                    body.append(inputLine);
                    body.append("\n");
                } else if (inputLine.equals("")) {
                    isBody = true;
                }
            }

            // Print the HTML of the web page
            System.out.println("------------------------");
            System.out.println("PARSED HTML:");
            System.out.println(body.toString());

            // Close the streams and socket
            out.close();
            in.close();
            socket.close();

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}


