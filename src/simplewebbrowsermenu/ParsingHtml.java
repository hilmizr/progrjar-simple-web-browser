import java.net.*;
import java.io.*;

public class HTMLParser {
    public static void main(String[] args) {
        try {
            // Open a socket to the web server
            Socket socket = new Socket("www.google.com", 80);

            // Send an HTTP request for the web page
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("GET / HTTP/1.1");
            out.println("Host: www.google.com");
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
