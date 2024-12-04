package Application.com.jmc.backend.Class.Library.Helpers;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoogleBooksAPI {
    //public static String apiKey = "AIzaSyBuLI1dOZrUS6U78tj41hZtWr5aKo6u_j0";
   //public static String apiKey = "AIzaSyAp9uprFh6mvLaUE_nfKhwpj86PlDuyXT8";

    //public static String apiKey = "AIzaSyBuLI1dOZrUS6U78tj41hZtWr5aKo6u_j0";
    //public static String apiKey = "AIzaSyAp9uprFh6mvLaUE_nfKhwpj86PlDuyXT8";
   // public static String apiKey = "AIzaSyAjVQyWugF0uMrY9gB4otQoCPA9tkBsHIY";
    //public static String apiKey = "AIzaSyC8qyQQ9Fs-rGJYe2CCaD3Evy5JziAR2tk";
    //public static String apiKey = "AIzaSyBRTivCWYJ_r_MA5Upaf7bsS0f1t3okcCo";
    public static String apiKey = "AIzaSyDu3Jjv9le5WZ-YsHomozicJL8aw0jyY00";
    public static void main(String[] args) {
        // Replace YOUR_API_KEY with your actual API key
        while (true) {
            Library.init_Library();
            System.out.println("What kind of books are you looking for?");
            String query = new Scanner(System.in).next();  // Example search query
            System.out.println("Current user id?");
            String currentUserId = new Scanner(System.in).next();
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
                try {
                    getListOfBooksThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int pos = new Scanner(System.in).nextInt();
                System.out.println();
                System.out.println(Library.bookLists);
                Library.borrow_books(get_book_ID(pos, response), currentUserId);
            } catch (IOException e) {
                System.err.println("Error during API request: " + e.getMessage());
            }
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
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query.replace(" ", "+") + "&filter=ebooks&maxResults=10&languageRestrict=en&key=" + apiKey;

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

    public static String searchBooksByCategory(String category) throws IOException {
        // Construct the URL with the category query and API key
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=subject:"
                + URLEncoder.encode(category, StandardCharsets.UTF_8)
                + "&filter=ebooks&maxResults=10&languageRestrict=en&key=" + apiKey;

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

    public static List<Book> searchBooksByCategory(String category,int size) {
        List<Book> return_book = new ArrayList<>();
        try {
            // Create an ObjectMapper
            String jsonResponse = searchBooksByCategory(category);
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode object
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Get the first book item (if exists)
            int querySize = rootNode.path("items").size();

            for (int i = 0; i < Math.min(size,querySize); i++) {
                JsonNode book = rootNode.path("items").get(i);
                return_book.add(getDocumentDetails(book.path("id").asText()));
            }
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        return return_book;
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
                printBookDetails(book.path("id").asText());
            }
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
    }

    public static List<String> getIdList(String queueFor) {
        List<String> IdsList = new ArrayList<>();

        try {
            String jsonResponse = searchMultiBooks(queueFor);
            try {
                // Create an ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON string into a JsonNode object
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                // Get the first book item (if exists)
                int querySize = rootNode.path("items").size();

                for (int i = 0; i < querySize; i++) {
                    JsonNode book = rootNode.path("items").get(i);
                    IdsList.add(book.path("id").asText());
                }
            } catch (IOException e) {
                System.err.println("Error parsing JSON response: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Loi khi tim ID trong getIdList googleapi");
        }
        return IdsList;
    }


    /**
     * lay thong tin mot cuon sach.
     *
     * @param id id_sach.
     * @return tra lai du lieu string cuon sach hoac loi.
     * @throws IOException co loi khi ket noi hoac doc du lieu.
     */

    public static String searchOneBook(String id) throws IOException {
        String urlString = "https://www.googleapis.com/books/v1/volumes/" + id + "?key=" + apiKey;
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
     * @param id id duong duong dan den sach.
     * @throws IOException co loi khi ket noi hoac doc du lieu.
     */
    public static void printBookDetails(String id) throws IOException {
        String jsonResponse = searchOneBook(id);
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

    /**
     * Lấy ID của cuốn sách từ JSON phản hồi.
     *
     * @param pos          Vị trí của cuốn sách trong danh sách items.
     * @param jsonResponse Chuỗi JSON phản hồi từ API.
     * @return trả về ID của cuốn sách hoặc error.
     */
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

    /**
     * Lấy URL ảnh bìa của cuốn sách từ ID.
     *
     * @param id id của cuốn sách.
     * @return URL ảnh bìa hoặc thông báo lỗi nếu không tìm thấy.
     * @throws IOException in ra lỗi.
     */
    public static String get_Book_Image(String id) throws IOException {
        String jsonResponse = searchOneBook(id);
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the  book item (if exists)
            JsonNode book = objectMapper.readTree(jsonResponse);

            // Extract the book's title, authors, and description
            return book.path("volumeInfo").path("imageLinks").path("thumbnail").asText("Couldn't find image");
        } catch (IOException e) {
            return "Error parsing JSON response: " + e.getMessage();
        }
    }

    /**
     * Lấy thông tin của cuốn sách từ ID.
     *
     * @param id id của cuốn sách.
     * @return in ra thông tin của cuốn sách hoặc null.
     * @throws IOException in ra lỗi.
     */
    public static Book getDocumentDetails(String id) throws IOException {
        String jsonResponse = searchOneBook(id);
        Book doc;
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the  book item (if exists)
            JsonNode book = objectMapper.readTree(jsonResponse);

            // Extract the book's title, authors, and description
            String book_id = book.path("id").asText();
            String title = book.path("volumeInfo").path("title").asText("Not available");

            JsonNode authorsNode = book.path("volumeInfo").path("authors");
            String author = "Author not available";
            if (authorsNode.isArray() && !authorsNode.isEmpty()) {
                author = authorsNode.get(0).asText("Not available");
            }
            String description = book.path("volumeInfo").path("description").asText("Not available");
            String publisher = book.path("volumeInfo").path("publisher").asText("Not available");
            String publishedDate = book.path("volumeInfo").path("publishedDate").asText("Not available");
            String pageCount = book.path("volumeInfo").path("pageCount").asText("Not available");
            String[] categories;
            JsonNode categoriesNode = book.path("volumeInfo").path("categories");

            if (!categoriesNode.isEmpty()) {
                categories = new String[(int) categoriesNode.size()];
                for (int i = 0; i < categoriesNode.size(); i++) {
                    categories[i] = categoriesNode.get(i).asText();
                }
            } else {
                categories = new String[1];
                categories[0] = "Couldn't load categories";
            }

            String[] dataBaseInfos = Library.loadBookBorrowedId(book_id);
            if (dataBaseInfos.length > 1) {
                doc = new Book(book_id, title, author, categories, publishedDate, pageCount);
                doc.setAvailable(false);
                doc.setBorrow_user_id(dataBaseInfos[1]);
                doc.setBorrowed_Date(dataBaseInfos[2]);
                doc.setRequired_date(dataBaseInfos[3]);
            } else {
                doc = new Book(book_id, title, author, categories, publishedDate, pageCount);
                doc.setAvailable(true);
            }
            doc.setRating("Not rated");
            doc.setDescription((description));
            doc.setCategories(getCategories(id));
            // Print out the book's details

        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
        return doc;
    }

    /**
     * Tra lai categories, phuc vu cho viec recommend book va luu vao BookRecord.
     *
     * @param book_id book_id.
     * @return w.
     */
    public static String[] getCategories(String book_id) throws IOException {
        String jsonResponse = searchOneBook(book_id);
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Get categories
            JsonNode categoriesNode = objectMapper.readTree(jsonResponse).path("volumeInfo").path("categories");
            String[] categories;
            if (!categoriesNode.isEmpty()) {
                categories = new String[(int) categoriesNode.size()];
                for (int i = 0; i < categoriesNode.size(); i++) {
                    categories[i] = categoriesNode.get(i).asText();
                }
            } else {
                categories = new String[1];
                categories[0] = "Couldn't load categories";
            }
            return categories;
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }
}
