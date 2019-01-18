import gameConstants.StringConstants;
import gameConstants.UIConstants;
import javafx.application.Application;
import javafx.stage.Stage;
import pages.*;

/**
 * APPLICATION START
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        UIConstants.window = primaryStage;
        UIConstants.window.setTitle(StringConstants.GAME_NAME);
        UIConstants.window.setResizable(false);

        createPages();

        LogInPage.show();

    }

    private void createPages() {
        LogInPage.create();
        MainMenuPage.create();
        LeaderBoardPage.create();
        GamePage.create();
        ResultPage.create();
        MultiplayerWaitingPage.create();
    }

}