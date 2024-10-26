import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class imageDisplay {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java imageDisplay <image_file>");
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
            JFrame frame = new JFrame("Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            try {
                BufferedImage image = ImageIO.read(new File(fileName));
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel();
                label.setIcon(icon);

                frame.getContentPane().add(label, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (IOException e) {
                System.out.println("Error loading the image: " + e.getMessage());
            }
        });
    }
}
