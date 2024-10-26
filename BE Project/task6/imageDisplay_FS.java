import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File; 
import java.io.IOException;

public class imageDisplay_FS {
    private static JFrame frame;
    private static GraphicsDevice graphicsDevice;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java imageDisplay_FS <image_file>");
            return;
        }

        String fileName = args[0];

        if (!fileName.toLowerCase().endsWith(".jpg") &&
            !fileName.toLowerCase().endsWith(".jpeg") &&
            !fileName.toLowerCase().endsWith(".png") &&
            !fileName.toLowerCase().endsWith(".bmp")) {
            System.out.println("Unsupported image format. Supported formats are .jpg, .jpeg, .png, and .bmp.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            try {
                BufferedImage image = ImageIO.read(new File(fileName));
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel();
                label.setIcon(icon);

                frame.setUndecorated(true);
                frame.add(label);

                frame.setResizable(false);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                frame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            closeFullScreen();
                        }
                    }
                });

                frame.setVisible(true);
                graphicsDevice.setFullScreenWindow(frame);
            } catch (IOException e) {
                System.out.println("Error loading the image: " + e.getMessage());
            }
        });
    }

    private static void closeFullScreen() {
        if (graphicsDevice.isFullScreenSupported()) {
            graphicsDevice.setFullScreenWindow(null);
        }
        frame.dispose();
        System.exit(0);
    }
}
