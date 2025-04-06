package cs1302.gallery;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents an iTunes Gallery App.
 */
public class GalleryApp extends Application {

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object

    private Stage stage;
    private Scene scene;
    private HBox root;
    private static ImageView [][] myImView;
    private Button mButtonSearch;
    private Button mButtonPlay;
    private Text mTextStatic = new Text("Search");
    private TextField mSearchText = new TextField();
    private ComboBox<String> mComboBoxType = new ComboBox<String>();
    private Text mTextStatus = new Text("Type in term, select a media type,then click the button.");
    private ProgressBar mProgressBarStatus = new ProgressBar();
    private Text mTextProgressBar = new Text("Images provided by itunes Search API.");

    private String mStringType;
    private String mStringTerm;
    private static boolean keepPlaying = false;

    private static LinkedList<String> mResponseDistList = new LinkedList<String>();

    /**
     * This is Itunes Response.
     */
    private static class ItunesResponse {
        int resultCount;         // package private visibility is intentional
        ItunesResult[] results;  // if you make these, private, then add getters
    } // ItunesResponse

    /**
     * Represents a result in a response from the iTunes Search API. This is
     * used by Gson to create an object from the JSON response body.
     *
     * <pre>
     * {
     *   "wrapperType": "track",
     *   "kind": "song",
     *   ...,
     *   "artworkUrl100": "https://.../source/100x100bb.jpg",
     *   ...
     * }
     * </pre>
     */
    private static class ItunesResult {
        String wrapperType;   // package private visibility is intentional
        String kind;          // if you make these, private, then add getters
        String artworkUrl100; // we omit variables for data we're not interested in
    } // ItunesResult


    private static final String ITUNES_API = "https://itunes.apple.com/search";

    /**
     * This is initialization method.
     */
    public void initialization() {
        this.stage = null;
        this.scene = null;
        this.root = new HBox();
        this.myImView = new ImageView[4][5];
        this.mButtonSearch = new Button("Get Images");
        this.mButtonSearch.setPrefSize(200, 20);
        this.mButtonPlay = new Button("Play");
        this.mButtonPlay.setPrefSize(100, 20);
        this.mProgressBarStatus.setPrefSize(500, 20);

        this.mComboBoxType.getItems().add("music");
        this.mComboBoxType.getItems().add("movie");
        this.mComboBoxType.getItems().add("musicVideo");
        this.mComboBoxType.getItems().add("audiobook");
        this.mComboBoxType.getItems().add("shortFilm");
        this.mComboBoxType.getItems().add("tvShow");
        this.mComboBoxType.getItems().add("software");
        this.mComboBoxType.getItems().add("ebook");
        this.mComboBoxType.getItems().add("all");
        this.mComboBoxType.setValue("music");
    }

    /**
     * Constructs a {@code GalleryApp} object}.
     */
    public GalleryApp() {
        initialization();
        this.mButtonPlay.setOnAction(event -> {
            if (this.mButtonPlay.getText().equals("Play")) {
                if (GalleryApp.mResponseDistList.size() >= 21) {
                    GalleryApp.keepPlaying = true;
                    this.mButtonPlay.setText("Pause");
                    Runnable chaneImageRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (GalleryApp.keepPlaying) {
                                    // TODO Auto-generated method stub
                                    Random rand = new Random();
                                    int selRow = rand.nextInt(4);
                                    int selCol = rand.nextInt(5);
                                    int temp = rand.nextInt(GalleryApp.mResponseDistList.size());
                                    try {
                                        InputStream stream = new FileInputStream(temp + ".jpg");
                                        Image image = new Image(stream);
                                        GalleryApp.myImView[selRow][selCol].setImage(image);
                                    } catch (FileNotFoundException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.scheduleAtFixedRate(chaneImageRunnable, 0, 2, TimeUnit.SECONDS);
                }
            } else {
                GalleryApp.keepPlaying = false;
                this.mButtonPlay.setText("Play");
            }
        });
        this.mButtonSearch.setOnAction(event -> {
            this.mResponseDistList.clear();
            this.mButtonSearch.setDisable(true);
            this.mStringType = this.mComboBoxType.getValue();
            this.mStringTerm = this.mSearchText.getText();
            //this.mStringTerm = URLEncoder.encode(this.mStringTerm, StandardCharsets.UTF_8);
            //System.out.println("Get strings -- " + this.mStringTerm + "  " + this.mStringType);
            this.mTextStatus.setText("Get strings -- " +
                this.mStringTerm + "  " + this.mStringType);
            this.mTextStatus.setText("Getting images...");
            String uri = "";
            // form URI
            String term = URLEncoder.encode(this.mStringTerm, StandardCharsets.UTF_8);
            String media = URLEncoder.encode(this.mStringType, StandardCharsets.UTF_8);
            String limit = URLEncoder.encode("200", StandardCharsets.UTF_8);
            String query = String.format("?term=%s&media=%s&limit=%s", term, media, limit);
            uri = ITUNES_API + query;
            ItunesResponse itunesResponse = getResponse(query);
            updateResponseDistList(itunesResponse);
            imageOutput(uri);
            this.mButtonSearch.setDisable(false);
            this.mTextStatus.setText(uri);
        });
        this.mSearchText.setPrefSize(300,20);
    } // GalleryApp

    /**
     * This is image out put.
     *
     * @param uri string
     */
    public void imageOutput(String uri) {
        if (this.mResponseDistList.size() >= 21) {
            Platform.runLater(() -> this.mProgressBarStatus.setProgress(0));

            for (int k = 0; k < this.mResponseDistList.size(); k++) {
                int temp = k;
                URL url;
                try {
                    url = new URL(this.mResponseDistList.get(k));
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] theResponse = out.toByteArray();

                    FileOutputStream fos = new FileOutputStream(k + ".jpg");
                    fos.write(theResponse);
                    fos.close();
                    Platform.runLater(() -> this.mProgressBarStatus.setProgress(1.0 * temp
                        / this.mResponseDistList.size()));
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            Platform.runLater(() -> this.mProgressBarStatus.setProgress(1));

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    String result = this.mResponseDistList.get(i * 5 + j);
                    this.myImView[i][j].setImage(new Image(result));
                }
            }
        } else {
            Alert mAlert = new Alert(AlertType.ERROR, uri);
            mAlert.show();
        }
    }

    /**
     * This is to get the response.
     *
     * @param query string
     * @return itunesResponse itunesresponse
     */
    public ItunesResponse getResponse(String query) {
        String uri = "";
        uri = ITUNES_API + query;
        // build request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .build();

        ItunesResponse itunesResponse = null;
        // send request / receive response in the form of a String
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());

            // ensure the request is okay
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            // get request body (the content we requested)
            String jsonString = response.body();
            System.out.println("********** RAW JSON STRING: **********");
            System.out.println(jsonString.trim());
            // parse the JSON-formatted string using GSON
            itunesResponse = GSON.fromJson(jsonString, GalleryApp.ItunesResponse.class);
         // print info about the response
            return itunesResponse;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return itunesResponse;


    }

