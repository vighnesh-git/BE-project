import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebImageDisplayKey {
    private List<URL> imageUrls = new ArrayList<>();
    private int currentIndex = 0;
    private JFrame frame;
    private JLabel imageLabel;

    public WebImageDisplayKey() {
        loadImagesFromFile();
        initializeUI();
    }

    private void loadImagesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("image_List.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                URL imageUrl = new URL(line);
                imageUrls.add(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        frame = new JFrame("Web Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        updateImage();

        frame.add(imageLabel, BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    navigateImage(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    navigateImage(1);
                }
            }
        });

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void updateImage() {
        if (imageUrls.isEmpty()) {
            imageLabel.setIcon(null);
            frame.setTitle("No Images Found");
            return;
        }

        try {
            URL imageUrl = imageUrls.get(currentIndex);
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            imageLabel.setIcon(imageIcon);
            frame.setTitle("Image " + (currentIndex + 1) + " of " + imageUrls.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateImage(int step) {
        currentIndex += step;
        if (currentIndex < 0) {
            currentIndex = imageUrls.size() - 1;
        } else if (currentIndex >= imageUrls.size()) {
            currentIndex = 0;
        }
        updateImage();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WebImageDisplayKey());
    }
}