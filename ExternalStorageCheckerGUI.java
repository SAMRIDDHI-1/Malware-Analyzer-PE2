import javax.swing.*;
import java.awt.*;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExternalStorageCheckerGUI {
    private JFrame frame;
    private JTextArea textArea;

    public ExternalStorageCheckerGUI() {
        frame = new JFrame("External Storage Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set background color of JFrame
        frame.getContentPane().setBackground(Color.BLACK);

        textArea = new JTextArea();
        textArea.setEditable(false);

        // Set font color of JTextArea
        textArea.setForeground(Color.YELLOW);

        // Set background color of JTextArea
        textArea.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Set background color of JScrollPane
        scrollPane.getViewport().setBackground(Color.BLACK);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void checkForExternalStorage() {
        StringBuilder result = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();

        for (Path root : roots) {
            try {
                FileStore store = Files.getFileStore(root);
                String type = store.type();
                String timestamp = dateFormat.format(new Date());

                // Append the details to the result string
                result.append("Time: " + timestamp);
                result.append("\nFile system: ").append(root).append("\n");
                result.append("  Type: ").append(type).append("\n");
                result.append("  Total space: ").append(store.getTotalSpace() / (1024.0 * 1024.0 * 1024.0))
                        .append(" GB\n");
                result.append("  Used space: ").append((store.getTotalSpace() - store.getUsableSpace()) / (1024.0 * 1024.0 * 1024.0))
                        .append(" GB\n");
                result.append("  Free: ")
                        .append((store.getUsableSpace()) / (1024.0 * 1024.0 * 1024.0))
                        .append(" GB\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        textArea.setText(result.toString());
    }

    public static void main(String[] args) {
        ExternalStorageCheckerGUI gui = new ExternalStorageCheckerGUI();
        gui.display();
        while (true) {
            gui.checkForExternalStorage();
            try {
                Thread.sleep(500); // Check every 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
