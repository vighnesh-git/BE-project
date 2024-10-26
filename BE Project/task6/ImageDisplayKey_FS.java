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

public class ImageDisplayKey_FS {
    private static JFrame frame;
    private static JLabel imageLabel;
    private static Timer timer;
    private static int currentIndex;
    private static List<ImageIcon> images;
    private static GraphicsDevice device;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ImageDisplayKey_FS <image_directory>");
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
        displayImage(currentIndex);

        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextImage();
            }
        });
        timer.start();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    nextImage();
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    previousImage();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void displayImage(int index) {
        if (index >= 0 && index < images.size()) {
            imageLabel.setIcon(images.get(index));
        }
    }

    private static void nextImage() {
        currentIndex = (currentIndex + 1) % images.size();
        displayImage(currentIndex);
    }

    private static void previousImage() {
        currentIndex = (currentIndex - 1 + images.size()) % images.size();
        displayImage(currentIndex);
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
