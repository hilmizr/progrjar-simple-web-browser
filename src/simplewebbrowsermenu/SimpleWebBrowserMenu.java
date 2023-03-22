/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simplewebbrowsermenu;
import java.util.Scanner;

import filedownloader.FileDownloader;
import simplewebbrowsermenu.ShowClickableLinks;
import simplewebbrowsermenu.ShowClickableLinksHTTP;
import simplewebbrowsermenu.OpenHTTPWeb;
import simplewebbrowsermenu.OpenHTTPWebAuth;
import simplewebbrowsermenu.HTTPErrorMessages;
import simplewebbrowsermenu.ParallelDownload;
import simplewebbrowsermenu.ParsingHtml;
import simplewebbrowsermenu.ParsingHtmlHTTP;


/**
 *
 * @author Hp
 */
public class SimpleWebBrowserMenu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        do {
            System.out.println("[ SIMPLE WEB BROWSER : CLI ]");
            System.out.println("-------------------------------------------------");
            System.out.println("[1] Open a web page given a URI and shows the text");
            System.out.println("[2] Show a list of clickable links");
            System.out.println("[3] Download a file regardless of its size");
            System.out.println("[4] Download a file in parallel (OPTIONAL)");
            System.out.println("[5] Follow redirections");
            System.out.println("[6] Show respective HTTP error messages");
            System.out.println("[7] Open a web page that is protected by HTTP Basic Authentication");
            System.out.println("[8] Can access a web page that is protected behind a login page");
            System.out.println("[0] Exit the program");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    // Open a web page given a URI and shows the text
                    Scanner myObj1 = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("------------------------");
                    System.out.println("ENTER URL:");
                    String url = myObj1.nextLine();  // Read user input
                    
                    if (url.startsWith("https")) {
                        // Create an instance of the ShowClickableLinks class
                        // Call the method to show clickable links
                        ParsingHtml.HTMLParser(url);
                    } else if (url.startsWith("http")) {
                        // Create an instance of the ShowClickableLinksHTTP class
                        // Call the method to show clickable links
                        ParsingHtmlHTTP.HTMLParser(url);
                    } else {
                        System.out.println("Invalid URL");
                    }
                    break;
                case 2:
                    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("------------------------");
                    System.out.println("ENTER URL:");

                    String url = myObj.nextLine();  // Read user input

                    if (url.startsWith("https")) {
                        // Create an instance of the ShowClickableLinks class
                        // Call the method to show clickable links
                        ShowClickableLinks.showLinks(url);
                    } else if (url.startsWith("http")) {
                        // Create an instance of the ShowClickableLinksHTTP class
                        // Call the method to show clickable links
                        ShowClickableLinksHTTP.showLinks(url);
                    } else {
                        System.out.println("Invalid URL");
                    }
                    break;
                case 3:
                    FileDownloader();
                    // Download a file regardless of its size
                    break;
                case 4:
                    // Download a file in parallel (OPTIONAL)
                    ParallelDownload.ParallelDownloader();
                    break;
                case 5:
                    // Follow redirections
                    HttpRedirectExample();
                    break;
                case 6:
                    // Show respective HTTP error messages
                    Scanner myObj6 = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("------------------------");
                    System.out.println("ENTER URL:");

                    String url6 = myObj6.nextLine();  // Read user input
                       
                    HTTPErrorMessages.showError(url6);
                    break;
                case 7:
                    // Open a web page that is protected by HTTP Basic Authentication
                    OpenHTTPWebAuth.openWeb();
                    break;
                case 8:
                    // Can access a web page that is protected behind a login page
                    break;
                case 0:
                    // Exit the program
                    System.out.println("Goodbye! Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
            System.out.println();
            
        } while (choice != 0);
    }
    
}


