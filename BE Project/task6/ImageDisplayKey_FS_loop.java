import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageDisplayKey_FS_loop {
    private static JFrame frame;
    private static JLabel imageLabel;
    private static int currentIndex;
    private static List<ImageIcon> images;
    private static GraphicsDevice device;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ImageDisplayKey_FS_loop <image_directory>");
            return;
        }

        String imageDirectory = args[0];
        images = loadImagesFromDirectory(imageDirectory);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = ge.getDefaultScreenDevice();

        frame = new JFrame(device.getDefaultConfiguration());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true); // Remove window decorations
        device.setFullScreenWindow(frame);

        imageLabel = new JLabel();
        frame.add(imageLabel);

        currentIndex = 0;

        while (true) {
            displayImage(currentIndex);
            currentIndex = (currentIndex + 1) % images.size();

            try {
                Thread.sleep(3000); // Pause for 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void displayImage(int index) {
        if (index >= 0 && index < images.size()) {
            imageLabel.setIcon(images.get(index));
            frame.repaint();
        }
    }

    private static List<ImageIcon> loadImagesFromDirectory(String directoryPath) {
        List<ImageIcon> loadedImages = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] imageFiles = directory.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png"));

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    try {
                        BufferedImage img = ImageIO.read(imageFile);
                        ImageIcon icon = new ImageIcon(img);
                        loadedImages.add(icon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return loadedImages;
    }
}
