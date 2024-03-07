import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ExternalStorageMonitor {

    private static int sessionCounter = 0;
    public static void sessionDetails(){
        while (true) {
            //ExternalStorageCheckerGUI obj = new ExternalStorageCheckerGUI();
            //obj.display();

            sessionCounter++;
            System.out.println("--------New session started--------(" + sessionCounter + ")");
            sessionCounter++;
            checkForExternalStorage();
            System.out.println("--------New session ended--------");
            // Sleep for some time before checking again
            try {
                Thread.sleep(5000); // Check every 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       sessionDetails();
    }

    public static void checkForExternalStorage() {
        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Using an iterator to iterate over roots
        Iterator<Path> iterator = roots.iterator();
        for (int i = 0; i < roots.spliterator().getExactSizeIfKnown(); i++) {
            Path root = iterator.next();
            try {
                FileStore store = Files.getFileStore(root);
                String type = store.type();
                // Get current timestamp
                String timestamp = dateFormat.format(new Date());
                

                // on Windows, we might check for type "NTFS" or "FAT32"
                // On Linux, we might check for type "ext4" or "ntfs"

                // For simplicity, let's just print out the details for each file system
                System.out.println("Time: " + timestamp);
                System.out.println("File system: " + root);
                System.out.println("  Type: " + type);
                System.out.println("  Total space: " + store.getTotalSpace());
                System.out.println("  Usable space: " + store.getUsableSpace());
                System.out.println("  Unallocated space: " + store.getUnallocatedSpace());
            } catch (Exception e) {
                // Handle exceptions here
                e.printStackTrace();
            }
        }
    }
}
