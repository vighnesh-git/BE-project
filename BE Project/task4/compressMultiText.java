import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class compressMultiText {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java compressMultiText <output_file.zip> <file1> <file2> <file3> ...");
            return;
        }

        String zipFileName = args[0];
        File zipFile = new File(zipFileName);

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (int i = 1; i < args.length; i++) {
                String fileName = args[i];
                File file = new File(fileName);

                if (!file.exists()) {
                    System.out.println("File not found: " + fileName);
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                    System.out.println("Added: " + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Zip file created: " + zipFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
