package Application.com.jmc.backend.Class.Helpers;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    public static String apiKey = "AIzaSyC8qyQQ9Fs-rGJYe2CCaD3Evy5JziAR2tk";
    //public static String apiKey = "AIzaSyBRTivCWYJ_r_MA5Upaf7bsS0f1t3okcCo";
    //public static String apiKey = "AIzaSyDu3Jjv9le5WZ-YsHomozicJL8aw0jyY00";
    public static String getJsonFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                response.append(line);
                line = reader.readLine();
            }
            reader.close();
            connection.disconnect();
            return response.toString();
        }
        connection.disconnect();
        return "Error: Unable to get response from Google Books API. HTTP Code: " + responseCode;

    }

    /**
     * Tìm sách bằng tên.
     *
     * @param query tên sách
     * @return Mã Json của sách
     * @throws IOException ?
     */
    // Method to search for books//
    public static String searchMultiBooks(String query) throws IOException {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query.replace(" ", "+") + "&filter=ebooks&maxResults=10&languageRestrict=en&key=" + apiKey;
        return getJsonFromUrl(urlString);
    }

    /**
     * Tìm sách bằng thể loại sách
     * @param category thể loại
     * @return json  chứa dữ liệu các quuển
     * @throws IOException
     */
    public static String searchBooksByCategory(String category) throws IOException {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=subject:"
                + URLEncoder.encode(category, StandardCharsets.UTF_8)
                + "&filter=ebooks&maxResults=10&languageRestrict=en&key=" + apiKey;
        return getJsonFromUrl(urlString);
    }

    public static List<Book> searchBooksByCategory(String category, int size) {
        List<Book> return_book = new ArrayList<>();
        try {
            // Create an ObjectMapper
            String jsonResponse = searchBooksByCategory(category);
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode object
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Get the first book item (if exists)
            int querySize = rootNode.path("items").size();


            for (int i = 0; i < Math.min(size, querySize); i++) {
                JsonNode book = rootNode.path("items").get(i);
                return_book.add(getDocumentDetails(book.path("id").asText()));
            }
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        return return_book;
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

    public static List<String> getCategoryIdList(String category) {
        List<String> IdsList = new ArrayList<>();

        try {
            String jsonResponse = searchBooksByCategory(category);
            try {
                // Create an ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON string into a JsonNode object
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                // Get the first book item (if exists)
                int querySize = rootNode.path("items").size();

                for (int i = 0; i < Math.min(querySize, 8); i++) {
                    JsonNode book = rootNode.path("items").get(i);
                    IdsList.add(book.path("id").asText());
                }
            } catch (IOException e) {
                System.err.println("Error parsing JSON response: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Loi khi tim ID trong getICategorydList googleapi");
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
        return getJsonFromUrl(urlString);
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
            String description = formatDescription(book.path("volumeInfo").path("description").asText("Not available"));
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
                //Changed
                doc = new Book(book_id, title, author, pageCount, categories, publishedDate);

                doc.setAvailable(false);
                doc.setBorrowUserId(dataBaseInfos[1]);
                doc.setBorrowed_Date(dataBaseInfos[2]);
                doc.setRequired_date(dataBaseInfos[3]);
            } else {
                //Changed,
                doc = new Book(book_id, title, author, pageCount, categories, publishedDate);
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


    public static String formatDescription(String description) {
        if (description == null || description.isEmpty()) {
            return "Not available";
        }

        // Use Jsoup to parse and clean the HTML
        String cleanDescription = Jsoup.parse(description).text();

        // Convert any special HTML entities like &nbsp; to normal spaces
        cleanDescription = cleanDescription.replace("&nbsp;", " ");

        // Replace multiple spaces with a single space to ensure readability
        cleanDescription = cleanDescription.replaceAll("\\s{2,}", " ");

        // Optionally add line breaks after paragraphs to make it look better
        cleanDescription = cleanDescription.replaceAll("(<p>.*?</p>)", "$1\n");

        return cleanDescription;
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

            // Parse the JSON response to get the categories node
            JsonNode categoriesNode = objectMapper.readTree(jsonResponse)
                    .path("volumeInfo")
                    .path("categories");

            // Check if categories are present
            if (categoriesNode.isArray() && categoriesNode.size() > 0) {
                List<String> categoryList = new ArrayList<>();
                for (int i = 0; i < categoriesNode.size(); i++) {
                    String category = categoriesNode.get(i).asText();

                    // Split by "/" if there's more than one genre listed
                    String[] splitCategories = category.split("/");
                    for (String cat : splitCategories) {
                        categoryList.add(cat.trim());  // Trim to avoid leading/trailing spaces
                    }
                }
                return categoryList.toArray(new String[0]);
            } else {
                // If no categories, return a default message
                return new String[]{"No categories available"};
            }
        } catch (IOException e) {
            // Log error and rethrow or handle more appropriately
            System.err.println("Error parsing JSON response for book_id " + book_id + ": " + e.getMessage());
            throw new IOException("Error retrieving categories for book ID: " + book_id, e);
        }
    }

    public static Image getCategoryCover(String category) {
        Image image = new Image(GoogleBooksAPI.class.getResource("/Img/no_image.png").toExternalForm());
        try {
            String jsonResponse = searchBooksByCategory(category);
            try {
                // Create an ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON string into a JsonNode object
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                // Get the first book item (if exists)
                if (rootNode.has("items") && !rootNode.get("items").isEmpty()) {
                    JsonNode book = rootNode.get("items").get(0);
                    JsonNode imageLinks = book.path("volumeInfo").path("imageLinks").path("smallThumbnail");

                    if (!imageLinks.isMissingNode() && !imageLinks.asText().isEmpty()) {
                        image = new Image(imageLinks.asText());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error parsing JSON response: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Loi khi tim cover trong  googleapi");
        }
        return image;
    }
}
