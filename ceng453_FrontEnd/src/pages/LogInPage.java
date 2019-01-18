package pages;


import alertbox.AlertBox;
import gameConstants.StringConstants;
import gameConstants.UIConstants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import webservice.WebServiceOperations;

/**
 * Log-in page where the user logs-in or registers
 */
public class LogInPage {

    private static Scene logInScene;

    private static final TextField nameTextField = new TextField();
    private static final PasswordField passwordTextField = new PasswordField();

    /**
     * Creates the page
     */
    public static void create() {
        Button btn = new Button();
        btn.setText(StringConstants.REGISTER_REGISTER_LABEL);
        btn.setOnAction(e->{
            boolean b = WebServiceOperations.isNickNameExists(nameTextField.getText());
            System.out.println(b);
            if(b){
                // nickname exist, should reenter nickname
                AlertBox.display(StringConstants.ALERT_BOX_TITLE_ALERT,StringConstants.ALERT_NICKNAME_EXIST);
            } else {
                // nickname is unique, can register
                WebServiceOperations.registerPlayer(nameTextField.getText(), passwordTextField.getText());
                AlertBox.display(StringConstants.ALERT_BOX_TITLE_SUCCESS,StringConstants.REGISTERATION_SUCCESSFUL);
            }
        });

        Button logInButton = new Button();
        logInButton.setText(StringConstants.LOGIN_BUTTON_LABEL);
        logInButton.setOnAction(e->{

            if(WebServiceOperations.canLogIn(nameTextField.getText(), passwordTextField.getText())) {
                MainMenuPage.show();
            } else {
                AlertBox.display(StringConstants.ALERT_BOX_TITLE_ALERT,StringConstants.LOGIN_WARNING);
            }
        });

        Label label1 = new Label(StringConstants.LOGIN_NAME_LABEL);
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, nameTextField);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        Label label2 = new Label(StringConstants.LOGIN_PASSWORD_LABEL);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(label2, passwordTextField);
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.CENTER);

        VBox vb = new VBox();
        vb.getChildren().addAll(hb , hb2, logInButton, btn);
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);

        logInScene = new Scene(vb, UIConstants.WIDTH, UIConstants.HEIGHT);

        UIConstants.window.show();
    }

    /**
     * Shows the page in the window
     */
    public static void show() {
        UIConstants.window.setScene(logInScene);
    }

    static String getNickname() {
        return nameTextField.getText();
    }
}
