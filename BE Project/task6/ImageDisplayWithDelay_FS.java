import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageDisplayWithDelay_FS {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ImageDisplayWithDelay_FS <directory_path>");
            return;
        }

        String directoryPath = args[0];

        try {
            List<File> imageFiles = listImageFiles(directoryPath);
            for (File file : imageFiles) {
                displayImageInFullScreen(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<File> listImageFiles(String directoryPath) throws IOException {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (isImageFile(file)) {
                        imageFiles.add(file);
                    }
                }
            }
        }

        return imageFiles;
    }

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }

    private static void displayImageInFullScreen(File imageFile) throws IOException {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode displayMode = graphicsDevice.getDisplayMode();

        if (graphicsDevice.isFullScreenSupported()) {
            Frame frame = new Frame();
            frame.setUndecorated(true);
            frame.setResizable(false);

            try {
                frame.add(new ImageComponent(imageFile));
                graphicsDevice.setFullScreenWindow(frame);

                System.out.println("Displaying: " + imageFile.getName());
                System.out.println("Press Enter to continue...");
                new Scanner(System.in).nextLine();

                graphicsDevice.setFullScreenWindow(null);
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Full screen mode is not supported.");
        }
    }
}

class ImageComponent extends Component {
    private Image image;

    public ImageComponent(File imageFile) {
        try {
            image = Toolkit.getDefaultToolkit().getImage(imageFile.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
