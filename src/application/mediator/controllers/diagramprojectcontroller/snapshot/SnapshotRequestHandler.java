package application.mediator.controllers.diagramprojectcontroller.snapshot;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SnapshotRequestHandler implements IHandler {
    public Void handle(Request request) {
        SnapshotRequest newRequest = (SnapshotRequest) request;
        Node contentRoot = newRequest.getContentRoot();

        WritableImage snapshot = contentRoot.snapshot(new SnapshotParameters(), null);

        String userHome = System.getProperty("user.home");
        String directoryPath = userHome + File.separator + "Snapshots";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String filename = "Snapshot_" + timestamp + ".png";
        File outputFile = new File(directory, filename);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputFile);
            System.out.println("Snapshot saved to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving snapshot: " + e.getMessage());
        }
        return null;
    }
}
