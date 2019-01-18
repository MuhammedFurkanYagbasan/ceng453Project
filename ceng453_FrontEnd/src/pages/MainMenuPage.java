package pages;

import engine.GameEngine;
import gameConstants.StringConstants;
import gameConstants.UIConstants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The page that is shown after the user logs-in
 */
public class MainMenuPage {

    private static Scene mainMenuScene;

    /**
     * Create the page
     */
    public static void create() {
        Button weeklyBoard = new Button();
        weeklyBoard.setText(StringConstants.LEADERBOARD_WEEKLY_TITLE);
        weeklyBoard.setOnAction(e -> {
            LeaderBoardPage.show(true);
        });

        Button allLeaderBoard = new Button();
        allLeaderBoard.setText(StringConstants.LEADERBOARD_ALLTIME_TITLE);
        allLeaderBoard.setOnAction(e -> {
            LeaderBoardPage.show(false);
        });


        Button newGame = new Button();
        newGame.setText(StringConstants.NEW_GAME_LABEL);
        newGame.setOnAction(e -> {
            GamePage.show();
            GameEngine.start();
        });

        Button backButton = new Button();
        backButton.setText(StringConstants.BACK_BUTTON_LABEL);
        backButton.setOnAction(e-> {
            LogInPage.show();
        });

        VBox vb = new VBox();
        vb.getChildren().addAll( allLeaderBoard, weeklyBoard,newGame,backButton);
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);

        mainMenuScene = new Scene(vb, UIConstants.WIDTH, UIConstants.HEIGHT);
    }

    /**
     * Shows the page in the window
     */
    public static void show() {
        UIConstants.window.setScene(mainMenuScene);
    }

}
