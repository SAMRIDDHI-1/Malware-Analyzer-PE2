
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class ExternalStorageChecker {

    public static void main(String[] args) {
        checkForExternalStorage();
    }

    public static void checkForExternalStorage() {
        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();

        for (Path root : roots) {
            try {
                FileStore store = Files.getFileStore(root);
                String type = store.type();
                
                // Here you can check if the type indicates it's an external storage
                // For example, on Windows, you might check for type "NTFS" or "FAT32"
                // On Linux, you might check for type "ext4" or "ntfs"

                // For simplicity, let's just print out the details for each file system
                System.out.println("File system: " + root);
                System.out.println("  Type: " + type);
                System.out.println("  Total space: " + store.getTotalSpace());
                System.out.println("  Usable space: " + store.getUsableSpace());
                System.out.println("  Unallocated space: " + store.getUnallocatedSpace());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
