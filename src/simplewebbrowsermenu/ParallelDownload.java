import java.io.*;
import java.net.*;

public class ParallelDownloader {
    private static final int NUM_THREADS = 4;
    private static final String FILE_URL = "https://replit.com/@fHACKrenn/vainglorydraftsimulator.zip";
    private static final String FILE_NAME = "file.zip";
    private static final int CHUNK_SIZE = 1024;

    public static void main(String[] args) {
        try {
            // Create a new URL object to represent the file to download
            URL url = new URL(FILE_URL);

            // Create a new file object to represent the file to save
            File file = new File(FILE_NAME);

            // Create an array to hold the threads
            Thread[] threads = new Thread[NUM_THREADS];

            // Calculate the size of each chunk
            long fileSize = url.openConnection().getContentLength();
            long chunkSize = fileSize / NUM_THREADS;

            System.out.println("File Size : " + fileSize);
            System.out.println("Chunk Size : " + chunkSize);

            // Create a socket for each thread and start the download
            for (int i = 0; i < NUM_THREADS; i++) {
                // Calculate the start and end positions for this chunk
                long start = i * chunkSize;
                long end = (i == NUM_THREADS - 1) ? fileSize - 1 : start + chunkSize - 1;

                System.out.println("Chunk number " + i + " size : " + (end - start));

                // Create a new socket for this thread
                Socket socket = new Socket(url.getHost(), 443);

                // Send an HTTP request for this chunk of the file
                OutputStream os = socket.getOutputStream();
                String request = "GET " + url.getPath() + " HTTP/1.1\r\n" +
                        "Host: " + url.getHost() + "\r\n" +
                        "Range: bytes=" + start + "-" + end + "\r\n" +
                        "\r\n";
                os.write(request.getBytes());
                os.flush();

                // Create a new thread to handle the download for this chunk
                threads[i] = new DownloadThread(socket.getInputStream(), start, end, file);

                // Start the thread
                threads[i].start();
            }

            // Wait for all threads to finish
            for (int i = 0; i < NUM_THREADS; i++) {
                threads[i].join();
            }

            System.out.println("File downloaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DownloadThread extends Thread {
        private InputStream is;
        private long start;
        private long end;
        private File file;

        public DownloadThread(InputStream is, long start, long end, File file) {
            this.is = is;
            this.start = start;
            this.end = end;
            this.file = file;
        }

        @Override
        public void run() {
            try {
                // Create a random access file for this thread to write to
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.seek(start);

                // Read from the input stream and write to the file
                byte[] buffer = new byte[CHUNK_SIZE];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    raf.write(buffer, 0, bytesRead);
                }

                // Close the input stream and file
                is.close();
                raf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