    /**
     * This is update response distlist.
     *
     * @param itunesResponse ItunesResponse
     */
    public void updateResponseDistList(ItunesResponse itunesResponse) {
        System.out.printf("resultCount = %s\n", itunesResponse.resultCount);
        for (int i = 0; i < itunesResponse.results.length; i++) {

            System.out.printf("itunesResponse.results[%d]:\n", i);
            ItunesResult result = itunesResponse.results[i];
            System.out.printf(" - wrapperType = %s\n", result.wrapperType);
            System.out.printf(" - kind = %s\n", result.kind);
            System.out.printf(" - artworkUrl100 = %s\n", result.artworkUrl100);

            if (!this.mResponseDistList.contains(result.artworkUrl100)) {
                this.mResponseDistList.add(result.artworkUrl100);
            }

        } // for
    }

    /** {@inheritDoc} */
    @Override
    public void init() {
        // feel free to modify this method
        System.out.println("init() called");
    } // init

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:linear-gradient(to bottom right, derive(goldenrod, 20%), derive(goldenrod, -40%));");//ImageView iv1 = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Ironman-icon.png"));  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        //ImageView iv2 = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Starwars-Stormtrooper-icon.png"));
        //iv1.relocate(10, 10);
        //iv2.relocate(80, 60);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                this.myImView[i][j] = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Ironman-icon.png"));
                this.myImView[i][j].relocate(j * 150, i * 150 + 50);
            }
        }
        for (int i = 0; i < 4; i ++) {
            pane.getChildren().addAll(myImView[i]);

        }

        this.mButtonPlay.relocate(0, 0);
        this.mTextStatic.relocate(110, 0);
        this.mSearchText.relocate(170, 0);
        this.mComboBoxType.relocate(480, 0);
        this.mButtonSearch.relocate(590, 0);
        this.mTextStatus.relocate(0, 30);
        this.mProgressBarStatus.relocate(0, 650);
        this.mTextProgressBar.relocate(510, 650);


        pane.getChildren().addAll(this.mButtonPlay);
        pane.getChildren().addAll(this.mTextStatic);
        pane.getChildren().addAll(this.mSearchText);
        pane.getChildren().addAll(this.mComboBoxType);
        pane.getChildren().addAll(this.mButtonSearch);
        pane.getChildren().addAll(this.mTextStatus);
        pane.getChildren().addAll(this.mProgressBarStatus);
        pane.getChildren().addAll(this.mTextProgressBar);


        this.scene = new Scene(pane);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("GalleryApp!");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();



        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    } // start

    /** {@inheritDoc} */
    @Override
    public void stop() {
        // feel free to modify this method
        System.out.println("stop() called");
    } // stop

    /**
     * This is image view that for get my view.
     *
     * @return myImView my image view
     */
    public ImageView[][] getMyImView() {
        return myImView;
    }

    /**
     * This is set my image view.
     *
     * @param myImView set myimageview
     */
    public void setMyImView(ImageView [][]myImView) {
        this.myImView = myImView;
    }

} // GalleryApp
