package alertbox;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Display function generates a window with given title and message.
 * We use this function three times which are "You have successfully registered" message,
 * "Someone else has this nickname" warning and "The name or password incorrect" warning in the project.
 * You can see GUI Documentation file for detail.
 */
public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button button = new Button("OK");
        button.setOnAction(e -> window.close());

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }

}
