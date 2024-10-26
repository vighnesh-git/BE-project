import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventLogger {
    private static int currentIndex = 0;
    private static ArrayList<URL> imageUrls;
    private static ImageIcon currentImage;
    private static JLabel imageLabel;
    private static EventLoggerInner logger;

    private static double zoomFactor = 1.0; // Initial zoom factor

    public static void main(String[] args) {
        logger = new EventLoggerInner();

        imageUrls = readImageUrlsFromFile("image_List.txt");
        if (imageUrls.isEmpty()) {
            System.out.println("No image URLs found in the file.");
            return;
        }

        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        frame.getContentPane().add(imageLabel);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    showNextImage();
                    logger.logEvent("NextKey", imageUrls.get(currentIndex).toString());
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    showPreviousImage();
                    logger.logEvent("PrevKey", imageUrls.get(currentIndex).toString());
                } else if (keyCode == KeyEvent.VK_Z) {
                    zoomIn();
                    logger.logEvent("ZoomIn", imageUrls.get(currentIndex).toString());
                } else if (keyCode == KeyEvent.VK_X) {
                    zoomOut();
                    logger.logEvent("ZoomOut", imageUrls.get(currentIndex).toString());
                }
            }
        });

        frame.setSize(800, 600);
        frame.setVisible(true);

        showCurrentImage();
    }

    private static ArrayList<URL> readImageUrlsFromFile(String fileName) {
        ArrayList<URL> urls = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    URL url = new URL(line);
                    urls.add(url);
                } catch (Exception e) {
                    // Handle invalid URLs
                    e.printStackTrace();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    private static void showCurrentImage() {
        if (currentIndex >= 0 && currentIndex < imageUrls.size()) {
            try {
                currentImage = new ImageIcon(imageUrls.get(currentIndex));
                // Apply zoom factor to the image
                Image scaledImage = currentImage.getImage().getScaledInstance(
                    (int) (currentImage.getIconWidth() * zoomFactor),
                    (int) (currentImage.getIconHeight() * zoomFactor),
                    Image.SCALE_SMOOTH
                );
                currentImage = new ImageIcon(scaledImage);
                imageLabel.setIcon(currentImage);
                logger.logEvent("StartSet", imageUrls.get(currentIndex).toString());
                // Simulate TimeEvent
                logger.logEvent("TimeEvent", imageUrls.get(currentIndex).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void showNextImage() {
        if (currentIndex < imageUrls.size() - 1) {
            currentIndex++;
            showCurrentImage();
        }
    }

    private static void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            showCurrentImage();
        }
    }

    private static void zoomIn() {
        if (zoomFactor < 2.0) { // Limit zoom to a max of 2x
            zoomFactor += 0.1;
            showCurrentImage();
        }
    }

    private static void zoomOut() {
        if (zoomFactor > 0.1) { // Limit zoom to a min of 10%
            zoomFactor -= 0.1;
            showCurrentImage();
        }
    }

    private static class EventLoggerInner {
        private static final String LOG_FILE_NAME = "EventLog.txt";
        private static BufferedWriter writer;

        public EventLoggerInner() {
            try {
                writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void logEvent(String eventType, String imageFileName) {
            String timeStamp = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss").format(new Date());
            String logEntry = eventType + " - " + imageFileName + " " + timeStamp;

            try {
                writer.write(logEntry);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
