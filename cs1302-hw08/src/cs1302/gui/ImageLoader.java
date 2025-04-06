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
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

/**
 * An sub graph for two VBox to reduce redundency.
 */
public class ImageLoader extends VBox {
/** A default image which loads when the application starts. */
    private static final String DEFAULT_IMG =
        "http://cobweb.cs.uga.edu/~mec/cs1302/gui/default.png";

    /** Default height and width for Images. */
    private static final int DEF_HEIGHT = 500;
    private static final int DEF_WIDTH = 500;

/** The root container for the application scene graph. */
    VBox vbox;

    /** The container for the url textfield and the load image button. */
    HBox urlLayer;
    TextField urlField;
    Button loadImage;

    /** The picture will be inserted. */
    Image image;
    /** The container for the loaded image. */
    ImageView imgView;

    Label search;

    HBox resizeBar;
    Button enlarge;
    Boolean isTooBig;
    Button shrink;
    Button reset;


    /**
     * Initialize all the Node in the sub graph.
     */
    public ImageLoader () {
        super();
        urlLayer = new HBox (10);
        search = new Label("Search: ");
        urlField = new TextField("https://");
        loadImage = new Button("Load");
        loadNewImage();
        urlLayer.getChildren().addAll(search, urlField, loadImage);
        urlLayer.setHgrow(urlField, Priority.ALWAYS);
        urlLayer.setPadding(new Insets(10));
        urlLayer.setAlignment(Pos.CENTER_LEFT);

        image = new Image (DEFAULT_IMG);
        imgView = new ImageView (image);
        imgView.setFitHeight(image.getHeight());
        imgView.setPreserveRatio(true);

        resizeBar = new HBox(15);
        enlarge = new Button("Enlarge");
        isTooBig();
        enlarge.setOnAction(event -> resizeImage(10));
        shrink = new Button("Shrink");
        shrink.setOnAction(event -> resizeImage(-10));
        reset = new Button("Reset");
        reset.setOnAction(event -> resizeImage(0));
        resizeBar.getChildren().addAll(enlarge, shrink, reset);

        this.getChildren().addAll(urlLayer, imgView, resizeBar); // this refers to the VBox

    }


    /**
     * Check if the default/newly-reloaded exceed the size limit, if yes, disable "enlarge"
     * button; if no, reable "enlarge" button.
     */
    private void isTooBig() {
        isTooBig = (imgView.getImage().getHeight() >= 500) ? true : false;
        enlarge.setDisable(isTooBig);
    }

    /**
     * Changes the size of the current image.
     * @param size the size that will be adjusted from the current image
     */
    private void resizeImage(int size) {
        double currentHeight = imgView.getFitHeight();
        if (size == 0) {
            currentHeight = imgView.getImage().getHeight();
        }
        imgView.setFitHeight(currentHeight + size);

        // when the height of "ImageView" is set to <= 0, the visual height will be adjusted back to
        // the "Image" height(the internal height will not). So, we need to keep track of the
        // internal height of "ImageView" to make sure the "Buttons" function properly.

        // make sure to use "ImageView.getImage().getHeight()" to get the "Image" height b/c if a
        // image is being loaded, the original "Image" variable is not longer relevant.
        if (imgView.getFitHeight() <= 0) {
            imgView.setFitHeight(imgView.getImage().getHeight());
        }

        isTooBig();

        imgView.setPreserveRatio(true);

    }

    /**
     * reload {@code imgView} with a new image.
     */
    private void loadNewImage() {
        loadImage.setOnAction(event -> {
            try {
                Image newImage = new Image(urlField.getText());
                if (newImage.isError()) {
                    // at this point, we can confirm that the link is a URL format but it not able
                    // to load
                    throw newImage.getException();
                }
                imgView.setImage(newImage);
                // must adjust the internal height to be consistent with the new "Image" height here
                imgView.setFitHeight(newImage.getHeight());
                isTooBig();
            } catch (Throwable e) {
                alertError(e);
            }
        });
    }


    /**
     * An error message prompts.
     * @param exception exception that is thrown by the invalid text input
     */
    private void alertError(Throwable exception) {
        TextArea textArea = new TextArea(exception.toString());
        textArea.setEditable(false);
        Alert errorPrompt = new Alert(AlertType.ERROR);
        errorPrompt.getDialogPane().setContent(textArea);
        errorPrompt.setResizable(true);
        errorPrompt.showAndWait();
    }

}
