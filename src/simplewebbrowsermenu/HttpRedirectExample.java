
package simplewebbrowsermenu;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRedirectExample {

  public static void followRedirect(String url) {

    try {

//	String url = "https://its.id/m/coba-buat-coba";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        con.connect();
        String realURL = con.getHeaderField("Location"); 

// get the cookie if need, for login
		String cookies = con.getHeaderField("Set-Cookie");

		
System.out.println("Redirect to URL : " + realURL);

    } catch (Exception e) {
	e.printStackTrace();
    }

  }

}
