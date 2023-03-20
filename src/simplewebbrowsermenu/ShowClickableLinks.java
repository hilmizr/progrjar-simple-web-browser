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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ShowClickableLinks {
    public static void showLinks(String url) {
        try {
            String host = getHost(url);
            int port = getPort(url);

            // Create a new socket object
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket s = (SSLSocket) factory.createSocket(host, port);

            // Obtain the input and output streams
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());

            String request = "GET " + getPath(url) + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n\r\n";
            bos.write(request.getBytes());
            bos.flush();

            // Read the response from the server
            String html = readResponse(bis);

            System.out.println("------------------------");
            System.out.println("LIST OF CLICKABLE LINKS:");

            // Extract all the links from the HTML content
            String[] links = html.split("<a href=\"");
            for (int i = 1; i < links.length; i++) {
                String link = links[i].split("\"")[0];
                System.out.println(link);
            }

            bis.close();
            bos.close();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(ShowClickableLinks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String readResponse(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        return new String(bos.toByteArray());
    }

    private static String getHost(String url) {
        String host = url.replaceAll("^https?://", "");
        return host.split("/")[0];
    }

    private static String getPath(String url) {
        String path = url.replaceAll("^https?://[^/]+/", "/");
        if (path.isEmpty()) {
            path = "/";
        }
        return path;
    }

    private static int getPort(String url) {
        String[] parts = url.split(":");
        if (parts.length > 2 && parts[0].equals("https")) {
            return Integer.parseInt(parts[2].split("/")[0]);
        } else {
            return 443; // Default HTTPS port
        }
    }
}

