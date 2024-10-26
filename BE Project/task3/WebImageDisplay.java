import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WebImageDisplay {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide image URLs as command line arguments.");
            return;
        }

        // Create the "local" directory if it doesn't exist
        Path localDirectory = Path.of("local");
        if (!Files.exists(localDirectory)) {
            try {
                Files.createDirectories(localDirectory);
            } catch (IOException e) {
                System.out.println("Failed to create 'local' directory.");
                return;
            }
        }

        for (String url : args) {
            try {
                URI imageUrl = new URI(url);
                String fileName = imageUrl.getPath().substring(imageUrl.getPath().lastIndexOf('/') + 1);
                Path localFilePath = localDirectory.resolve(fileName);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(imageUrl)
                        .build();

                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                if (response.statusCode() == 200) {
                    try (InputStream inputStream = response.body()) {
                        Files.copy(inputStream, localFilePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    System.out.println("Failed to download image from URL: " + url + ". HTTP Status Code: " + response.statusCode());
                    continue;
                }

                Image image = ImageIO.read(localFilePath.toFile());

                if (image != null) {
                    ImageIcon imageIcon = new ImageIcon(image);
                    JFrame frame = new JFrame();
                    frame.setLayout(new FlowLayout());
                    frame.setSize(800, 600);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JLabel label = new JLabel(imageIcon);
                    frame.add(label);

                    frame.setVisible(true);
                } else {
                    System.out.println("Failed to load image from local directory: " + localFilePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to process image from URL: " + url);
            }
        }
    }
}
