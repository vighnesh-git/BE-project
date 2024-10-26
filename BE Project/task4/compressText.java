import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class compressText {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java compressText <input_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = inputFile.substring(0, inputFile.lastIndexOf(".")) + ".zip";

        try {
            compressTextMethod(inputFile, outputFile);
            System.out.println("File '" + inputFile + "' compressed to '" + outputFile + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compressTextMethod(String inputFile, String outputFile) throws IOException {
        try (FileInputStream in = new FileInputStream(inputFile);
             FileOutputStream out = new FileOutputStream(outputFile);
             ZipOutputStream zipOut = new ZipOutputStream(out)) {

            ZipEntry entry = new ZipEntry(inputFile);
            zipOut.putNextEntry(entry);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, bytesRead);
            }

            zipOut.closeEntry();
        }
    }
}
