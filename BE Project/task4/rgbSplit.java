import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class rgbSplit {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java rgbSplit <imageFileName>");
            return;
        }

        String imageFileName = args[0];

        try {
            BufferedImage image = ImageIO.read(new File(imageFileName));
            int width = image.getWidth();
            int height = image.getHeight();

            // Create files for red, green, and blue pixel values
            File redFile = new File(imageFileName.replace(".jpg", "_r_" + width + "x" + height + ".txt"));
            File greenFile = new File(imageFileName.replace(".jpg", "_g_" + width + "x" + height + ".txt"));
            File blueFile = new File(imageFileName.replace(".jpg", "_b_" + width + "x" + height + ".txt"));

            FileWriter redWriter = new FileWriter(redFile);
            FileWriter greenWriter = new FileWriter(greenFile);
            FileWriter blueWriter = new FileWriter(blueFile);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Write red, green, and blue values to respective files
                    redWriter.write(String.format("%02x ", red));
                    greenWriter.write(String.format("%02x ", green));
                    blueWriter.write(String.format("%02x ", blue));
                }

                redWriter.write("\n");
                greenWriter.write("\n");
                blueWriter.write("\n");
            }

            redWriter.close();
            greenWriter.close();
            blueWriter.close();

            System.out.println("RGB values have been split and saved in separate files.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
