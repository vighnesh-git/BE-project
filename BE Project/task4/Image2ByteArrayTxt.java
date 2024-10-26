import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image2ByteArrayTxt {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Image2ByteArrayTxt <image_file>");
            System.exit(1);
        }

        String inputFileName = args[0];

        try {
            // Read the input image
            File inputFile = new File(inputFileName);
            BufferedImage image = ImageIO.read(inputFile);

            // Get width and height of the image
            int width = image.getWidth();
            int height = image.getHeight();

            // Create the output file name
            String outputFileName = inputFileName.replaceFirst("\\.(jpg|bmp|png|jpeg)$", ".txt");

            // Create a FileWriter for the output file
            FileWriter writer = new FileWriter(outputFileName);

            // Iterate through each pixel and write RGBA data to the output file
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    String hexPixel = String.format("%08X", pixel);
                    writer.write(hexPixel);
                    if (x < width - 1) {
                        writer.write(" ");
                    }
                }
                writer.write("\n");
            }

            // Close the FileWriter
            writer.close();

            System.out.println("Image dimensions: " + width + "x" + height);
            System.out.println("Output written to " + outputFileName);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
