import java.io.File;

public class readDirContent {
    public static void main(String[] args) {
        // Check if a command-line argument is provided
        if (args.length != 1) {
            System.out.println("Usage: java readDirContent <directory_path>");
            return;
        }

        // Get the directory path from the command-line argument
        String directoryPath = args[0];
        
        // Create a File object for the specified directory
        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path or directory does not exist.");
            return;
        }

        // List the files and directories in the specified directory
        String[] contents = directory.list();

        // Display the contents of the directory
        System.out.println("Contents of the directory " + directoryPath + ":");
        for (String content : contents) {
            System.out.println(content);
        }
    }

    public static File[] getFilesWithExtension(String directoryPath, String string) {
        return null;
    }
}
