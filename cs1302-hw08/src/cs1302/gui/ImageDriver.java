package cs1302.gui;

import javafx.application.Application;

/**
 * This is driver class for launch program.
 */
public class ImageDriver {

    public static void main(String[] args) {
        try {
            Application.launch(ImageApp.class, args);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Likely due to X11 timeout. Logout and log back in...");
            System.exit(1);
        } // try

    } // mian


} // Driver
