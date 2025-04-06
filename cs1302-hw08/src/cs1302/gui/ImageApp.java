package cs1302.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import javafx.scene.layout.TilePane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

/**
 * A basic JavaFX 8 program which takes a user specified URL and loads it
 * into an {@code ImageView}.
 *
 */
public class ImageApp extends Application {

    Stage stage;
    Scene scene;
    TabPane tabPane;
    TilePane tile;

    /** A default image which loads when the application starts. */
    protected static final String DEFAULT_IMG =
        "http://cobweb.cs.uga.edu/~mec/cs1302/gui/default.png";

    /** Default height and width for Images. */
    protected static final int DEF_HEIGHT = 500;
    protected static final int DEF_WIDTH = 500;

    /**
     * The init method in Javafx life cycle.
     */
    public void init() {
        tabPane = new TabPane();
        for (int i = 0; i < 4; i++) {
            Tab tab = new Tab();
            ImageLoader loader = new ImageLoader();
            tab.setContent(loader);
            tabPane.getTabs().addAll(tab);
        }
    }

    /**
     * The entry point for our image viewer application.
     *
     * @param stage A reference to the stage object (window) created by the system.
     */
    public void start(Stage stage) {
        this.stage = stage;
        scene = new Scene(tabPane);

        // Set up the stage and set it to be visible
        // stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.setTitle("1302 Image Viewer!");
        this.stage.sizeToScene();
        this.stage.show();

    } // start

} // ImageApp
