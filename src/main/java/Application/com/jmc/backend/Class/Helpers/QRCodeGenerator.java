package Application.com.jmc.backend.Class.Helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeGenerator {
    public static void generateQR(String book_id) {
        try {
            String jsonResponse = GoogleBooksAPI.searchOneBook(book_id);
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the  book item (if exists)
            JsonNode book = objectMapper.readTree(jsonResponse);
            String link = book.path("volumeInfo").path("infoLink").asText();
            generateQRThroughLink(link, book_id);
            // Extract the book's title, authors, and description
        } catch (IOException e) {
            System.out.println("Loi khi Generate QR");
        }
    }

    public static void generateQRThroughLink(String link, String book_id) {
        int size = 300;  // Size of the QR code
        String filePath = "src/main/resources/QRCodes/" + book_id + "_qrCode";

        try {
            // Create a hint map with encoding options
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1); // Optional: Set margin

            // Generate the QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, size, size, hints);

            // Convert BitMatrix to BufferedImage
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    image.setRGB(i, j, bitMatrix.get(i, j) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // Write the image to file
            ImageIO.write(image, "JPG", new File(filePath));
            System.out.println("QR code generated at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
