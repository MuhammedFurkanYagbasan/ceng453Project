package pages;


import engine.GameEngine;
import gameConstants.StringConstants;
import gameConstants.UIConstants;
import gameobjects.Player;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * GamePage where the game is played
 */
public class GamePage {

    private static Scene gameScene;

    public static Pane gamePane;
    private static Label levelLabel;
    private static Label hpLabel;
    private static Label scoreLabel;

    /**
     * Creates the page
     */
    public static void create() {
        gamePane = new Pane();

        levelLabel = new Label();
        levelLabel.setLayoutX(UIConstants.WIDTH /2.0); levelLabel.setLayoutY(10); levelLabel.setStyle("-fx-font-weight: bold");

        try {
            Image image = new Image(UIConstants.BACKGROUND_IMAGE_PATH);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(UIConstants.HEIGHT);
            imageView.setFitWidth(UIConstants.WIDTH);

            gamePane.getChildren().add(imageView);
        } catch (Exception e) {
            System.out.println(e);
        }

        hpLabel = new Label(); scoreLabel = new Label();
        hpLabel.setLayoutX(10); hpLabel.setLayoutY(10); hpLabel.setStyle("-fx-font-weight: bold");
        scoreLabel.setLayoutX(UIConstants.WIDTH -80); scoreLabel.setLayoutY(10); scoreLabel.setStyle("-fx-font-weight: bold");

        gamePane.getChildren().addAll(levelLabel, hpLabel, scoreLabel);

        gameScene = new Scene(gamePane, UIConstants.WIDTH, UIConstants.HEIGHT);
        gameScene.setCursor(Cursor.NONE);
        gameScene.setOnMouseMoved(e-> {
            Player player = GameEngine.player;
            double x_trans, y_trans;
            if(e.getX()<player.getWidth()/2){
                x_trans = -player.getShip().getX();
            } else if(e.getX()> gameScene.getWidth()-player.getWidth()/2) {
                x_trans = gameScene.getWidth()-player.getShip().getX()-player.getShip().getWidth();
            } else {
                x_trans = e.getX()-player.getShip().getX()-player.getWidth()/2;
            }
            if(e.getY()> gameScene.getHeight()-player.getHeight()/2){
                y_trans = gameScene.getHeight()-player.getShip().getY()-player.getHeight();
            } else if(e.getY()< gameScene.getHeight()/2+player.getHeight()/2) {
                y_trans = gameScene.getHeight()/2-player.getShip().getY();
            } else {
                y_trans = e.getY()-player.getShip().getY()-player.getHeight()/2;
            }

            player.getShip().setTranslateX(x_trans);
            player.getShip().setTranslateY(y_trans);
        });
    }

    /**
     * Shows the page in the window
     */
    public static void show() {
        UIConstants.window.setScene(gameScene);
    }

    /**
     * Update Level Label
     * @param level >> level information to bu shown
     */
    public static void updateLevelLabel(int level) {
        levelLabel.setText("Level " + level);
    }

    /**
     * Update Health Label
     * @param health >> health information to be shown
     */
    public static void updateHpLabel(int health) {
        hpLabel.setText(StringConstants.HEALTH_LABEL + health);
    }

    /**
     * Update the Score
     * @param score >> SCore information to be shown
     */
    public static void updateScoreLabel(int score) {
        scoreLabel.setText(StringConstants.SCORE_LABEL + score);
    }

}
