import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageDisplayApp {
    public static void main(String[] args) {
        BufferedImage defaultImage = loadDefaultImage();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        try {
            BufferedImage image = loadImage("my_picture.jpg"); // Replace with your image filename
            ImagePanel imagePanel = new ImagePanel(image, defaultImage, screenSize);
            frame.getContentPane().add(imagePanel);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            ImagePanel imagePanel = new ImagePanel(defaultImage, defaultImage, screenSize);
            frame.getContentPane().add(imagePanel);
        }
        frame.setVisible(true);
    }

    private static BufferedImage loadImage(String filename) throws Exception {
        return ImageIO.read(ImageDisplayApp.class.getResource(filename));
    }

    private static BufferedImage loadDefaultImage() {
        try {
            return loadImage("system_error.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }
}

class ImagePanel extends JPanel {
    private BufferedImage image;
    private BufferedImage defaultImage;
    private Dimension screenSize;

    public ImagePanel(BufferedImage image, BufferedImage defaultImage, Dimension screenSize) {
        this.image = image;
        this.defaultImage = defaultImage;
        this.screenSize = screenSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            if (imageWidth < screenWidth && imageHeight < screenHeight) {
                int x = (screenWidth - imageWidth) / 2;
                int y = (screenHeight - imageHeight) / 2;

                // Fill the remaining area with black
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, x, screenHeight);
                g.fillRect(x + imageWidth, 0, screenWidth - x - imageWidth, screenHeight);
                g.fillRect(0, 0, screenWidth, y);
                g.fillRect(0, y + imageHeight, screenWidth, screenHeight - y - imageHeight);

                g.drawImage(image, x, y, this);
            } else {
                g.drawImage(image, 0, 0, screenWidth, screenHeight, this);
            }
        } else {
            g.drawImage(defaultImage, 0, 0, screenSize.width, screenSize.height, this);
        }
    }
}
