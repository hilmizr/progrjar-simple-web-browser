package filedownloader;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {

    public static void main(String[] args) throws IOException {
        String fileUrl = "https://qcfi.in/wp-content/uploads/2022/04/1st-Announcement-ICQCC-2022-JAKARTA.pdf";
        String saveDir = "D:/kuliah";
        downloadFile(fileUrl, saveDir);
    }

    public static void downloadFile(String fileUrl, String saveDir) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String fileName = getFileName(conn);
        String saveFilePath = saveDir + "/" + fileName;
        FileOutputStream out = new FileOutputStream(saveFilePath);
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numRead);
        }
        out.close();
        in.close();
        System.out.println("File downloaded succesfully to: " + saveFilePath);
    }

    private static String getFileName(URLConnection conn) {
        String fileName = "";
        String disposition = conn.getHeaderField("Content-Disposition");
        if (disposition != null) {
            int index = disposition.indexOf("filename=");
            if (index > 0) {
                fileName = disposition.substring(index + 10, disposition.length() - 1);
            }
        } else {
            fileName = conn.getURL().getFile();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
        }
        return fileName;
    }
}
