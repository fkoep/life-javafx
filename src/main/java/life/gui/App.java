package life.gui;

import life.simulation.SimpleWorld;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage controlStage) throws IOException {
        /* initialize world */
        WorldPane world = new WorldPane(new SimpleWorld(400, 300));
        Group worldRoot = new Group();
        worldRoot.getChildren().add(world);

        Scene worldScene = new Scene(worldRoot, 800, 600);
        world.prefWidthProperty().bind(worldScene.widthProperty());
        world.prefHeightProperty().bind(worldScene.heightProperty());

        Stage worldStage = new Stage();
        worldStage.setTitle("LIFE World");
        worldStage.setScene(worldScene);
        worldStage.show();


        /* initialize control */
        WorldControl control = new WorldControl(world);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorldControl.fxml"));
        loader.setController(control);
        Parent root = loader.load();

        Scene controlScene = new Scene(root);
        controlStage.setTitle("LIFE Controls");
        controlStage.setScene(controlScene);
        controlStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
