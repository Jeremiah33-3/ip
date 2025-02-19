import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import ui.HeyJudy;
import ui.UI;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private HeyJudy judyBot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DefaultUser.png"));
    private Image judyImage = new Image(this.getClass().getResourceAsStream("/images/HeyJudy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String greeting = UI.greetUser();
        dialogContainer.getChildren().add(DialogBox.getJudyDialog(greeting, judyImage));
    }

    /** Injects the Duke instance */
    public void setBot(HeyJudy judy) {
        judyBot = judy;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = judyBot.readCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJudyDialog(response, judyImage)
        );
        userInput.clear();

        // Exit the application if the user inputs "bye"
        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}
