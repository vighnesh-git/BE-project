import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class WebImageDisplayKey_FS_loop {
    private List<URL> imageUrls = new ArrayList<>();
    private int currentIndex = 0;
    private JFrame frame;
    private JLabel imageLabel;

    public WebImageDisplayKey_FS_loop() {
        loadImagesFromFile();
        initializeUI();
        startImageLoop();
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);

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

    private void startImageLoop() {
        Timer timer = new Timer(5000, new ActionListener() { // Change the delay (in milliseconds) between images here
            public void actionPerformed(ActionEvent e) {
                navigateImage(1);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WebImageDisplayKey_FS_loop());
    }
}
