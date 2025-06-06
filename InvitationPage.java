package application;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// Make sure to import your User class, e.g.:
// import models.User;

/**
 * InvitationPage class represents the page where an admin can generate an invitation code.
 * The invitation code is displayed upon clicking a button.
 */
public class InvitationPage {

    /**
     * Displays the Invite Page in the provided primary stage.
     *
     * @param databaseHelper An instance of DatabaseHelper to handle database operations.
     * @param primaryStage   The primary stage where the scene will be displayed.
     * @param user           The current User instance to pass to the WelcomeLoginPage.
     */
    public void show(DatabaseHelper databaseHelper, Stage primaryStage, User user) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
        
        // Label to display the title of the page
        Label userLabel = new Label("Invite");
        userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Button to generate the invitation code
        Button showCodeButton = new Button("Generate Invitation Code");
        
        // Label to display the generated invitation code
        Label inviteCodeLabel = new Label("");
        inviteCodeLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");
        
        showCodeButton.setOnAction(a -> {
            // Generate the invitation code using the databaseHelper and set it to the label
            String invitationCode = databaseHelper.generateInvitationCode();
            inviteCodeLabel.setText(invitationCode);
        });
        
        // Back button to navigate to the WelcomeLoginPage
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            WelcomeLoginPage welcomePage = new WelcomeLoginPage(databaseHelper);
            welcomePage.show(primaryStage, user);
        });
        
        layout.getChildren().addAll(userLabel, showCodeButton, inviteCodeLabel, backButton);
        Scene inviteScene = new Scene(layout, 800, 400);
        
        // Set the scene to primary stage
        primaryStage.setScene(inviteScene);
        primaryStage.setTitle("Invite Page");
    }
}
