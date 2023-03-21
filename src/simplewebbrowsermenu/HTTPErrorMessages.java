/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package simplewebbrowsermenu;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class HTTPErrorMessages {

    /**
     * @param args the command line arguments
     */
    public static void showError(String url6) {
        // TODO code application logic here
        try{
            String host = getHost(url6);
            Socket s = new Socket(host, 80);

            OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), "UTF8");
            out.write("GET " + getPath(url6) + " HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("Connection: close\r\n");
            out.write("\r\n");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String line = null;
            boolean headersRead = false;
            while ((line = in.readLine()) != null) {
                if (!headersRead) {
                    headersRead = true;
                    String[] parts = line.split(" ");
                    int statusCode = Integer.parseInt(parts[1]);
                    if (statusCode >= 400 && statusCode <= 499) {
                        System.out.println("------------------------");
                        System.out.println("ERROR MESSAGE:");
                        System.out.println("HTTP " + statusCode);
                        break;
                    }
                } else {
                    System.out.println(line);
                }
            }
            
            out.close();
            in.close();
            s.close();
        }catch (IOException ex) {
            Logger.getLogger(HTTPErrorMessages.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private static String getHost(String url6) {
        String host = url6.replaceAll("^https?://", "");
        return host.split("/")[0];
    }
    
    private static String getPath(String url6) {
        String path = url6.replaceAll("^https?://[^/]+/", "/");
        if (path.isEmpty()) {
            path = "/";
        }
        return path;
    }
}
