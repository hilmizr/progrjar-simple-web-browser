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
import java.util.Scanner;

public class ShowClickableLinksHTTP {

    public static void showLinks(String url) {
        try {
            String host = getHost(url);
            
            Socket s = new Socket(host, 80);
            
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
            
            String request = "GET " + getPath(url) + " HTTP/1.1\r\n" +
                             "Host: " + host + "\r\n" +
                             "Connection: close\r\n\r\n";
            bos.write(request.getBytes());
            bos.flush();

            String html = readResponse(bis);
            
            System.out.println("------------------------");
            System.out.println("LIST OF CLICKABLE LINKS:");
            
            String[] links = html.split("<a href=\"");
            for (int i = 1; i < links.length; i++) {
                String link = links[i].split("\"")[0];
                System.out.println(link);
            }

            bis.close();
            bos.close();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(ShowClickableLinksHTTP.class.getName()).log(Level.SEVERE, null, ex);
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
}
