package pages;


import entries.LeaderBoardEnrty;
import gameConstants.StringConstants;
import gameConstants.UIConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import webservice.WebServiceOperations;
import java.util.List;


/**
 * Leader board page where the learder boards exist
 */
public class LeaderBoardPage {

    private static Scene leaderBoardScene;

    private static TableView leaderBoardTable;
    private static Label leaderBoardTitle;

    /**
     * Creates the page
     */
    public static void create() {

        Button back = new Button();
        back.setText(StringConstants.BACK_BUTTON_LABEL);
        back.setOnAction(e->{
            MainMenuPage.show();
        });

        TableColumn rankCol = new TableColumn(StringConstants.LEADERBOARD_COLUMN_TITLE_RANK);
        TableColumn nickCol = new TableColumn(StringConstants.LEADERBOARD_COLUMN_TITLE_NICKNAME);
        TableColumn scoreCol = new TableColumn(StringConstants.LEADERBOARD_COLUMN_TITLE_SCORE);
        rankCol.setResizable(false);
        nickCol.setResizable(false);
        scoreCol.setResizable(false);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.setAlignment(Pos.CENTER);

        leaderBoardTable = new TableView();
        leaderBoardTable.setEditable(false);
        leaderBoardTitle = new Label();
        leaderBoardTitle.setStyle("-fx-font-weight: bold");
        rankCol.prefWidthProperty().bind(leaderBoardTable.widthProperty().multiply(0.2));
        nickCol.prefWidthProperty().bind(leaderBoardTable.widthProperty().multiply(0.4));
        scoreCol.prefWidthProperty().bind(leaderBoardTable.widthProperty().multiply(0.4));
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nickCol.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        leaderBoardTable.getColumns().addAll(rankCol, nickCol, scoreCol);

        vbox.getChildren().addAll(leaderBoardTitle ,leaderBoardTable,back);
//        ((Group) leaderBoardScene.getRoot()).getChildren().addAll(vbox);
        leaderBoardScene = new Scene(vbox, UIConstants.WIDTH, UIConstants.HEIGHT);

    }

    /**
     * Shows the page
     * @param weekly >> indicates if the leader board is weakly or alltime
     */
    public static void show(boolean weekly) {
        List<LeaderBoardEnrty> entries;
        if(weekly) {
            leaderBoardTitle.setText(StringConstants.LEADERBOARD_WEEKLY_TITLE);
            entries = WebServiceOperations.getWeeklyLeaderBoardEntries();
        }
        else{
            leaderBoardTitle.setText(StringConstants.LEADERBOARD_ALLTIME_TITLE);
            entries = WebServiceOperations.getAllTimesLeaderBoardEntries();
        }
        leaderBoardTable.getItems().clear();
        for (LeaderBoardEnrty entry : entries) {
            leaderBoardTable.getItems().add(entry);
        }
        UIConstants.window.setScene(leaderBoardScene);
    }

}
