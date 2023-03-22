package simplewebbrowsermenu;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ParsingHTML {
    public static void HTMLParser(String url) {
        try {
            // Extract the hostname and path from the URL
            URL u = new URL(url);
            String hostname = u.getHost();
            String path = u.getPath();
            if (path.isEmpty()) {
                path = "/";
            }

            // Open a socket to the web server
            Socket socket = new Socket(hostname, 443);

            // Send an HTTP request for the web page
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("GET / HTTP/1.1");
            out.println("Host: " + hostname);
            out.println("");
            out.flush();

            // Read the response from the web server
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                // Process the HTML content here
                System.out.println(line);
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
