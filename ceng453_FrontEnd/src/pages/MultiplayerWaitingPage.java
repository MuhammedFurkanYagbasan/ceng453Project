package pages;


import gameConstants.StringConstants;
import gameConstants.UIConstants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The page that is shown while waiting the other player in multiplayer level
 */
public class MultiplayerWaitingPage {

    private static Scene waitingRoomScene;

    private static Label label;

    /**
     * Creates the page
     */
    public static void create() {

        label = new Label();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        vBox.getChildren().add(label);

        waitingRoomScene = new Scene(vBox, UIConstants.WIDTH, UIConstants.HEIGHT);

    }

    /**
     * Shows the page in the window
     */
    public static void show() {
        label.setText(StringConstants.CONNECTING_TO_SERVER);
        UIConstants.window.setScene(waitingRoomScene);
    }

    public static void changeWaitingStatus(String message) {
        label.setText(message);
    }

}
