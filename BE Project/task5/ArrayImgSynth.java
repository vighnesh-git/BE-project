import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ArrayImgSynth {

    public static void main(String[] args) throws IOException {

        // Read the input file
        File inputFile = new File("image_array-text.txt");
        Scanner reader = new Scanner(inputFile);

        // Skip any unwanted text-strings at the end of the file
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.startsWith("//")) {
                continue;
            }

            // Get the dimensions of the image
            int width = reader.nextInt();
            int height = reader.nextInt();

            // Create the output file
            File outputFile = new File("abc_x_y.bmp");
            FileOutputStream writer = new FileOutputStream(outputFile);

            // Write the header of the BMP file
            writer.write(new byte[]{0x42, 0x4D}); // Signature
            writer.write(new byte[]{0x7A, 0x00, 0x00, 0x00}); // File size
            writer.write(new byte[]{0x00, 0x00, 0x00, 0x00}); // Reserved
            writer.write(new byte[]{0x36, 0x00, 0x00, 0x00}); // Offset

            // Write the image data
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixelValue = reader.nextInt();
                    writer.write(new byte[]{(byte) pixelValue, (byte) pixelValue, (byte) pixelValue});
                }
            }

            // Close the files
            reader.close();
            writer.close();
            break;
        }
    }
}
