import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DecompressFiles {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java DecompressFiles <zip_file_path> <destination_directory>");
            return;
        }

        String zipFilePath = args[0];
        String destinationDirectory = args[1];

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String destFilePath = destinationDirectory + File.separator + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(destFilePath)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    System.out.println("Extracted: " + destFilePath);
                }
            }
        } catch (IOException e) {
            System.err.println("Error decompressing files: " + e.getMessage());
        }
    }
}
