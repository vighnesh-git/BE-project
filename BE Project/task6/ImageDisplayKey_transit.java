import javax.swing.*;
import java.awt.*;
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

public class ImageDisplayKey_transit {
    private static JFrame frame;
    private static JLabel imageLabel;
    private static Timer timer;
    private static int currentIndex;
    private static List<ImageIcon> images;
    private static GraphicsDevice device;
    private static int transitionEffect;

    public static void main(String[] args) {
        if (args.length == 2) {
            String imageDirectory = args[0];
            transitionEffect = Integer.parseInt(args[1]);
            images = loadImagesFromDirectory(imageDirectory);
        } else {
            System.out.println("Usage: java ImageDisplayKey_transit <image_directory> <transition_effect>");
            System.out.println("Transition Effects:");
            System.out.println("1 - Wipe");
            System.out.println("2 - Flash");
            System.out.println("3 - Fall Over");
            System.out.println("4 - Curtains");
            System.out.println("5 - Peel Off");
            System.out.println("6 - Dissolve");
            System.out.println("7 - Checkerboard");
            System.out.println("8 - Blinds");
            return;
        }

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
            applyTransitionEffect(images.get(index).getImage());
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

    private static void applyTransitionEffect(Image image) {
        if (transitionEffect == 7) {
            // Apply Checkerboard transition
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            int squares = 8; // Number of squares per side
            int squareSize = Math.min(width, height) / squares;

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < squares; x++) {
                for (int y = 0; y < squares; y++) {
                    int xOffset = x * squareSize;
                    int yOffset = y * squareSize;
                    int subWidth = Math.min(squareSize, width - xOffset);
                    int subHeight = Math.min(squareSize, height - yOffset);
                    BufferedImage subImage = new BufferedImage(subWidth, subHeight, BufferedImage.TYPE_INT_ARGB);
                    subImage.getGraphics().drawImage(image, 0, 0, subWidth, subHeight, xOffset, yOffset, xOffset + subWidth, yOffset + subHeight, null);
                    bufferedImage.getGraphics().drawImage(subImage, xOffset, yOffset, null);
                }
            }

            ImageIcon icon = new ImageIcon(bufferedImage);
            imageLabel.setIcon(icon);
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
