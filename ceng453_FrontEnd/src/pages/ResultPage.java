package pages;

import engine.GameEngine;
import gameConstants.StringConstants;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import webservice.WebServiceOperations;
import gameConstants.UIConstants;

/**
 * The page that is shown when game finishes
 */
public class ResultPage {

    private static Scene resultScene;

    private static Label label;

    /**
     * Creates the page
     */
    public static void create() {

        label = new Label();
        label.setFont(Font.font(UIConstants.RESULT_PAGE_FONT_SIZE));
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        Button button = new Button(StringConstants.MAINPAGE_BUTTON_LABEL);
        button.setOnAction(e -> {
            MainMenuPage.show();
        });

        vBox.getChildren().addAll(label, button);

        resultScene = new Scene(vBox, UIConstants.WIDTH, UIConstants.HEIGHT);
        resultScene.setCursor(Cursor.DEFAULT);

    }

    /**
     * Shows the page in the window
     * @param message >> message shown in the page
     */
    public static void show(String message) {
        label.setText(message);
        WebServiceOperations.addToLeaderBoard(GameEngine.player.getScore(), LogInPage.getNickname());
        UIConstants.window.setScene(resultScene);
        GameEngine.clearGame();
    }

}
