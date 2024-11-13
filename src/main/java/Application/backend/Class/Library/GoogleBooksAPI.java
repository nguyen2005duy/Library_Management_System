package Application.backend.Class.Library;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GoogleBooksAPI {
    public static String apiKey = "AIzaSyBuLI1dOZrUS6U78tj41hZtWr5aKo6u_j0";

    public static void main(String[] args) {
        // Replace YOUR_API_KEY with your actual API key
        String query = new Scanner(System.in).next();  // Example search query

        try {
            String response = searchMultiBooks(query);
            Thread getListOfBooksThread = new Thread(() -> {
                // Call your function here
                getBookInfo(response);
                try {
                    // Wait for 1 second (1000 milliseconds) before running again
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread was interrupted");
                }

            });
            getListOfBooksThread.start();
            int pos = new Scanner(System.in).nextInt();
            Library.borrow_books(get_book_ID(pos, response), "2");
        } catch (IOException e) {
            System.err.println("Error during API request: " + e.getMessage());
        }
    }

    /**
     * Searching for book in generals.
     *
     * @param query the title name.
     * @return A string that lead to the book.
     * @throws IOException ?
     */
    // Method to search for books//
    public static String searchMultiBooks(String query) throws IOException {
        // Construct the URL with the search query and API key
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query.replace(" ", "+") + "&languageRestrict=en&key=" + apiKey;
//
        // Create a URL object from the URL string
        URL url = new URL(urlString);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // Set the request method to GET
        connection.setConnectTimeout(5000);  // Set timeout (optional)
        connection.setReadTimeout(5000);     // Set read timeout (optional)

        // Get the response code to check if the request was successful
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {  // HTTP 200 means success
            // Read the response using BufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();  // Return the response as a String
        } else {
            return "Error: Unable to get response from Google Books API. HTTP Code: " + responseCode;
        }


    }

    /**
     * lay thong tin nhieu quyen sach.
     *
     * @param jsonResponse Phản hồi JSON từ API.
     */
    public static void getBookInfo(String jsonResponse) {
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode object
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Get the first book item (if exists)
            int querySize = rootNode.path("items").size();

            for (int i = 0; i < querySize; i++) {
                JsonNode book = rootNode.path("items").get(i);
                printBookDetails(book.path("selfLink").asText());
            }
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
    }


    /**
     * lay thong tin mot cuon sach.
     *
     * @param selfLink duong dan den sach.
     * @return tra lai du lieu string cuon sach hoac loi.
     * @throws IOException co loi khi ket noi hoac doc du lieu.
     */

    public static String searchOneBook(String selfLink) throws IOException {
        String urlString = selfLink + "?key=" + apiKey;
        URL url = new URL(urlString);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // Set the request method to GET
        connection.setConnectTimeout(5000);  // Set timeout (optional)
        connection.setReadTimeout(5000);     // Set read timeout (optional)

        // Get the response code to check if the request was successful
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {  // HTTP 200 means success
            // Read the response using BufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();  // Return the response as a String
        } else {
            return "Error: Unable to get response from Google Books API. HTTP Code: " + responseCode;
        }
    }

    /**
     * in ra thong tin cua cuon sach.
     *
     * @param selfLink duong dan den sach.
     * @throws IOException co loi khi ket noi hoac doc du lieu.
     */
    public static void printBookDetails(String selfLink) throws IOException {
        String jsonResponse = searchOneBook(selfLink);
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Get the  book item (if exists)
            JsonNode book = objectMapper.readTree(jsonResponse);


            // Extract the book's title, authors, and description
            String book_id = book.path("id").asText();
            String title = book.path("volumeInfo").path("title").asText("Title not available");

            JsonNode authorsNode = book.path("volumeInfo").path("authors");
            String author = "Author not available";
            if (authorsNode.isArray() && !authorsNode.isEmpty()) {
                author = authorsNode.get(0).asText("Author not available");
            }

            String description = book.path("volumeInfo").path("description").asText("Description not available");
            String publisher = book.path("volumeInfo").path("publisher").asText();
            String publishedDate = book.path("volumeInfo").path("publishedDate").asText();
            String pageCount = book.path("volumeInfo").path("pageCount").asText();
            // Print out the book's details
            System.out.println("Book_id:" + book_id);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Description: " + description);
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
    }

    public static String get_book_ID(int pos, String jsonResponse) {
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode object
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Get the first book item (if exists)
            int querySize = rootNode.path("items").size();
            return rootNode.path("items").get(pos).path("id").asText();
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        return "error!";
    }
}
