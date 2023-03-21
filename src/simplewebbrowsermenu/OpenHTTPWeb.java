/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplewebbrowsermenu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author Hp
 */
public class OpenHTTPWeb {
    public static void openWeb() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("------------------------");
        System.out.println("ENTER URL:");
        String url = myObj.nextLine();  // Read user input
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 80 : uri.getPort();
            String path = uri.getRawPath().isEmpty() ? "/" : uri.getRawPath();

            // Create a new socket object
            Socket socket = new Socket(host, port);

            // Obtain the input and output streams
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            // Create the HTTP request
            String request = "GET " + path + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n\r\n";

            // Send the HTTP request
            bos.write(request.getBytes(StandardCharsets.UTF_8));
            bos.flush();
            
            // Read the HTTP response
            byte[] responseBytes = readResponse(bis);
            
            System.out.println("------------------------");
            System.out.println("PARSED HTML:");
            // Print the HTTP response
            String response = new String(responseBytes, StandardCharsets.UTF_8);
            System.out.println(response);

            // Close the streams and socket
            bis.close();
            bos.close();
            socket.close();

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readResponse(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = bis.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }

        return baos.toByteArray();
    }
}




    

