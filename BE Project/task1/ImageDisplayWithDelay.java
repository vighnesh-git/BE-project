import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.ArrayList;
import java.util.List;

public class ImageDisplayWithDelay {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ImageDisplayWithDelay <directoryPath> <delayInSeconds>");
            return;
        }

        String directoryPath = args[0];
        int delayInSeconds = Integer.parseInt(args[1]);

        try {
            List<File> imageFiles = listImageFiles(directoryPath);
            for (File file : imageFiles) {
                System.out.println("Opening file: " + file.getName());
                displayImageWithDelay(file, delayInSeconds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Program #1: List image files in a directory
    public static List<File> listImageFiles(String directoryPath) throws IOException {
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

    public static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }

    // Program #2: Display an image with a delay
    public static void displayImageWithDelay(File imageFile, int delayInSeconds) throws IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                Desktop.getDesktop().open(imageFile);
                Thread.sleep(delayInSeconds * 1000); // Delay for specified seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Desktop is not supported, or OPEN action is not supported.");
        }
    }
}
